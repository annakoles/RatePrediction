package ru.liga;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Прогнозирование курсов валют
 * Пользователь вводит команду, на основе которой прогнозируется курс валюты на выбор: EUR, USD, TRY
 * Прогноз валюты на завтра (tomorrow) и на 7 дней (week)
 */
public class App {
    public static final int PREDICT_DAYS = 7;

    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            CommandInput commandInput = new CommandInput(reader.readLine());
            commandInput.validate();

            String fileName = "/" + commandInput.getCurrency() + ".csv";
            try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(fileName)))) {
                List<Rate> rates = RatesReading.read7rates(csvReader);

                Prediction prediction = new Prediction(rates, commandInput.getPeriod());
                List<Rate> predictRates = prediction.predict();

                for (Rate rate : predictRates) {
                    System.out.println(rate.toString());
                }
            }
        }  catch(ValidateException e) {
            System.out.println("Введена некорректная команда");
        }
    }
}
