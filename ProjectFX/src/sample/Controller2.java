package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Controller2 implements Initializable {

  @FXML
  public Pagination paginator;

  @FXML
  public BorderPane borderPane;

  @FXML
  public Button button;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    button.setOnAction(actionEvent -> {
      Parent root = null;
      try {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Controller controller = loader.getController();
        controller.initialize(null, null);
        Controller.researchPaperList = new ArrayList<>();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Researcher");
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

  }

  public ScrollPane createPage(int pageIndex) {
    Stage stage = (Stage) borderPane.getScene().getWindow();
    stage.setMaximized(true);
    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
    scroll.setStyle("-fx-background-color: #383838;");
    scroll.setFitToWidth(true);
    VBox box = new VBox(5);
    box.setStyle("-fx-background-color: #383838;");
    box.setMinWidth(1800);
    box.setMinHeight(1500);
    int page = pageIndex * 10;
    for (int i = page; i < page + 10; i++) {
      VBox element = new VBox();
      box.setMargin(element, new Insets(30, 0, 0, 0));
      ResearchPaper researchPaper = Controller.researchPaperList.get(i);

//      Label id = new Label(researchPaper.getId());
//      id.setTextFill(Color.LIGHTGRAY);
//      id.setWrapText(true);
      //element.setMargin(id, new Insets(0, 50, 0, 0));

      HBox titleBox = new HBox();
      Hyperlink title1 = buildEachPaperLink(researchPaper.getTitle().trim(), i);
      title1.setTextFill(Color.LIGHTGRAY);
      title1.setWrapText(true);
      title1.setStyle("-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 24px;");
      titleBox.getChildren().add(title1);
      if (researchPaper.getDocType() != "") {
        Label docType = new Label("[" + researchPaper.getDocType() + "]");
        docType.setTextFill(Color.LIGHTGRAY);
        docType.setWrapText(true);
        docType.setStyle("-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 9px;");
        titleBox.getChildren().add(docType);
      }
      Label yearPublisher = new Label("");
      if (researchPaper.getYear() != "") {
        if (researchPaper.getPublisher() != "") {
          yearPublisher = new Label(
              researchPaper.getYear() + "  |  " + researchPaper.getPublisher());
        } else {
          yearPublisher = new Label(researchPaper.getYear());
        }
        yearPublisher.setTextFill(Color.LIGHTGRAY);
        yearPublisher.setWrapText(true);
        yearPublisher.setStyle("-fx-font-style: 'italic';"
            + "-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 11px;");
        element.setMargin(yearPublisher, new Insets(0, 0, 0, 10));
      } else if (researchPaper.getPublisher() != "") {
        yearPublisher = new Label(researchPaper.getPublisher());
        yearPublisher.setTextFill(Color.LIGHTGRAY);
        yearPublisher.setWrapText(true);
        yearPublisher.setStyle("-fx-font-style: 'italic';"
            + "-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 11px;");
        element.setMargin(yearPublisher, new Insets(0, 0, 0, 10));
      }
      Label authors = new Label("");
      if (!researchPaper.getAuthors().isEmpty()) {
        authors = new Label(researchPaper.getAuthors().toString());
        authors.setTextFill(Color.LIGHTGRAY);
        authors.setWrapText(true);
        authors.setStyle("-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 11px;");
        element.setMargin(authors, new Insets(0, 0, 0, 10));
      }

//      TextFlow flow = new TextFlow(title1);
//      flow.setPadding(new Insets(10));
//      Label title = new Label(researchPaper.getTitle());
//      title.setWrapText(true);
//      title.setTextFill(Color.LIGHTGRAY);

      Label abstractString = new Label("");
      if (researchPaper.getAbstractString() != "") {
        abstractString = new Label(researchPaper.getAbstractString());
        abstractString.setTextFill(Color.LIGHTGRAY);
        //abstractString.setWrapText(true);
        abstractString.setStyle("-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 14px;");
        element.setMargin(abstractString, new Insets(5, 100, 0, 10));
      }

      HBox citations = new HBox();
      Label nCitations = new Label("");
      if (!researchPaper.getNcitation().equals("")) {
        nCitations = new Label(researchPaper.getNcitation());
        nCitations.setTextFill(Color.LIGHTGRAY);
        nCitations.setWrapText(true);
        nCitations.setStyle("-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 11px;");
        //element.setMargin(nCitations, new Insets(5, 0, 0, 10));

        ImageView imageView = new ImageView(new Image("sample/light-bulb-5-16.png"));
        citations.getChildren().addAll(imageView, nCitations);
        element.setMargin(citations, new Insets(5, 0, 0, 10));
      }

      Label keywords = new Label("");
      if (!researchPaper.getKeywords().isEmpty()) {
        keywords = new Label(researchPaper.getKeywords().toString());
        keywords.setTextFill(Color.LIGHTGRAY);
        keywords.setWrapText(true);
        keywords.setStyle("-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 11px;");
        element.setMargin(keywords, new Insets(5, 0, 0, 10));
      }

      Separator separator = new Separator();
      separator.setOrientation(Orientation.HORIZONTAL);
      separator.setHalignment(HPos.CENTER);
      element.setMargin(separator, new Insets(7, 0, 0, 10));

      element.getChildren()
          .addAll(titleBox, yearPublisher, authors, abstractString, citations, separator);
      box.getChildren().add(element);
    }
    scroll.setContent(box);
    return scroll;
  }

  public Hyperlink buildEachPaperLink(String title, int paperID) {
    Hyperlink hyperlink = new Hyperlink(title);
    hyperlink.setOnAction(actionEvent -> {
      Stage currentStage = (Stage) borderPane.getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
      try {
        Parent root = loader.load();
        currentStage.setScene(new Scene(root));
        currentStage.setMaximized(true);
        currentStage.setTitle("Second Window");
        currentStage.show();
        Controller3 scene3Controller = loader.getController();
        scene3Controller.initialize(null, null);
        scene3Controller.setData(paperID);
        //scene3Controller.getResults(inputField.getText());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    return hyperlink;
  }

  public void getResults(String message) {

    paginator.setPageFactory(new Callback<Integer, Node>() {
      @Override
      public Node call(Integer pageIndex) {
        return createPage(pageIndex);
      }
    });
  }


}
