package futoshiki.game.model;

public class Field {
    public Symbol leftRelation;
    public Symbol rightRelation;
    public Symbol topRelation;
    public Symbol bottomRelation;
    public Integer number;

    public Field(Symbol leftRelation, Symbol rightRelation, Symbol topRelation, Symbol bottomRelation, Integer number) {
        this.leftRelation = leftRelation;
        this.rightRelation = rightRelation;
        this. topRelation = topRelation;
        this.bottomRelation = bottomRelation;
        this.number = number;
    }
}
