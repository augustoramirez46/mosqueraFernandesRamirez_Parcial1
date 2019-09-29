package com.example.singleton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    // Variable que muestra los cuadrados en el Arraylis de eclipse
    private TextView numberOfRects;

    // El string del color
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se muestra en la actividad 1
        setContentView(R.layout.activity_main);

        // Pone este texto en la pantalla
        numberOfRects = findViewById(R.id.tv_num_main);

        // Inicia el patron Singleton
        SingletonComunicacion.getInstance();
        SingletonComunicacion.getInstance().addObserver(this);

        // Impirme en la consola
        System.out.println("INSTANCIA:" + SingletonComunicacion.getInstance());

        // Conecta las variables de botones con las del activity
        Button btnRed = findViewById(R.id.btn_main_red);
        Button btnGreen = findViewById(R.id.btn_main_green);
        Button btnBlue = findViewById(R.id.btn_main_blue);

        // Pone el listener a el boton
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Asigna el string a la variable
                color = "rojo";
            }
        });

        // Se repite
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Se repite pero en verde
                color = "verde";
            }
        });


        // Se repite
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Se repite pero en azul
                color = "azul";
            }
        });

        //Pone lo definido en color como un extra en el bundle para la segunda actividad
        Button btnSend = findViewById(R.id.btn_main_go);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);


                // inicia la segunda actividad
                startActivity(i);

                //ni idea
               /* i.putExtra("apellido",color);
                i.putExtra("nombre",color);
                i.putExtra("telefono",color);
                i.putExtra("nota",color);*/
            }
        });

    }

    @Override

    // Pues el patron observable
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            String m = (String)o;
            String[] partes = m.split(":");
            if(m.contains("num") && partes.length == 2){
                final int value = Integer.parseInt(partes[1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberOfRects.setText(""+value);
                    }
                });
            }
        }
    }
}
