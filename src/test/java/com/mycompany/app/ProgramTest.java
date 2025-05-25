package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProgramTest {
    
    @Test
    public void testEmptyBoard() {
        char[][] board = new char[3][3];
        Program.initializeBoard(board);
        assertTrue(Program.isBoardEmpty(board));
    }

    @Test
    public void testMakeMove() {
        char[][] board = new char[3][3];
        Program.initializeBoard(board);
        Program.makeMove(board, 0, 0, 'X');
        assertEquals('X', board[0][0]);
    }

    @Test
    public void testCheckWin() {
        char[][] board = new char[3][3];
        Program.initializeBoard(board);
        // Test horizontal win
        board[0][0] = 'X';
        board[0][1] = 'X';
        board[0][2] = 'X';
        assertTrue(Program.checkWin(board, 'X'));
    }

    @Test
    public void testCheckDraw() {
        char[][] board = new char[3][3];
        Program.initializeBoard(board);
        // Fill board with alternating X and O
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (i + j) % 2 == 0 ? 'X' : 'O';
            }
        }
        assertTrue(Program.isBoardFull(board));
    }

    @Test
    public void testMinimax() {
        char[][] board = new char[3][3];
        Program.initializeBoard(board);
        // Test minimax evaluation for a winning position
        board[0][0] = 'X';
        board[0][1] = 'X';
        board[0][2] = ' ';
        int score = Program.minimax(board, 0, true);
        assertTrue(score > 0); // Should favor X's winning position
    }
} 