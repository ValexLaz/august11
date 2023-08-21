
package com.example.august11.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.august11.R;

public class Horse implements Runnable {
    private final ImageView horseImage;
    public LinearLayout horsesLayout;
    TextView porcentajetxt,nombrecaballo;
    private static final int MAX_DISTANCE = 1000;
    boolean ProgresoCarrera = true;
    private String horseName;
    private static boolean ganador = false;
    private static Horse primerGanador = null;



    public Horse(View view, int horseImageResource, String horseName) {
        horseImage = view.findViewById(R.id.horseImage);
        horsesLayout = view.findViewById(R.id.racetrackLayout);
        horseImage.setImageResource(horseImageResource);
        nombrecaballo = horsesLayout.findViewById(R.id.nombrecaballo);

        porcentajetxt = horsesLayout.findViewById(R.id.porcentajetxt);
        nombrecaballo.setText(horseName);
        this.horseName = horseName;
    }


    @Override
    public void run() {
        int distance = 0;
        while (distance < MAX_DISTANCE && ProgresoCarrera) {
            try {
                Thread.sleep((long) (Math.random() * 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            distance += (int) (Math.random() * 10);
            int finalDistance = Math.min(distance, MAX_DISTANCE);
            float progress = (float) finalDistance / MAX_DISTANCE;
            horseImage.post(() -> {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) horseImage.getLayoutParams();
                params.leftMargin = (int) (progress * (horsesLayout.getWidth() - horseImage.getWidth()));
                horseImage.setLayoutParams(params);
                int porcentaje = (int) (progress * 100);
                porcentajetxt.setText(porcentaje + " %");

                if (finalDistance >= MAX_DISTANCE && ProgresoCarrera &&!ganador) {
                    ganador = true;
                    ProgresoCarrera = false;
                    primerGanador = this;
                    horseImage.post(() -> {
                        Toast.makeText(horsesLayout.getContext(), "¡Ganó " + horseName + "!", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }
    public void stopRace(){
        ProgresoCarrera = false;
    }
    public boolean isPrimerGanador(){
        return this == primerGanador;
    }

}