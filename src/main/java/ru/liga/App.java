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

            String fileName = "/" + commandInput.getCurrency() + ".csv";
            List<Rate> rates = RatesReading.read7rates(fileName);

            Prediction prediction = new Prediction(rates);
            List<Rate> predictRates = prediction.predict(commandInput.getPeriod());
            printPrediction(predictRates);
        } catch (ValidateException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printPrediction(List<Rate> rates) {
        for (Rate rate : rates) {
            System.out.println(rate.dateRateFormat());
        }
    }
}
