package gogame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoneGroup implements Serializable {
    private List<Cross> stoneList;
    private List<Cross> liberties;

    public StoneGroup() {
        stoneList = new ArrayList<>();
        liberties = new ArrayList<>();
    }
    public List<Cross> getStoneList() {
        return stoneList;
    }

    public void setStoneList(List<Cross> stoneList) {
        this.stoneList = stoneList;
    }

    public List<Cross> getLiberties() {
        return liberties;
    }

    public void setLiberties(List<Cross> liberties) {
        this.liberties = liberties;
    }
}
