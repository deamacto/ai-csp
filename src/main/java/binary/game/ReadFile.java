package binary.game;

import binary.game.model.Board;
import datatype.DataType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {

    public static Board readFile(DataType dt) {
        Integer[][] board = new Integer[dt.size][dt.size];
        try {
            String content = new String(Files.readAllBytes(Paths.get(dt.file)));
            content = content.replace("\r\n", "");
            for(int i = 0; i < content.length(); i++) {
                Integer symbol = -1;
                if(content.charAt(i) == '1') {
                    symbol = 1;
                } else if(content.charAt(i) == '0') {
                    symbol = 0;
                } else if(content.charAt(i) == 'x') {
                    symbol = null;
                }
                board[i / board[0].length][i % board[0].length] = symbol;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Board(board);
    }
}
