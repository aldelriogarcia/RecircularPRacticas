package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    Button back;


    Switch notificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs;
        prefs = getSharedPreferences( "Prefs" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();



        notificaciones = findViewById(R.id.notificaciones);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        notificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (notificaciones.isChecked()) {


                    editor.putBoolean("notifs", true);
                    editor.commit();
                } else {

                    NotificationManagerCompat.from(Settings.this).cancelAll();

                    editor.putBoolean("notifs", false);
                    editor.commit();
                }
            }
        });


    }
}