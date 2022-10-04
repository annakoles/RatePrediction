package ru.liga;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.App.PREDICT_DAYS;

public class Prediction {
    private List<Rate> rates;

    public Prediction(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Rate> predict(CommandInput.Period period) {
        List<Rate> predictRates = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate lastDay = rates.get(0).getDate();
        while (!today.isEqual(lastDay)) {
            lastDay = lastDay.plusDays(1);
            rates.add(0, new Rate(1, lastDay, average(rates)));
        }

        LocalDate dayOfPredict = today.plusDays(1);
        if (period.equals(CommandInput.Period.tomorrow)) {
            predictRates.add(new Rate(1, dayOfPredict, average(rates)));
        } else if (period.equals(CommandInput.Period.week)) {
            for (int i = 0; i < PREDICT_DAYS; i++) {
                rates.add(0, new Rate(1, dayOfPredict, average(rates)));
                predictRates.add(new Rate(1, dayOfPredict, average(rates)));
                dayOfPredict = dayOfPredict.plusDays(1);
            }
        }
        return predictRates;
    }

    /**
     * Прогнозирует курс валюты на основании 7 последних курсов
     *
     * @param rates курсы валют за последние 7+ дней
     * @return среднее арифметическое значение на основании 7 последних курсов
     */
    private static BigDecimal average(List<Rate> rates) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < PREDICT_DAYS; i++) {
            sum = sum.add(rates.get(i).getRate().divide(new BigDecimal(rates.get(i).getNominal()), RoundingMode.CEILING));
        }
        return sum.divide(new BigDecimal(PREDICT_DAYS), 2, RoundingMode.CEILING);
    }
}
