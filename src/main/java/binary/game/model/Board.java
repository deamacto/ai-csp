package binary.game.model;

import util.Coordinates;

import java.util.Arrays;
import java.util.Objects;


public class Board {
    private Integer[][] board;
    public static final int[] domain = {0, 1};

    public Board(Integer[][] board) {
        this.board = board;
    }

    public boolean setTileIfPossible(Coordinates coordinates, Integer value) {
        if (board[coordinates.y][coordinates.x] != null) {
            return false;
        }

        board[coordinates.y][coordinates.x] = value;
        if(!checkNotThree(coordinates) || !checkSameAmount(coordinates) || !checkUnique(coordinates)) {
            board[coordinates.y][coordinates.x] = null;
            return false;
        }

        return true;
    }

    public boolean checkNotThree(Coordinates coordinates) {
        Integer lastNumber = -1;
        int howMany = 0;

        for(int i = 0; i < board.length; i++) {
            if(board[coordinates.y][i] != null) {
                if(Objects.equals(board[coordinates.y][i], lastNumber)) {
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

            lastNumber = board[coordinates.y][i];
        }

        howMany = 0;

        for(int i = 0; i < board.length; i++) {
            if(board[i][coordinates.x] != null) {
                if(Objects.equals(board[i][coordinates.x], lastNumber)) {
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

            lastNumber = board[coordinates.y][i];
        }

        return true;
    }

    public boolean checkSameAmount(Coordinates coordinates) {
        int oneInRow = 0;
        int oneInColumn = 0;
        int zeroInRow = 0;
        int zeroInColumn = 0;

        Integer[] column = Arrays.stream(board).map(r -> r[coordinates.x]).toArray(Integer[]::new);

        for(int i = 0; i < board.length; i++) {
            if(board[coordinates.y][i] != null){
                if(board[coordinates.y][i] == 1) {
                    oneInRow += 1;
                } else if(board[coordinates.y][i] == 0) {
                    zeroInRow += 1;
                }
            }

            if(column[i] != null) {
                if(column[i] == 1) {
                    oneInColumn += 1;
                } else if(column[i] == 0) {
                    zeroInColumn += 1;
                }
            }
        }

        int half = board.length/2;
        return oneInColumn <= half && zeroInColumn <= half && oneInRow <= half && zeroInRow <= half;
    }

    public boolean checkUnique(Coordinates coordinates) {
        Integer[] row = board[coordinates.y];
        Integer[] column = Arrays.stream(board).map(r -> r[coordinates.x]).toArray(Integer[]::new);

        for(int i = 0; i < board.length; i++) {
            if(Arrays.equals(row, board[i]) && i != coordinates.y) {
                return false;
            }

            int finalI = i;
            Integer[] checkedColumn = Arrays.stream(board).map(r -> r[finalI]).toArray(Integer[]::new);
            if(Arrays.equals(checkedColumn, column) && i != coordinates.x) {
                return false;
            }
        }
        return true;
    }

//    public boolean checkSameAmountOnWholeBoard() {
//        ArrayList<Integer> zeros = new ArrayList<>();
//        ArrayList<Integer> ones = new ArrayList<>();
//
//        for(int i = 0; i < board.length; i++) {
//            zeros.add(0);
//            ones.add(0);
//        }
//
//        int zero = 0;
//        int one = 0;
//
//        for(int i = 0; i < board.length; i++) {
//            for(int j = 0; j < board[0].length; j++) {
//                if(board[i][j] == 1) {
//                    one += 1;
//                    ones.set(j, ones.get(j) + 1);
//                } else if(board[i][j] == 0) {
//                    zero += 1;
//                    zeros.set(j, zeros.get(j) +1);
//                }
//            }
//            if(zero != one) {
//                return false;
//            }
//        }
//
//        for(int i = 0; i < zeros.size(); i++) {
//            if(!zeros.get(i).equals(ones.get(i))) {
//                return false;
//            }
//        }
//
//        return true;
//    }

    public Coordinates findFirstEmptySpot() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == null) {
                    return new Coordinates(j, i);
                }
            }
        }
        return new Coordinates(-1, -1);
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
