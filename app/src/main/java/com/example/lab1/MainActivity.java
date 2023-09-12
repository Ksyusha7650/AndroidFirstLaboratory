package com.example.lab1;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editTextA, editTextB, editTextAngle;
    TextView textViewResult;
    double A, B, angle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectUIElements();
    }

    private void outputErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error!")
                .setMessage("Something is going wrong! We are already working on it")
                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
    }

    private void connectUIElements() {
        setContentView(R.layout.activity_main);
        editTextA = findViewById(R.id.editTextSide1);
        editTextB = findViewById(R.id.editTextSide2);
        editTextAngle = findViewById(R.id.editTextAngle);
        textViewResult = findViewById(R.id.textViewResult);
    }

    private void setValuesFromUIElements(){
        A = Double.parseDouble(editTextA.getText().toString());
        B = Double.parseDouble(editTextB.getText().toString());
        angle = Integer.parseInt(editTextAngle.getText().toString());
    }

    public void buttonCalculateClick(View view) {
        try{
            setValuesFromUIElements();
        }
        catch (Exception exception){
            outputErrorDialog();
        }
        double square = getSquare();
        textViewResult.setText(String.valueOf((double) Math.round(square * 100) / 100)); // с точностью до 2 знаков после запятой
    }

    public double getSquare() { // вычисление площади треугольника как произведение двух сторон на синус угла между ними
        return (double)1/2 * A * B * Math.sin(Math.toRadians(angle));
    }
}