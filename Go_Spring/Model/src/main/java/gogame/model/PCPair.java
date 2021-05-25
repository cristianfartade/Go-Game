package gogame.model;

import java.io.Serializable;

public class PCPair implements Serializable {
    private Cross cross;
    private Player player;

    public PCPair(Cross cross, Player player) {
        this.cross = cross;
        this.player = player;
    }
    public PCPair(){}

    public Cross getCross() {
        return cross;
    }

    public void setCross(Cross cross) {
        this.cross = cross;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
