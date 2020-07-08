package com.example.tictactoegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount, player1Points, player2Points;

    private TextView txtViewPlayer1, txtViewPlayer2;

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewPlayer1 = findViewById(R.id.txt_view_p1);
        txtViewPlayer2 = findViewById(R.id.txt_view_p2);

        /*-- For loop goes through the board 3X3 times*/
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){

                String buttonID = "btn_" + i + j;  /**---
                 This line made a Null object reference, because I didn't use
                 the exactly same name as XML layout name. I changed buttons_ to btn_ based on my names on XML layout
                 ----*/

                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i] [j] = findViewById (resID);
                buttons[i][j].setOnClickListener (this);
            }
        }
        /*-- For loop goes through the board 3X3 times*/


        /*-- Reset button restart the game --*/
        Button resetButton = findViewById (R.id.reset_btn);
        resetButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                resetGame();
            }
        });
        /*-- Reset button restart the game --*/

    }
    //onCreate

    //Implementing an interface
    @Override
    public void onClick (View view) {

        /*-- In this case, we check if the field has already contain anything or not --*/
        if(!((Button) view).getText().toString().equals("")){
            return;
        }
        /*-- In this case, we check if the field has already contain anything or not --*/

        if(player1Turn){
            ((Button) view).setText("X");
        }else{
            ((Button) view).setText("O");
        }

        roundCount++;

        if(checkForWinner ()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount == 9){
            draw();
        }else{
            player1Turn = ! player1Turn;
        }
    }
    //Implementing an interface

    //Checking the result for winner
    private boolean checkForWinner(){
        String[][] field = new String[3][3];

        /**
         * Here is the row and column to check the winner of the game, at the same time we need to check diagonally as well
         * [0][0] [0][1] [0][2]
         * [1][0] [1][1] [1][2]
         * [2][0] [2][1] [2][2]
         *
         * */
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
            field[i][j] = buttons[i][j].getText ().toString ();
            }
        }

        /*-- Checking the rows --*/
        for(int i = 0; i < 3; i++){
            if(field[i][0].equals (field[i][1])
            && field[i][0].equals(field[i][2])
            && !field[i][0].equals ("")){
                return true;
            }
        }
        /*-- Checking the rows --*/

        /*-- Checking the columns--*/
        for(int i = 0; i < 3; i++){
            if(field[0][i].equals (field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals ("")){
                return true;
            }
        }
        /*-- Checking the columns --*/

        /*-- Checking diagonally --*/
        for(int i = 0; i < 3; i++){
            if(field[0][0].equals (field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals ("")){
                return true;
            }
        }

        for(int i = 0; i < 3; i++){
            if(field[0][2].equals (field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals ("")){
                return true;
            }
        }
        /*-- Checking the diagonally --*/

        return false;
    }
    //Checking the result for winner

    //Methods to identify the winner
    private void player1Wins(){
        player1Points++;
        Toast.makeText (this, "Player 1 won!", Toast.LENGTH_SHORT).show ();
        updatePointsTxt();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText (this, "Player 2 won!", Toast.LENGTH_SHORT).show ();
        updatePointsTxt();
        resetBoard();
    }

    private void draw(){
        Toast.makeText (this, "Draw!", Toast.LENGTH_SHORT).show ();
        resetBoard();
    }

    private void updatePointsTxt(){
        txtViewPlayer1.setText ("Player 1: " + player1Points);
        txtViewPlayer2.setText ("Player 2: " + player2Points);
    }

    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
    //Methods to identify the winner

    //This method will reset the game. It runs inside onCreate() method
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        resetBoard();
        updatePointsTxt();
    }
    //This method will reset the game. It runs inside onCreate() method


    @Override
    protected void onSaveInstanceState (@NonNull Bundle outState){
        super.onSaveInstanceState (outState);

        outState.putInt ("roundCount", roundCount);
        outState.putInt ("player1Points", player1Points);
        outState.putInt ("player2Points", player2Points);
        outState.putBoolean ("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState (@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState (savedInstanceState);

        roundCount = savedInstanceState.getInt ("roundCount");
        player1Points = savedInstanceState.getInt ("player1Points");
        player2Points = savedInstanceState.getInt ("player2Points");
        player1Turn = savedInstanceState.getBoolean ("player1Turn");
    }



}//Main
