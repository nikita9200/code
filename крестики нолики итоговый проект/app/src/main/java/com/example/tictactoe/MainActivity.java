package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0: is O; 1: is X; 2 is empty
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameActive = true;


    public void dropIn(View view){
        ImageView counter = (ImageView) view;

//        Log.i("tag", counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 & gameActive){

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if(activePlayer == 1 ){
                counter.setImageResource(R.drawable.x);
                activePlayer = 0;
            }
            else{
                counter.setImageResource(R.drawable.o);
                activePlayer = 1;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(500);

            for(int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    //                someone has won
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 0) {
                        winner = "X's";
                    } else {
                        winner = "O's";
                    }

                    Toast.makeText(this, winner + " are a winner", Toast.LENGTH_SHORT).show();

                    Button resetButton = (Button) findViewById(R.id.resetButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner + " the winner ! :)");

                    resetButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void resetGame(View view){

        Button resetButton =  findViewById(R.id.resetButton);
        TextView winnerTextView =  findViewById(R.id.winnerTextView);

        resetButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<grid.getChildCount(); i++){
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }

        activePlayer = 0;
        gameActive = true;

        for(int i=0; i<gameState.length;i++){
            gameState[i] = 2;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
