package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerStatus;
    private Button [] buttons = new Button[9];
    private Button newGame;

    private int roundCount;
    boolean activePlayer;

    // P1 => 0
    // P2 => 1
    // empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {1,4,7}, {2,5,8}, // columns
            {0,4,8}, {2,4,6} // diagnol
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStatus = (TextView) findViewById(R.id.playerStatus);

        newGame = (Button) findViewById(R.id.btnNewGame);

        for(int i = 0; i < buttons.length; i++) {
            String buttonID = "btn" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        activePlayer = true;

    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); // btn2
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); // 2

        if (activePlayer) {
            playerStatus.setText("Player O's turn");
            ((Button) v).setText("X");
            gameState[gameStatePointer] = 0;
        }else {
            playerStatus.setText("Player X's turn");
            ((Button) v).setText("O");
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                playerStatus.setText("X wins!");
            }else {
                playerStatus.setText("O wins!");
            }
        }else if(roundCount == 9){
            playerStatus.setText("It's a tie!");
        }else {
            activePlayer = !activePlayer;
        }

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerStatus.setText("Player X's turn");
            }
        });
    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int [] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
            gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
            gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void playAgain() {
        roundCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}