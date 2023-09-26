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

    private void setValuesFromUIElements(){
        String textA = editTextA.getText().toString();
        String textB = editTextB.getText().toString();
        String textAngle = editTextAngle.getText().toString();

        if (textA.isEmpty()) {
            throw new ArithmeticException(getString(R.string.error_empty_field_a)); // выбрасывает исключение, если сторона A не введена
        }

        if (textB.isEmpty()) {
            throw new ArithmeticException(getString(R.string.error_empty_field_b)); // выбрасывает исключение, если сторона B не введена
        }

        if (textAngle.isEmpty()) {
            throw new ArithmeticException(getString(R.string.error_empty_field_angle)); // выбрасывает исключение, если угол не введен
        }

        A = Double.parseDouble(textA);
        B = Double.parseDouble(textB);
        angle = Integer.parseInt(textAngle);
        if (angle >= 180) throw new ArithmeticException(getString(R.string.error_angle)); // выбрасывает исключение, если угол >= 180
        if (A == 0) throw new ArithmeticException(getString(R.string.error_side)); // выбрасывает исключение, если сторона A равна 0
        if (B == 0) throw new ArithmeticException(getString(R.string.error_side)); // выбрасывает исключение, если сторона B равна 0
        if (angle == 0) throw new ArithmeticException(getString(R.string.error_angle)); // выбрасывает исключение, если угол равен 0
    }

    public void buttonCalculateClick(View view) {
        try{
            setValuesFromUIElements(); // задает значения переменных A, B, angle на основе введенных пользователем данных
            double square = getSquare(); // вычисляет площадь треугольника
            textViewResult.setText(String.valueOf((double) Math.round(square * 100) / 100)); // выводит результат на экран с округлением до 2 знаков после запятой
        }
        catch (ArithmeticException | NumberFormatException exception){
            outputErrorDialog(exception.getMessage()); // выводит диалоговое окно с сообщением об ошибке
        }
    }

    public double getSquare() { // вычисление площади треугольника как произведение двух сторон на синус угла между ними
        return (double)1/2 * A * B * Math.sin(Math.toRadians(angle));
    }
}
