package com.tjdev.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game_activity extends AppCompatActivity {


    private TextView playerName;
    private TextView playerScoreView;
    private TextView skillsScoreView;
    private TextView winnerInfo;
    private Button newGame;
    private Button exit;
    private List<String> currentPlayer;
    private Button buttons[];
    private SharedPreferences sp;
    private int round = 0;
    private int random;
    private int level;
    int [][] boardStatus ;
    private int lastPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);


        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boardStatus = new int[3][3];

        currentPlayer = new ArrayList<>();
        currentPlayer.add("X");
        currentPlayer.add("O");

        playerName = findViewById(R.id.textPlayerNameId);
        playerScoreView = findViewById(R.id.textPlayerScoreId);
        skillsScoreView = findViewById(R.id.textSkillsScoreId);


        playerName.setText("  "+sp.getString("playerName","Jogador"));
        level = sp.getInt("Difficulty",0);


        if(level == 0){
            Log.e("Level","Facil");
        }else{
            Log.e("Level","Expert");
        }

        newGame = findViewById(R.id.buttonNovoId);
        exit = findViewById(R.id.buttonSairId);


        buttons = new Button[9];
        for(int i = 0; i<9; i++){

            buttons[i] = findViewById(R.id.b0+i);
            buttons[i].setOnClickListener(myListener);
            buttons[i].setTag(i);
        }

        checkScore();

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeBoardStatus();
                for(int i = 0; i<9; i++){

                    buttons[i] = findViewById(R.id.b0+i);
                    buttons[i].setOnClickListener(myListener);
                    buttons[i].setText("");
                    buttons[i].setTag(i);
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        winnerInfo = findViewById(R.id.winnerInfo);
        winnerInfo.setText("");
        initializeBoardStatus();
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        public void onClick(View v) {

            Object tag = v.getTag();
            int j = Integer.valueOf(v.getTag().toString());
            buttons[j].setText(currentPlayer.get(round));

            switch (v.getId()){
                case R.id.b0:
                    if(round == 0){
                        boardStatus  [0][0] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [0][0] = 0;
                    }
                    break;

                case R.id.b1:
                    if(round == 0){
                        boardStatus  [0][1] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [0][1] = 0;
                    }
                    break;


                case R.id.b2:
                    if(round == 0){
                        boardStatus  [0][2] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [0][2] = 0;
                    }
                    break;

                case R.id.b3:
                    if(round == 0){
                        boardStatus  [1][0] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [1][0] = 0;
                    }
                    break;

                case R.id.b4:
                    if(round == 0){
                        boardStatus  [1][1] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [1][1] = 0;
                    }
                    break;


                case R.id.b5:
                    if(round == 0){
                        boardStatus  [1][2] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [1][2] = 0;
                    }
                    break;

                case R.id.b6:
                    if(round == 0){
                        boardStatus  [2][0] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [2][0] = 0;
                    }
                    break;

                case R.id.b7:
                    if(round == 0){
                        boardStatus  [2][1] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [2][1] = 0;
                    }
                    break;


                case R.id.b8:
                    if(round == 0){
                        boardStatus  [2][2] = 1;
                    }else if(round == 1)
                    {
                        boardStatus  [2][2] = 0;
                    }
                    break;
            }

            iterateGame(j);
        }
    };

    private void iterateGame(int j){
        if(!checkWinner())
        {
            checkScore();
            return;
        }else{
            if(round == 0){
                lastPlay = j;
                round++;
                skillTime();
            }
            else{
                round--;
            }
        }
    }

    private boolean checkWinner(){
        int emptyFields = 0;

        for(int t = 0 ;  t<9; t++){
            String p = buttons[t].getText().toString();
            if(p == ""){
                emptyFields++;
            }
        }

        if(emptyFields == 1){
            winnerInfo.setText("Empate !");
            for(int t = 0 ;  t<9; t++){
                buttons[t].setOnClickListener(null);
            }
            return false;
        }

        for(int i=0; i<3; i++){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if (boardStatus[i][0]==1){
                    winnerInfo.setText("O Vencedor é o "+sp.getString("playerName","Jogador"));
                    int score = sp.getInt("Player_Score",0);
                    score++;
                    sp.edit().putInt("Player_Score",score).commit();
                    return false;
                }
                else if (boardStatus[i][0]==0) {
                    winnerInfo.setText("O Vencedor é o Skills");
                    int score = sp.getInt("Skills_Score",0);
                    score++;
                    sp.edit().putInt("Skills_Score",score).commit();
                    return false;
                }
            }
        }

        for(int i=0; i<3; i++){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if (boardStatus[0][i]==1){
                    winnerInfo.setText("O Vencedor é o "+sp.getString("playerName","Jogador"));
                    int score = sp.getInt("Player_Score",0);
                    score++;
                    sp.edit().putInt("Player_Score",score).commit();
                    return false;
                }
                else if (boardStatus[0][i]==0) {
                    winnerInfo.setText("O Vencedor é o Skills");
                    int score = sp.getInt("Skills_Score",0);
                    score++;
                    sp.edit().putInt("Skills_Score",score).commit();
                    return false;
                }

            }
        }


        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if (boardStatus[0][0]==1){
                winnerInfo.setText("O Vencedor é o "+sp.getString("playerName","Jogador"));
                int score = sp.getInt("Player_Score",0);
                score++;
                sp.edit().putInt("Player_Score",score).commit();
                return false;
            }
            else if (boardStatus[0][0]==0) {
                winnerInfo.setText("O Vencedor é o Skills");
                int score = sp.getInt("Skills_Score",0);
                score++;
                sp.edit().putInt("Skills_Score",score).commit();
                return false;
            }

        }


        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if (boardStatus[0][2]==1){
                winnerInfo.setText("O Vencedor é o "+sp.getString("playerName","Jogador"));
                int score = sp.getInt("Player_Score",0);
                score++;
                sp.edit().putInt("Player_Score",score).commit();
                return false;
            }
            else if (boardStatus[0][2]==0) {
                winnerInfo.setText("O Vencedor é o Skills");
                int score = sp.getInt("Skills_Score",0);
                score++;
                sp.edit().putInt("Skills_Score",score).commit();
                return false;
            }
        }
        return  true;
    }

    private void skillTime() {
        String played;
        if (level == 0){
            do {
                random = new Random().nextInt(9);
                played = buttons[random].getText().toString();
            } while (played.equals("X") || played.equals("O"));

         buttons[random].callOnClick();
        }else{

            played = buttons[4].getText().toString();

            if(played == ""){
                buttons[4].callOnClick();
            }else{

                    if(lastPlay == 0 || lastPlay == 2 || lastPlay == 6 || lastPlay == 8){
                        do{
                            random = new Random().nextInt(9);
                            if(random + 2 <= 8){
                                played = buttons[random + 2].getText().toString();
                                lastPlay = random + 2;
                            }else if(random + 4 <= 8){
                                played = buttons[random + 4].getText().toString();
                                lastPlay = random + 4;
                            }else if(random + 6 <= 8){
                                played = buttons[random + 6].getText().toString();
                                lastPlay = random + 6;
                            }else if(random - 6 >= 0){
                                played = buttons[random  - 6].getText().toString();
                                lastPlay = random - 6;
                            }else if(random - 4 >= 0){
                                played = buttons[random - 4].getText().toString();
                                lastPlay = random - 4;
                            }else if(random - 2 >= 0){
                                played = buttons[random - 2].getText().toString();
                                lastPlay = random - 2;
                            }

                        }while (played.equals("X") || played.equals("O"));
                        buttons[lastPlay].callOnClick();
                    }else{
                        do {
                            random = new Random().nextInt(9);
                            if (random - 1 > 0 && random - 4 > 0) {
                                int tmp = new Random().nextInt(2);
                                if (tmp == 0) {
                                    played = buttons[random - 1].getText().toString();
                                    if(!played.equals("X") && !played.equals("O")){
                                        random = random - 1;
                                    }
                                } else {
                                    played = buttons[random - 4].getText().toString();
                                    if(!played.equals("X") && !played.equals("O")){
                                        random = random - 4;
                                    }
                                }

                            } else if (random + 1 < 8 && random + 4 < 8) {
                                int tmp = new Random().nextInt(2);
                                if (tmp == 0) {
                                    played = buttons[random + 1].getText().toString();
                                    if(!played.equals("X") && !played.equals("O")){
                                        random = random + 1;
                                    }
                                } else {
                                    played = buttons[random + 4].getText().toString();
                                    if(!played.equals("X") && !played.equals("O")){
                                        random = random + 4;
                                    }
                                }
                            } else {
                                played = buttons[random].getText().toString();
                            }
                        } while (played.equals("X") || played.equals("O"));

                        buttons[random].callOnClick();
                    }
            }
        }
    }

    private void initializeBoardStatus(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardStatus[i][j] = -1;
            }
        }

        round = 0;
        winnerInfo.setText("");

    }

    private void checkScore(){
       SharedPreferences spScore = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       skillsScoreView.setText(String.valueOf(spScore.getInt("Skills_Score",0)));
       playerScoreView.setText(String.valueOf(spScore.getInt("Player_Score",0)));
    }
}
