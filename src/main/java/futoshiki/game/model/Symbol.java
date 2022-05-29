package futoshiki.game.model;

public enum Symbol {
    PLACEHOLDER(" "),
    GREATER(">"),
    LESSTHAN("<"),
    NONE("");

    private String symbol;

    private Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
