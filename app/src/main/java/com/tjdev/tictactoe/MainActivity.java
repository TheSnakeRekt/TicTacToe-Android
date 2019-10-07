package com.tjdev.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private TextView playerScoreView;
    private TextView skillScoreView;
    private TextView playerNameView;
    public static String playerName;

    private ImageButton newGameButton;
    private ImageButton settingsButton;
    private ImageButton exitButton;

    private static final String spName ="LocalStorage";
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerNameView = findViewById(R.id.playerNameId);
        playerScoreView = findViewById(R.id.playerScoreTextId);
        skillScoreView = findViewById(R.id.skillsScoreTextId);

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setPlayerNameView();

        checkScores();

        newGameButton = findViewById(R.id.buttonNovoJogoId);
        settingsButton = findViewById(R.id.buttonDefinId);
        exitButton = findViewById(R.id.buttonSairId);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(MainActivity.this,game_activity.class);
                startActivity(game);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(MainActivity.this,definicoes.class);
                startActivity(game);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setPlayerNameView();
        checkScores();
    }

    public void setPlayerNameView(){
        boolean isSet = sp.getBoolean("playerNameSet",false);
        if(!isSet){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nome");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            builder.setView(input);
            builder.setCancelable(false);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = input.getText().toString();
                    if(!name.trim().equals("")){
                        playerName = name;
                        sp.edit().putString("playerName",name).apply();
                        sp.edit().putBoolean("playerNameSet",true).apply();
                        playerNameView.setText(name);
                        dialog.cancel();
                    }else{
                        Toast.makeText(MainActivity.this, "Insira um nome!", Toast.LENGTH_LONG).show();
                        setPlayerNameView();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        else{
            playerName = sp.getString("playerName","Jogador");
            playerNameView.setText(sp.getString("playerName","Jogador"));
        }
    }

    private void checkScores(){
            skillScoreView.setText(String.valueOf(sp.getInt("Skills_Score",0)));
            playerScoreView.setText(String.valueOf(sp.getInt("Player_Score",0)));
    }
}
