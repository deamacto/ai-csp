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
        Board board = ReadFile.readFile(new DataType(Game.BINARY6x6));

        var st_t = LocalTime.now();
        ArrayList<Board> solutions = Solve.forwardchecking(board);
        var end_t = LocalTime.now();
        System.out.println(Solve.counter);
        System.out.println(Duration.between(st_t, end_t).toMillis());

        Board board1 = ReadFile.readFile(new DataType(Game.BINARY8x8));

        var st_t_1 = LocalTime.now();
        ArrayList<Board> solutions1 = Solve.forwardchecking(board1);
        var end_t_1 = LocalTime.now();
        System.out.println(Solve.counter);
        System.out.println(Duration.between(st_t_1, end_t_1).toMillis());

        Board board2 = ReadFile.readFile(new DataType(Game.BINARY10x10));

        var st_t_2 = LocalTime.now();
        ArrayList<Board> solutions2 = Solve.forwardchecking(board2);
        var end_t_2 = LocalTime.now();
        System.out.println(Solve.counter);
        System.out.println(Duration.between(st_t_2, end_t_2).toMillis());
    }
}
