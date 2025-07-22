package payshack_qa;


import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Optimizedtbot {

    // Telegram Bot credentials for sending the message
    private static final String BOT_TOKEN = "8086734575:AAFoGOb6DAVmHYul6FYp7PZRf9aK7VU1Pdc";
    private static final String CHAT_ID = "-1002512419942";

    // List of client IDs for whom transaction volume data needs to be fetched from the API
    private static final String[] CLIENT_IDS = {
            null, // 'null' is for the first API call which does not require client-id
            "d4a5c1a8-bac8-4400-b42d-d04f11a98017", // Monetex
            "102bbcda-b66d-4262-963b-d718fbe38112", // IG
            "45f0d5ed-3d11-45b5-8827-e1ca71d16d6f", // Valor
            "c3532312-5673-4847-8118-a3a2ec4e1e5d", // Karnatech
            "096d3ea5-c19d-4d42-975e-7e4d69cdfe91"  // Jack
    };

    // Readable client names for reporting purposes, matching the CLIENT_IDS array
    private static final String[] CLIENT_NAMES = {
            "All clients current volume",
            "Monetex",
            "IG",
            "Valor",
            "Karnatech",
            "Jack"
    };

    public static void main(String[] args) {
        System.out.println("Telegram volume reporter started. First run immediately, then scheduled every hour.");

        // A scheduler to repeatedly run our task automatically every hour
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Fetching latest volume data...");
                fetchAndSendData();
                System.out.println("Completed. Next fetch will happen in 1 hour.");
            } catch (Exception e) {
                System.err.println("Error during scheduled task execution: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);
    }

    /**
     * Core logic to collect the data for each client and send the compiled report to Telegram.
     */
    private static void fetchAndSendData() throws IOException {
        StringBuilder messageBuilder = new StringBuilder("Current Success Volumes (Updated hourly):\n\n");
        String apiUrl = "https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta";

        // Iterate through all clients and fetch their respective volumes
        for (int i = 0; i < CLIENT_IDS.length; i++) {
            JSONObject data = fetchPayshackData(apiUrl, CLIENT_IDS[i]);
            if (data != null) {
                int volume = data.getInt("todaySuccessVolume");
                String formattedVolume = String.format("%,d", volume); // Formatting with commas for readability
                messageBuilder.append(i + 1).append(". ")
                              .append(CLIENT_NAMES[i]).append(" : ")
                              .append(formattedVolume).append("\n");
            } else {
                messageBuilder.append(i + 1).append(". ")
                              .append(CLIENT_NAMES[i]).append(" : Failed to fetch data\n");
            }
        }

        // Send the formatted report to the configured Telegram group/channel
        sendTelegramMessage(messageBuilder.toString());
    }

    /**
     * Fetches volume data from the Payshack API for a specific client.
     *
     * @param url      The API endpoint URL
     * @param clientId The client-id header value, can be null if not needed
     * @return JSONObject containing fetched data, or null if the call failed
     */
    private static JSONObject fetchPayshackData(String url, String clientId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Building the HTTP GET request, adding 'client-id' header only if required
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        if (clientId != null) {
            requestBuilder.header("client-id", clientId);
        }

        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("API call failed for client " + clientId + ": HTTP " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Return the 'data' object only if the API call was marked as 'success'
            return jsonResponse.getBoolean("success") ? jsonResponse.getJSONObject("data") : null;
        }
    }

    /**
     * Sends a plain text message to a Telegram chat using the Telegram Bot API.
     *
     * @param message The text message content to send
     */
    private static void sendTelegramMessage(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Building the POST request with required parameters for Telegram API
        RequestBody body = new FormBody.Builder()
                .add("chat_id", CHAT_ID)
                .add("text", message)
                .build();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage")
                .post(body)
                .build();

        // Execute the HTTP request and log the response
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Telegram API Response: " + response.body().string());
        }
    }
}
