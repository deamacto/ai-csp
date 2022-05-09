import binary.game.ReadFile;
import binary.game.model.Board;
import datatype.DataType;
import datatype.Game;

public class Main {
    public static void main(String[] args) {
        Board board = ReadFile.readFile(new DataType(Game.BINARY10x10));
        System.out.println(board);
    }
}
