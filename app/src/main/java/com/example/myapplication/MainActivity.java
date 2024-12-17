package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String currentValue = "0";
    String operator = "";
    double storedValue = 0.0;
    NumberFormat numberFormat = new DecimalFormat("#.#######");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpListener();


    }

    private void setUpListener() {
        List.of(
                binding.zero, binding.one, binding.two, binding.three, binding.four, binding.five,
                binding.six, binding.seven, binding.eight, binding.nine, binding.dot
        ).forEach(view -> view.setOnClickListener(this::digitClicked));
        List.of(
                binding.plus, binding.minus, binding.mul, binding.div, binding.percent
        ).forEach(view -> view.setOnClickListener(this::operationClicked));
        binding.AllClear.setOnClickListener(v -> {
            storedValue = 0.0;
            operator = "";
            currentValue = "0";
            showScreen();
        });
        binding.clear.setOnClickListener(v ->{
            currentValue = "0";
            showScreen();
        });
        binding.equal.setOnClickListener(v ->{
            if(!currentValue.isEmpty() && !operator.isEmpty()){
                storedValue = calculate();
                operator = "";
                currentValue = "";
                showScreen();
            }
        });
        binding.factorial.setOnClickListener(v ->{
            if(!currentValue.isEmpty()){
                storedValue = factorize(Integer.parseInt(currentValue));
                operator = "";
                currentValue = "";
                showScreen();
            }
        });




    }

    private float factorize(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return (float) result;

    }

    private void operationClicked(View view) {
        String operator = view.getTag().toString();
        if(this.operator.isEmpty()){
            storedValue = Double.parseDouble(currentValue);
        }
        else {
            storedValue = calculate();
        }
//        if (operator.equals("%")) {
//            currentValue = String.valueOf(storedValue * (Double.parseDouble(currentValue) / 100));
//            storedValue = 0.0;
//        }
        currentValue = "0";
        this.operator = operator;
        showScreen();

    }

    private double calculate() {
        double result = storedValue;
        double currentValue = Double.parseDouble(this.currentValue);
        switch (operator){
            case "+" :
                result += currentValue;
                break;
            case "-":
                result -= currentValue;
                break;
            case "*" :
                result *= currentValue;
                break;
            case "/" :
                if (currentValue != 0) {
                    result /= currentValue;
                } else {

                    result = 0.0;
                }
                break;
            case "%" :
                result = (result*currentValue)/100;
                break;
            case "M" :

                break;


        }
        return result;
    }

    private void digitClicked(View view) {
        String digit = view.getTag().toString();
        String currentText = currentValue + digit;
        currentValue = currentText.startsWith("0") && currentText.length() != 1
                ? currentText.substring(1) : currentText;
        showScreen();
    }
    private void showScreen(){
        binding.screen.setText(
                ((!operator.isEmpty()) ? numberFormat.format(storedValue) + " " + operator + "\n" : (numberFormat.format(storedValue) + "\n")) + currentValue
        );

    }
}