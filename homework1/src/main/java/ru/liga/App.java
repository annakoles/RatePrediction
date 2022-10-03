package ru.liga;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Прогнозирование курсов валют
 * Пользователь вводит команду, на основе которой прогнозируется курс валюты на выбор: EUR, USD, TRY
 * Прогноз валюты на завтра (tomorrow) и на 7 дней (week)
 */
public class App 
{
    static final int PREDICT_DAYS = 7;

    public static void main( String[] args) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String command = reader.readLine();
        String currency = command.split(" ")[1];
        String period = command.split(" ")[2];

        String fileName = "/" + currency + ".csv";
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(fileName)));

        List<Rate> rates = new ArrayList<>();
        csvReader.readLine();
        for (int i = 0; i < PREDICT_DAYS; i++) {
            String[] nextLine = csvReader.readLine().split(";");
            if (nextLine != null) {
                rates.add(new Rate(Integer.parseInt(nextLine[0]), LocalDate.parse(nextLine[1], formatter), Double.parseDouble(nextLine[2])));
            }
        }

        LocalDate today = LocalDate.now();
        LocalDate lastDay = rates.get(0).getDate();
        while (!today.isEqual(lastDay)) {
            lastDay = lastDay.plusDays(1);
            rates.add(0, new Rate(1, lastDay, predict(rates)));
        }

        LocalDate dayOfPredict = today.plusDays(1);
        if (period.equals("tomorrow")) {
            System.out.println(new Rate(1, dayOfPredict, predict(rates)).toString());
        } else if (period.equals("week")) {
            for (int i = 0; i < PREDICT_DAYS; i++) {
                rates.add(0, new Rate(1, dayOfPredict, predict(rates)));
                System.out.println(rates.get(0).toString());
                dayOfPredict = dayOfPredict.plusDays(1);
            }
        }

        csvReader.close();
        reader.close();
    }

    /**
     * Прогнозирует курс валюты на основании 7 последних курсов
     *
     * @param rates курсы валют за последние 7+ дней
     * @return среднее арифметическое значение на основании 7 последних курсов
     */
    public static double predict(List<Rate> rates) {
        double sum = 0;
        for (int i = 0; i < PREDICT_DAYS; i++) {
            sum += rates.get(i).getRate() / rates.get(i).getNominal();
        }
        return sum / PREDICT_DAYS;
    }
}
