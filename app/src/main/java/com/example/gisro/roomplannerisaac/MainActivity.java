package com.example.gisro.roomplannerisaac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Classes.Appointment;

public class MainActivity extends AppCompatActivity {

    private Button btnOpenClose;

    Appointment a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClose = (Button) findViewById(R.id.button);
    }

    public void btnVergaderingOnClick(View v){
        if(btnOpenClose.getText() == "Open vergadering"){
            a.open();
            btnOpenClose.setText("Sluit vergadering");
        }
        else if(btnOpenClose.getText() == "Sluit vergadering"){
            a.close();
        }
    }
}
