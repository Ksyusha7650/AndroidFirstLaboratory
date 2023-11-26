package com.example.lab1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalculateRunnable implements Runnable {
    private final URL url;
    private String request;

    public Data getData() {
        return data;
    }

    private Data data;

    CalculateRunnable(URL url) {
        this.url = url;
    }
    @Override
    public void run() {
        String line = "";

        HttpURLConnection connection = null;

        InputStream stream = null;
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            while ((line = reader.readLine()) != null) {
                request += line;
            }

            data = printResult(request);

        } catch (Exception e) {
            request = "";
        } finally {
            try {
                assert stream != null;
                stream.close();
            } catch (Exception e) {
            }
            try {
                reader.close();
            } catch (Exception e) {
            }

            connection.disconnect();
        }
    }

    private Data printResult(String result) throws JSONException {

        if (result.startsWith("null")) {
            result = result.substring(4);
        }

        JSONObject obj = new JSONObject(result);

        String square = (String) obj.get("square");
        String error = (String) obj.get("error");

        return new Data(square, error);
    }
}
