import binary.game.ReadFile;
import binary.game.model.Board;
import datatype.DataType;
import datatype.Game;

public class Main {
    public static void main(String[] args) {
        Board board = ReadFile.readFile(new DataType(Game.BINARY10x10));
        System.out.println(board);
        System.out.println(board.checkNotThree(1, 2));
        board.setTile(0,1,0);
        System.out.println(board);
        System.out.println(board.checkNotThree(0, 1));
    }
}
