package binary.game.model;

import util.Coordinates;

import java.util.Arrays;
import java.util.Objects;


public class Board {
    private Field[][] board;
    public static final int[] domain = {0, 1};

    public Board(Field[][] board) {
        this.board = board;
    }

    public Field getTile(Coordinates coordinates) {
        return board[coordinates.y][coordinates.x];
    }

    public boolean setTileIfPossible(Coordinates coordinates, Integer value) {
        if (board[coordinates.y][coordinates.x].value != null) {
            return false;
        }

        board[coordinates.y][coordinates.x].value = value;
        if(!checkNotThree(coordinates) || !checkSameAmount(coordinates) || !checkUnique(coordinates)) {
            board[coordinates.y][coordinates.x].value = null;
            return false;
        }

        return true;
    }

    public boolean deleteFromDomain(Coordinates coordinates) {
        return deleteFromDomainRepetitions(coordinates) && deleteFromDomainSameAmount(coordinates);
    }

    private boolean deleteFromDomainSameAmount(Coordinates cord) {
        long countRow = Arrays.stream(board[cord.y]).filter(elem -> Objects.equals(elem.value, board[cord.y][cord.x].value)).count();
        if(countRow == board.length/2) {
            for(int i = 0; i < board[cord.y].length; i++) {
                if(board[cord.y][i].value == null) {
                    board[cord.y][i].domain.remove(board[cord.y][cord.x].value);
                    if(board[cord.y][i].domain.isEmpty()) {
                        return false;
                    }
                }
            }
        }

        Field[] column = Arrays.stream(board).map(r -> r[cord.x]).toArray(Field[]::new);
        long countColumn = Arrays.stream(column).filter(elem -> Objects.equals(elem.value, board[cord.y][cord.x].value)).count();
        if(countColumn == board.length/2) {
            for(int i = 0; i < board.length; i++) {
                if(board[i][cord.x].value == null) {
                    board[i][cord.x].domain.remove(board[cord.y][cord.x].value);
                    if(board[i][cord.x].domain.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean deleteFromDomainRepetitions(Coordinates cord) {
        Field current = board[cord.y][cord.x];
        if(current.value != null) {
            if(cord.x - 1 >= 0 && Objects.equals(board[cord.y][cord.x - 1].value, current.value) && cord.x + 1 < board.length && board[cord.y][cord.x + 1].value == null ) {
                board[cord.y][cord.x + 1].domain.remove(current.value);

                if( board[cord.y][cord.x + 1].domain.isEmpty()) {
                    return false;
                }
            }

            if(cord.x + 1 < board.length && Objects.equals(current.value, board[cord.y][cord.x + 1].value) && cord.x + 2 < board.length && board[cord.y][cord.x + 2].value == null) {
                board[cord.y][cord.x + 2].domain.remove(current.value);

                if(board[cord.y][cord.x + 2].domain.isEmpty()) {
                    return false;
                }
            }

            if (cord.y - 1 >= 0 && Objects.equals(current.value, board[cord.y - 1][cord.x].value) && cord.y + 1 < board.length && board[cord.y + 1][cord.x].value == null) {
                board[cord.y + 1][cord.x].domain.remove(current.value);

                if(board[cord.y + 1][cord.x].domain.isEmpty()) {
                    return false;
                }
            }

            if(cord.y + 1 < board.length && Objects.equals(current.value, board[cord.y + 1][cord.x].value) && cord.y + 2 < board.length && board[cord.y + 2][cord.x].value == null) {
                board[cord.y + 2][cord.x].domain.remove(current.value);

                if(board[cord.y + 2][cord.x].domain.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkNotThree(Coordinates coordinates) {
        Integer lastNumber = -1;
        int howMany = 0;

        for(int i = 0; i < board.length; i++) {
            if(board[coordinates.y][i].value != null) {
                if(Objects.equals(board[coordinates.y][i].value, lastNumber)) {
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

            lastNumber = board[coordinates.y][i].value;
        }

        howMany = 0;
        lastNumber = -1;

        for(int i = 0; i < board.length; i++) {
            if(board[i][coordinates.x].value != null) {
                if(Objects.equals(board[i][coordinates.x].value, lastNumber)) {
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

            lastNumber = board[i][coordinates.x].value;
        }

        return true;
    }

    public boolean checkSameAmount(Coordinates coordinates) {
        int oneInRow = 0;
        int oneInColumn = 0;
        int zeroInRow = 0;
        int zeroInColumn = 0;

        Field[] column = Arrays.stream(board).map(r -> r[coordinates.x]).toArray(Field[]::new);

        for(int i = 0; i < board.length; i++) {
            if(board[coordinates.y][i].value != null){
                if(board[coordinates.y][i].value == 1) {
                    oneInRow += 1;
                } else if(board[coordinates.y][i].value == 0) {
                    zeroInRow += 1;
                }
            }

            if(column[i].value != null) {
                if(column[i].value == 1) {
                    oneInColumn += 1;
                } else if(column[i].value == 0) {
                    zeroInColumn += 1;
                }
            }
        }

        int half = board.length/2;
        return oneInColumn <= half && zeroInColumn <= half && oneInRow <= half && zeroInRow <= half;
    }

    public boolean checkUnique(Coordinates coordinates) {
        Integer[] row = Arrays.stream(board[coordinates.y]).map(elem -> elem.value).toArray(Integer[]::new);
        Integer[] column = Arrays.stream(board).map(r -> r[coordinates.x].value).toArray(Integer[]::new);

        if(Arrays.stream(row).noneMatch(Objects::isNull)) {
            for(int i = 0; i < board.length; i++) {
                if(Arrays.equals(row, Arrays.stream(board[i]).map(elem -> elem.value).toArray(Integer[]::new)) && i != coordinates.y) {
                    return false;
                }
            }
        }

        if(Arrays.stream(column).noneMatch(Objects::isNull)) {
            for(int i = 0; i < board.length; i++) {
                int finalI = i;
                Integer[] checkedColumn = Arrays.stream(board).map(r -> r[finalI].value).toArray(Integer[]::new);
                if(Arrays.equals(checkedColumn, column) && i != coordinates.x) {
                    return false;
                }
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
                if(board[i][j].value == null) {
                    return new Coordinates(j, i);
                }
            }
        }
        return new Coordinates(-1, -1);
    }

    public Coordinates findSmallestDomainSpot() {
        Coordinates smallestCord = new Coordinates(-1, -1);
        int smallestDomain = Integer.MAX_VALUE;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j].domain.size() < smallestDomain) {
                    smallestDomain = board[i][j].domain.size();
                    smallestCord = new Coordinates(j, i);
                }
            }
        }

        return smallestCord;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("Binary \n");
        for (Field[] field : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (field[j].value == null) {
                    b.append(" _ ");
                } else {
                    b.append(" ").append(field[j].value).append(" ");
                }
            }
            b.append("\n");
        }
        return b.toString();
    }

    @Override
    protected Board clone() {
        Field[][] newBoard = new Field[board.length][board.length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                newBoard[i][j] = new Field(board[i][j].value);
            }
        }
        return new Board(newBoard);
    }
}
