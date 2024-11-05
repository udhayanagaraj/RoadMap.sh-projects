package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GithubUserActivity {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the username: ");
            String userInput = scanner.nextLine();
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


                    for(int i=0;i<n;i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        System.out.println("Event "+i+1);
                        System.out.println("Created_at: "+jsonObject.get("created_at"));
                        System.out.println("Id: "+jsonObject.get("id"));
                        System.out.println("Type: "+jsonObject.get("type"));
                        JSONObject payload = (JSONObject) jsonObject.get("payload");
                        System.out.println("Payload: "+payload+"\n");
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
