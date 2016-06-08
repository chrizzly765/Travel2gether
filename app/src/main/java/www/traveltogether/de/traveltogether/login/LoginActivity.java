package www.traveltogether.de.traveltogether.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        //registerBtn = (Button) findViewById(R.id.register_text2);
        //registerBtn.setOnClickListener(this);
        presenter = new LoginPresenter(this);


    }

    public void onClick(View v){
        if(v.getId() == R.id.login_button){
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        }
        else if(v.getId()==R.id.login_button){
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String salt = "";
            sharedPref.getString(getString(R.string.saved_salt), salt);
            if(salt != "") {
                String hash = hashPassword(password.getText().toString().toCharArray(), salt.getBytes());
                presenter.onLogin(email.getText().toString(), hash);
            }
            else{
                //TODO: not registered yet!
            }
        }
    }

    public void onViewError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Error");
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewSuccess(String message){
        Intent tLIntent = new Intent(this, TripListActivity.class);
        startActivity(tLIntent);
    }

    public Activity getActivity() {
        return this;
    }
}
