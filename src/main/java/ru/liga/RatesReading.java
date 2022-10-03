package ru.liga;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.App.PREDICT_DAYS;

public class RatesReading {
    public static List<Rate> read7rates(BufferedReader csvReader) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<Rate> rates = new ArrayList<>();

        csvReader.readLine();
        for (int i = 0; i < PREDICT_DAYS; i++) {
            String[] nextLine = csvReader.readLine().split(";");
            if (nextLine != null) {
                rates.add(new Rate(Integer.parseInt(nextLine[0]), LocalDate.parse(nextLine[1], formatter), new BigDecimal(nextLine[2])));
            }
        }
        return rates;
    }
}
