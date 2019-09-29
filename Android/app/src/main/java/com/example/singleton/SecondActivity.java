package com.example.singleton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class SecondActivity extends AppCompatActivity implements Observer {

    //La variable de color anterior
    private String color;

    // Variable
    private TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Guarda el estado
        super.onCreate(savedInstanceState);

        // Muestra lo de activity second
        setContentView(R.layout.activity_second);

        // Sigue con los elementos de la actividad pasada
        Intent i = getIntent();

        //Saca el extra del sting de color y lo guarda
        color = i.getStringExtra("color");

        // Lo mismo pero para el numero
        num = findViewById(R.id.tv_second_num);

        // Patron signleton y Observer
        SingletonComunicacion com = SingletonComunicacion.getInstance();
        com.addObserver(this);


        //Define el boton send y le pone el listener
        Button btnSend = findViewById(R.id.btn_second_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtX = findViewById(R.id.edt_second_x);
                EditText edtY = findViewById(R.id.edt_second_y);
                String valX = edtX.getText().toString();
                String valY = edtY.getText().toString();
                SingletonComunicacion.getInstance().enviar(valX+":"+valY+":"+color);
                finish();
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String m = (String)o;
            String[] partes = m.split(":");
            if(m.contains("num") && partes.length == 2){
                final int value = Integer.parseInt(partes[1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        num.setText(""+value);
                    }
                });
            }
        }
    }
}
