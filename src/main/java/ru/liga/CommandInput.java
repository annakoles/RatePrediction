package ru.liga;

import java.util.Arrays;

public class CommandInput {
    public enum Command {rate};
    public enum Currency {EUR, USD, TRY};
    public enum Period {tomorrow, week};

    private final Command command;
    private final Currency currency;
    private final Period period;

    public CommandInput(String commandLine) {
        validate(commandLine);

        String[] commandArgs = commandLine.split(" ");
        this.command = Command.valueOf(commandArgs[0]);
        this.currency = Currency.valueOf(commandArgs[1]);
        this.period = Period.valueOf(commandArgs[2]);
    }

    public Currency getCurrency() {
        return currency;
    }

    public Period getPeriod() {
        return period;
    }

    private void validate(String commandLine) throws ValidateException {
        String[] commandArgs = commandLine.split(" ");
        if (commandArgs.length != 3) {
            throw new ValidateException("Некорректное количество аргументов");
        } else {
            if (!Arrays.asList(Command.values()).toString().contains(commandArgs[0])) {
                throw new ValidateException("Некорректная команда: " + commandArgs[0]);
            } else if (!Arrays.asList(Currency.values()).toString().contains(commandArgs[1])) {
                throw new ValidateException("Некорректно введен код валюты: " + commandArgs[1]);
            } else if (!Arrays.asList(Period.values()).toString().contains(commandArgs[2])) {
                throw new ValidateException("Некорректно указан период прогнозирования: " + commandArgs[2]);
            }
        }
    }
}

