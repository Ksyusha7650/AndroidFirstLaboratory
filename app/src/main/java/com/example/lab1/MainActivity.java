package com.example.lab1;

import static java.lang.Math.PI;
import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonCalculateClick(View view) {
        EditText editTextA = findViewById(R.id.editTextSide1);
        double A = Double.parseDouble(editTextA.getText().toString());
        EditText editTextB = findViewById(R.id.editTextSide2);
        double B = Double.parseDouble(editTextB.getText().toString());
        EditText editTextAngle = findViewById(R.id.editTextAngle);
        double angle = Double.parseDouble(editTextAngle.getText().toString());
        TextView textViewResult = findViewById(R.id.textViewResult);
        double square = (double)1/2 * A * B * Math.sin(PI * angle/ 180);
        textViewResult.setText(String.valueOf(square));
    }
}