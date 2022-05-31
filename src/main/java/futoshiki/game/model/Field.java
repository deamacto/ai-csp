package futoshiki.game.model;

import java.util.ArrayList;

public class Field {
    public Symbol leftRelation;
    public Symbol rightRelation;
    public Symbol topRelation;
    public Symbol bottomRelation;
    public Integer number;
    public ArrayList<Integer> domain;

    public Field(Symbol leftRelation, Symbol rightRelation, Symbol topRelation, Symbol bottomRelation, Integer number, ArrayList<Integer> domain) {
        this.leftRelation = leftRelation;
        this.rightRelation = rightRelation;
        this. topRelation = topRelation;
        this.bottomRelation = bottomRelation;
        this.number = number;
        this.domain = domain;
    }

    public void normalize() {
        if(this.leftRelation == Symbol.GREATER) {
            this.leftRelation = Symbol.LESSTHAN;
        } else if(this.leftRelation == Symbol.LESSTHAN) {
            this.leftRelation = Symbol.GREATER;
        }

        if(this.topRelation == Symbol.GREATER) {
            this.topRelation = Symbol.LESSTHAN;
        } else if(this.topRelation == Symbol.LESSTHAN) {
            this.topRelation = Symbol.GREATER;
        }
    }
}
