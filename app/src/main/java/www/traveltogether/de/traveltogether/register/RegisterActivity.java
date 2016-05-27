package www.traveltogether.de.traveltogether.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import www.traveltogether.de.traveltogether.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private IRegisterPresenter presenter;
    protected Button registerBtn;
    protected EditText name;
    protected EditText email;
    protected EditText password;
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
        presenter.onRegister(name.getText().toString(), email.getText().toString(), password.getText().toString());
    }
}
