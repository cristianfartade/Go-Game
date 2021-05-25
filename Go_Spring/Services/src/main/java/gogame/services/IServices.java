package gogame.services;

import gogame.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IServices {

    String login(IObserver client) throws RemoteException;
    void logout(IObserver client) throws RemoteException;
    void changePlayer();
    void placeStone(Player player, int i, int j);
    String captureGroup(int i, int j) throws RemoteException;
    String checkGroup(int i, int j) throws RemoteException;
    void showStoneScore();
    String getCapturerColor(String capturedColor);
    void setSizes(int boardSize, int stoneSize);
    boolean isInGroupWith(PCPair stoneInGroup, PCPair stoneToCheck);
    void showGroup();
    void showGroupLiberties(int i,int j);
    String computeScore();
    void computeGroupLiberties(int x, int y);

    int getStoneSize();

    void setStoneSize(int stoneSize);

    GoBoard getBoard();

    void setBoard(GoBoard board);

    Player getWhite();

    Player getBlack();

    Player getCurrentPlayer();

    void setCurrentPlayer(Player currentPlayer);

    int getPassesNumber();
    void setPassesNumber(int passesNumber);

}
