package gogame.server;

import gogame.model.*;
import gogame.services.IObserver;
import gogame.services.IServices;

import java.rmi.RemoteException;
import java.util.*;

public class ServicesImpl implements IServices {

    List<IObserver> clients = new ArrayList<>();

    public ServicesImpl(){}




    public String login(IObserver client) throws RemoteException {
        if(clients.size()==0){
            clients.add(client);
            return "BLACK";
        }
        else if(clients.size()==1){
            clients.add(client);
            clients.get(0).letStart();
            return "WHITE";
        }
        return "TOO MANY";
    }

    @Override
    public void logout(IObserver client) throws RemoteException {
        clients.remove(client);
        System.out.println("logout: "+ clients.indexOf(client));
    }

    private int stoneSize;
    private GoBoard board;
    private Player white = new Player("WHITE");
    private Player black = new Player("BLACK");
    private Player currentPlayer = new Player("BLACK");
    private int passesNumber;

    public void setSizes(int boardSize, int stoneSize){
        this.stoneSize = stoneSize;
        board = new GoBoard(boardSize);
    }
    //    public ClientController(int boardSize, int stoneSize) {
//        this.stoneSize = stoneSize;
//        board = new GoBoard(boardSize);
//    }
    public String computeScore(){
        double whiteScore=6.5;
        double blackScore=0;
        int [][] verified = new int[board.getBoardSize()+1][board.getBoardSize()+1];
        for(int i =1;i<=board.getBoardSize();i++){
            for(int j=1;j<=board.getBoardSize();j++){
                verified[i][j]=0;
            }
        }
        //parcurgem matricea
        for(int i =1;i<=board.getBoardSize();i++) {
          for (int j = 1; j <= board.getBoardSize(); j++) {
                List<PCPair> growing = new ArrayList<>();
                Queue<PCPair> grQueue = new LinkedList<>();
                List<PCPair> frontier = new ArrayList<>();
                PCPair currentCross = board.getCrosses()[i][j];
                if (verified[i][j] == 0 && board.getCrosses()[i][j].getPlayer().getColor().equals("AVAILABLE")) {
                    System.out.println("------>>> i si j "+ i + " " +j);
                    growing.add(currentCross);
                    grQueue.add(currentCross);
                    //we check all the neighbours and add to the list only the empty ones
                    while (!grQueue.isEmpty()) {

                        PCPair varf = grQueue.peek();
                        int x = varf.getCross().getI();
                        int y = varf.getCross().getJ();

                        if (x > 1) {
                            PCPair up = board.getCrosses()[x - 1][y];
                            if (up.getPlayer().getColor().equals("AVAILABLE") && !growing.contains(up)) {
                                growing.add(up);
                                grQueue.add(up);
                            } else if(!growing.contains(up)&&!frontier.contains(up)) {
                                frontier.add(up);
                            }
                        }

                        if (x < board.getBoardSize()) {
                            PCPair down = board.getCrosses()[x + 1][y];
                            if (down.getPlayer().getColor().equals("AVAILABLE")&& !growing.contains(down)) {
                                growing.add(down);
                                grQueue.add(down);
                            } else if(!growing.contains(down)&&!frontier.contains(down)) {
                                frontier.add(down);
                            }
                        }
                        if (y > 1) {
                            PCPair left = board.getCrosses()[x][y - 1];
                            if (left.getPlayer().getColor().equals("AVAILABLE")&& !growing.contains(left)) {
                                growing.add(left);
                                grQueue.add(left);
                            } else if(!growing.contains(left)&&!frontier.contains(left))  {
                                frontier.add(left);
                            }
                        }
                        if (y < board.getBoardSize()) {
                            PCPair right = board.getCrosses()[x][y + 1];
                            if (right.getPlayer().getColor().equals("AVAILABLE")&& !growing.contains(right)) {
                                growing.add(right);
                                grQueue.add(right);
                            } else if (!growing.contains(right)&&!frontier.contains(right)) {
                                frontier.add(right);
                            }
                        }
                        //we checked all possible neighbours, now we pop the stack
                        grQueue.remove();

                    }
                   //the stack is empty, so there is no other empty neighbour --> we check the frontier list
                    System.out.println("the stack is empty, so there is no other empty neighbour --> we check the frontier list");
                    int wh=0;
                    int bl=0;
                    for (PCPair pair : frontier){
                        if(pair.getPlayer().getColor().equals("WHITE")){
                            wh++;
                        }
                        else if(pair.getPlayer().getColor().equals("BLACK")){
                            bl++;
                        }
                        else
                            System.out.println("smth is quite wrong");
                    }
                    //if the island is surrounded by a player, he gets the points
                    System.out.println("white: "+ wh);
                    System.out.println("black: "+ bl);
                    if(bl==frontier.size()&&frontier.size()!=0){
                        System.out.println("it is a black island");
                        blackScore+=growing.size();
                    }
                    else if(wh==frontier.size()&&frontier.size()!=0){
                        System.out.println("it is a white island");
                        whiteScore+= growing.size();
                    }
                    else
                        System.out.println("NO STONES PLACED");
                    //verify the lists
                    for(PCPair pair : growing){
                       int x = pair.getCross().getI();
                       int y = pair.getCross().getJ();
                       verified[x][y] = 1;
                    }
                    for(PCPair pair : frontier){
                        int x = pair.getCross().getI();
                        int y = pair.getCross().getJ();
                        verified[x][y] = 1;
                    }
                    System.out.println("WHITE SCORE: "+ whiteScore);
                    System.out.println("BLACK SCORE: "+ blackScore);
                }
//
           }

        }
        whiteScore+=white.getStonesTaken();
        blackScore+=black.getStonesTaken();
        String result="" ;
        if(whiteScore>blackScore){
            result="Castigatorul este WHITE \n" + "WHITE: " + whiteScore + "\nBLACK: " + blackScore;
        }
        else{
            result="Castigatorul este BLACK \n" + "WHITE: " + whiteScore + "\nBLACK: " + blackScore;
        }

        return result;
    }
    public void changePlayer(){
        if(currentPlayer.getColor().equals("BLACK")){
            currentPlayer = new Player (white);
        }else if(currentPlayer.getColor().equals("WHITE")){
            currentPlayer = new Player (black);
        }
    }
    public void placeStone(Player player, int i, int j){
        board.getCrosses()[i][j].setPlayer(player);
        System.out.println("stone placed");
    }
    public void sayWhiteToPlace(){

    }
    public void sayBlackToPlace() throws RemoteException {
        clients.get(0).letStart();
    }

    public String captureGroup(int i, int j) throws RemoteException {
        System.out.println("a group is captured");
        //delete all the stones in a stonegroup
        String capturer = getCapturerColor(board.getCrosses()[i][j].getPlayer().getColor());
        List<Cross> group = board.getCrosses()[i][j].getCross().getStoneGroup().getStoneList();
        List<Cross> crosses = new ArrayList<>(group);
        crosses.addAll(group);
        if(capturer.equals("WHITE"))
            white.addStones(group.size());
        else
            black.addStones(group.size());
        for(Cross cross : group){
            int x = cross.getI();
            int y = cross.getJ();
            PCPair stoneToCapture = board.getCrosses()[x][y];
            stoneToCapture.setCross(new Cross(x,y));
            stoneToCapture.setPlayer(new Player());
        }
        clients.get(0).capture(crosses);
        clients.get(1).capture(crosses);
        showStoneScore();
        return "captured";
    }
    public String getCapturerColor(String capturedColor){
        if(capturedColor.equals("WHITE")){
            return "BLACK";
        }
        else
            return "WHITE";
    }
    public void showStoneScore() {
        System.out.println("WHITE STONES: "+ white.getStonesTaken());
        System.out.println("BLACK STONES: "+ black.getStonesTaken());

    }

    public String checkGroup(int i, int j) throws RemoteException {
        String result="nothing";
        PCPair currentStone = board.getCrosses()[i][j];
        if(currentPlayer.getColor().equals("BLACK")){
            clients.get(1).opponentMoved(i,j);
        }
        else{
            clients.get(0).opponentMoved(i,j);
        }
        if(i>1) {
            PCPair up = board.getCrosses()[i-1][j];
            if (up.getPlayer().equals(currentPlayer)) {
                for (Cross cross : up.getCross().getStoneGroup().getStoneList())
                    if(!currentStone.getCross().getStoneGroup().getStoneList().contains(cross))
                        currentStone.getCross().getStoneGroup().getStoneList().add(cross);
            }
            else if(!up.getPlayer().getColor().equals("AVAILABLE")){

                System.out.println("found a stranger stone up and we check liberties");

                computeGroupLiberties(i-1,j);
                System.out.println("EMPTY_N " + board.getEmptyNeighbours(i-1,j));
                System.out.println("LIBERTIES " + up.getCross().getStoneGroup().getLiberties().size());

                if(up.getCross().getStoneGroup().getLiberties().size()==0){
                    captureGroup(i-1,j);
                    result="captured";
                }
            }
        }
        if(i<board.getBoardSize()) {
            PCPair down = board.getCrosses()[i+1][j];
            if (down.getPlayer().equals(currentPlayer)) {
                for (Cross cross : down.getCross().getStoneGroup().getStoneList())
                    if(!currentStone.getCross().getStoneGroup().getStoneList().contains(cross))
                        currentStone.getCross().getStoneGroup().getStoneList().add(cross);
            }
            else if(!down.getPlayer().getColor().equals("AVAILABLE")){

                System.out.println("found a stranger stone down and we check liberties");

                computeGroupLiberties(i+1,j);
                System.out.println("EMPTY_N " + board.getEmptyNeighbours(i+1,j));
                System.out.println("LIBERTIES " + down.getCross().getStoneGroup().getLiberties().size());

                if(down.getCross().getStoneGroup().getLiberties().size()==0){
                    captureGroup(i+1,j);
                    result="captured";
                }
            }
        }
        if(j>1) {
            PCPair left = board.getCrosses()[i ][j-1];
            if (left.getPlayer().equals(currentPlayer)) {
                for (Cross cross : left.getCross().getStoneGroup().getStoneList())
                    if(!currentStone.getCross().getStoneGroup().getStoneList().contains(cross))
                        currentStone.getCross().getStoneGroup().getStoneList().add(cross);
            }
            else if(!left.getPlayer().getColor().equals("AVAILABLE")){

                System.out.println("found a stranger stone left and we check liberties");

                computeGroupLiberties(i,j-1);
                System.out.println("EMPTY_N " + board.getEmptyNeighbours(i,j-1));
                System.out.println("LIBERTIES " + left.getCross().getStoneGroup().getLiberties().size());

                if(left.getCross().getStoneGroup().getLiberties().size()==0){
                    captureGroup(i,j-1);
                    result="captured";
                }
            }
        }
        if(j< board.getBoardSize()) {
            PCPair right = board.getCrosses()[i][j + 1];
            if (right.getPlayer().equals(currentPlayer)) {
                for (Cross cross : right.getCross().getStoneGroup().getStoneList())
                    if(!currentStone.getCross().getStoneGroup().getStoneList().contains(cross))
                        currentStone.getCross().getStoneGroup().getStoneList().add(cross);
            }
            else if(!right.getPlayer().getColor().equals("AVAILABLE")){

                System.out.println("found a stranger stone right and we check liberties");

                computeGroupLiberties(i,j+1);
                System.out.println("EMPTY_N " + board.getEmptyNeighbours(i,j+1));
                System.out.println("LIBERTIES " + right.getCross().getStoneGroup().getLiberties().size());

                if(right.getCross().getStoneGroup().getLiberties().size()==0){
                    captureGroup(i,j+1);
                    result="captured";
                }
            }
        }

        for(Cross cross : currentStone.getCross().getStoneGroup().getStoneList()){
            cross.getStoneGroup().setStoneList(currentStone.getCross().getStoneGroup().getStoneList());
        }

        computeGroupLiberties(i,j);
        if(board.getCrosses()[i][j].getCross().getStoneGroup().getLiberties().size()==0){
            captureGroup(i,j);
            result="captured";
        }

        changePlayer();
    return result;
    }
    public boolean isInGroupWith(PCPair stoneInGroup, PCPair stoneToCheck){
        return stoneInGroup.getCross().getStoneGroup().getStoneList().contains(stoneToCheck.getCross());
    }
    public void showGroup(){
        int max=0;
        List<Cross> bsg = new ArrayList<>();
        for(int i =1;i<=board.getBoardSize();i++){
            for(int j=1;j<=board.getBoardSize();j++){
                List<Cross> stoneG = board.getCrosses()[i][j].getCross().getStoneGroup().getStoneList();
                if(stoneG.size()>max){
                    max = stoneG.size();
                    bsg = stoneG;
                }
            }
        }
        System.out.println(bsg.size());
    }
    public void showGroupLiberties(int i,int j){
        System.out.println(board.getCrosses()[i][j].getCross().getStoneGroup().getLiberties().size());
    }

    public void computeGroupLiberties(int x, int y){
        if(!board.getCrosses()[x][y].getPlayer().getColor().equals("AVAILABLE")){
            StoneGroup group = board.getCrosses()[x][y].getCross().getStoneGroup();
            List<Cross> libertiesList= new ArrayList<>();
            for (Cross cross : group.getStoneList()) {
                int i = cross.getI();
                int j = cross.getJ();
                //check empty neighbours and add them if they are not already in the list
                for(Cross cross1 : board.getEmptyNeighbours(i,j)){
                    if(!libertiesList.contains(cross1)){
                        libertiesList.add(cross1);
                    }
                }
            }
            for (Cross cross : group.getStoneList()) {
                cross.getStoneGroup().setLiberties(libertiesList);
            }

        }
    }


    public int getStoneSize() {
        return stoneSize;
    }

    public void setStoneSize(int stoneSize) {
        this.stoneSize = stoneSize;
    }

    public GoBoard getBoard() {
        return board;
    }

    public void setBoard(GoBoard board) {
        this.board = board;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getPassesNumber() {
        return passesNumber;
    }
    public void setPassesNumber(int passesNumber) {
        this.passesNumber = passesNumber;
    }
}
