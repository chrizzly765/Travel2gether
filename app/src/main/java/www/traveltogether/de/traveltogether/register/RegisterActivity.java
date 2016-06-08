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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //private static final String TAG = RegisterActivity.class.getSimpleName();

    private IRegisterPresenter presenter;
    protected Button registerBtn;
    protected EditText name;
    protected EditText email;
    protected EditText password;
    protected byte[] salt;
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
        salt = HashFactory.getNextSalt();
        String hash = HashFactory.hashPassword(password.getText().toString().toCharArray(), salt);
        presenter.onRegister(name.getText().toString(), email.getText().toString(), hash);
    }

    public void onViewSuccessMessage(String message){
        //save salt in shared preferences
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_salt), salt.toString());
        editor.commit();

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
