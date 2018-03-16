package moletap.rogelet.diiage.org.moletaprogelet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import static moletap.rogelet.diiage.org.moletaprogelet.MainActivity.MesPrefs;
import static moletap.rogelet.diiage.org.moletaprogelet.MainActivity.NAMEPLAYER;
import static moletap.rogelet.diiage.org.moletaprogelet.MainActivity.score_miss;
import static moletap.rogelet.diiage.org.moletaprogelet.MainActivity.score_nb;

/**
 * Created by steven on 15/03/2018.
 */

public class GameActivity extends AppCompatActivity
{
    int cpt = 5;
    int numImg = 0;
    int lastMole = 0;
    int nbPoint = 0;
    int totalMole = 0;
//    ArrayList<Long> listClick = new ArrayList<>();
//    long speedClick = 0;
//    long initialTime = 0;
//    long endTime = 0;
//    long minClick = 400;
//    long maxClick = 0;
//    long avgClick = 0;
    boolean finished = false;
    int clickIdMole = 0;
    int currentIdMole = 0;
    final Random imgRandom = new Random();
    private Handler handler = new Handler();
    private Handler handlerMole = new Handler();
    private Handler handlerScore = new Handler();
    private TextView tvChrono;
    ImageButton imgMole;
    String namePlayer;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sharedpreferences = getSharedPreferences(MesPrefs, Context.MODE_PRIVATE);
        Intent extra = getIntent();
        namePlayer =  extra.getStringExtra(NAMEPLAYER);

        tvChrono = findViewById(R.id.txtChrono);
        startTimer();
        startTimerMole();
    }

    // Gestion du Timer
    Runnable runnableGame = new Runnable() {
        @Override
        public void run() {
            if(cpt > 0) {
                startTimer();
                tvChrono.setText("Temps restant: " + cpt + "s");
                cpt--;
            } else {
                onStop();
                onStopMole();

                finished = true;
                tvChrono.setText("Temps écoulé !!! \n" + nbPoint + "/" + totalMole +  " taupes touchées");

//                for(long unClick : listClick) {
//                    if(unClick < minClick) {
//                        minClick = unClick;
//                    }
//                    if(unClick > maxClick) {
//                        maxClick = unClick;
//                    }
//
//                    avgClick += unClick;
//                }

                // affichage de toutes les taupes à la fin du chrono
                for(int i=1; i<10; i++) {
                    hideShowMole(i);
                    imgMole.setImageDrawable(getResources().getDrawable(R.drawable.lilmole));
                }

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(score_nb + namePlayer, nbPoint);
                editor.putInt(score_miss + namePlayer, totalMole-nbPoint);
                editor.commit();

                startActivityScore();
            }
        }
    };

    // Gestion des taupes durant le chrono
    Runnable runnableMole = new Runnable() {
        @Override
        public void run() {
            startTimerMole();
            imgMole = null;
            // on cache la dernière taupe affichée
            if(lastMole != 0) {
                hideShowMole(lastMole);
                imgMole.setImageDrawable(null);
            }

            // on sélectionne une taupe au hasard pour l'afficher
            numImg = imgRandom.nextInt(10 - 1) + 1;
            hideShowMole(numImg);

    //        final long start = System.currentTimeMillis();
            currentIdMole = imgMole.getId();
            imgMole.setImageDrawable(getResources().getDrawable(R.drawable.lilmole));
            imgMole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentIdMole != clickIdMole) {

                        if (!finished) {
                            onStopMole();
                            startTimerMole();
                            imgMole.setImageDrawable(null);
    //                        listClick.add(speedClick);
    //                        long end = System.currentTimeMillis();
    //                        speedClick = ((end - start) / 1000);
    //                        initialTime = 0;
                            clickIdMole = currentIdMole;
                            nbPoint++;
                        }
                    }
                }
            });

            lastMole = numImg;
            totalMole++;
        }
    };

    // une fois la partie terminée, redirection sur la page des scores 2 secondes après
    Runnable runnableScore = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(GameActivity.this, ScoresActivity.class);
            intent.putExtra(NAMEPLAYER, namePlayer);
            startActivity(intent);
        }
    };

    public void startTimer() {
        handler.postDelayed(runnableGame, 1000);
    }

    public void startTimerMole() { handlerMole.postDelayed(runnableMole, 500); }

    public void startActivityScore() {
        handlerScore.postDelayed(runnableScore, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnableGame);
    }

    protected void onStopMole() {
        super.onStop();
        handlerMole.removeCallbacks(runnableMole);
    }

    // permet de récupérer la mole selon le numéro reçu par le random;
    public void hideShowMole(int i) {
        switch (i) {
            case 1:
                imgMole = findViewById(R.id.mole1);
                break;
            case 2:
                imgMole = findViewById(R.id.mole2);
                break;
            case 3:
                imgMole = findViewById(R.id.mole3);
                break;
            case 4:
                imgMole = findViewById(R.id.mole4);
                break;
            case 5:
                imgMole = findViewById(R.id.mole5);
                break;
            case 6:
                imgMole = findViewById(R.id.mole6);
                break;
            case 7:
                imgMole = findViewById(R.id.mole7);
                break;
            case 8:
                imgMole = findViewById(R.id.mole8);
                break;
            case 9:
                imgMole = findViewById(R.id.mole9);
        }
    }

}
