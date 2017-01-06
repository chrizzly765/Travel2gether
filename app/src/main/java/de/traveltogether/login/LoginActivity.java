package de.traveltogether.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import de.traveltogether.R;
import de.traveltogether.gcm.GCMRegistrationIntentService;
import de.traveltogether.register.RegisterActivity;
import de.traveltogether.triplist.TripListActivity;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    protected Button loginBtn;
    protected TextView registerBtn;
    protected EditText email;
    protected EditText password;
    TextView forgotPwButton;
    protected ILoginPresenter presenter;
    String salt;
    ProgressDialog progressDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    AlertDialog passwordDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button)findViewById(R.id.login_button);
        loginBtn.setOnClickListener(this);
        registerBtn = (TextView) findViewById(R.id.register_Text2);
        registerBtn.setOnClickListener(this);
        presenter = new LoginPresenter(this);

        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPassword);
        forgotPwButton = (TextView)findViewById(R.id.forgot_pw_text);
        forgotPwButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.register_Text2) {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        } else if (v.getId() == R.id.login_button) {
            if (!(email.getText().length() > 0 && email.getText().toString().contains("@"))) {
                onViewError(getString(R.string.invalid_mailadress));
                return;
            }
            if(password.getText().length()<7){
                onViewError(getString(R.string.pw_minimum_length));
                return;
            }
            v.setEnabled(false);
            progressDialog = ProgressDialog.show(this, "",
                    "Bitte warten...", true);
            presenter.onGetSalt(email.getText().toString());
        }else if(v.getId() == R.id.forgot_pw_text){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.forgot_pw_title);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
            if(email.getText()!=null && email.getText().toString().length()>0) {
                ((EditText) dialogView.findViewById(R.id.dialog_forgot_pw_email)).setText(email.getText().toString());
            }
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    presenter.onForgotPassword(((EditText)dialogView.findViewById(R.id.dialog_forgot_pw_email)).getText().toString());
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            passwordDialog = builder.create();
            passwordDialog.show();
        }
    }


    public void onViewError(String message) {
        progressDialog.cancel();
        progressDialog.cancel();
        loginBtn.setEnabled(true);
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

    public void onViewSuccess(String message){
        progressDialog.cancel();
        loginBtn.setEnabled(true);
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.login_success);
        int duration = Toast.LENGTH_SHORT;


        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check type of intent filter
                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    //Registration success
                    String token = intent.getStringExtra("token");
                    presenter.onUpdateToken(token);
                } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    //Registration error
                    onViewError("Error in receiving token from GCM.");
                }
            }
        };


        //Check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode) {

            //Check type of error
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                //So notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }
        } else {
            //Start service
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));



        Intent tLIntent = new Intent(this, TripListActivity.class);
        startActivity(tLIntent);
    }

    public Activity getActivity() {
        return this;
    }

    public void onGetSaltSuccess(String _salt){
        salt = _salt;
        int length = password.getText().length();

        char [] pw = new char[length];
        password.getText().getChars(0,length, pw, 0);

        String hash = hashPassword(pw, salt);
        presenter.onLogin(email.getText().toString(), hash);
    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void onSuccessForgotPassword(){
        passwordDialog.cancel();
        Toast.makeText(getApplicationContext(), "Ein neues Passwort wurde dir zugesendet.", Toast.LENGTH_LONG).show();
    }
}
