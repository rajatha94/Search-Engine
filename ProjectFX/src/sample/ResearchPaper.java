package sample;

import java.util.ArrayList;

public class ResearchPaper {

  String id;
  String title;
  String abstractString;
  ArrayList<String> url;
  ArrayList<String> keywords;
  ArrayList<String> authors;
  String ncitation;
  String publisher;
  String docType;
  String year;

  public ResearchPaper() {
    this.id = "";
    this.title = "";
    this.abstractString = "";
  }

  public ResearchPaper(String id, String title, String abstractString, ArrayList<String> url,
      ArrayList<String> keywords, ArrayList<String> authors, String ncitation, String publisher,
      String docType, String year) {
    this.id = id;
    this.title = title;
    this.abstractString = abstractString;
    this.url = url;
    this.keywords = keywords;
    this.authors = authors;
    this.ncitation = ncitation;
    this.publisher = publisher;
    this.docType = docType;
    this.year = year;
  }

  public ArrayList<String> getUrl() {
    return url;
  }

  public void setUrl(ArrayList<String> url) {
    this.url = url;
  }

  public ArrayList<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(ArrayList<String> keywords) {
    this.keywords = keywords;
  }

  public ArrayList<String> getAuthors() {
    return authors;
  }

  public void setAuthors(ArrayList<String> authors) {
    this.authors = authors;
  }

  public String getNcitation() {
    return ncitation;
  }

  public void setNcitation(String ncitation) {
    this.ncitation = ncitation;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getDocType() {
    return docType;
  }

  public void setDocType(String docType) {
    this.docType = docType;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAbstractString() {
    return abstractString;
  }

  public void setAbstractString(String abstractString) {
    this.abstractString = abstractString;
  }
}
