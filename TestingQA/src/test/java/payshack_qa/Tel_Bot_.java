//package payshack;
//
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class Tel_Bot_ {
//	 public static void main(String[] args) throws Exception {
//		 String botToken = "8180907721:AAEFgCFx7JVHTFry7cwf7kHFiLks-qOvY_A";
//	        String chatId = "-4982360241";
//	        String message = "Total volume:2,630,941";
//
//	        OkHttpClient client = new OkHttpClient();
//	        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
//
//	        RequestBody body = new FormBody.Builder()
//	                .add("chat_id", chatId)
//	                .add("text", message)
//	                .build();
//
//	        Request request = new Request.Builder()
//	                .url(url)
//	                .post(body)
//	                .build();
//
//	        try (Response response = client.newCall(request).execute()) {
//	            System.out.println(response.body().string());
//	        }
//		 
//	 }
//
//}

//package payshack;
//
//import okhttp3.*;
//import org.json.JSONObject;
//import java.io.IOException;
//
//public class Tel_Bot_ {
//    public static void main(String[] args) throws Exception {
//        // Step 1: Fetch data from Payshack API
//        JSONObject payshackData = fetchPayshackData();
//        
//        if (payshackData != null) {
//            // Extract today's success volume
//            int todayVolume = payshackData.getInt("todaySuccessVolume");
//            String formattedVolume = String.format("%,d", todayVolume); // Format with commas
//            
//            // Step 2: Send Telegram message
//            sendTelegramMessage(formattedVolume);
//        }
//    }
//
//    private static JSONObject fetchPayshackData() throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        String payshackUrl = "https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta";
//        
//        Request request = new Request.Builder()
//                .url(payshackUrl)
//                .get()
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                System.err.println("Failed to fetch Payshack data: " + response.code());
//                return null;
//            }
//            
//            String responseBody = response.body().string();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            
//            if (jsonResponse.getBoolean("success")) {
//                return jsonResponse.getJSONObject("data");
//            } else {
//                System.err.println("API returned unsuccessful response");
//                return null;
//            }
//        }
//    }
//
//    private static void sendTelegramMessage(String volume) throws IOException {
//        String botToken = "8180907721:AAEFgCFx7JVHTFry7cwf7kHFiLks-qOvY_A";
//        String chatId = "-4982360241";
//        String message = "Today's Success Volume: " + volume;
//
//        OkHttpClient client = new OkHttpClient();
//        String telegramUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage";
//
//        RequestBody body = new FormBody.Builder()
//                .add("chat_id", chatId)
//                .add("text", message)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(telegramUrl)
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Telegram response: " + response.body().string());
//        }
//    }
//}


//package payshack;  fetch all clients volume
//
//import okhttp3.*;
//import org.json.JSONObject;
//import java.io.IOException;
//
//public class Tel_Bot_ {
//    public static void main(String[] args) throws Exception {
//        // Step 1: Fetch data from both APIs
//        JSONObject payshackData1 = fetchPayshackData("https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta");
//        JSONObject payshackData2 = fetchPayshackData("https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta", "d4a5c1a8-bac8-4400-b42d-d04f11a98017");
//        JSONObject payshackData3 = fetchPayshackData("https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta", "102bbcda-b66d-4262-963b-d718fbe38112");
//        JSONObject payshackData4 = fetchPayshackData("https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta", "45f0d5ed-3d11-45b5-8827-e1ca71d16d6f");
//        JSONObject payshackData5 = fetchPayshackData("https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta", "c3532312-5673-4847-8118-a3a2ec4e1e5d");
//        
//        
//        if (payshackData1 != null && payshackData2 != null) {
//            // Extract volumes from both APIs
//            int todayVolume1 = payshackData1.getInt("todaySuccessVolume");
//            int todayVolume2 = payshackData2.getInt("todaySuccessVolume");
//            int todayVolume3 = payshackData3.getInt("todaySuccessVolume");
//            int todayVolume4 = payshackData4.getInt("todaySuccessVolume");
//            int todayVolume5 = payshackData5.getInt("todaySuccessVolume");
//            
//            // Format the volumes
//            String formattedVolume1 = String.format("%,d", todayVolume1);
//            String formattedVolume2 = String.format("%,d", todayVolume2);
//            String formattedVolume3 = String.format("%,d", todayVolume3);
//            String formattedVolume4 = String.format("%,d", todayVolume4);
//            String formattedVolume5 = String.format("%,d", todayVolume5);
//            
//            // Step 2: Send Telegram message
//            sendTelegramMessage(formattedVolume1, formattedVolume2, formattedVolume3, formattedVolume4, formattedVolume5);
//        }
//    }
//
//    // First API (without client-id)
//    private static JSONObject fetchPayshackData(String url) throws IOException {
//        return fetchPayshackData(url, null);
//    }
//
//    // Second API (with client-id header)
//    private static JSONObject fetchPayshackData(String url, String clientId) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        
//        Request.Builder requestBuilder = new Request.Builder()
//                .url(url)
//                .get();
//        
//        if (clientId != null) {
//            requestBuilder.header("client-id", clientId);
//        }
//        
//        Request request = requestBuilder.build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                System.err.println("Failed to fetch Payshack data: " + response.code());
//                return null;
//            }
//            
//            String responseBody = response.body().string();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            
//            if (jsonResponse.getBoolean("success")) {
//                return jsonResponse.getJSONObject("data");
//            } else {
//                System.err.println("API returned unsuccessful response");
//                return null;
//            }
//        }
//    }
//
//    private static void sendTelegramMessage(String volume1, String volume2, String volume3, String volume4 , String volume5) throws IOException {
//        String botToken = "8180907721:AAEFgCFx7JVHTFry7cwf7kHFiLks-qOvY_A";
//        String chatId = "-4982360241";
//        String message = "Current Success Volumes:\n"+"\n"
//                       + "1. All clients current volume : " + volume1 + "\n"
//                       + "2. Monetex  : " + volume2+"\n"
//                       + "3. IG  : " + volume3 + "\n"
//                       + "4. Valor  : " + volume4 +"\n"
//                       + "5. Karnatech : " + volume5 ;
//        			
//
//        OkHttpClient client = new OkHttpClient();
//        String telegramUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage";
//
//        RequestBody body = new FormBody.Builder()
//                .add("chat_id", chatId)
//                .add("text", message)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(telegramUrl)
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Telegram response: " + response.body().string());
//        }
//    }
//}


//package payshack; //run immediately and every 1 hour
//
//import okhttp3.*;
//import org.json.JSONObject;
//import java.io.IOException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//public class Tel_Bot_ {
//    private static final String BOT_TOKEN = "8180907721:AAEFgCFx7JVHTFry7cwf7kHFiLks-qOvY_A";
//    private static final String CHAT_ID = "-4982360241";
//    private static final String[] CLIENT_IDS = {
//            null, // First API without client-id
//            "d4a5c1a8-bac8-4400-b42d-d04f11a98017", // Monetex
//            "102bbcda-b66d-4262-963b-d718fbe38112", // IG
//            "45f0d5ed-3d11-45b5-8827-e1ca71d16d6f", // Valor
//            "c3532312-5673-4847-8118-a3a2ec4e1e5d"  // Karnatech
//    };
//    private static final String[] CLIENT_NAMES = {
//            "All clients current volume",
//            "Monetex",
//            "IG",
//            "Valor",
//            "Karnatech"
//    };
//
//    public static void main(String[] args) {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        
//        // Run immediately and then every hour
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
//                fetchAndSendData();
//            } catch (Exception e) {
//                System.err.println("Error in scheduled task: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }, 0, 1, TimeUnit.HOURS);
//    }
//
//    private static void fetchAndSendData() throws IOException {
//        StringBuilder messageBuilder = new StringBuilder("Current Success Volumes:\n\n");
//        String apiUrl = "https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta";
//
//        for (int i = 0; i < CLIENT_IDS.length; i++) {
//            JSONObject data = fetchPayshackData(apiUrl, CLIENT_IDS[i]);
//            if (data != null) {
//                int volume = data.getInt("todaySuccessVolume");
//                String formattedVolume = String.format("%,d", volume);
//                messageBuilder.append(i + 1).append(". ")
//                             .append(CLIENT_NAMES[i]).append(" : ")
//                             .append(formattedVolume).append("\n");
//            } else {
//                messageBuilder.append(i + 1).append(". ")
//                             .append(CLIENT_NAMES[i]).append(" : Failed to fetch data\n");
//            }
//        }
//
//        sendTelegramMessage(messageBuilder.toString());
//    }
//
//    private static JSONObject fetchPayshackData(String url, String clientId) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request.Builder requestBuilder = new Request.Builder().url(url).get();
//        
//        if (clientId != null) {
//            requestBuilder.header("client-id", clientId);
//        }
//
//        try (Response response = client.newCall(requestBuilder.build()).execute()) {
//            if (!response.isSuccessful()) {
//                System.err.println("Failed to fetch data for client " + clientId + ": " + response.code());
//                return null;
//            }
//            
//            String responseBody = response.body().string();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            
//            return jsonResponse.getBoolean("success") ? jsonResponse.getJSONObject("data") : null;
//        }
//    }
//
//    private static void sendTelegramMessage(String message) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("chat_id", CHAT_ID)
//                .add("text", message)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage")
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Telegram response: " + response.body().string());
//        }
//    }
//}


//package payshack;
//
//import okhttp3.*;
//import org.json.JSONObject;
//import java.io.IOException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//public class Tel_Bot_ {
//    private static final String BOT_TOKEN = "8180907721:AAEFgCFx7JVHTFry7cwf7kHFiLks-qOvY_A";
//    private static final String CHAT_ID = "-4982360241";
//    private static final String[] CLIENT_IDS = {
//            null, // First API without client-id
//            "d4a5c1a8-bac8-4400-b42d-d04f11a98017", // Monetex
//            "102bbcda-b66d-4262-963b-d718fbe38112", // IG
//            "45f0d5ed-3d11-45b5-8827-e1ca71d16d6f", // Valor
//            "c3532312-5673-4847-8118-a3a2ec4e1e5d"  // Karnatech
//    };
//    private static final String[] CLIENT_NAMES = {
//            "All clients current volume",
//            "Monetex",
//            "IG",
//            "Valor",
//            "Karnatech"
//    };
//
//    public static void main(String[] args) {
//        System.out.println("Starting Telegram volume reporter...");
//        System.out.println("First execution will run immediately, then every 1 hour");
//        
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
////        
//        // Run immediately and then every 1 hour
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
//                System.out.println("Executing scheduled data fetch...");
//                fetchAndSendData();
//                System.out.println("Completed successfully. Next run in 1 hour.");
//            } catch (Exception e) {
//                System.err.println("Error in scheduled task: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }, 0, 1, TimeUnit.HOURS); // every 1 hour
//    }
//
//    private static void fetchAndSendData() throws IOException {
//        StringBuilder messageBuilder = new StringBuilder("Current Success Volumes (Updated every 1 hour):\n\n");
//        String apiUrl = "https://api.payshack.in/indigate-payin-svc/api/v1/dashboard/meta";
//
//        for (int i = 0; i < CLIENT_IDS.length; i++) {
//            JSONObject data = fetchPayshackData(apiUrl, CLIENT_IDS[i]);
//            if (data != null) {
//                int volume = data.getInt("todaySuccessVolume");
//                String formattedVolume = String.format("%,d", volume);
//                messageBuilder.append(i + 1).append(". ")
//                             .append(CLIENT_NAMES[i]).append(" : ")
//                             .append(formattedVolume).append("\n");
//            } else {
//                messageBuilder.append(i + 1).append(". ")
//                             .append(CLIENT_NAMES[i]).append(" : Failed to fetch data\n");
//            }
//        }
//
//        sendTelegramMessage(messageBuilder.toString());
//    }
//
//    private static JSONObject fetchPayshackData(String url, String clientId) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request.Builder requestBuilder = new Request.Builder().url(url).get();
//        
//        if (clientId != null) {
//            requestBuilder.header("client-id", clientId);
//        }
//
//        try (Response response = client.newCall(requestBuilder.build()).execute()) {
//            if (!response.isSuccessful()) {
//                System.err.println("Failed to fetch data for client " + clientId + ": " + response.code());
//                return null;
//            }
//            
//            String responseBody = response.body().string();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            
//            return jsonResponse.getBoolean("success") ? jsonResponse.getJSONObject("data") : null;
//        }
//    }
//
//    private static void sendTelegramMessage(String message) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("chat_id", CHAT_ID)
//                .add("text", message)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage")
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Telegram response: " + response.body().string());
//        }
//    }
//}



package payshack;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tel_Bot_ {
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
