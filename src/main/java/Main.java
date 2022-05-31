import binary.game.ReadFile;
import binary.game.model.Board;
import binary.game.model.Solve;
import datatype.DataType;
import datatype.Game;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Board board = ReadFile.readFile(new DataType(Game.BINARY6x6));
        ArrayList<Board> solutions = Solve.backtracking(board);
        for(Board solution : solutions) {
            System.out.println(solution);
        }
        System.out.println(solutions.size());
    }
}
