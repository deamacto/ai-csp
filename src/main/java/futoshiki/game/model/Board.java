package futoshiki.game.model;

public class Board {
    private Field[][] board;
    public int[] domain;

    public Board(Field[][] board, int[] domain) {
        this.board = board;
        this.domain = domain;
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder("Futoshiki \n");
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if (board[i][j].number == null) {
                    f.append(" _ ");
                } else {
                    f.append(" ").append(board[i][j].number).append(" ");
                }

                if (board[i][j].rightRelation != Symbol.NONE) {
                    f.append(board[i][j].rightRelation.getSymbol());
                }
            }
            f.append("\n");

            for(int k = 0; k < board[0].length; k++) {
                if (board[i][k].bottomRelation != Symbol.NONE) {
                    f.append(" ").append(board[i][k].bottomRelation.getSymbol()).append("  ");
                }
            }
            f.append("\n");
        }
        return f.toString();
    }
}
