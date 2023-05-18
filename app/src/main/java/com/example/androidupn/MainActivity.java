package com.example.androidupn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Boolean generacion = true;
    int ptj1 = 0, ptj2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtPtjP1 = findViewById(R.id.txtPtj1);
        TextView txtPtjP2 = findViewById(R.id.txtPtj2);
        TextView txtWinner = findViewById(R.id.txtWinner);

        Button btnReset = findViewById(R.id.btnReset);
        Button btnSeleccion = findViewById(R.id.btnSeleccionador);

        btnSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puntaje(btnSeleccion, txtPtjP1, txtPtjP2, txtWinner);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSeleccion.setText("Jugador 1");
                txtPtjP1.setText("Puntaje: ");
                txtPtjP2.setText("Puntaje: ");
                txtWinner.setText("");
                ptj1 = 0;
                ptj2 = 0;
                generacion = true;
            }
        });
    }

    void puntaje(Button btnSeleccion, TextView txtPtjP1, TextView txtPtjP2, TextView txtWinner){
        Random rnd = new Random();
        String txt = btnSeleccion.getText().toString();
        String txtPt1 = txtPtjP1.getText().toString();
        String txtPt2 = txtPtjP2.getText().toString();

        if(txt.equals("Jugador 1")) {
            ptj1 = rnd.nextInt(11);
            txtPtjP1.setText("Puntaje: " + String.valueOf(ptj1));
            btnSeleccion.setText("Jugador 2");
        }
        if(txt.equals("Jugador 2")){
            if(generacion) {
                ptj2 = rnd.nextInt(11);
                txtPtjP2.setText("Puntaje: " + String.valueOf(ptj2));
                generacion = false;
            }
        }

        if(!txtPt1.equals("") && !txtPt2.equals("")){
            if(ptj1 > ptj2)
                txtWinner.setText("Ganador: Jugador 1");
            if(ptj1 == ptj2)
                txtWinner.setText("Empate!!");
            if(ptj1 < ptj2)
                txtWinner.setText("Ganador: Jugador 2");
        }
    }
}
