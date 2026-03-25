package services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JobFinderBotService {

    // HTTP client (handles requests to external APIs)
    private final OkHttpClient client = new OkHttpClient();

    // JSON parser
    private final ObjectMapper mapper = new ObjectMapper();

    // welcome message shown on /start
    public String getWelcomeMessage() {
        return """
💼 Welcome to JobFinder Bot!

Tell me what kind of job you're looking for.

Examples:
- java
- backend
- python
- data engineer

Type a keyword and I’ll fetch remote jobs for you.
""";
    }

    // fetch jobs from API
    public String getJobs(String query) {

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://jobicy.com/api/v2/remote-jobs?tag=" + encodedQuery;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.body() == null) {
                return "Error: empty response from API";
            }

            String json = response.body().string();

            // debug (remove in production if you want)
            System.out.println("RAW RESPONSE:\n" + json);

            return json;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching jobs";
        }
    }

    // parse JSON into readable text
    public String parseJobs(String json) {

        try {
            JsonNode root = mapper.readTree(json);
            JsonNode jobs = root.get("jobs");

            if (jobs == null || !jobs.isArray() || jobs.size() == 0) {
                return "No jobs found.";
            }

            StringBuilder result = new StringBuilder();

            // limit to 10 jobs
            for (int i = 0; i < Math.min(10, jobs.size()); i++) {

                JsonNode job = jobs.get(i);

                // field extraction
                String title = job.hasNonNull("title")
                        ? job.get("title").asText()
                        : job.hasNonNull("jobTitle")
                        ? job.get("jobTitle").asText()
                        : "No title";

                String company = job.hasNonNull("companyName")
                        ? job.get("companyName").asText()
                        : "No company";

                String url = job.hasNonNull("url")
                        ? job.get("url").asText()
                        : "No link";

                result.append("💼 ").append(title).append("\n")
                        .append("🏢 ").append(company).append("\n")
                        .append("🔗 ").append(url).append("\n\n");
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing jobs";
        }
    }
}