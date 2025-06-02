package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Program {
    public static void initializeBoard(char[][] board) {
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }
    }

    public static boolean isBoardEmpty(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell != ' ') return false;
            }
        }
        return true;
    }

    public static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') return false;
            }
        }
        return true;
    }

    public static void makeMove(char[][] board, int row, int col, char symbol) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            board[row][col] = symbol;
        }
    }

    public static boolean checkWin(char[][] board, char symbol) {
        // Проверка строк и столбцов
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        // Проверка диагоналей
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
               (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    public static int minimax(char[][] board, int depth, boolean isMaximizing) {
        if (checkWin(board, 'X')) return 10 - depth;
        if (checkWin(board, 'O')) return depth - 10;
        if (isBoardFull(board)) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new TicTacToePanel(new GridLayout(3, 3)));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}