package de.traveltogether.settings.profilesettings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Person;
import de.traveltogether.servercommunication.HashFactory;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;

public class ProfileSettingsActivity extends AppCompatActivity {
    private IProfileSettingsPresenter presenter;
    private ProgressDialog progressDialog;
    private Menu optionsMenu;
    private TextView email;
    private TextView name;
    private TextView errorText;
    private String salt;
    private String newHash;
    private View passwordDialogView;
    private AlertDialog passwordDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        getSupportActionBar().setTitle("Mein Profil");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        presenter = new ProfileSettingsPresenter(this);
        email = (EditText) findViewById(R.id.activity_profile_settings_mail);
        name = (EditText) findViewById(R.id.activity_profile_settings_name);
    }

    @Override
    protected void onStart() {
        progressDialog = ProgressDialog.show(this, "", "Daten werden geladen...", true);
        super.onStart();
        presenter.onGetProfileInfos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_settings, menu);
        hideOption(R.id.action_save);
        return super.onCreateOptionsMenu(menu);
    }

    private void hideOption(int id) {
        MenuItem item = optionsMenu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = optionsMenu.findItem(id);
        item.setVisible(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_profile:
                showOption(R.id.action_save);
                hideOption(R.id.change_profile);
                //hideOption(R.id.change_pw);
                email.setEnabled(true);
                name.setEnabled(true);
                break;
            case R.id.action_save:
                presenter.onUpdateProfileInfos(new Person(StaticData.getUserId(), email.getText().toString(), name.getText().toString()));
                progressDialog.show();
                break;
            case R.id.change_pw:
                presenter.onGetSalt(email.getText().toString());
                progressDialog.show();
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }


    public void onSuccessGetSalt(String _salt){
        salt = _salt;
        progressDialog.cancel();
        openPasswordDialog();
    }

    public void onSuccessGetProfileInfos(Person person) {
        email.setText(person.getMailAddress());
        name.setText(person.getUserName());
        progressDialog.cancel();
    }

    public void onSuccessUpdatePasswort() {
        Toast.makeText(getApplicationContext(), "Passwort erfolgreich geändert.", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPref = getSharedPreferences("TravelTogetherPrefs",Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_hash), newHash);
        editor.commit();
    }

    public void onSuccessUpdateProfileInfos() {
        hideOption(R.id.action_save);
        showOption(R.id.change_profile);
        //showOption(R.id.change_pw);
        email.setEnabled(false);
        name.setEnabled(false);
        progressDialog.cancel();
    }


    public void onError(String message) {
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void openPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        passwordDialogView = inflater.inflate(R.layout.dialog_change_password, null);
        errorText = (TextView)passwordDialogView.findViewById(R.id.dialog_change_password_error);

        builder.setView(passwordDialogView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setTitle(getString(R.string.change_pw));

        passwordDialog = builder.create();
        Button b = (Button)passwordDialog.getButton(Dialog.BUTTON_POSITIVE);

        passwordDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = passwordDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (changePassword())
                            passwordDialog.dismiss();
                        else {
                            ((EditText) passwordDialogView.findViewById(R.id.dialog_change_password_oldpw)).setText("");
                            ((EditText) passwordDialogView.findViewById(R.id.dialog_change_password_newpw)).setText("");
                            ((EditText) passwordDialogView.findViewById(R.id.dialog_change_password_wdhpw)).setText("");

                        }
                    }
                });
            }
        });
        passwordDialog.setCanceledOnTouchOutside(false);
        passwordDialog.setCancelable(false);
        passwordDialog.show();
    }

    public boolean changePassword() {
        String oldPW = "";
        String newPW = "";
        String wdhPW = "";
        oldPW = ((EditText)passwordDialogView.findViewById(R.id.dialog_change_password_oldpw)).getText().toString();
        newPW = ((EditText)passwordDialogView.findViewById(R.id.dialog_change_password_newpw)).getText().toString();
        wdhPW = ((EditText)passwordDialogView.findViewById(R.id.dialog_change_password_wdhpw)).getText().toString();

        if (!newPW.equals(wdhPW)) {
            errorText.setText("Die Passwörter stimmen nicht überein.");
            return false;
        }
        SharedPreferences sharedPref = getSharedPreferences("TravelTogetherPrefs",Context.MODE_PRIVATE );
        String oldHash = sharedPref.getString(getString(R.string.saved_hash), "").replace(" ", "").replace("\u003d", "").replace("\\n", "");;
        int length = oldPW.length();
        char [] pw = new char[length];
        oldPW.getChars(0,length, pw, 0);
        String enteredHash = hashPassword(pw, salt);

        if(!enteredHash.equals(oldHash)){
            errorText.setText("Das eingegebene Passwort ist falsch.");
            return false;
        }
        salt = HashFactory.getNextSalt();
        length = newPW.length();
        if(length<7){
            errorText.setText("Das eingegebene Passwort ist zu kurz.");
            return false;
        }
        char [] newpw = new char[length];
        newPW.getChars(0, length, newpw, 0);
        newHash = hashPassword(newpw, salt);
        newHash = newHash.replace("/", "");
        Log.d("hash", newHash);

        presenter.onUpdatePasswort(salt, newHash);

        return true;
    }

}
