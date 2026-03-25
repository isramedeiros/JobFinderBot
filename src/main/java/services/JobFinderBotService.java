package services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JobFinderBotService {
    // OkHttpClient = this is the engine responsible for making HTTP requests
    private final OkHttpClient client = new OkHttpClient();

    // method that fetches jobs from API using a search query
    public String getJobs(String query) {

        // query encoding (converting characters into a format that can be transmitted)
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        // building the API URL with the encoded query
        // the URL is available in the API docs
        String url = "https://jobicy.com/api/v2/remote-jobs?tag=" + encodedQuery;

        // building the HTTP request (define where we are sending the request)
        Request request = new Request.Builder() // creating an empty request builder
                .url(url) // sets the destination of the request
                .build(); // creates the actual request object

        // sending the request and getting the response from the API
        try (Response response = client.newCall(request).execute()) {
            // extracting the response body as a String
            String json = response.body().string();

            // returning the raw JSON that will be parsed later
            System.out.println("RAW RESPONSE:\n" + json);

            return json;

        // handling exceptions
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching jobs";
        }
    }

    // method to parse jobs
    public String parseJobs(String json) {
        // ObjectMapper (Jackson lib) = parses JSON text into Java objects
        ObjectMapper mapper = new ObjectMapper();


        try {
            // readTree() parses JSON string into a tree structure (JsonNode)
            JsonNode root = mapper.readTree(json);
            // root = entire JSON structure
            JsonNode jobs = root.get("jobs");

            // get the "jobs" array from the JSON
            if (jobs == null || !jobs.isArray()) {
                return "No jobs found or API response changed.";
            }

            // validate if "jobs" exists and is an array
            StringBuilder result = new StringBuilder();

            // loop through jobs (max 10 results)
            for (int i = 0; i < Math.min(10, jobs.size()); i++) {
                JsonNode job = jobs.get(i);

                // extract fields
                String title = job.has("title") ? job.get("title").asText() : "No title";
                String company = job.has("companyName") ? job.get("companyName").asText() : "No company";
                String url = job.has("url") ? job.get("url").asText() : "No link";

                // append() adds text to Stringbuilder
                result.append("\uD83D\uDCBC ").append(title).append("\n")
                        .append(company).append("\n")
                        .append(url).append("\n\n");
            }

            // convert StringBuilder into final String
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing jobs";
        }
    }
}