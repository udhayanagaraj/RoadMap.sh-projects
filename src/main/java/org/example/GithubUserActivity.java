package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubUserActivity {
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Usage: github-activity <username>");
            return;
        }
        String userInput = args[0];
        try {
            int n = 5;
            URL url = new URL("https://api.github.com/users/"+userInput+"/events");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                StringBuilder sb = new StringBuilder();
                String inputLine;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while((inputLine = bufferedReader.readLine()) != null){
                    sb.append(inputLine);
                }
                Object file = JSONValue.parse(sb.toString());

                if (file instanceof JSONArray){
                    JSONArray jsonArray = (JSONArray) file;

                    for(int i=0;i<Math.min(n, jsonArray.size());i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String eventType = (String) jsonObject.get("type");

                        String activityMessage = "";
                        switch (eventType) {
                            case "PushEvent": {
                                JSONObject payload = (JSONObject) jsonObject.get("payload");
                                long commits = (long) payload.get("size");
                                JSONObject repo = (JSONObject) jsonObject.get("repo");
                                String repoName = (String) repo.get("name");
                                activityMessage = "Pushed " + commits + " commits to " + repoName;
                                break;
                            }
                            case "IssuesEvent": {
                                JSONObject payload = (JSONObject) jsonObject.get("payload");
                                String action = (String) payload.get("action");
                                JSONObject repo = (JSONObject) jsonObject.get("repo");
                                String repoName = (String) repo.get("name");

                                if (action.equals("opened")) {
                                    activityMessage = "Opened a new issue in " + repoName;
                                }
                                break;
                            }
                            case "WatchEvent": {
                                JSONObject repository = (JSONObject) jsonObject.get("repo");
                                String repoName = (String) repository.get("name");
                                activityMessage = "Starred " + repoName;
                                break;
                            }
                        }

                        if(!activityMessage.isEmpty()){
                            System.out.println("- "+activityMessage);
                        }
                    }
                }else{
                    System.out.println("Expected a JSON array but got something else.");
                }
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("User name: "+userInput+" is not found");
            } else {
                System.out.println("Failed to fetch data. HTTP Code: " + responseCode);
            }
        }catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
