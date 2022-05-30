package binary.game.model;

public class Field {
    Integer value;
    int[] domain;

    public Field(Integer value) {
        this.value = value;
        domain = new int[]{0, 1};
    }
}
