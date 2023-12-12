package com.example.lab1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalculateRunnable implements Runnable {
    public static final int TIMEOUT = 3000;
    private final URL url;
    private String request;

    private ServerData data;
    public ServerData getData() {
        return data;
    }


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
            // Устанавливаем соединение с сервером и задаем таймауты
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.connect();
        } catch (IOException e) {
            if (connection != null) {
                connection.disconnect();
            }
            return;
        }
        try {
            // Получаем входной поток данных с сервера
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            while ((line = reader.readLine()) != null) {
                // Читаем данные из входного потока и добавляем их в переменную request
                request += line;
            }

            // Парсим полученные данные в объект ServerData
            data = getServerData(request);

        } catch (Exception e) {
            request = "";
        } finally {
            try {
                // Закрываем входной поток
                assert stream != null;
                stream.close();
            } catch (Exception e) {
            }
            try {
                // Закрываем BufferedReader
                reader.close();
            } catch (Exception e) {
            }

            // Разрываем соединение
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private ServerData getServerData(String result) throws JSONException {
        // Удаляем префикс "null" из результата, если он есть
        if (result.startsWith("null")) {
            result = result.substring(4);
        }

        // Создаем объект JSONObject и парсим полученные поля
        JSONObject obj = new JSONObject(result);

        String square = (String) obj.get("square");
        String error = (String) obj.get("error");

        return new ServerData(square, error);
    }
}
