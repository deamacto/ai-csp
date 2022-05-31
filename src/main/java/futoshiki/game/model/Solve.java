package futoshiki.game.model;
import util.Coordinates;

import java.util.ArrayList;

public class Solve {

    public static int counter = 0;

    public static ArrayList<Board> backtracking(Board emptyBoard) {
        counter = 0;
        ArrayList<Board> solutions = new ArrayList<>();
        Board currentBoard = emptyBoard.clone();
        return backtrack(currentBoard, solutions);
    }

    private static ArrayList<Board> backtrack(Board currentBoard, ArrayList<Board> solutions) {
        counter++;
        Coordinates spot = currentBoard.findSmallestDomainSpot();

        if(spot.x == -1 && spot.y == -1) {
            solutions.add(currentBoard);
        } else {
//            for(int domainElement : currentBoard.domain) {
            for(int domainElement : currentBoard.sortDomainByOccurrences(currentBoard.getTile(spot).domain)) {
                Board newBoard = currentBoard.clone();
                if(newBoard.setFieldIfPossible(spot, domainElement)) {
                    solutions = backtrack(newBoard, solutions);
                }
            }
        }

        return solutions;
    }

    public static ArrayList<Board> forwardchecking(Board startingBoard) {
        counter = 0;
        ArrayList<Board> solutions = new ArrayList<>();
        Board currentBoard = startingBoard.clone();
        return forwardcheck(currentBoard, solutions);
    }

    private static ArrayList<Board> forwardcheck(Board currentBoard, ArrayList<Board> solutions) {
        counter++;
//        Coordinates spot = currentBoard.findSmallestDomainSpot();
        Coordinates spot = currentBoard.findFirstEmptySpot();

        if(spot.x == -1 && spot.y == -1) {
            solutions.add(currentBoard);
        } else {
//            for(Integer domainElement : currentBoard.getTile(spot).domain) {
            for(Integer domainElement : currentBoard.sortDomainByOccurrences(currentBoard.getTile(spot).domain)) {
                Board newBoard = currentBoard.clone();
                if(newBoard.setFieldIfPossible(spot, domainElement) && newBoard.deleteFromDomain(spot)) {
                    solutions = forwardcheck(newBoard, solutions);
                }
            }
        }

        return solutions;
    }
}
