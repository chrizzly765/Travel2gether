package de.traveltogether.register;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.traveltogether.R;
import de.traveltogether.login.LoginActivity;
import de.traveltogether.servercommunication.HashFactory;

import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import de.traveltogether.gcm.GCMRegistrationIntentService;

import static de.traveltogether.servercommunication.HashFactory.hashPassword;

/**
 * Activty for registering
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private IRegisterPresenter presenter;
    private Button registerBtn;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private String salt;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
        registerBtn = (Button)findViewById(R.id.activity_register_button_register);
        registerBtn.setOnClickListener(this);
        TextView loginBtn = (TextView)findViewById(R.id.activity_register_button_login);
        loginBtn.setOnClickListener(this);
        name = (EditText)findViewById(R.id.registerName);
        email = (EditText)findViewById(R.id.registerEmail);
        password=(EditText)findViewById(R.id.registerPassword);
        repeatPassword=(EditText)findViewById(R.id.registerPassword_repeat);
    }

    public void onClick(View v){
        if(v.getId()==R.id.activity_register_button_register) {
            if (!(email.getText().length() > 0 && email.getText().toString().contains("@"))) {
                onViewErrorMessage(getString(R.string.invalid_mailadress));
                return;
            }
            if(password.getText().length()<7) {
                Log.d("pw length", password.getText().length() + " ");
                onViewErrorMessage(getString(R.string.pw_minimum_length));
                return;
            }
            if(!password.getText().toString().equals(repeatPassword.getText().toString())){
                onViewErrorMessage(getString(R.string.pw_repeat_error));
                return;
            }
            progressDialog = ProgressDialog.show(this, "",
                    getString(R.string.please_wait), true);
            v.setEnabled(false);

            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //Check type of intent filter
                    if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                        //Registration success
                        String token = intent.getStringExtra("token");
                        salt = HashFactory.getNextSalt();
                        int length = password.getText().length();
                        char[] pw = new char[length];
                        password.getText().getChars(0, length, pw, 0);
                        String hash = hashPassword(pw, salt);
                        presenter.onRegister(name.getText().toString(), email.getText().toString(), hash, salt, token);


                    } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                        //Registration error
                       Log.e("GCM","Error in receiving token from GCM.");
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
        else if (v.getId()== R.id.activity_register_button_login){
            finish();
        }
        /*
        if(progressDialog!=null) {
            progressDialog.cancel();
        }
        */
    }

    public void onViewSuccessMessage(String message){
        if(progressDialog!=null) {
            progressDialog.cancel();
        }
        registerBtn.setEnabled(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mail_was_sent);
        builder.setTitle(getString(R.string.register_success));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onViewErrorMessage(String message){
        if(progressDialog != null) {
            progressDialog.cancel();
        }
        registerBtn.setEnabled(true);
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
