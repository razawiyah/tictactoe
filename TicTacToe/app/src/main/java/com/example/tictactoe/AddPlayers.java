package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayers extends AppCompatActivity {

    EditText playerOne, playerTwo;
    Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        playerOne = findViewById(R.id.playerOne);
        playerTwo = findViewById(R.id.playerTwo);

        playBtn = findViewById(R.id.playBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerOneName = playerOne.getText().toString();
                String playerTwoName = playerTwo.getText().toString();

                if(playerOneName.isEmpty() || playerTwoName.isEmpty()){
                    Toast.makeText(AddPlayers.this,"Enter Player Names To Proceed!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(AddPlayers.this, MainActivity.class);
                    intent.putExtra("playerOne",playerTwoName);
                    intent.putExtra("playerTwo",playerOneName);
                    startActivity(intent);
                }

            }
        });
    }
}