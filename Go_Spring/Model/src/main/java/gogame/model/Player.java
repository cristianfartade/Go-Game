package gogame.model;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private final String color;
    private int stonesTaken;
    private String playerPath;


    public Player(String color) {
        this.color=color;
        this.stonesTaken = 0;
        if(color.equals("WHITE")){
            playerPath="/images/white_stone.png";
        }else if(color.equals("BLACK")){
            playerPath="/images/black_stone.png";
        }
    }
    public Player() {
        this.color="AVAILABLE";
    }
    public Player(Player oldPlayer){
        this.color=oldPlayer.getColor();
        this.stonesTaken= oldPlayer.getStonesTaken();
        this.playerPath= oldPlayer.getPlayerPath();
    }

    public String getPlayerPath() {
        return playerPath;
    }

    public void setPlayerPath(String playerPath) {
        this.playerPath = playerPath;
    }

    public void addStones(int stonesNumber){
        stonesTaken+=stonesNumber;
    }

    public String getColor() {
        return color;
    }

    public int getStonesTaken() {
        return stonesTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return stonesTaken == player.stonesTaken && Objects.equals(color, player.color) && Objects.equals(playerPath, player.playerPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, stonesTaken, playerPath);
    }
}
