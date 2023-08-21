
package com.example.august11;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.august11.models.Horse;

public class HorseRaceActivity extends AppCompatActivity {

    private static final int NUM_CABALLOS = 10;
    boolean ProgresoCarrera = false;
    boolean BotonParar = false;
    Button PararCaballo, stop;
    EditText NumeroCaballos;
    private Horse[] horses = new Horse[NUM_CABALLOS];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_race);
        LinearLayout layout = findViewById(R.id.horsesLayout);

        for (int i = 1; i <= NUM_CABALLOS; i++) {
            View horseView = getLayoutInflater().inflate(R.layout.horse_layout, layout, false);
            String horseName = "horse" + i;
            int horseImageResource = getResources().getIdentifier(horseName, "drawable", getPackageName());
            horses[i - 1] = new Horse(horseView, horseImageResource, horseName);
            layout.addView(horseView);
        }
        Button startRaceButton = findViewById(R.id.startRaceButton);
        stop = findViewById(R.id.stop);
        PararCaballo = findViewById(R.id.PararCaballo);
        NumeroCaballos = findViewById(R.id.NumeroCaballos);
        startRaceButton.setOnClickListener(v -> startRace());
        stop.setOnClickListener(view -> stopRace());
        PararCaballo.setOnClickListener(view -> stopHorse());

    }


    private void startRace() {
        if (!ProgresoCarrera && !BotonParar) {
            ProgresoCarrera = true;
            for (Horse horse : horses) {
                new Thread(horse).start();
            }
        }
    }
    private void stopRace(){
        if(ProgresoCarrera && !BotonParar) {
            ProgresoCarrera = false;
            for (Horse horse : horses) {
                horse.stopRace();
            }
        }
    }
    private void stopHorse(){
        try{
            int numCaballos = Integer.parseInt(NumeroCaballos.getText().toString());
            if (numCaballos >=1 && numCaballos <=NUM_CABALLOS){
                Horse stop_Horse= horses[numCaballos -1];
                stop_Horse.stopRace();
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "Ingrese un numero", Toast.LENGTH_SHORT).show();
        }
    }

}