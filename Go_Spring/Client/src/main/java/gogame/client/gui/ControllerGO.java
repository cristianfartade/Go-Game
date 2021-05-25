package gogame.client.gui;


import gogame.model.Player;
import gogame.services.IServices;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ControllerGO {
    private ClientController game;
    private List<List<Button>> buttonList = new ArrayList<>();
    private Pair<Button,Boolean>[][] crossesClicked;
    private Pair<Button,String>[][] crossesPlayer;



    BorderPane mainPane;
    @FXML Button btnUndoAll;
    @FXML Button btnUndo;
    @FXML Button btnRedoAll;
    @FXML Button btnRedo;
    @FXML Button btnNewGame;
    @FXML Label label;

    public void updateTurn(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(message);
            }
        });

    }
    public void setGame(ClientController game) {
        this.game = game;
    }
    public String setServer(IServices server,int boardSize, int stoneSize) throws RemoteException {
        this.game=new ClientController(server);
        game.setCtrl(this);
        server.setSizes(boardSize,stoneSize);

//        ((Stage)label.getScene().setO)

        return game.login();
    }
    public void deletePiece(int i, int j){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                buttonList.get(i).get(j).setGraphic(new ImageView());
            }
        });
        crossesClicked[i+1][j+1]=new Pair<>(buttonList.get(i).get(j),false);
    }
    public void placePiece(int i, int j){
        String myColor = game.getColor();
        Player opponent = new Player();
        if(myColor.equals("WHITE")){
           opponent = new Player(game.server.getBlack());
        }
        else{
            opponent = new Player(game.server.getWhite());
        }
        Image img = new Image(opponent.getPlayerPath());
        ImageView view = new ImageView(img);
        view.setFitHeight(game.server.getStoneSize());
        view.setFitWidth(game.server.getStoneSize());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                buttonList.get(i).get(j).setGraphic(view);
            }
        });
        crossesClicked[i+1][j+1]=new Pair<>(buttonList.get(i).get(j),true);

    }
    public void setMainPane(BorderPane mainPane) {
        this.mainPane = mainPane;
    }
    public void newGame(){
        VBox vbox = new VBox();
        int size = game.server.getBoard().getBoardSize();
        vbox.setPrefHeight(size *100);
        vbox.setPrefWidth(size *100);
        //matrix creation
        crossesClicked = new Pair[size+1][size+1];
        crossesPlayer = new Pair[size+1][size+1];
        for(int i = 1; i<= size; i++){
            List<Button> row= new ArrayList<>();
            HBox hbox= new HBox();
            hbox.setPrefHeight(100);
            hbox.setPrefWidth(size *100);
            for(int j = 1; j<= size; j++){
                Button button = new Button();
                button.setPrefHeight(100);
                button.setPrefWidth(100);
                /// adding hover action listener for each cross
                crossHover(button,i,j);
                /// adding click action listener for each cross
                crossClick(button,i,j);
                /// selecting the image
                chooseCrossImg(button, i,j);
                hbox.getChildren().add(button);
//                crossesClicked.put(button,false);  ////-----
                row.add(button);
                crossesClicked[i][j] = new Pair<>(button,false);
                crossesPlayer[i][j] = new Pair<>(button,"AVAILABLE");
            }
            buttonList.add(row);
            vbox.getChildren().add(hbox);
        }
        mainPane.setCenter(vbox);
    }

    private void chooseCrossImg(Button button, int i, int j) {
       int size=game.server.getBoard().getBoardSize();
        if(i==1){
            if(j==1){
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/ul.png');-fx-focus-color: transparent;-fx-border-style: none");

            }
            else if(j== size){
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/ur.png');-fx-focus-color: transparent;-fx-border-style: none");
            }
            else button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/u.png');-fx-focus-color: transparent;-fx-border-style: none");

        }
        else if(i== size){
            if(j==1){
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/bl.png');-fx-focus-color: transparent;-fx-border-style: none");
            }
            else if(j== size){
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/br.png');-fx-focus-color: transparent;-fx-border-style: none");
            }
            else button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/b.png');-fx-focus-color: transparent;-fx-border-style: none");

        }
        else{
            if(j==1)
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/l.png');-fx-focus-color: transparent;-fx-border-style: none");
            else if(j== size)
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/r.png');-fx-focus-color: transparent;-fx-border-style: none");
            else {
                button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/c.png');-fx-focus-color: transparent;-fx-border-style: none");
                switch (size) {
                    case 9:
                        if ((i == 3 || i == 5 || i == 7) && (j == 3 || j == 5 || j == 7))
                            button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/spot.png');-fx-focus-color: transparent;-fx-border-style: none");
                        break;
                    case 13:
                        if ((i == 4 || i == 7 || i == 10) && (j == 4 || j == 7 || j == 10))
                            button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/spot.png');-fx-focus-color: transparent;-fx-border-style: none");
                        break;
                    case 19:
                        if ((i == 4 || i == 10 || i == 16) && (j == 4 || j == 10 || j == 16))
                            button.setStyle("-fx-padding: 0;-fx-background-position: center;-fx-background-image: url('/images/spot.png');-fx-focus-color: transparent;-fx-border-style: none");
                        break;
                    default:
                        break;
                }
            }
            }



    }
    private void refreshBoard(){
        for(int i =1;i<=game.server.getBoard().getBoardSize();i++){
            for(int j=1;j<=game.server.getBoard().getBoardSize();j++){
                Button button = crossesPlayer[i][j].getKey();
                if(game.server.getBoard().getCrosses()[i][j].getPlayer().getColor().equals("AVAILABLE")){
                   //make the equivalent button in the GUI also empty
                    button.setGraphic(new ImageView());
                    crossesClicked[i][j]= new Pair<>(button,false);

                }
                else if(game.server.getBoard().getCrosses()[i][j].getPlayer().getColor().equals("WHITE")){
                    // make the equivalent button hold a white piece
                    Image img = new Image(game.server.getWhite().getPlayerPath());
                    ImageView view = new ImageView(img);
                    view.setFitHeight(game.server.getStoneSize());
                    view.setFitWidth(game.server.getStoneSize());
                    button.setGraphic(view);
                }
                else{
                    Image img = new Image(game.server.getBlack().getPlayerPath());
                    ImageView view = new ImageView(img);
                    view.setFitHeight(game.server.getStoneSize());
                    view.setFitWidth(game.server.getStoneSize());
                    button.setGraphic(view);
                }
            }
        }
    }
    private void crossClick(Button button, int i, int j) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(game.myTurn==true){
                if(crossesClicked[i][j].getValue()==false) {
                    String myColor = game.getColor();
                    Player actualPlayer = new Player(myColor);
                    Image img = new Image(actualPlayer.getPlayerPath());
                    ImageView view = new ImageView(img);
                    view.setFitHeight(game.server.getStoneSize());
                    view.setFitWidth(game.server.getStoneSize());
                    button.setGraphic(view);
//                    crossesClicked.put(button,true);    ///----

                    crossesClicked[i][j]=new Pair<>(button,true);
                    crossesPlayer[i][j] = new Pair<>(button,game.server.getCurrentPlayer().getColor());

                    //modificam boardul din game -> jucatorul curent pune o piesa jos
                    game.server.placeStone(game.server.getCurrentPlayer(),i,j);
                    //verificam daca piesa pusa pe tabla este capturata sau ajuta la capturarea alteia
                    try {
                        String result=game.server.checkGroup(i,j);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
//                    if(!result.equals("nothing")){
//                        refreshBoard();
//                    }
//                    game.showGroup();
//                    game.computeGroupLiberties(i,j);
//                    game.showGroupLiberties(i,j);
//                    System.out.println(game.getBoard().getEmptyNeighbours(i,j));

                    //contorul de PASS ia valoarea 0
                    game.server.setPassesNumber(0);
                    //se trece la urmatorul jucator
//                    game.server.changePlayer();

                    game.myTurn=false;
                    updateTurn("Not my turn");

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("There is already a stone here");
                    alert.showAndWait();
                }


            }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("It's not your turn");
                    alert.showAndWait();
                }
            }
        });
    }

    private void crossHover(Button button, int i, int j) {
        button.hoverProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) -> {

                    if(newVal) {
                        String myColor = game.getColor();
                        Player actualPlayer = new Player(myColor);
                        if ( crossesClicked[i][j].getValue()==false) {
                            Image img = new Image(actualPlayer.getPlayerPath());
                            ImageView view = new ImageView(img);
                            view.setFitHeight(game.server.getStoneSize());
                            view.setFitWidth(game.server.getStoneSize());
                            button.setGraphic(view);
                        }
                    }
                    else if( crossesClicked[i][j].getValue()==false)
                        button.setGraphic(new ImageView());



            }
        );
    }


    @FXML void passClicked(ActionEvent actionEvent) {
        game.server.setPassesNumber(game.server.getPassesNumber()+1);

        if(game.server.getPassesNumber()==2){
            System.out.println("JOCUL S-A INCHEIAT");
            String result = game.server.computeScore();
            Alert message= new Alert(Alert.AlertType.INFORMATION);
            message.setContentText(result);
            message.showAndWait();
            //este calculat scorul si afisat castigatorul
        }
    }
    public void logout() throws RemoteException{
        game.logout();
    }
}
