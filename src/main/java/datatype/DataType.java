package datatype;

public class DataType {

    public String file;
    public int size;

    public DataType(Game game) {
        if(game == Game.BINARY6x6) {
            file = "src/data/binary_6x6";
            size = 6;
        } else if(game == Game.BINARY8x8) {
            file = "src/data/binary_8x8";
            size = 8;
        } else if(game == Game.BINARY10x10) {
            file = "src/data/binary_10x10";
            size = 10;
        } else if(game == Game.FUTOSHIKI4x4) {
            file = "src/data/futoshiki_4x4";
            size = 7;
        } else if(game == Game.FUTOSHIKI5x5) {
            file = "src/data/futoshiki_5x5";
            size = 9;
        } else if(game == Game.FUTOSHIKI6x6) {
            file = "src/data/futoshiki_6x6";
            size = 11;
        }
    }
}
