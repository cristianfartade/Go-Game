package gogame.client.gui;

import gogame.model.*;
import gogame.services.IObserver;
import gogame.services.IServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends UnicastRemoteObject implements IObserver, Serializable {

     IServices server;
     String color;
     ControllerGO ctrl;
     boolean myTurn=false;


    public String login() throws RemoteException {
        color=server.login(this);
        return color;
    }
    public ClientController(IServices server) throws RemoteException {
        this.server=server;
    }

    public String getColor() {
        return color;
    }

    public void setCtrl(ControllerGO ctrl) {
        this.ctrl = ctrl;
    }

    public void letStart(){
        myTurn=true;
        ctrl.updateTurn("You can start");
    }
    public void opponentMoved(int i, int j){
        ctrl.placePiece(i-1,j-1);
        myTurn=true;
        ctrl.updateTurn("It's my turn");
    }

    public void capture(List<Cross> crosses) throws RemoteException {
        for(Cross cross : crosses){
            int i= cross.getI();
            int j = cross.getJ();
            ctrl.deletePiece(i-1,j-1);
        }
    }
    public void logout() throws RemoteException{
        server.logout(this);
    }
}
