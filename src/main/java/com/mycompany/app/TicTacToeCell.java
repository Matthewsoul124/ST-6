package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    public TicTacToePanel(GridLayout layout) {
        super(layout);
        initializeGame();
        createCells();
    }

    private void initializeGame() {
        game = new Game();
        game.cplayer = game.player1;
    }

    private void createCells() {
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            cells[i] = new TicTacToeCell(i, col, row);
            cells[i].addActionListener(this);
            add(cells[i]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        TicTacToeCell clickedCell = (TicTacToeCell) e.getSource();
        
        if (clickedCell.getMarker() != ' ') return;
        
        clickedCell.setMarker(String.valueOf(game.cplayer.symbol));
        updateGameBoard();
        
        if (game.cplayer == game.player1) {
            makeAIMove();
        }
        
        checkGameState();
    }

    private void updateGameBoard() {
        for (int i = 0; i < 9; i++) {
            game.board[i] = cells[i].getMarker();
        }
    }

    private void makeAIMove() {
        game.player2.move = game.MiniMax(game.board, game.player2);
        if (game.player2.move > 0) {
            cells[game.player2.move - 1].setMarker(String.valueOf(game.player2.symbol));
            updateGameBoard();
        }
    }

    private void checkGameState() {
        game.state = game.checkState(game.board);
        game.symbol = game.cplayer.symbol;

        if (game.state == State.XWIN) {
            JOptionPane.showMessageDialog(this, "X wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else if (game.state == State.OWIN) {
            JOptionPane.showMessageDialog(this, "O wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else if (game.state == State.DRAW) {
            JOptionPane.showMessageDialog(this, "Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else {
            game.cplayer = (game.cplayer == game.player1) ? game.player2 : game.player1;
        }
    }

    private void resetGame() {
        for (TicTacToeCell cell : cells) {
            cell.setMarker(" ");
        }
        Arrays.fill(game.board, ' ');
        game.cplayer = game.player1;
        game.state = State.PLAYING;
    }
}