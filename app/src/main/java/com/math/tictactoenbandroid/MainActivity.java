package com.math.tictactoenbandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import com.math.tictactoenbandroid.R;


public class MainActivity extends AppCompatActivity {
    private boolean playerXTurn = true; // Track the current player (true = X, false = O)
    private String[] board = new String[9]; // Board representation
    private Button[] buttons = new Button[9]; // Button references
    private TextView turnLabel; // Label to show the current turn
    private TextView gameFeedback; // TextView for game feedback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the GridLayout, reset button, turn label, and feedback label
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        Button resetButton = findViewById(R.id.resetButton);
        turnLabel = findViewById(R.id.turnLabel);
        gameFeedback = findViewById(R.id.feedback);

        // Set initial turn label text
        updateTurnLabel();

        // Setup the Tic Tac Toe grid
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            buttons[i] = (Button) gridLayout.getChildAt(i);
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeMove(index);
                }
            });
        }

        // Setup the reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    // Handle player move
    private void makeMove(int index) {
        if (board[index] != null) {
            showFeedback("Cell already taken!");
            return;
        }

        // Update the board and button text
        board[index] = playerXTurn ? "X" : "O";
        buttons[index].setText(board[index]);

        // Check for a winner or draw
        if (checkWinner()) {
            String winner = playerXTurn ? "Player X" : "Player O";
            showFeedback(winner + " wins!");
            turnLabel.setText(winner + " wins!");
            disableBoard();
        } else if (isDraw()) {
            showFeedback("It's a draw!");
            turnLabel.setText("It's a draw!");
        } else {
            // Switch player turn
            playerXTurn = !playerXTurn;
            updateTurnLabel();
        }
    }

    // Update the turn label
    private void updateTurnLabel() {
        String turnText = playerXTurn ? "Player X's Turn" : "Player O's Turn";
        turnLabel.setText(turnText);
    }

    // Show feedback message
    private void showFeedback(String message) {
        gameFeedback.setText(message);
        gameFeedback.setVisibility(View.VISIBLE);
    }

    // Check for a winner
    private boolean checkWinner() {
        int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] pos : winPositions) {
            if (board[pos[0]] != null &&
                board[pos[0]].equals(board[pos[1]]) &&
                board[pos[0]].equals(board[pos[2]])) {
                return true;
            }
        }
        return false;
    }

    // Check if the game is a draw
    private boolean isDraw() {
        for (String cell : board) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    // Disable all buttons on the grid
    private void disableBoard() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    // Reset the game
    private void resetGame() {
        // Clear the board and buttons
        for (int i = 0; i < board.length; i++) {
            board[i] = null;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        playerXTurn = true;
        updateTurnLabel();
        gameFeedback.setText("");
        gameFeedback.setVisibility(View.GONE);
    }
}
