package futoshiki.game.model;

import util.Coordinates;

public class Board {
    private Field[][] board;
    public int[] domain;

    public Board(Field[][] board, int[] domain) {
        this.board = board;
        this.domain = domain;
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
                newBoard[i][j] = new Field(board[i][j].leftRelation, board[i][j].rightRelation, board[i][j].topRelation, board[i][j].bottomRelation, board[i][j].number);
            }
        }
        return new Board(newBoard, domain);
    }
}
