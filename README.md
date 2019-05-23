# Search-Engine

The project builds an information retrieval system for a huge collection of research articles and publications. The project uses SolrJ on the backend to index the documents for retrieval. 
The data was obtained from Open Academic Graph obtained from https://www.openacademic.ai/oag/ . The code has been written in Java and a user interface was created for it's usability using JavaFX. A customized ranking function has been written to rank the relevance of the search results. It provides better results than a standalone BM25 ranking function.

## Researcher folder

The folder contains the source code to establish a client connection to the Solr Server, parse the data, create a document with parsed details and add them to the collection created on the server. This data stored in Solr will be used to create the search engine and extract relevant resutls.



##
