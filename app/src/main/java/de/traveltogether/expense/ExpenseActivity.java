package de.traveltogether.expense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import de.traveltogether.R;
import de.traveltogether.expense.newexpense.NewExpenseActivity;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener{
    long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        ImageButton add= (ImageButton)findViewById(R.id.activity_expense_button_add);
        add.setOnClickListener(this);

        tripId = getIntent().getLongExtra("tripId", -1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_expense_button_add){
            Intent intent = new Intent(this, NewExpenseActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
    }
}
