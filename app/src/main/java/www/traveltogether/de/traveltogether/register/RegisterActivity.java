package www.traveltogether.de.traveltogether.register;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.login.LoginActivity;
import www.traveltogether.de.traveltogether.servercommunication.HashFactory;

import android.app.AlertDialog;

import java.io.Console;

import static www.traveltogether.de.traveltogether.servercommunication.HashFactory.hashPassword;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //private static final String TAG = RegisterActivity.class.getSimpleName();

    private IRegisterPresenter presenter;
    protected Button registerBtn;
    protected EditText name;
    protected EditText email;
    protected EditText password;
    protected String salt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
        registerBtn = (Button)findViewById(R.id.button_register);
        registerBtn.setOnClickListener(this);
        name = (EditText)findViewById(R.id.registerName);
        email = (EditText)findViewById(R.id.registerEmail);
        password=(EditText)findViewById(R.id.registerPassword);
    }

    public void onClick(View v){
        if(v.getId()==R.id.button_register) {
            if (!(email.getText().length() > 0 && email.getText().toString().contains("@"))) {
                onViewErrorMessage(getString(R.string.invalid_mailadress));
                return;
            }
            if(password.getText().length()<7) {
                onViewErrorMessage(getString(R.string.pw_minimum_length));
                return;
            }

            salt = HashFactory.getNextSalt();
            int length = password.getText().length();
            char[] pw = new char[length];
            password.getText().getChars(0, length, pw, 0);
            String hash = hashPassword(pw, salt);
            presenter.onRegister(name.getText().toString(), email.getText().toString(), hash, salt);

        }
        else if (v.getId()==R.id.login_Text2){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }

    }

    public void onViewSuccessMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mail_was_sent);
        builder.setTitle(getString(R.string.register_success));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewErrorMessage(String message){
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
