package com.example.lab1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText editTextA, editTextB, editTextAngle;
    TextView textViewResult;
    ServerData data = null;
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
        // Формирование URL-адреса запроса с использованием введенных значений
        String URL = getString(R.string.htpps_request,editTextA.getText().toString(), editTextB.getText().toString(), editTextAngle.getText().toString());
        try {
            // Создание объекта CalculateRunnable с указанным URL-адресом
            CalculateRunnable target = new CalculateRunnable(new URL(URL));

            // Создание нового потока и запуск выполнения Runnable
            Thread process = new Thread(target);
            process.start();

            // Ожидание завершения потока
            process.join();

            // Получение данных из CalculateRunnable
            data = target.getData();

        } catch (Exception exception){
            //NOP
        }

        // Проверяем наличие данных
        if (data == null) {
            // Если данных нет, выводим диалоговое окно с сообщением о ошибке сервера
            outputErrorDialog(getString(R.string.server_error));
            return;
        }

        if (!Objects.equals(data.getError(), "")) {
            // Если есть ошибка в данных, выводим диалоговое окно с сообщением об ошибке
            outputErrorDialog(data.getError());
        } else {
            // Выводим результат на экран
            textViewResult.setText(data.getSquare());
        }
    }


}
