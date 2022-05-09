package binary.game.model;

import java.util.ArrayList;

public class Board {
    private Integer[][] board;

    public Board(Integer[][] board) {
        this.board = board;
    }

    public boolean checkSameAmountOnWholeBoard() {
        ArrayList<Integer> zeros = new ArrayList<>();
        ArrayList<Integer> ones = new ArrayList<>();

        for(int i = 0; i < board.length; i++) {
            zeros.add(0);
            ones.add(0);
        }

        int zero = 0;
        int one = 0;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 1) {
                    one += 1;
                    ones.set(j, ones.get(j) + 1);
                } else if(board[i][j] == 0) {
                    zero += 1;
                    zeros.set(j, zeros.get(j) +1);
                }
            }
            if(zero != one) {
                return false;
            }
        }

        for(int i = 0; i < zeros.size(); i++) {
            if(!zeros.get(i).equals(ones.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("Binary \n");
        for (Integer[] integers : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (integers[j] == null) {
                    b.append(" _ ");
                } else {
                    b.append(" ").append(integers[j]).append(" ");
                }
            }
            b.append("\n");
        }
        return b.toString();
    }

    @Override
    protected Board clone() {
        Integer[][] newBoard = new Integer[board.length][board.length];
        for(int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[0].length);
        }
        return new Board(newBoard);
    }
}
