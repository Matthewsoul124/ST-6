package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    private void createCell(int num, int x, int y) {
        cells[num] = new TicTacToeCell(num, x, y);
        cells[num].addActionListener(this);
        add(cells[num]);
    }

    public TicTacToePanel(GridLayout layout) {
        super(layout);
        createCell(0, 0, 0);
        createCell(1, 1, 0);
        createCell(2, 2, 0);
        createCell(3, 0, 1);
        createCell(4, 1, 1);
        createCell(5, 2, 1);
        createCell(6, 0, 2);
        createCell(7, 1, 2);
        createCell(8, 2, 2);
        game = new Game();
        game.cplayer = game.player1;
    }

    public void actionPerformed(ActionEvent ae) {
        game.player1.move = -1;
        game.player2.move = -1;

        int i = 0;
        for (TicTacToeCell jb : cells) {
            if (ae.getSource() == jb) {
                jb.setMarker(Character.toString(game.cplayer.symbol));
            }
            game.board[i++] = jb.getMarker();
        }

        if (game.cplayer == game.player1) {
            game.player2.move = game.MiniMax(game.board, game.player2);
            game.nmove = game.player2.move;
            game.symbol = game.player2.symbol;
            game.cplayer = game.player2;
            if (game.player2.move > 0)
                cells[game.player2.move - 1].doClick();
        } else {
            game.nmove = game.player1.move;
            game.symbol = game.player1.symbol;
            game.cplayer = game.player1;
        }

        game.state = game.checkState(game.board);

        if (game.state == State.XWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли крестики", "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } else if (game.state == State.OWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли нолики", "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } else if (game.state == State.DRAW) {
            JOptionPane.showMessageDialog(null, "Ничья", "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
} 