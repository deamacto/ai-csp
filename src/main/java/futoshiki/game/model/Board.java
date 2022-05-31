package futoshiki.game.model;

import util.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private Field[][] board;
    public int[] domain;

    public Board(Field[][] board, int[] domain) {
        this.board = board;
        this.domain = domain;
    }

    public Field getTile(Coordinates coordinates) {
        return board[coordinates.y][coordinates.x];
    }

    public boolean setFieldIfPossible(Coordinates coordinates, int value) {
        if (board[coordinates.y][coordinates.x].number != null) {
            return false;
        }

        board[coordinates.y][coordinates.x].number = value;
        if(!checkNotSameNumber(coordinates) || !checkGreaterAndLowerConstraints(coordinates)) {
            board[coordinates.y][coordinates.x].number = null;
            return false;
        }

        return true;
    }

    public boolean deleteFromDomain(Coordinates  coordinates) {
        return deleteFromDomainConstraints(coordinates) && deleteFromDomainSameNumber(coordinates);
    }

    private boolean deleteFromDomainConstraints(Coordinates cord) {
        Field field = board[cord.y][cord.x];

        if(field.leftRelation != Symbol.NONE && board[cord.y][cord.x - 1].number == null) {
            deleteDomainRelation(field.number, field.leftRelation, new Coordinates(cord.x - 1, cord.y));

            if(board[cord.y][cord.x - 1].domain.isEmpty()) {
                return false;
            }
        }

        if(field.rightRelation != Symbol.NONE && board[cord.y][cord.x + 1].number == null) {
            deleteDomainRelation(field.number, field.rightRelation, new Coordinates(cord.x + 1, cord.y));

            if(board[cord.y][cord.x + 1].domain.isEmpty()) {
                return false;
            }
        }

        if(field.topRelation != Symbol.NONE && board[cord.y - 1][cord.x].number == null) {
            deleteDomainRelation(field.number, field.topRelation, new Coordinates(cord.x, cord.y - 1));

            if(board[cord.y - 1][cord.x].domain.isEmpty()) {
                return false;
            }
        }

        if(field.bottomRelation != Symbol.NONE && board[cord.y + 1][cord.x].number == null) {
            deleteDomainRelation(field.number, field.bottomRelation, new Coordinates(cord.x, cord.y + 1));

            if(board[cord.y + 1][cord.x].domain.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void deleteDomainRelation(int number, Symbol relation, Coordinates fieldCoordinates) {
        Field fieldToUpdate = board[fieldCoordinates.y][fieldCoordinates.x];

        if(relation == Symbol.GREATER) {
            fieldToUpdate.domain.removeIf(elem -> elem >= number);
        } else if(relation == Symbol.LESSTHAN) {
            fieldToUpdate.domain.removeIf(elem -> elem <= number);
        }
    }

    private boolean deleteFromDomainSameNumber(Coordinates cord) {
        for (int i = 0; i < board.length; i++) {
            if(board[cord.y][i].number == null) {
                board[cord.y][i].domain.remove(board[cord.y][cord.x].number);

                if(board[cord.y][i].domain.isEmpty()) {
                    return false;
                }
            }

            if(board[i][cord.x].number == null) {
                board[i][cord.x].domain.remove(board[cord.y][cord.x].number);

                if(board[i][cord.x].domain.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkNotSameNumber(Coordinates coordinates) {
        for(int i = 0; i < board.length; i++) {
            if(board[coordinates.y][i].number == board[coordinates.y][coordinates.x].number && i != coordinates.x) {
                return false;
            }

            if(board[i][coordinates.x].number == board[coordinates.y][coordinates.x].number && i != coordinates.y) {
                return false;
            }
        }
        return true;
    }

    public boolean checkGreaterAndLowerConstraints(Coordinates coordinates) {
        Field field = board[coordinates.y][coordinates.x];
        field.normalize();

        if(field.leftRelation != Symbol.NONE) {
            if(!checkIfRelationIsOK(field.number, field.leftRelation, board[coordinates.y][coordinates.x - 1].number)) {
                return false;
            }
        }

        if(field.topRelation != Symbol.NONE) {
            if(!checkIfRelationIsOK(field.number, field.topRelation, board[coordinates.y - 1][coordinates.x].number)) {
                return false;
            }
        }

        if(field.rightRelation != Symbol.NONE) {
            if(!checkIfRelationIsOK(field.number, field.rightRelation, board[coordinates.y][coordinates.x + 1].number)) {
                return false;
            }
        }

        if(field.bottomRelation != Symbol.NONE) {
            if(!checkIfRelationIsOK(field.number, field.bottomRelation, board[coordinates.y + 1][coordinates.x].number)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfRelationIsOK(int number, Symbol relation, Integer numberChecked) {
        if(numberChecked == null) {
            return true;
        }

        if(relation == Symbol.GREATER) {
            return number > numberChecked;
        } else if(relation == Symbol.LESSTHAN) {
            return number < numberChecked;
        } else if(relation == Symbol.PLACEHOLDER) {
            return true;
        }
        return true;
    }

    public Coordinates findFirstEmptySpot() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j].number == null) {
                    return new Coordinates(j, i);
                }
            }
        }
        return new Coordinates(-1, -1);
    }

    public Coordinates findSmallestDomainSpot() {
        Coordinates smallestCord = new Coordinates(-1, -1);
        Integer smallestDomain = Integer.MAX_VALUE;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j].number == null && board[i][j].domain.size() < smallestDomain) {
                    smallestDomain = board[i][j].domain.size();
                    smallestCord = new Coordinates(j, i);
                }
            }
        }

        return smallestCord;
    }

    public ArrayList<Integer> sortDomainByOccurrences(ArrayList<Integer> domain) {
        HashMap<Integer, Integer> occurrences = new HashMap<>();
        for(int elem : domain) {
            occurrences.put(elem, 0);
        }

        for(int i = 0 ; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(occurrences.containsKey(board[i][j].number)) {
                    occurrences.put(board[i][j].number, occurrences.get(board[i][j].number) + 1);
                }
            }
        }

        return new ArrayList<Integer>(occurrences
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .toList());
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

    @Override
    public Board clone() {
        Field[][] newBoard = new Field[board.length][board.length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                ArrayList<Integer> newDomain = new ArrayList<>();
                for(Integer domainElement : board[i][j].domain) {
                    newDomain.add(domainElement);
                }
                newBoard[i][j] = new Field(board[i][j].leftRelation, board[i][j].rightRelation, board[i][j].topRelation, board[i][j].bottomRelation, board[i][j].number, newDomain);
            }
        }
        return new Board(newBoard, domain);
    }
}
