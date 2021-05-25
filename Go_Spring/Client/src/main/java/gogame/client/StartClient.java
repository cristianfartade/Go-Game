package gogame.client;

import gogame.client.gui.ClientController;
import gogame.client.gui.ControllerFW;
import gogame.services.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application {
    public void start(Stage primaryStage) throws Exception{
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServices server=(IServices)factory.getBean("goGameServer");
        System.out.println("Obtained a reference to remote chat server");
//        ClientController ctrl=new ClientController(server);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/first.fxml"));
        Parent root = loader.load();
        ControllerFW controlerFW=loader.getController();
//        controlerFW.setServer(ctrl);
        controlerFW.setServer(server);
        primaryStage.setTitle("GO game");
        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
