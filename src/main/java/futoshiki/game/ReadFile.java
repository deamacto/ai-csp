package futoshiki.game;

import datatype.DataType;
import futoshiki.game.model.Board;
import futoshiki.game.model.Field;
import futoshiki.game.model.Symbol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {

    public static Board readFile(DataType dt) {
        Field[][] board = new Field[dt.size][dt.size];


        int[] domain = new int[dt.size];
        for(int i = 0; i < dt.size; i++) {
            domain[i] = i+1;
        }

        try{
            String content = new String(Files.readAllBytes(Paths.get(dt.file)));
            String[] contentInLines = content.split("\n");
            for(int i = 0; i < contentInLines.length; i++) {
                for(int j = 0; j < contentInLines[i].length(); j++) {
                    if(i % 2 == 0) {
                        if(j % 2 == 0) {
                            Symbol right = Symbol.NONE;
                            Symbol left = Symbol.NONE;
                            Symbol top = Symbol.NONE;
                            Symbol bottom = Symbol.NONE;
                            Integer value = null;
                            if(i != 0) {
                                top = checkSymbol(contentInLines[i-1].charAt(j/2));
                            }
                            if(i != contentInLines.length - 1) {
                                bottom = checkSymbol(contentInLines[i+1].charAt(j/2));
                            }
                            if(j != 0) {
                                left = checkSymbol(contentInLines[i].charAt(j-1));
                            }
                            if(j != contentInLines[i].length() - 1) {
                                right = checkSymbol(contentInLines[i].charAt(j+1));
                            }
                            if(contentInLines[i].charAt(j) != 'x') {
                                value = Character.getNumericValue(contentInLines[i].charAt(j));
                            }

                            ArrayList<Integer> currentDomain = new ArrayList<>();
                            for (int elem : domain) {
                                currentDomain.add(elem);
                            }

                            Field field = new Field(left, right, top, bottom, value, currentDomain);
                            board[i/2][j/2] = field;
                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Board(board, domain);
    }

    static Symbol checkSymbol(char sign) {
        if(sign == '<') {
            return Symbol.LESSTHAN;
        } else if(sign == '>') {
            return Symbol.GREATER;
        } else if(sign == '-') {
            return Symbol.PLACEHOLDER;
        }
        return Symbol.NONE;
    }
}
