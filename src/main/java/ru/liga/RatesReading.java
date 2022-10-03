package ru.liga;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.App.PREDICT_DAYS;

public class RatesReading {
    public static List<Rate> read7rates(String fileName) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Rate> rates = new ArrayList<>();

        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(fileName)))) {
            csvReader.readLine();
            for (int i = 0; i < PREDICT_DAYS; i++) {
                String[] nextLine = csvReader.readLine().split(";");
                rates.add(new Rate(Integer.parseInt(nextLine[0]), LocalDate.parse(nextLine[1], formatter), new BigDecimal(nextLine[2])));
            }
        }
        return rates;
    }
}
