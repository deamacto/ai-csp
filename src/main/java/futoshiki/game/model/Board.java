package futoshiki.game.model;

public class Board {
    private Field[][] board;
    public int[] domain;

    public Board(Field[][] board, int[] domain) {
        this.board = board;
        this.domain = domain;
    }
}
