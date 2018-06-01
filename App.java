package com.microsoft.azure.search.samples.console;

import com.microsoft.azure.search.samples.*;

import java.io.IOException;
/**
 * adapted from code sample at https://github.com/Azure-Samples/search-java-getting-started 
 * @author niroshinie.fernando
 *
 */
public class App
{
    private static final String SERVICE_NAME = "myassignmenturl";
    private static final String INDEX_NAME = "azureblob-index";
    private static final String API_KEY = "2E132E4C07784CE38CCCCCE00643A2AF";

    public static void main( String[] args ) {
        SearchIndexClient indexClient = new SearchIndexClient(SERVICE_NAME, INDEX_NAME, API_KEY);
        try {
            searchSimple(indexClient);
            searchAllFeatures(indexClient);
        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }


    static void searchSimple(SearchIndexClient indexClient) throws IOException {
        IndexSearchOptions options = new IndexSearchOptions();
        options.setIncludeCount(true);
        IndexSearchResult result = indexClient.search("history", options);
        System.out.printf("Found %s hits\n", result.getCount());
        for (IndexSearchResult.SearchHit hit: result.getHits()) {
            System.out.printf("\tBook: %s, score: %s\n",
                    hit.getDocument().get("metadata_storage_name"), hit.getScore());
        }
    }

    static void searchAllFeatures(SearchIndexClient indexClient) throws IOException {
        IndexSearchOptions options = new IndexSearchOptions();
        options.setIncludeCount(true);
        options.setFilter("metadata_storage_name eq 'The discovery of blue mountains.txt'");
        options.setSelect("metadata_storage_name");
        options.setSearchFields("content");
        options.setHighlight("content");
        options.setHighlightPreTag("*pre*");
        options.setHighlightPostTag("*post*");
        options.setRequireAllTerms(true);
        options.setMinimumCoverage(0.75);

        IndexSearchResult result = indexClient.search("home", options);

        // list search hits
        System.out.printf("Found %s hits, coverage: %s\n", result.getCount(), result.getCoverage() == null ? "-" : result.getCoverage());
        for (IndexSearchResult.SearchHit hit: result.getHits()) {
            System.out.printf("\tBook: %s, score: %s\n",
                    hit.getDocument().get("metadata_storage_name"),  hit.getScore());
        }

    }

    

}
