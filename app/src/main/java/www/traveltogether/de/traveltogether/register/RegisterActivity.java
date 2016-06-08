package www.traveltogether.de.traveltogether.register;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import www.traveltogether.de.traveltogether.R;
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
        name = (EditText)findViewById(R.id.editText);
        email = (EditText)findViewById(R.id.editText2);
        password=(EditText)findViewById(R.id.editText3);
    }

    public void onClick(View v){
        Log.d("onclick", "");
        salt = HashFactory.getNextSalt();
        Log.e("salt", salt);
        Log.e("pw", password.getText().toString());
        int length = password.getText().length();
        char [] pw = new char[length];
        password.getText().getChars(0,length, pw, 0);
        String hash = hashPassword(pw, salt);
        presenter.onRegister(name.getText().toString(), email.getText().toString(), hash, salt);

    }

    public void onViewSuccessMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Erfolgreich registriert");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewErrorMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Error");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
