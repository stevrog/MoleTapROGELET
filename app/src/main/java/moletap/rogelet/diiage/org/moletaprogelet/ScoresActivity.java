package moletap.rogelet.diiage.org.moletaprogelet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static moletap.rogelet.diiage.org.moletaprogelet.MainActivity.MesPrefs;
import static moletap.rogelet.diiage.org.moletaprogelet.MainActivity.NAMEPLAYER;

/**
 * Created by steven on 15/03/2018.
 */

public class ScoresActivity extends AppCompatActivity
{
    SharedPreferences sharedpreferences;
    TextView titleScore;
    TextView tvNbPoints;
    TextView tvMissMole;
    TextView tvMinReact;
    TextView tvMaxReact;
    TextView tvAvgReact;
    String namePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        sharedpreferences = getSharedPreferences(MesPrefs, Context.MODE_PRIVATE);

        Intent extra = getIntent();
        namePlayer =  extra.getStringExtra(NAMEPLAYER);
        titleScore = findViewById(R.id.titleScore);
        titleScore.setText(namePlayer + " - Score de votre dernière partie");

        tvNbPoints = findViewById(R.id.tvPoint);
        tvMissMole = findViewById(R.id.tvMissMole);
        tvMinReact = findViewById(R.id.tvMinReact);
        tvMaxReact = findViewById(R.id.tvMaxReact);
        tvAvgReact = findViewById(R.id.tvAvgReact);

        tvNbPoints.setText(""+sharedpreferences.getInt("score_nb_"+namePlayer, 0));
        tvMissMole.setText(""+sharedpreferences.getInt("score_miss_"+namePlayer, 0));
        tvMinReact.setText(""+sharedpreferences.getInt("score_min_"+namePlayer, 0));
        tvMaxReact.setText(""+sharedpreferences.getInt("score_max_"+namePlayer, 0));
        tvAvgReact.setText(""+sharedpreferences.getInt("score_avg_"+namePlayer, 0));

    }

}
