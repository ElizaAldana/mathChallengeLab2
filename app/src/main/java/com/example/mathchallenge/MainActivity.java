package com.example.mathchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView pregunta, puntajeNum, contador, pasar;
    private EditText respuesta;
    private Button okBtn, intentarDN;
    private Pregunta p;
    private int time, points = 0, timeNp;
    private boolean t = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pregunta = findViewById(R.id.pregunta);
        respuesta = findViewById(R.id.respuesta);
        okBtn = findViewById(R.id.okBtn);
        puntajeNum = findViewById(R.id.puntajeNum);
        contador = findViewById(R.id.contador);
        intentarDN = findViewById(R.id.intentarDN);
        pasar = findViewById(R.id.pasar);

        p = new Pregunta();
        pregunta.setText(p.getPregunta());



        newPregunta();
        temp();

        okBtn.setOnClickListener(
                v->{
                    responder();
                }
        );

        pasar.setOnTouchListener(
                (view, event) -> {
                  switch (event.getAction()){
                      case MotionEvent.ACTION_DOWN:
                          if(timeNp>1.5){
                              newPregunta();
                          }
                  }
                  return true;
                }
        );


    }


    //lo del contador de 30 segundos - O sea si sirve el contador y todo bien pero no puedo intentar de nuevo
    public void temp(){
        new Thread(
                ()->{
                    while (t){
                        time = time-1;
                        timeNp = timeNp+1;
                        runOnUiThread(()-> contador.setText(""+time));

                        if (time == 0){
                            //aparece pero se cierra en ese mismo instante
                            //intentarDN.setVisibility(View.VISIBLE);
                            t = false;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
        ).start();


    }



    public void responder(){
        String resp = respuesta.getText().toString();
        int resInt = Integer.parseInt(resp);
        int correcta = p.getRespuesta();



        if(resInt == correcta){
            points = points+5;
            Toast.makeText(this,"Correcto", Toast.LENGTH_SHORT).show();
        }else{
            points = points-4;
            Toast.makeText(this,"Incorrecto", Toast.LENGTH_SHORT).show();
        }
        puntajeNum.setText(""+points);
        newPregunta();

    }

    public void newPregunta(){
        p = new Pregunta();
        pregunta.setText(p.getPregunta());
        time = 30;
        timeNp = 0;
        contador.setText(""+time);
    }



}