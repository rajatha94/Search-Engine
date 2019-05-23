package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

  Button search;
  Button back;
  Scene scene1, scene2;
  Stage window;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setMaximized(true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Researcher");
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void handle(ActionEvent actionEvent) {
    if (search == actionEvent.getSource()) {
      window.setScene(scene2);
    }
    if (back == actionEvent.getSource()) {
      window.setScene(scene1);
    }
  }
}
