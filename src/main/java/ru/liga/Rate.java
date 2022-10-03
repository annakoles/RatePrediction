package ru.liga;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rate {
    private final int nominal;
    private final LocalDate date;
    private final BigDecimal rate;

    public Rate(int nominal, LocalDate date, BigDecimal rate) {
        this.nominal = nominal;
        this.date = date;
        this.rate = rate;
    }

    public int getNominal() {
        return nominal;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy")) + " - " + String.format("%.2f", rate);
    }
}
