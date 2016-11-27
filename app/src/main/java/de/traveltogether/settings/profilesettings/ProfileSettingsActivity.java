package de.traveltogether.settings.profilesettings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.model.Person;

public class ProfileSettingsActivity extends AppCompatActivity {
    IProfileSettingsPresenter presenter;
    ProgressDialog progressDialog;
    Menu optionsMenu;
    TextView email;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        getSupportActionBar().setTitle("Mein Profil");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        presenter=new ProfileSettingsPresenter(this);
        email = (EditText)findViewById(R.id.activity_profile_settings_mail);
        name = (EditText)findViewById(R.id.activity_profile_settings_name);
    }

    @Override
    protected void onStart(){
        progressDialog = ProgressDialog.show(this, "", "Daten werden geladen...", true);
        super.onStart();
        presenter.onGetProfileInfos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_settings, menu);
        hideOption(R.id.action_save);
        return super.onCreateOptionsMenu(menu);
    }

    private void hideOption(int id)
    {
        MenuItem item = optionsMenu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id)
    {
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

                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }


    public void onSuccessGetProfileInfos(Person person){
        email.setText(person.getMailAddress());
        name.setText(person.getUserName());
        progressDialog.cancel();
    }
    public void onSuccessUpdatePasswort(){

    }
    public void onSuccessUpdateProfileInfos(){
        hideOption(R.id.action_save);
        showOption(R.id.change_profile);
        showOption(R.id.change_pw);
        email.setEnabled(false);
        name.setEnabled(false);
        progressDialog.cancel();
    }


    public void onError(String message){
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


}
