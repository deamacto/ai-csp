package binary.game.model;

import java.util.Arrays;

public class Board {
    private Integer[][] board;

    public Board(Integer[][] board) {
        this.board = board;
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
}
