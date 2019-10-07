package com.tjdev.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class definicoes extends AppCompatActivity {

    private ImageButton okButton;
    private RadioGroup radioGroup;
    private EditText playerName;
    private SharedPreferences sp;
    private RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int level = sp.getInt("Difficulty",0);


        okButton = findViewById(R.id.okButtonDefId);
        radioGroup = findViewById(R.id.radioGroupId);
        playerName =findViewById(R.id.playerNameInput);

        playerName.setText(MainActivity.playerName);

        if(level == 1){
            radioGroup.clearCheck();
            radioGroup.check(R.id.radioExpertId);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = playerName.getText().toString();

                if(name.trim().length() > 0){
                    sp.edit().putString("playerName",name).apply();
                }

                rb = findViewById(radioGroup.getCheckedRadioButtonId());
                String dif = rb.getText().toString();

                if(dif.trim().equals("Nivel Expert")){
                    sp.edit().putInt("Difficulty",1).apply();
                }else{
                    sp.edit().putInt("Difficulty",0).apply();
                }
                Toast.makeText(definicoes.this, "Definições Salvas", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
