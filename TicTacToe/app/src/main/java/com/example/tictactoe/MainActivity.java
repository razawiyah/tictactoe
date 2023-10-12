package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0};
    private int playerTurn = 1;
    private int totalSelectableBoxes = 1;
    private LinearLayout playerOneLayout, playerTwoLayout;

    TextView playerOne, playerTwo;
    ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOne = findViewById(R.id.playerOne);
        playerTwo = findViewById(R.id.playerTwo);

        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);


        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        box3 = findViewById(R.id.box3);
        box4 = findViewById(R.id.box4);
        box5 = findViewById(R.id.box5);
        box6 = findViewById(R.id.box6);
        box7 = findViewById(R.id.box7);
        box8 = findViewById(R.id.box8);
        box9 = findViewById(R.id.box9);

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});

        String getPlayerOne = getIntent().getStringExtra("playerOne");
        String getPlayerTwo = getIntent().getStringExtra("playerTwo");

        playerOne.setText(getPlayerOne);
        playerTwo.setText(getPlayerTwo);

        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(0)){
                    performAction((ImageView) view,0);
                }
            }
        });

        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(1)){
                    performAction((ImageView) view,1);

                }
            }
        });

        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(2)){
                    performAction((ImageView) view,2);

                }
            }
        });

        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(3)){
                    performAction((ImageView) view,3);

                }
            }
        });

        box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(4)){
                    performAction((ImageView) view,4);

                }
            }
        });

        box6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(5)){
                    performAction((ImageView) view,5);

                }
            }
        });

        box7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(6)){
                    performAction((ImageView) view,6);

                }
            }
        });

        box8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(7)){
                    performAction((ImageView) view,7);

                }
            }
        });

        box9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(8)){
                    performAction((ImageView) view,8);

                }
            }
        });
    }

    private boolean isBoxSelectable(int boxPosition){
        boolean response = false;

        if(boxPositions[boxPosition] == 0){
            response = true;
        }

        return response;
    }

    private void performAction(ImageView imageView, int selectedBoxPosition){
        boxPositions[selectedBoxPosition] = playerTurn;

        if(playerTurn == 1){
            imageView.setImageResource(R.drawable.logo_cross);

            if(checkPlayerWin()){
                WinDialog winDialog = new WinDialog(MainActivity.this, "Congratulations!\n" + playerTwo.getText().toString() + "Won This Round!", MainActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
            }else if(totalSelectableBoxes == 9){
                WinDialog winDialog = new WinDialog(MainActivity.this, "It is a draw!", MainActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
            }else {
                changePlayerTurn(2);
                totalSelectableBoxes++;
            }
        }else {
            imageView.setImageResource(R.drawable.logo_circle);
            if(checkPlayerWin()){
                WinDialog winDialog = new WinDialog(MainActivity.this, "Congratulations!\n" + playerOne.getText().toString() + " Won This Round!", MainActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
            }else if(totalSelectableBoxes == 9){
                WinDialog winDialog = new WinDialog(MainActivity.this, "It is a draw!", MainActivity.this);
                winDialog.setCancelable(false);
                winDialog.show();
            }else {
                changePlayerTurn(1);
                totalSelectableBoxes++;
            }
        }
    }

    private  void changePlayerTurn(int currentPlayerTurn){
        playerTurn = currentPlayerTurn;

        if(playerTurn == 1){
            playerOne.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black_licorice));
            playerOneLayout.setBackgroundResource(R.color.snow_white);
            playerTwoLayout.setBackgroundResource(R.color.black_licorice);
        }else {
            playerTwo.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black_licorice));
            playerOneLayout.setBackgroundResource(R.color.black_licorice);
            playerTwoLayout.setBackgroundResource(R.color.snow_white);
        }
    }

    private boolean checkPlayerWin(){
        boolean response = false;

        for(int i=0; i<combinationList.size(); i++){
            final int[] combination = combinationList.get(i);

            if(boxPositions[combination[0]] == playerTurn &&  boxPositions[combination[1]] == playerTurn && boxPositions[combination[2]] == playerTurn){
                response = true;
            }
        }
        return response;
    }

    public void restartGame(){
        boxPositions = new int[]{0,0,0,0,0,0,0,0,0};

        playerTurn = 1;

        totalSelectableBoxes = 1;

        box1.setImageResource(R.color.black_licorice);
        box2.setImageResource(R.color.black_licorice);
        box3.setImageResource(R.color.black_licorice);
        box4.setImageResource(R.color.black_licorice);
        box5.setImageResource(R.color.black_licorice);
        box6.setImageResource(R.color.black_licorice);
        box7.setImageResource(R.color.black_licorice);
        box8.setImageResource(R.color.black_licorice);
        box9.setImageResource(R.color.black_licorice);

    }
}