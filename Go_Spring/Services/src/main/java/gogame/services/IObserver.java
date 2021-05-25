package gogame.services;

import gogame.model.Cross;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IObserver extends Remote {
    void letStart()throws RemoteException;
    void opponentMoved(int i, int j) throws RemoteException;
    void capture(List<Cross> crosses )throws RemoteException;
}
