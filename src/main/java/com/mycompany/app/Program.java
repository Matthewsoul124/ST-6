package com.mycompany.app;

// Реализация игры "Крестики-нолики" (3x3)
// Минимаксный алгоритм

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Program {
    public static FileWriter fileWriter;
    public static PrintWriter printWriter;

    public static void initializeBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public static boolean isBoardEmpty(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void makeMove(char[][] board, int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    public static boolean checkWin(char[][] board, char symbol) {
        // Проверка строк
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
        }
        // Проверка столбцов
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol) {
                return true;
            }
        }
        // Проверка диагоналей
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true;
        }
        return false;
    }

    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int minimax(char[][] board, int depth, boolean isMaximizing) {
        if (checkWin(board, 'X')) {
            return 10 - depth;
        }
        if (checkWin(board, 'O')) {
            return depth - 10;
        }
        if (isBoardFull(board)) {
            return 0;
        }

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

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Demo");
        frame.add(new TicTacToePanel(new GridLayout(3, 3)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(5, 5, 500, 500);
        frame.setVisible(true);
    }
}

class TicTacToeCell extends JButton {
    private boolean isFill;
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num,int x,int y) {
        this.num=num;
        row=y;
        col=x;
        marker=' ';
        setText(Character.toString(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }
    public void setMarker(String m) {
        marker=m.charAt(0);
        setText(m);
        setEnabled(false);
    }
    public char getMarker() {
        return marker;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int getNum() {
        return num;
    }

}

class Utility {

  public static void print(char[] board) {
    System.out.println();
        for(int j=0;j<9;j++)
          System.out.print(board[j]+"-");
        System.out.println();
  }
  public static void print(int[] board) {
    System.out.println();
        for(int j=0;j<9;j++)
          System.out.print(board[j]+"-");
        System.out.println();
  }  
  public static void print(ArrayList<Integer> moves) {
    System.out.println();
        for(int j=0;j<moves.size();j++)
          System.out.print(moves.get(j)+"-");
        System.out.println();
  }  
}

class TicTacToePanel extends JPanel implements ActionListener {

   private Game game;

   private void createCell(int num,int x,int y) {
       cells[num]=new TicTacToeCell(num,x,y);
       cells[num].addActionListener(this);
       add(cells[num]);

   }

   private TicTacToeCell[] cells = new TicTacToeCell[9];
   TicTacToePanel(GridLayout layout) {
       super(layout);
       createCell(0,0,0);
       createCell(1,1,0);
       createCell(2,2,0);
       createCell(3,0,1);
       createCell(4,1,1);
       createCell(5,2,1);
       createCell(6,0,2);
       createCell(7,1,2); 
       createCell(8,2,2);
       game=new Game();
       game.cplayer=game.player1;
   }

   public void actionPerformed(ActionEvent ae) {
      game.player1.move = -1;
      game.player2.move = -1;
      //System.out.println(game.cplayer.symbol);
      //System.out.println(((TicTacToeCell)(ae.getSource())).getNum());


      int i=0;
      for(TicTacToeCell jb: cells) {
         if(ae.getSource()==jb) {
            jb.setMarker(Character.toString(game.cplayer.symbol));
         }
         game.board[i++]=jb.getMarker();
      }
      if(game.cplayer==game.player1) {

         game.player2.move = game.MiniMax(game.board, game.player2);
         game.nmove = game.player2.move;
         game.symbol = game.player2.symbol;
         game.cplayer = game.player2;
         if(game.player2.move>0)
            cells[game.player2.move-1].doClick();
       }
       else
       {
         game.nmove = game.player1.move;
         game.symbol = game.player1.symbol;
         game.cplayer = game.player1;
       }

      game.state=game.checkState(game.board);


      if(game.state==State.XWIN) {
        JOptionPane.showMessageDialog(null,"Выиграли крестики","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);

      }
      else if(game.state==State.OWIN) {
        JOptionPane.showMessageDialog(null,"Выиграли нолики","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      }
      else if(game.state==State.DRAW) {
        JOptionPane.showMessageDialog(null,"Ничья","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      } 




   }
}


