package com.lotech.docskikda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    TextView doctor_name, doctor_phone, doctor_local;
    ImageView doctor_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setContentView(R.layout.activity_profile);
        doctor_name = (TextView)  findViewById(R.id.doctor_name);
        doctor_phone = (TextView) findViewById(R.id.doctor_phone);
        doctor_local = (TextView) findViewById(R.id.doctor_local);


        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("doctor_id");
        String doctor_id = b.getString("id");

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ProfileHandler doctor = databaseAccess.getDoctor(doctor_id);
        databaseAccess.close();
        if (doctor != null) {
            doctor_name.setText(doctor.getName());
            doctor_phone.setText(doctor.getPhone());
            doctor_local.setText(doctor.getLocal());

        } else {
            Toast.makeText(getApplicationContext(), "No Doctor to show",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
