package gogame.client.gui;

import gogame.services.IServices;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class ControllerFW {
    VBox mainPane;
    ClientController game;
    IServices server;
    @FXML Label label;
    @FXML ComboBox<String> combo;
    @FXML Button startButton;
    public void setMainPane(VBox mainPane) {
        this.mainPane = mainPane;
    }
    public void initialize(){
        combo.setItems(FXCollections.observableArrayList("9x9","13x13","19x19"));
        combo.getSelectionModel().selectFirst();
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void setServer(ClientController server){
        this.game=server;
    }
    public void handleStart() {
//        if (game.login() == null) {
            String choice = combo.getValue();
            int boardSize = 9;
            int windowSize = 600;
            int stoneSize = 60;
            if (choice.equals("13x13")) {
                boardSize = 13;
                windowSize = 750;
                stoneSize = 50;
            } else if (choice.equals("19x19")) {
                boardSize = 19;
                windowSize = 900;
                stoneSize = 45;
            }
            //ClientController game = new ClientController();
//            game.server.setSizes(boardSize, stoneSize);
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/game.fxml"));
            BorderPane root = null;
            try {
                root = loader.load();
                ControllerGO ctrl = loader.getController();
                ctrl.setMainPane(root);
                stage.setOnCloseRequest(event->{
                    try{
                        ctrl.logout();
                    }catch (RemoteException ex){
                        Alert message= new Alert(Alert.AlertType.INFORMATION);
                        message.setContentText("Error on logout");
                        message.showAndWait();
                    }
                });
                if(ctrl.setServer(server,boardSize, stoneSize).equals("TOO MANY")){
                    Alert message= new Alert(Alert.AlertType.INFORMATION);
                    message.setContentText("Sunt deja 2 jucatori");
                    message.showAndWait();
                }
                else{
//                ctrl.setGame(game);
//                ctrl.setBoardSize(boardSize);
//                ctrl.setStoneSize(stoneSize);
                    ctrl.newGame();
                    stage.setTitle("GO game");
                    stage.setScene(new Scene(root, windowSize, windowSize + 100));
                    stage.setResizable(false);
                    stage.show();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
//        else{
//            Alert message= new Alert(Alert.AlertType.INFORMATION);
//            message.setContentText("Sunt deja 2 jucatori");
//            message.showAndWait();
//        }
//        Alert message= new Alert(Alert.AlertType.INFORMATION);
//            message.setContentText(game.sum(1,2)+"");
//            message.showAndWait();
    }


}
