package services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JobFinderBotService {
    private final OkHttpClient client = new OkHttpClient();

    public String getJobs(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://jobicy.com/api/v2/remote-jobs?tag=" + encodedQuery;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();

            System.out.println("RAW RESPONSE:\n" + json);

            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching jobs";
        }
    }

    public String parseJobs(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(json);
            JsonNode jobs = root.get("jobs");

            if (jobs == null || !jobs.isArray()) {
                return "No jobs found or API response changed.";
            }

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < Math.min(3, jobs.size()); i++) {
                JsonNode job = jobs.get(i);

                String title = job.has("title") ? job.get("title").asText() : "No title";
                String company = job.has("companyName") ? job.get("companyName").asText() : "No company";
                String url = job.has("url") ? job.get("url").asText() : "No link";

                result.append("\uD83D\uDCBC ").append(title).append("\n")
                        .append(company).append("\n")
                        .append(url).append("\n\n");
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing jobs";
        }
    }
}