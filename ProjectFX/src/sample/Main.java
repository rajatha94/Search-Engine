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



//    window = primaryStage;
//
//    Label label = new Label("Please click below to search");
//    search = new Button("Search");
//    search.setOnAction(this);
//    HBox hBox = new HBox();
//    TextField textField = new TextField();
//    textField.setAlignment(Pos.CENTER);
//    textField.setText("Researchr");
//    textField.setMinSize(20, 20);
//    hBox.setAlignment(Pos.CENTER);
//    hBox.getChildren().addAll(textField);
//    VBox layout = new VBox(20);
//    layout.getChildren().addAll(label, search);
//
//
//    BorderPane borderPane = new BorderPane();
//    borderPane.setTop(hBox);
//    borderPane.setCenter(layout);
//
//    scene1 = new Scene(borderPane, 200, 200);
//
//
//    back = new Button("Back");
//    back.setOnAction(this);
//    StackPane stackPane = new StackPane();
//    stackPane.getChildren().add(back);
//    scene2 = new Scene(stackPane, 600, 600);
//    window.setTitle("Researcher");
//    window.setScene(scene1);
//    window.show();
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
