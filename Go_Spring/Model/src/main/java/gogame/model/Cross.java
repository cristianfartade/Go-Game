package gogame.model;

import java.io.Serializable;

public class Cross implements Serializable {
    private StoneGroup stoneGroup;
    private int i=0,j=0;

    public Cross(int i, int j) {
        this.i = i;
        this.j = j;
        stoneGroup= new StoneGroup();
        stoneGroup.getStoneList().add(this);

    }
    public Cross(){}

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public StoneGroup getStoneGroup() {
        return stoneGroup;
    }

    public void setStoneGroup(StoneGroup stoneGroup) {
        this.stoneGroup = stoneGroup;
    }

    @Override
    public String toString() {
        return "Cross{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
