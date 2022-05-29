import futoshiki.game.ReadFile;
import futoshiki.game.model.Board;
import datatype.DataType;
import datatype.Game;

public class Main {
    public static void main(String[] args) {
        Board board = ReadFile.readFile(new DataType(Game.FUTOSHIKI4x4));
        System.out.println(board);
    }
}
