package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.solr.client.solrj.SolrServerException;

public class Controller3 implements Initializable {

  @FXML
  private Button button;

  @FXML
  private Button buttonToResults;

  @FXML
  private VBox box;

  @FXML
  private BorderPane borderPane;

  @FXML
  private Label title;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Platform.runLater(() -> borderPane.requestFocus());
    button.setOnAction(actionEvent -> {
      goback();
    });
    buttonToResults.setOnAction(actionEvent -> {
      backToResults();
    });
  }

  public void setData(int paperID) {
//    Stage stage = (Stage) borderPane.getScene().getWindow();
//    stage.setMaximized(true);

    ScrollPane scroll = new ScrollPane();
    scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
    scroll.setStyle("-fx-background-color: #383838;");
    scroll.setFitToWidth(true);
    VBox vBox = new VBox();
    vBox.setStyle("-fx-background-color: #383838;");
    vBox.setMinHeight(1500);
    ResearchPaper researchPaper = Controller.researchPaperList.get(paperID);
    HBox titleBox = new HBox();
    titleBox.setMaxWidth(box.getWidth());
    titleBox.setPadding(new Insets(20, 50, 0, 50));
    Label title = new Label(researchPaper.getTitle());
    title.setWrapText(true);
    title.setTextFill(Color.LIGHTGRAY);
    title.setStyle("-fx-font-family: 'Baskerville';"
        + "-fx-font-size: 38px;");
    titleBox.getChildren().add(title);
    if (researchPaper.getDocType() != "") {
      Label docType = new Label("[" + researchPaper.getDocType() + "]");
      docType.setTextFill(Color.valueOf("a1a1a1"));
      docType.setStyle("-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 12px;");
      titleBox.getChildren().add(docType);
    }
    titleBox.setAlignment(Pos.TOP_LEFT);

    Label authors = new Label("");
    if (!researchPaper.getAuthors().isEmpty()) {
      authors = new Label("");
      String authorText = "";
      ArrayList<String> authorList = researchPaper.getAuthors();
      authorText += authorList.get(0);
      for (int i = 1; i < authorList.size(); i++) {
        authorText += ", " + authorList.get(i);
      }
      authors.setText(authorText);
      authors.setTextFill(Color.valueOf("a1a1a1"));
      authors.setStyle("-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 18px;");
    }
    authors.setPadding(new Insets(0, 50, 0, 50));

    Label yearPublisher = new Label("");
    if (researchPaper.getYear() != "") {
      if (researchPaper.getPublisher() != "") {
        yearPublisher = new Label(
            researchPaper.getYear() + "  |  " + researchPaper.getPublisher());
      } else {
        yearPublisher = new Label(researchPaper.getYear());
      }
      yearPublisher.setTextFill(Color.valueOf("a1a1a1"));
      //yearPublisher.setWrapText(true);
      yearPublisher.setStyle("-fx-font-style: 'italic';"
          + "-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 18px;");
      yearPublisher.setPadding(new Insets(0, 50, 0, 50));
    } else if (researchPaper.getPublisher() != "") {
      yearPublisher = new Label(researchPaper.getPublisher());
      yearPublisher.setTextFill(Color.valueOf("a1a1a1"));
      //yearPublisher.setWrapText(true);
      yearPublisher.setStyle("-fx-font-style: 'italic';"
          + "-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 18px;");
      yearPublisher.setPadding(new Insets(0, 50, 0, 50));
    }

    Separator separator = new Separator();
    separator.setOrientation(Orientation.HORIZONTAL);
    separator.setHalignment(HPos.CENTER);
    separator.setMaxWidth(box.getWidth());
    separator.setPadding(new Insets(20, 50, 20, 50));

    Label abstractString = new Label("");
    if (researchPaper.getAbstractString() != "") {
      abstractString = new Label(researchPaper.getAbstractString());
      abstractString.setTextFill(Color.LIGHTGRAY);
      abstractString.setWrapText(true);
      abstractString.setStyle("-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 20px;");
      abstractString.setMaxWidth(box.getWidth());
      abstractString.setPadding(new Insets(5, 50, 0, 50));


    }

    FlowPane keywords = new FlowPane();
    keywords.setMaxWidth(box.getWidth());
    keywords.setPadding(new Insets(20, 50, 5, 50));
    keywords.setVgap(4);
    keywords.setHgap(4);
    //keywords.setPrefWrapLength(170);
    //HBox keywords = new HBox();
    keywords.setPadding(new Insets(0, 50, 0, 50));
    if (!researchPaper.getKeywords().isEmpty()) {
      ArrayList<String> keywordList = researchPaper.getKeywords();
      for (String kw : keywordList) {
        HBox keyWordBox = new HBox();
        keyWordBox.setMaxWidth(box.getWidth());
        Button keyword = new Button("");
        keyword.setText(kw);
        keyword.setTextFill(Color.valueOf("a1a1a1"));
        keyword.setCursor(Cursor.HAND);
        //keyWordBox.setMargin(keyword, new Insets(0, 50, 0, 0));
        keyword.setMaxWidth(box.getWidth());
        keyword.setWrapText(true);
        keyword.setStyle("-fx-font-family: 'Baskerville';"
            + "-fx-font-size: 12px;"
            + "-fx-background-color: transparent;");
        //keyword.setPadding(new Insets(20, 10, 0, 0));
        keyword.setOnAction(actionEvent -> {
          Parent root = null;
          try {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            root = loader.load();
            Controller.researchPaperList = new ArrayList<>();
            Controller controller1 = new Controller();
            //controller1.initialize(null, null);
            controller1.processQuery(keyword.getText());
            Scene scene = new Scene(root);
            Controller2 controller = loader.getController();
            controller.initialize(null, null);
            controller.getResults("");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Researcher");
            stage.show();
          } catch (IOException | SolrServerException e) {
            e.printStackTrace();
          }
        });

        ImageView imageView = new ImageView(new Image("sample/tag-5-16.png"));
        keyWordBox.getChildren().addAll(imageView, keyword);
        keywords.getChildren().add(keyWordBox);
      }
    }

    HBox citations = new HBox();
    citations.setPadding(new Insets(20, 50, 0, 50));
    Label nCitations = new Label("");
    if (!researchPaper.getNcitation().equals("")) {
      nCitations = new Label(researchPaper.getNcitation());
      nCitations.setTextFill(Color.LIGHTGRAY);
      nCitations.setWrapText(true);
      nCitations.setStyle("-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 15px;");
      //element.setMargin(nCitations, new Insets(5, 0, 0, 10));

      ImageView imageView = new ImageView(new Image("sample/light-bulb-5-24.png"));
      citations.getChildren().addAll(imageView, nCitations);
    }

    Label urls = new Label("");
    //urls.setStyle("-fx-background-color: #383838;");
    if (!researchPaper.getUrl().isEmpty()) {
      String urlText = "";
      ArrayList<String> urlList = researchPaper.getUrl();
      urlText += "Related Link(s): \n" + urlList.get(0);
      for (int i = 1; i < urlList.size(); i++) {
        urlText += "\n " + urlList.get(i);
      }
      urls.setText(urlText);
      urls.setTextFill(Color.valueOf("a1a1a1"));
      urls.setStyle("-fx-font-family: 'Baskerville';"
          + "-fx-font-size: 12px;");
    }
    urls.setWrapText(true);
    urls.setMaxWidth(box.getWidth());
    urls.setPadding(new Insets(20, 50, 0, 50));
    //scroll.setContent(urls);

    vBox.setMargin(abstractString, new Insets(0, 100, 0, 0));
    vBox.setMargin(keywords, new Insets(0, 100, 0, 0));
    //vBox.setAlignment(Pos.TOP_LEFT);
    vBox.getChildren()
        .addAll(titleBox, authors, yearPublisher, separator, abstractString, keywords, citations,
            urls);
    scroll.setContent(vBox);
    box.getChildren().add(scroll);
  }

  public void backToResults() {

    Parent root = null;
    try {
      Stage stage = (Stage) borderPane.getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
      root = loader.load();
      Scene scene = new Scene(root);
      Controller2 controller = loader.getController();
      controller.initialize(null, null);
      controller.getResults("");
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.setTitle("Researcher");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void goback() {

    Parent root = null;
    try {
      Stage stage = (Stage) borderPane.getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
      root = loader.load();
      Scene scene = new Scene(root);
      Controller controller = loader.getController();
      Controller.researchPaperList = new ArrayList<>();
      controller.initialize(null, null);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.setTitle("Researcher");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
