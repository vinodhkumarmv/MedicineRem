package com.vinodh.medicinerem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText medName, medDate;
    TextView medtxt;
    Button btnInsert, btnFetch;
    Spinner timeDaySpinner;
    Switch swtch;
    DBConn dbconn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbconn = new DBConn(this);
        setContentView(R.layout.activity_main);
        medName = findViewById(R.id.editTextName);
        medtxt = findViewById(R.id.medtxt);
        medDate = findViewById(R.id.editTextDate);
        btnInsert = findViewById(R.id.button1);
        btnFetch = findViewById(R.id.button2);
        btnFetch.setVisibility(View.INVISIBLE);
        timeDaySpinner = findViewById(R.id.spinner);
        swtch = findViewById(R.id.switch1);
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    btnFetch.setVisibility(View.INVISIBLE);
                    medName.setVisibility(View.VISIBLE);
                    btnInsert.setVisibility(View.VISIBLE);
                    medtxt.setVisibility(View.VISIBLE);
                }
                else{
                    btnFetch.setVisibility(View.VISIBLE);
                    medName.setVisibility(View.INVISIBLE);
                    btnInsert.setVisibility(View.INVISIBLE);
                    medtxt.setVisibility(View.INVISIBLE);

                }
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = medName.getText().toString();
                String date = medDate.getText().toString();
                String time = timeDaySpinner.getSelectedItem().toString();
                boolean insert = dbconn.insertvalues(name,date,time);
                if (insert==true){
                    Toast.makeText(getApplicationContext(),"Data Inserted", Toast.LENGTH_SHORT).show();
                    medName.setText(null);
                    medDate.setText(null);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = medDate.getText().toString();
                String time = timeDaySpinner.getSelectedItem().toString();
                String med = "";
                Cursor c = dbconn.FetchData(date, time);
                c.moveToFirst();
                do{
                    med = med+(String.valueOf(c.getString(c.getColumnIndex("MedicineName"))));
                    med+="\n";
                }while (c.moveToNext());

                Toast.makeText(getApplicationContext(),med,Toast.LENGTH_LONG).show();
            }
        });
    }
}