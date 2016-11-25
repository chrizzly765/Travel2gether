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
import de.traveltogether.servercommunication.HashFactory;
import de.traveltogether.triplist.TripListActivity;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    protected Button loginBtn;
    protected TextView registerBtn;
    protected EditText email;
    protected EditText password;
    protected ILoginPresenter presenter;
    String salt;
    ProgressDialog progressDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


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
            v.setEnabled(false);
            progressDialog = ProgressDialog.show(this, "",
                    "Bitte warten...", true);
            presenter.onGetSalt(email.getText().toString());
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
        Intent tLIntent = new Intent(this, TripListActivity.class);
        startActivity(tLIntent);
    }

    public Activity getActivity() {
        return this;
    }

    public void onGetSaltSuccess(String _salt){
        salt = _salt;
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check type of intent filter
                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    //Registration success
                    String token = intent.getStringExtra("token");

                    int length = password.getText().length();
                    char [] pw = new char[length];
                    password.getText().getChars(0,length, pw, 0);
                    String hash = hashPassword(pw, salt);
                    presenter.onLogin(email.getText().toString(), hash, token);


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


    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
