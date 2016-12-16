package com.example.balva.simodiceexa;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SimonDice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_dice);

        Button verde= (Button) findViewById(R.id.verde);
        verde.setEnabled(false);

        Button rojo= (Button) findViewById(R.id.Rojo);
        rojo.setEnabled(false);
        Button amarelo= (Button) findViewById(R.id.Amarelo);

        amarelo.setEnabled(false);
        Button azul= (Button) findViewById(R.id.Azul);

        azul.setEnabled(false);
        TextView nivel = (TextView) findViewById(R.id.nivel);
    }

    MediaPlayer btn1;
    MediaPlayer btn2;
    MediaPlayer btn3;
    MediaPlayer btn4;

    //int [] Audio ={R.raw.boton3,R.raw.buton2,R.raw.boton4,R.raw.boton_1};
    int [] botons= {R.id.Azul,R.id.Rojo,R.id.Amarelo,R.id.verde};
    String [] colorBoton={"#00B0FF","#E57373","#FFFF99","#8BC34A"};
    int [] colorClaro={Color.BLUE,Color.RED,Color.parseColor("#FFFF00"),Color.GREEN};

    TimerTask tempoTrancurrido;
    Timer tempo;
    ArrayList<Integer> cores = new ArrayList();
    ArrayList<Integer> xogador = new ArrayList();

    protected static int CONTADOR = 0;
    protected static int CONTTEMPO=0;
    protected static int DIFICULT=4;
    protected static int NVL=1;
    //Esta parte do sonido non podo asegurar que este ben o código xa que non o podo comprobar
    void eventoAzul(View a) {
       // btn3= MediaPlayer.create(this, R.raw.boton3);
       // btn3.start();
        CONTADOR++;
        xogador.add(0);
        parpadear(0);
        restablecer();
    }

    void eventoRojo(View r) {
        //btn2=MediaPlayer.create(this,R.raw.buton2);
        //btn2.start();
        CONTADOR++;
        xogador.add(1);
        parpadear(1);
        restablecer();
    }

    void eventoAmarillo(View am) {
       // btn4=MediaPlayer.create(this,R.raw.boton4);
       // btn4.start();
        CONTADOR++;
        xogador.add(2);
        parpadear(2);
        restablecer();
    }

    void eventoVerde(View a) {
        //btn1=MediaPlayer.create(this,R.raw.boton3);
        //btn1.start();
        CONTADOR++;
        xogador.add(3);
        parpadear(3);
        restablecer();
    }





    void parpadear(final int posicionBotn){
        //final MediaPlayer sonido=MediaPlayer.create(this,Audio[posicionBotn]);
        findViewById(botons[posicionBotn]).setBackgroundColor(colorClaro[posicionBotn]);
        findViewById(botons[posicionBotn]).postDelayed(new Runnable() {
            public void run() {
                //sonido.reset();
                findViewById(botons[posicionBotn]).setBackgroundColor(Color.parseColor(colorBoton[posicionBotn]));
            }
        }, 600);

    }


    void inicializarTimer (){
        tempoTrancurrido = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int numeroAleatorio=aleatorio();
                        cores.add(numeroAleatorio);
                        if(numeroAleatorio==0){
                            parpadear(0);}
                        if(numeroAleatorio==1){
                            parpadear(1);
                        }
                        if(numeroAleatorio==2){
                            parpadear(2);
                        }
                        if(numeroAleatorio==3){
                            parpadear(3);
                        }

                        CONTTEMPO++;
                        if(CONTTEMPO==DIFICULT){
                            pararTimer();
                        }

                    }
                });

            }
        };


    }


    public void empezarTimer(){
        tempo = new Timer();
        inicializarTimer();
        tempo.schedule(tempoTrancurrido, 200, 1000);
    }

    public void pararTimer(){
        if (tempo !=null){
            tempo.cancel();
            tempo= null;
        }
    }

    void empezar(View e) {
        findViewById(R.id.Azul).setEnabled(true);
        findViewById(R.id.Rojo).setEnabled(true);
        findViewById(R.id.Amarelo).setEnabled(true);
        findViewById(R.id.verde).setEnabled(true);
        CONTADOR = 0;
        empezarTimer();
        e.setEnabled(false);
    }
    void restablecer(){
        Button start = (Button) findViewById(R.id.start);
        if (CONTADOR == DIFICULT) {
            comprobar();
            cores.clear();
            xogador.clear();
            CONTTEMPO=0;
            start.setEnabled(true);
        }
    }
    void comprobar() {

        TextView nivel = (TextView) findViewById(R.id.nivel);
        if (cores.toString().equals(xogador.toString())) {
            Intent intent = new Intent(getBaseContext(),HasGanado.class);
            startActivity(intent);
            DIFICULT++;
            NVL++;
            nivel.setText("Nivel: "+NVL);
        } else{
            Intent intent = new Intent(getBaseContext(),HasPerdido.class);
            startActivity(intent);
            DIFICULT=4;
            NVL=1;
            nivel.setText("Nivel: "+NVL);}
        findViewById(R.id.Azul).setEnabled(false);
        findViewById(R.id.Rojo).setEnabled(false);
        findViewById(R.id.Amarelo).setEnabled(false);
        findViewById(R.id.verde).setEnabled(false);
    }
    int aleatorio() {

        int al = (int) Math.floor(Math.random() * 4);
        return al;

    }

}
