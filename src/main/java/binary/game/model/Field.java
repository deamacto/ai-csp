package binary.game.model;

import java.util.ArrayList;

public class Field {
    Integer value;
    ArrayList<Integer> domain;

    public Field(Integer value) {
        this.value = value;
        domain = new ArrayList<Integer>();
        domain.add(0);
        domain.add(1);
    }
}
