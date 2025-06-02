package com.mycompany.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Game {
    public State state;
    public Player player1, player2;
    public Player cplayer;
    public int nmove;
    public char symbol;
    public static final int INF = 100;
    public int q;
    public char[] board;

    public Game() {
        player1 = new Player();
        player2 = new Player();
        player1.symbol = 'X';
        player2.symbol = 'O';
        state = State.PLAYING;
        board = new char[9];
        Arrays.fill(board, ' ');
    }

    public State checkState(char[] board) {
        // Проверка линий
        int[][] lines = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},
                        {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
        
        for (int[] line : lines) {
            if (board[line[0]] == symbol && board[line[1]] == symbol && board[line[2]] == symbol) {
                return symbol == 'X' ? State.XWIN : State.OWIN;
            }
        }
        
        // Проверка ничьи
        for (char cell : board) {
            if (cell == ' ') return State.PLAYING;
        }
        
        return State.DRAW;
    }

    void generateMoves(char[] board, ArrayList<Integer> move_list) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') move_list.add(i);
        }
    }

    int evaluatePosition(char[] board, Player player) {
        State state = checkState(board);
        if (state != State.PLAYING) {
            if ((state == State.XWIN && player.symbol == 'X') || 
                (state == State.OWIN && player.symbol == 'O')) {
                return INF - q;
            } else if ((state == State.XWIN && player.symbol == 'O') || 
                      (state == State.OWIN && player.symbol == 'X')) {
                return q - INF;
            }
            return 0;
        }
        return -1;
    }

    int MiniMax(char[] board, Player player) {
        int best_val = -INF, index = 0;
        ArrayList<Integer> move_list = new ArrayList<>();
        int[] best_moves = new int[9];

        generateMoves(board, move_list);

        for (int move : move_list) {
            board[move] = player.symbol;
            symbol = player.symbol;

            int val = MinMove(board, player);

            if (val > best_val) {
                best_val = val;
                index = 0;
                best_moves[index] = move + 1;
            } else if (val == best_val) {
                best_moves[++index] = move + 1;
            }

            board[move] = ' ';
        }

        if (index > 0) {
            Random r = new Random();
            index = r.nextInt(index + 1);
        }

        return best_moves[index];
    }

    int MinMove(char[] board, Player player) {
        int pos_value = evaluatePosition(board, player);
        if (pos_value != -1) return pos_value;
        q++;
        
        int best_val = INF;
        ArrayList<Integer> move_list = new ArrayList<>();
        generateMoves(board, move_list);

        for (int move : move_list) {
            symbol = (player.symbol == 'X') ? 'O' : 'X';
            board[move] = symbol;
            int val = MaxMove(board, player);
            best_val = Math.min(best_val, val);
            board[move] = ' ';
        }
        return best_val;
    }

    int MaxMove(char[] board, Player player) {
        int pos_value = evaluatePosition(board, player);
        if (pos_value != -1) return pos_value;
        q++;
        
        int best_val = -INF;
        ArrayList<Integer> move_list = new ArrayList<>();
        generateMoves(board, move_list);

        for (int move : move_list) {
            symbol = (player.symbol == 'X') ? 'X' : 'O';
            board[move] = symbol;
            int val = MinMove(board, player);
            best_val = Math.max(best_val, val);
            board[move] = ' ';
        }
        return best_val;
    }
}