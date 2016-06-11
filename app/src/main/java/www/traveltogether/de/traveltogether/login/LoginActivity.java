package www.traveltogether.de.traveltogether.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.register.RegisterActivity;
import www.traveltogether.de.traveltogether.servercommunication.HashFactory;
import www.traveltogether.de.traveltogether.triplist.TripListActivity;

import static www.traveltogether.de.traveltogether.servercommunication.HashFactory.hashPassword;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    protected Button loginBtn;
    protected TextView registerBtn;
    protected EditText email;
    protected EditText password;
    protected ILoginPresenter presenter;

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
            presenter.onGetSalt(email.getText().toString());
        }
    }


    public void onViewError(String message) {
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

    public void onGetSaltSuccess(String salt){
        int length = password.getText().length();
        char [] pw = new char[length];
        password.getText().getChars(0,length, pw, 0);
        String hash = hashPassword(pw, salt);
        presenter.onLogin(email.getText().toString(), hash);
    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
