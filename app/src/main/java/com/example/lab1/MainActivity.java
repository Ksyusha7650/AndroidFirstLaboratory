package com.example.lab1;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText editTextA, editTextB, editTextAngle;
    TextView textViewResult;
    Data data = null;
    double A, B, angle; // две стороны и угол треугольника

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectUIElements(); // связывает компоненты пользовательского интерфейса с переменными класса
    }

    private void outputErrorDialog(String errorText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // создает диалоговое окно
        builder.setTitle(getString(R.string.error)) // устанавливает заголовок диалогового окна
                .setMessage(errorText) // устанавливает текст сообщения в диалоговом окне
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel()); // устанавливает кнопку "OK" и ее обработчик
        builder.show(); // отображает диалоговое окно
    }

    private void connectUIElements() {
        setContentView(R.layout.activity_main); // устанавливает макет для активности
        editTextA = findViewById(R.id.editTextSide1); // находит элементы пользовательского интерфейса по их id
        editTextB = findViewById(R.id.editTextSide2);
        editTextAngle = findViewById(R.id.editTextAngle);
        textViewResult = findViewById(R.id.textViewResult);
    }


    public void buttonCalculateClick(View view) {
        String URL = "https://worldlab.technolog.edu.ru/stud/group3/laboratory_4.php?side1=" + editTextA.getText().toString()
                + "&side2=" + editTextB.getText().toString() + "&angle=" + editTextAngle.getText().toString();
        try {
            CalculateRunnable target = new CalculateRunnable(new URL(URL));
            Thread process = new Thread(target);
            process.start();
            process.join();
            data = target.getData();

        } catch (Exception exception){
            //outputErrorDialog(exception.getMessage()); // выводит диалоговое окно с сообщением об ошибке
        }

        if (!Objects.equals(data.getError(), "")) {
            outputErrorDialog(data.getError());
        } else {
            textViewResult.setText(data.getSquare());
        }
    }

}
