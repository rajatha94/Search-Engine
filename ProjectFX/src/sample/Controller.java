package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

public class Controller implements Initializable {

  @FXML
  private BorderPane borderPane;
  @FXML
  private TextField inputField;
  @FXML
  private Button actionBtn;

  final String solrUrl = "http://localhost:8983/solr/researcher";
  final SolrClient client = new ConcurrentUpdateSolrClient.Builder(solrUrl).build();

  public static List<ResearchPaper> researchPaperList = new ArrayList<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    Platform.runLater(() -> borderPane.requestFocus());
    actionBtn.setOnAction(event -> {
      loadSceneAndSendMessage();
    });
  }

  public void receiveMessage(String message) {

  }

  private void loadSceneAndSendMessage() {
    try {

      processQuery(inputField.getText());

      FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
      Parent root = loader.load();
      Stage stage = (Stage) borderPane.getScene().getWindow();
      //Stage stage = new Stage(StageStyle.DECORATED);
//      stage.setMinWidth(borderPane.getWidth());
//      stage.setMinHeight(borderPane.getHeight());
//      stage.setMaximized(true);
      stage.setScene(new Scene(root));
      stage.setTitle("Second Window");
      stage.show();
      //Get controller of scene2
      Controller2 scene2Controller = loader.getController();
      scene2Controller.getResults(inputField.getText());
      scene2Controller.initialize(null, null);

    } catch (IOException ex) {
      System.err.println(ex);
    } catch (SolrServerException e) {
      e.printStackTrace();
    }
  }

  public void processQuery(String query) throws IOException, SolrServerException {

//    getStopWordList();
//    Map<String, Map<String, String>> relMap = new HashMap<>();
//    Scanner rel = new Scanner(new FileReader("qrels.adhoc.51-100.AP89.txt"));
//    while (rel.hasNextLine()) {
//      String line = rel.nextLine();
//      String[] values = line.trim().split(" ");
//      if (relMap.containsKey(values[0])) {
//        Map<String, String> docRelevance = relMap.get(values[0]);
//        docRelevance.put(values[2], values[3]);
//        relMap.put(values[0], docRelevance);
//      } else {
//        Map<String, String> docRelevance = new HashMap<>();
//        docRelevance.put(values[2], values[3]);
//        relMap.put(values[0], docRelevance);
//      }
//    }
//
//    final String solrUrl = "http://localhost:8983/solr/projectCore";
//    final SolrClient client = new ConcurrentUpdateSolrClient.Builder(solrUrl).build();
    //Scanner in = new Scanner(new FileReader("query_desc.51-100.short.txt"));
//    File file = new File("solrQueryResults.txt");
//    if (!file.exists()) {
//      file.createNewFile();
//    }
    //FileWriter fileWriter = new FileWriter(file);
    //String queryNumber = "";
    //String query = "the Story";
    if (query.equals("")) {
      return;
    }
    Map<String, String> queryParams = new HashMap<>();

    queryParams.put("q", query);
    queryParams.put("fl", "*, score");
    queryParams.put("start", "0");
    queryParams.put("rows", "100");
    queryParams.put("defType", "dismax");
    queryParams.put("qf", "title^1.8 doc^1.5 keywords^1.2 url^0.5 authors publisher");
    queryParams.put("pf", "title^1.8 doc^1.5 keywords^1.2 url^0.5 authors publisher");
    MapSolrParams solrQueryParams = new MapSolrParams(queryParams);
    QueryResponse queryResponse = client.query(solrQueryParams);
    SolrDocumentList solrDocumentList = queryResponse.getResults();
    System.out.println("Number of Docs retrieved: " + solrDocumentList.getNumFound());
    for (SolrDocument solrDocument : solrDocumentList) {
      String id = "";
      String title = "";
      String abstractString = "";
      ArrayList<String> keywords = new ArrayList<>();
      ArrayList<String> url = new ArrayList<>();
      ArrayList<String> authors = new ArrayList<>();
      String n_citation = "";
      String publisher = "";
      String docType = "";
      String year = "";
      for (String fieldName : solrDocument.getFieldNames()) {

        if (fieldName.equalsIgnoreCase("id")) {
          id = solrDocument.get(fieldName).toString();
        } else if (fieldName.equalsIgnoreCase("title")) {
          ArrayList<String> titleList = (ArrayList<String>) solrDocument.get(fieldName);
          title = titleList.get(0).replace("\"", "");
        } else if (fieldName.equalsIgnoreCase("doc")) {
          ArrayList<String> docList = (ArrayList<String>) solrDocument.get(fieldName);
          abstractString = docList.get(0).replace("\"", "");
        } else if (fieldName.equalsIgnoreCase("keywords")) {
          keywords = (ArrayList<String>) solrDocument.get(fieldName);
        } else if (fieldName.equalsIgnoreCase("url")) {
          url = (ArrayList<String>) solrDocument.get(fieldName);
        } else if (fieldName.equalsIgnoreCase("authors")) {
          authors = (ArrayList<String>) solrDocument.get(fieldName);
        } else if (fieldName.equalsIgnoreCase("n_citation")) {
          n_citation = String.valueOf(((ArrayList<String>) solrDocument.get(fieldName)).get(0))
              .replace("\"", "");
        } else if (fieldName.equalsIgnoreCase("publisher")) {
          publisher = ((ArrayList<String>) solrDocument.get(fieldName)).get(0).replace("\"", "");
        } else if (fieldName.equalsIgnoreCase("doc_type")) {
          docType = ((ArrayList<String>) solrDocument.get(fieldName)).get(0).replace("\"", "");
        } else if (fieldName.equalsIgnoreCase("year")) {
          year = String.valueOf(((ArrayList<String>) solrDocument.get(fieldName)).get(0))
              .replace("\"", "");
        }
        System.out.println(fieldName + ": " + solrDocument.get(fieldName));
      }

      researchPaperList.add(
          new ResearchPaper(id, title, abstractString, url, keywords, authors, n_citation,
              publisher, docType, year));
    }
  }
}
