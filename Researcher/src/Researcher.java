import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;

public class Researcher {


  static final String solrUrl = "http://localhost:8983/solr/researcher";
  static final SolrClient client = new ConcurrentUpdateSolrClient.Builder(solrUrl).build();
  static Collection<SolrInputDocument> collection;

  public static void parseData() throws IOException {
    int i = 0;
    collection = new ArrayList<>();
    JsonFactory f = new MappingJsonFactory();
    JsonParser jp = null;
    try {
      jp = f.createJsonParser(new File("mag_papers_62.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    JsonToken current = null;
    try {
      current = jp.nextToken();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (current != JsonToken.START_OBJECT) {
      System.out.println("Error: root should be object: quiting.");
      return;
    }

    while (jp.nextToken() != JsonToken.END_OBJECT) {
      String fieldName = jp.getCurrentName();
      JsonNode jsonNode = jp.readValueAsTree();
//      Iterator<String> iterator = jsonNode.fieldNames();
      // String abstractString = jsonNode.get("abstract").toString();
      try {
        String title = jsonNode.get("title").toString();
        String id = jsonNode.get("id").toString().replace("\"", "");
        System.out.println("ID: " + id + " Title: " + title);
        ++i;
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        if (jsonNode.has("abstract")) {
          doc.addField("doc", jsonNode.get("abstract").toString().replace("\"", ""));
        } else {
          doc.addField("doc", "");
        }
        if (jsonNode.has("lang")) {
          if (!jsonNode.get("lang").asText().equalsIgnoreCase("en")) {
            continue;
          }
        }
        if (jsonNode.has("keywords")) {
          JsonNode node = jsonNode.get("keywords");
          ObjectMapper objectMapper = new ObjectMapper();
          ArrayList<String> strings = (ArrayList<String>) objectMapper
              .readValue(node.toString(), List.class);
          doc.addField("keywords", strings);
        }

        if (jsonNode.has("url")) {
          JsonNode node = jsonNode.get("url");
          ObjectMapper objectMapper = new ObjectMapper();
          ArrayList<String> strings = (ArrayList<String>) objectMapper
              .readValue(node.toString(), List.class);
          doc.addField("url", strings);
        }

        if (jsonNode.has("authors")) {
          JsonNode node = jsonNode.get("authors");
          ArrayList<String> authors = new ArrayList<>();
          ObjectMapper objectMapper = new ObjectMapper();
          for (int j = 0; j < node.size(); j++) {
            if (node.get(j).has("name")) {
              authors.add(node.get(j).get("name").toString().replace("\"", ""));
            }
          }
          doc.addField("authors", authors);
        }

        if (jsonNode.has("n_citation")) {
          JsonNode node = jsonNode.get("n_citation");
          doc.addField("n_citation", node.toString().replace("\"", ""));
        }

        if (jsonNode.has("publisher")) {
          JsonNode node = jsonNode.get("publisher");
          doc.addField("publisher", node.toString().replace("\"", ""));
        }

        if (jsonNode.has("doc_type")) {
          JsonNode node = jsonNode.get("doc_type");
          doc.addField("doc_type", node.toString().replace("\"", ""));
        }

        if (jsonNode.has("year")) {
          JsonNode node = jsonNode.get("year");
          doc.addField("year", node.toString().replace("\"", ""));
        }

        doc.addField("title", title.replace("\"", ""));
        collection.add(doc);
      } catch (Exception e) {
        System.out.println("Number of documents: " + i);
        jp.skipChildren();
        break;
      }
      //JsonNode node1 = jp.readValueAsTree();
//      while (iterator.hasNext()) {
//        System.out.println(jsonNode.get(string));
//        string = iterator.next();
//      }
//      // move from field name to field value
//      current = jp.nextToken();
//      if (fieldName.equals("page_end")) {
//        if (current == JsonToken.START_OBJECT) {
//          // For each of the records in the array
//          while (jp.nextToken() != JsonToken.END_OBJECT) {
//            // read the record into a tree model,
//            // this moves the parsing position to the end of it
//            JsonNode node = jp.readValueAsTree();
//            Iterator<String> iterator1 = node.fieldNames();
//            String string1 = iterator1.toString();
//            //JsonNode node1 = jp.readValueAsTree();
//            while (iterator1.hasNext()) {
//              System.out.println(node.get(string1));
//              string1 = iterator1.next();
//            }
//            // And now we have random access to everything in the object
////            System.out.println("field1: " + node.get("field1").asText());
////            System.out.println("field2: " + node.get("field2").asText());
//          }
//        } else {
//          System.out.println("Error: records should be an array: skipping.");
//          jp.skipChildren();
//        }
//      } else {
//        System.out.println("Unprocessed property: " + fieldName);
//        jp.skipChildren();
//      }
      //break;
    }
    try {
      client.add(collection);
      client.commit();
    } catch (SolrServerException e) {
      e.printStackTrace();
    }
  }

  public static void processQuery() throws IOException, SolrServerException {

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
    String queryNumber = "";
    String query = "the Story";
    if (query.equals("")) {
      return;
    }
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("q", "the story");
    queryParams.put("fl", "*, score");
    queryParams.put("start", "0");
    queryParams.put("rows", "10");
    queryParams.put("defType", "dismax");
    queryParams.put("qf", "title^2 doc");
    MapSolrParams solrQueryParams = new MapSolrParams(queryParams);
    QueryResponse queryResponse = client.query(solrQueryParams);
    SolrDocumentList solrDocumentList = queryResponse.getResults();
    System.out.println("Number of Docs retrieved: " + solrDocumentList.getNumFound());
    //System.out.println("Ids:");
//      Map<String, String> allDocs = relMap.get(queryNumber);
//      int retrievedRelevantDocs = 0;
    for (SolrDocument solrDocument : solrDocumentList) {

      for (String fieldName : solrDocument.getFieldNames()) {
        if (fieldName.equals("title")) {
          ArrayList<String> arrayList = (ArrayList<String>) solrDocument.get(fieldName);
          System.out.println(solrDocument.get(fieldName).getClass());
        }
        System.out.println(fieldName + ": " + solrDocument.get(fieldName));
      }
//        String id = (String) (solrDocument.getFirstValue("id"));
//        if (allDocs.containsKey(id) && allDocs.get(id).equals("1")) {
//          ++retrievedRelevantDocs;
//        }
//        fileWriter.write((String) solrDocument.getFirstValue("id") + "\n");
    }
//      System.out.println("Relevant retrieved documents: " + retrievedRelevantDocs);
//      System.out.println("Precision for " + queryNumber + " : " + String
//          .valueOf(retrievedRelevantDocs * 1.0 / 50));
    //break;
  }


  public static void main(String[] args) {
    try {
      parseData();
      System.out.println("parsing done");
      //processQuery();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

