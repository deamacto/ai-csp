package binary.game.model;

import util.Coordinates;

import java.util.ArrayList;

public class Solve {

    public static ArrayList<Board> backtracking(Board emptyBoard) {
        ArrayList<Board> solutions = new ArrayList<>();
        Board currentBoard = emptyBoard.clone();
        return backtrack(currentBoard, solutions);
    }

    private static ArrayList<Board> backtrack(Board currentBoard, ArrayList<Board> solutions) {
        Coordinates spot = currentBoard.findFirstEmptySpot();

        if(spot.x == -1 && spot.y == -1) {
            solutions.add(currentBoard);
        } else {
            for(int domainElement : Board.domain) {
                Board newBoard = currentBoard.clone();
                if(newBoard.setTileIfPossible(spot, domainElement)) {
                    solutions = backtrack(newBoard, solutions);
                }
            }
        }

        return solutions;
    }

    public static ArrayList<Board> forwardchecking(Board startingBoard) {
        ArrayList<Board> solutions = new ArrayList<>();
        Board currentBoard = startingBoard.clone();
        return forwardcheck(currentBoard, solutions);
    }

    private static ArrayList<Board> forwardcheck(Board currentBoard, ArrayList<Board> solutions) {
        Coordinates spot = currentBoard.findFirstEmptySpot();

        if(spot.x == -1 && spot.y == -1) {
            solutions.add(currentBoard);
        } else {
            for(Integer domainElement : currentBoard.getTile(spot).domain) {
                Board newBoard = currentBoard.clone();
                if(newBoard.setTileIfPossible(spot, domainElement) && newBoard.deleteFromDomain(spot)) {
                    solutions = backtrack(newBoard, solutions);
                }
            }
        }

        return solutions;
    }
}
