package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    Button back;


    Switch notificaciones, mensajes, pedidos, publicaciones, ofertas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs;
        prefs = getSharedPreferences( "Prefs" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();



        notificaciones = findViewById(R.id.notificaciones);
        mensajes = findViewById(R.id.MsgNotif);
        pedidos = findViewById(R.id.OrderNotif);
        publicaciones = findViewById(R.id.PubNotif);
        ofertas = findViewById(R.id.OfferNotif);
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

                    editor.putBoolean("notifs", false);
                    editor.commit();

                    Boolean check = prefs.getBoolean("notifs",true);
                    Log.d("Ajustes ","Preferencias "+ check +" corta notificaciones");
                    NotificationManagerCompat.from(Settings.this).cancelAll();
                }
            }
        });

        notificaciones.setChecked(prefs.getBoolean("notifs",false));

        mensajes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mensajes.isChecked()) {


                    editor.putBoolean("Msgnotifs", true);
                    editor.commit();
                } else {

                    editor.putBoolean("Msgnotifs", false);
                    editor.commit();

                    Boolean check = prefs.getBoolean("Msgnotifs",true);
                    Log.d("Ajustes ","Mensajes "+ check +" corta notificaciones");
                   
                }
            }
        });

        mensajes.setChecked(prefs.getBoolean("Msgnotifs",false));

        pedidos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (pedidos.isChecked()) {


                    editor.putBoolean("Ordernotifs", true);
                    editor.commit();
                } else {

                    editor.putBoolean("Ordernotifs", false);
                    editor.commit();

                    Boolean check = prefs.getBoolean("Ordernotifs",true);
                    Log.d("Ajustes ","Pedidos "+ check +" corta notificaciones");
                }
            }
        });

        pedidos.setChecked(prefs.getBoolean("Ordernotifs",false));

        ofertas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (ofertas.isChecked()) {


                    editor.putBoolean("Offernotifs", true);
                    editor.commit();
                } else {

                    editor.putBoolean("Offernotifs", false);
                    editor.commit();

                    Boolean check = prefs.getBoolean("Offernotifs",true);
                    Log.d("Ajustes ","Ofertas "+ check +" corta notificaciones");
                }
            }
        });

        ofertas.setChecked(prefs.getBoolean("Offernotifs",false));

        publicaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (publicaciones.isChecked()) {


                    editor.putBoolean("Pubnotifs", true);
                    editor.commit();
                } else {

                    editor.putBoolean("Pubnotifs", false);
                    editor.commit();

                    Boolean check = prefs.getBoolean("Pubnotifs",true);
                    Log.d("Ajustes ","Publicaciones "+ check +" corta notificaciones");
                }
            }
        });

        publicaciones.setChecked(prefs.getBoolean("Pubnotifs",false));
    }
}