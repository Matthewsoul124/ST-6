package com.mycompany.app;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    public State state;
    public Player player1, player2;
    public Player cplayer; // текущий игрок
    public int nmove;  // последний шаг сделанный действующим игроком 
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
        board = new char[9];   // текущая доска в игре  
        for (int i = 0; i < 9; i++)
            board[i] = ' ';
    }

    // возвращаем состояние игры
    public State checkState(char[] board) {
        State state = State.PLAYING;
        if ((board[0] == symbol && board[1] == symbol && board[2] == symbol) ||
            (board[3] == symbol && board[4] == symbol && board[5] == symbol) ||
            (board[6] == symbol && board[7] == symbol && board[8] == symbol) ||
            (board[0] == symbol && board[3] == symbol && board[6] == symbol) ||
            (board[1] == symbol && board[4] == symbol && board[7] == symbol) ||
            (board[2] == symbol && board[5] == symbol && board[8] == symbol) ||
            (board[0] == symbol && board[4] == symbol && board[8] == symbol) ||
            (board[2] == symbol && board[4] == symbol && board[6] == symbol)) {
            if (symbol == 'X')
                state = State.XWIN;
            else if (symbol == 'O')
                state = State.OWIN;
        } else {
            state = State.DRAW;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    state = State.PLAYING;
                    break;
                }
            }
        }
        return state;
    }

    // сгенерировать возможные ходы
    void generateMoves(char[] board, ArrayList<Integer> move_list) {
        for (int i = 0; i < 9; i++)
            if (board[i] == ' ')
                move_list.add(i);
    }

    // оценка позиции
    int evaluatePosition(char[] board, Player player) {
        State state = checkState(board);
        if ((state == State.XWIN || state == State.OWIN || state == State.DRAW)) {
            if ((state == State.XWIN && player.symbol == 'X') || (state == State.OWIN && player.symbol == 'O'))
                return +Game.INF;
            else if ((state == State.XWIN && player.symbol == 'O') || (state == State.OWIN && player.symbol == 'X'))
                return -Game.INF;
            else if (state == State.DRAW)
                return 0;
        }
        return -1;
    }

    int MiniMax(char[] board, Player player) // выбор наилучшего хода
    {
        int best_val = -Game.INF, index = 0;
        ArrayList<Integer> move_list = new ArrayList<>();
        int[] best_moves = new int[9];

        generateMoves(board, move_list);

        while (move_list.size() != 0) {
            board[move_list.get(0)] = player.symbol;
            symbol = player.symbol;

            int val = MinMove(board, player);

            if (val > best_val) {
                best_val = val;
                index = 0;
                best_moves[index] = move_list.get(0) + 1;
            } else if (val == best_val)
                best_moves[++index] = move_list.get(0) + 1;

            System.out.printf("\nminimax: %3d(%1d) ", 1 + move_list.get(0), val);
            board[move_list.get(0)] = ' ';
            move_list.remove(0);
        }
        if (index > 0) {
            Random r = new Random();
            index = r.nextInt(index);
        }

        System.out.printf("\nminimax best: %3d(%1d) ", best_moves[index], best_val);
        System.out.printf("Steps counted: %d", q);
        q = 0;
        return best_moves[index];
    }

    int MinMove(char[] board, Player player) {
        int pos_value = evaluatePosition(board, player);
        if (pos_value != -1)
            return pos_value;
        q++;
        int best_val = +Game.INF;
        ArrayList<Integer> move_list = new ArrayList<>();

        generateMoves(board, move_list);

        while (move_list.size() != 0) {
            symbol = (player.symbol == 'X') ? 'O' : 'X';
            board[move_list.get(0)] = symbol;

            int val = MaxMove(board, player);

            if (val < best_val) {
                best_val = val;
            }
            board[move_list.get(0)] = ' ';
            move_list.remove(0);
        }
        return best_val;
    }

    int MaxMove(char[] board, Player player) {
        int pos_value = evaluatePosition(board, player);
        if (pos_value != -1)
            return pos_value;
        q++;
        int best_val = -Game.INF;
        ArrayList<Integer> move_list = new ArrayList<>();
        generateMoves(board, move_list);
        while (move_list.size() != 0) {
            symbol = (player.symbol == 'X') ? 'X' : 'O';
            board[move_list.get(0)] = symbol;
            int val = MinMove(board, player);
            if (val > best_val) {
                best_val = val;
            }
            board[move_list.get(0)] = ' ';
            move_list.remove(0);
        }
        return best_val;
    }
} 