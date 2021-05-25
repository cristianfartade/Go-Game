package gogame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoBoard implements Serializable {
    private int boardSize;
    private PCPair[][] crosses;

    public GoBoard(int size) {

        this.boardSize = size;
        this.crosses=new PCPair[size+1][size+1];;
        createBoard(size);
        }

        public GoBoard(){}

//    public Button createSquare(int x, int y) {
//        Button square = new Button();
//        square.setPickOnBounds(false); //The pick bounds 'wander' upwards is this isn't set. Don't know why though?
//        square.getStyleClass().setAll("square", "greenFocusHighlight");
//        square.getStyleClass().add( (x + y) % 2 == 1 ? "lightSquare" : "darkSquare");
//        return square;
//    }
    private void createBoard(int size) {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                crosses[i][j]= new PCPair(new Cross(i,j),new Player());
            }
        }
    }
    public List<Cross> getEmptyNeighbours(int i, int j){
        List<Cross> neighbours = new ArrayList<>();
        if(i>1){
            PCPair up = crosses[i-1][j];
            if(up.getPlayer().getColor().equals("AVAILABLE")){
                neighbours.add(up.getCross());
            }
        }
        if(i<getBoardSize()){
            PCPair down = crosses[i+1][j];
            if(down.getPlayer().getColor().equals("AVAILABLE")){
                neighbours.add(down.getCross());
            }
        }
        if(j>1){
            PCPair left = crosses[i][j-1];
            if(left.getPlayer().getColor().equals("AVAILABLE")){
                neighbours.add(left.getCross());
            }
        }
        if(j<getBoardSize()){
            PCPair right = crosses[i][j+1];
            if(right.getPlayer().getColor().equals("AVAILABLE")){
                neighbours.add(right.getCross());
            }
        }

        return neighbours;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public PCPair[][] getCrosses() {
        return crosses;
    }
}
