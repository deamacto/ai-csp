package binary.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {
    private Integer[][] board;

    public Board(Integer[][] board) {
        this.board = board;
    }

    public void setTile(int cordX, int cordY, Integer value) {
        if(board[cordY][cordX] == null) {
            board[cordY][cordX] = value;
        }
    }

    public boolean checkNotThree(int cordX, int cordY) {
        Integer lastNumber = -1;
        int howMany = 0;

        for(int i = 0; i < board.length; i++) {
            if(board[cordY][i] != null) {
                if(Objects.equals(board[cordY][i], lastNumber)) {
                    howMany += 1;
                } else {
                    howMany = 1;
                }
            } else {
                howMany = 1;
            }

            if(howMany >= 3) {
                return false;
            }

            lastNumber = board[cordY][i];
        }

        howMany = 0;

        for(int i = 0; i < board.length; i++) {
            if(board[i][cordX] != null) {
                if(Objects.equals(board[i][cordX], lastNumber)) {
                    howMany += 1;
                } else {
                    howMany = 1;
                }
            } else {
                howMany = 1;
            }

            if(howMany >= 3) {
                return false;
            }

            lastNumber = board[cordY][i];
        }

        return true;
    }

    public boolean checkSameAmount(int cordX, int cordY) {
        int oneInRow = 0;
        int oneInColumn = 0;
        int zeroInRow = 0;
        int zeroInColumn = 0;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(j == cordY) {
                    if(board[i][j] == 1) {
                        oneInColumn += 1;
                    } else if(board[i][j] == 0) {
                        zeroInColumn += 1;
                    }
                } else if(i == cordX) {
                    if(board[i][j] == 1) {
                        oneInRow += 1;
                    } else if(board[i][j] == 0) {
                       zeroInRow += 1;
                    }
                }
            }
        }

        int half = board.length/2;
        return oneInColumn <= half && zeroInColumn <= half && oneInRow <= half && zeroInRow <= half;
    }

    public boolean checkUnique(int cordX, int cordY) {
        Integer[] row = board[cordY];
        Integer[] column = Arrays.stream(board).map(r -> r[cordX]).toArray(Integer[]::new);
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
