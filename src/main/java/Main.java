import binary.game.ReadFile;
import binary.game.model.Board;
import binary.game.model.Solve;
import datatype.DataType;
import datatype.Game;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Board board = ReadFile.readFile(new DataType(Game.FUTOSHIKI6x6));

        var st_t = LocalTime.now();
        ArrayList<Board> solutions = Solve.forwardchecking(board);
        var end_t = LocalTime.now();
//        for(Board solution : solutions) {
//            System.out.println(solution);
//        }
//        System.out.println(solutions.size());
        System.out.println(Solve.counter);
        System.out.println(Duration.between(st_t, end_t).toMillis());
    }
}
