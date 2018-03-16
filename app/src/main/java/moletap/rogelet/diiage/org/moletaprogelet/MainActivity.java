package moletap.rogelet.diiage.org.moletaprogelet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by steven on 15/03/2018.
 */

public class MainActivity extends AppCompatActivity
{
    public static String NAMEPLAYER = "name_default";

    // je préfère SharedPreferences plûtot que Session
    public static final String MesPrefs = "MyPrefs";
    public static final String DateRegister = "key_date";
    public static final String Name = "key_name";
    public static final String score_nb = "score_nb_";
    public static final String score_miss = "score_miss_";
    public static final String score_min = "score_min_";
    public static final String score_max = "score_max_";
    public static final String score_avg = "score_avg_";
    SharedPreferences sharedpreferences;

    Button btStartGame;
    Button btScore;
    EditText tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MesPrefs, Context.MODE_PRIVATE);
        final String dateCurrent = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // On passe à la vue activity_game
        btStartGame = findViewById(R.id.btnNewGame);
        btStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName = findViewById(R.id.txtName);
                String nameStoredActually = sharedpreferences.getString(Name, tvName.getText().toString());

                /* Si le nom du joueur est renseigné OK sinon Toast;
                   Si le nom est différent que celui stocké, on stock le nouveau avec une nouvelle 'grille' de score */
                if(!tvName.getText().toString().matches("")) {
                    if (!nameStoredActually.equals(tvName.getText().toString())) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(DateRegister, dateCurrent);
                        editor.putString(Name, tvName.getText().toString());
                        editor.commit();
                    }

                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra(NAMEPLAYER, tvName.getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Entrez un nom avant de jouer !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // On passe à la vue activity_game
        btScore = findViewById(R.id.btnScores);
        btScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName = findViewById(R.id.txtName);
                String nameStoredActually = sharedpreferences.getString(Name, tvName.getText().toString());

                /* Si le nom du joueur est renseigné OK sinon Toast;
                   Si le nom est différent que celui stocké, on stock le nouveau avec une nouvelle 'grille' de score */
                if(!tvName.getText().toString().matches("")) {
                    if (!nameStoredActually.equals(tvName.getText().toString())) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(DateRegister, dateCurrent);
                        editor.putString(Name, tvName.getText().toString());
                        editor.commit();
                    }

                    Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
                    intent.putExtra(NAMEPLAYER, tvName.getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Entrez un nom avant de consulter vos scores !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}