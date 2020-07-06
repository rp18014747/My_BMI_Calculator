package sg.edu.rp.c346.id18014747.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tvDate, tvBMI, tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etWeight.getText().toString().isEmpty() || etHeight.getText().toString().isEmpty()) {
                    return;
                }
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();

                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float BMI = weight / (height*height);
                String outcome = "";
                if (BMI < 18.5) {
                    outcome = "underweight";
                } else if (BMI < 25) {
                    outcome = "normal";
                } else if (BMI < 30) {
                    outcome = "overweight";
                } else {
                    outcome = "obese";
                }
                BMI = Float.parseFloat(String.format("%.3f", BMI));

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                prefEdit.putString("datetime", datetime+"");
                prefEdit.putFloat("BMI", BMI);
                prefEdit.putString("outcome", outcome);

                prefEdit.commit();

                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date: "+datetime);
                tvBMI.setText("Last Calculated BMI: "+BMI);
                tvOutcome.setText("You are "+outcome);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();

                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: 0.0");
                tvOutcome.setText("You are ");
                prefEdit.clear();
                prefEdit.commit();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        float weight = Float.parseFloat(etWeight.getText().toString());
        float height = Float.parseFloat(etHeight.getText().toString());
        float BMI = weight / (height*height);

        String outcome = "";
        if (BMI < 18.5) {
            outcome = "underweight";
        } else if (BMI < 25) {
            outcome = "normal";
        } else if (BMI < 30) {
            outcome = "overweight";
        } else {
            outcome = "obese";
        }
        BMI = Float.parseFloat(String.format("%.3f", BMI));

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        prefEdit.putString("datetime", datetime+"");
        prefEdit.putFloat("BMI", BMI);
        prefEdit.putString("outcome", outcome);

        prefEdit.commit();

        etWeight.setText("");
        etHeight.setText("");
        tvDate.setText("Last Calculated Date: "+datetime);
        tvBMI.setText("Last Calculated BMI: "+BMI);
        tvOutcome.setText("You are "+outcome);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        float BMI = prefs.getFloat("BMI", 0);
        String datetime = prefs.getString("datetime", "");
        String outcome = prefs.getString("outcome", "");

        etWeight.setText("");
        etHeight.setText("");
        tvDate.setText("Last Calculated Date: "+datetime);
        tvBMI.setText("Last Calculated BMI: "+BMI);
        tvOutcome.setText("You are "+outcome);
    }
}
