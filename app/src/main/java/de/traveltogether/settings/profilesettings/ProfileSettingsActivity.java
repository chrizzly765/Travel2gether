package de.traveltogether.settings.profilesettings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Person;
import de.traveltogether.servercommunication.HashFactory;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;

public class ProfileSettingsActivity extends AppCompatActivity {
    IProfileSettingsPresenter presenter;
    ProgressDialog progressDialog;
    Menu optionsMenu;
    TextView email;
    TextView name;
    String salt;
    View passwordDialogView;


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
                hideOption(R.id.change_pw);
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

    }

    public void onSuccessUpdateProfileInfos() {
        hideOption(R.id.action_save);
        showOption(R.id.change_profile);
        showOption(R.id.change_pw);
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
        builder.setView(passwordDialogView);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (changePassword())
                    dialog.cancel();
                else {
                    ((EditText) passwordDialogView.findViewById(R.id.dialog_change_password_oldpw)).setText("");
                    ((EditText) passwordDialogView.findViewById(R.id.dialog_change_password_newpw)).setText("");
                    ((EditText) passwordDialogView.findViewById(R.id.dialog_change_password_wdhpw)).setText("");
                }

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setTitle(getString(R.string.change_pw));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean changePassword() {
        String oldPW = "";
        String newPW = "";
        String wdhPW = "";
        oldPW = ((EditText)passwordDialogView.findViewById(R.id.dialog_change_password_oldpw)).getText().toString();
        newPW = ((EditText)passwordDialogView.findViewById(R.id.dialog_change_password_newpw)).getText().toString();
        wdhPW = ((EditText)passwordDialogView.findViewById(R.id.dialog_change_password_wdhpw)).getText().toString();

        if (newPW != wdhPW) {
            onError("Die Passwörter stimmen nicht überein.");
            return false;
        }
        SharedPreferences sharedPref = getSharedPreferences("TravelTogetherPrefs",Context.MODE_PRIVATE );
        String oldHash = sharedPref.getString(getString(R.string.saved_hash), "");
        int length = oldPW.length();
        char [] pw = new char[length];
        oldPW.getChars(0,length, pw, 0);
        String enteredHash = hashPassword(pw, salt);
        if(enteredHash!=oldHash){
            onError("Das eingegebene Passwort ist falsch.");
            return false;
        }
        salt = HashFactory.getNextSalt();
        length = newPW.length();
        char [] newpw = new char[length];
        String newHash = hashPassword(newpw, salt);
        presenter.onUpdatePasswort(salt, newHash);

        return true;
    }

}
