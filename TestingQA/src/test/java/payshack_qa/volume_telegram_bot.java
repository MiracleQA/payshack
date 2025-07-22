package payshack_qa;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class volume_telegram_bot {

	private static final String BOT_TOKEN = "8086734575:AAFoGOb6DAVmHYul6FYp7PZRf9aK7VU1Pdc";
//    private static final String BOT_TOKEN = "7593774104:AAFZ1NzlYZLmIjAL72h5N9MbIVNbfqMQ2I8";
    private static final String CHAT_ID = "-1002512419942";
//	 private static final String CHAT_ID = "-4874396573";
    private static final String[] CLIENT_IDS = {
            null, // First API without client-id
            "d4a5c1a8-bac8-4400-b42d-d04f11a98017", // Monetex
            "102bbcda-b66d-4262-963b-d718fbe38112", // IG				
            "45f0d5ed-3d11-45b5-8827-e1ca71d16d6f", // Valor
            "c3532312-5673-4847-8118-a3a2ec4e1e5d",  // Karnatech
            "096d3ea5-c19d-4d42-975e-7e4d69cdfe91" //Jack
    };
    private static final String[] CLIENT_NAMES = {
            "All clients current volume",
            "Monetex",
            "IG",
            "Valor",
            "Karnatech",
            "Jack"
    };

		    public static void main(String[] args) {
		        System.out.println("Starting Telegram volume reporter...");
		        System.out.println("First execution will run immediately, then every 1 hour");
		        
		        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		        
		        // Run immediately and then every 1 hour
		        scheduler.scheduleAtFixedRate(() -> {
		            try {
		                System.out.println("Executing scheduled data fetch...");
		                fetchAndSendData();
		                System.out.println("Completed successfully. Next run in 1 hour.");
		            } catch (Exception e) {
		                System.err.println("Error in scheduled task: " + e.getMessage());
		                e.printStackTrace();
		            }
		        }, 0, 1, TimeUnit.HOURS); // Changed to 1 hour interval
		    }

		    private static void fetchAndSendData() throws IOException {
		        StringBuilder messageBuilder = new StringBuilder("Current Success Volumes (Updated hourly):\n\n");
		        String apiUrl = "https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta";

		        for (int i = 0; i < CLIENT_IDS.length; i++) {
		            JSONObject data = fetchPayshackData(apiUrl, CLIENT_IDS[i]);
		            if (data != null) {
		                int volume = data.getInt("todaySuccessVolume");
		                String formattedVolume = String.format("%,d", volume);
		                messageBuilder.append(i + 1).append(". ")
		                             .append(CLIENT_NAMES[i]).append(" : ")
		                             .append(formattedVolume).append("\n");
		            } else {
		                messageBuilder.append(i + 1).append(". ")
		                             .append(CLIENT_NAMES[i]).append(" : Failed to fetch data\n");
		            }
		        }

		        sendTelegramMessage(messageBuilder.toString());
		    }

		    private static JSONObject fetchPayshackData(String url, String clientId) throws IOException {
		        OkHttpClient client = new OkHttpClient();
		        Request.Builder requestBuilder = new Request.Builder().url(url).get();
		        
		        if (clientId != null) {
		            requestBuilder.header("client-id", clientId);
		        }

		        try (Response response = client.newCall(requestBuilder.build()).execute()) {
		            if (!response.isSuccessful()) {
		                System.err.println("Failed to fetch data for client " + clientId + ": " + response.code());
		                return null;
		            }
		            
		            String responseBody = response.body().string();
		            JSONObject jsonResponse = new JSONObject(responseBody);
		            
		            return jsonResponse.getBoolean("success") ? jsonResponse.getJSONObject("data") : null;
		        }
		    }

		    private static void sendTelegramMessage(String message) throws IOException {
		        OkHttpClient client = new OkHttpClient();
		        RequestBody body = new FormBody.Builder()
		                .add("chat_id", CHAT_ID)
		                .add("text", message)
		                .build();

		        Request request = new Request.Builder()
		                .url("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage")
		                .post(body)
		                .build();

		        try (Response response = client.newCall(request).execute()) {
		            System.out.println("Telegram response: " + response.body().string());
		        }
		    }
		}   
