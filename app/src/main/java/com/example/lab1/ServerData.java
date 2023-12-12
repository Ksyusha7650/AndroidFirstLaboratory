package com.example.lab1;

public class ServerData {
    private final String square;
    private final String error;

    public String getSquare() {
        return square;
    }

    public String getError() {
        return error;
    }

    public ServerData(String square, String error) {
        this.square = square;
        this.error = error;
    }
}
