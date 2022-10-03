package ru.liga;

import java.util.Arrays;

public class CommandInput {
    enum Command {rate};

    enum Currency {EUR, USD, TRY};

    enum Period {tomorrow, week};

    private final String commandLine;
    private Command command;
    private Currency currency;
    private Period period;

    public CommandInput(String commandLine) {
        this.commandLine = commandLine;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Period getPeriod() {
        return period;
    }

    public void validate() throws ValidateException {
        String[] commandArgs = commandLine.split(" ");
        if (commandArgs.length != 3) {
            throw new ValidateException();
        } else {
            if (!Arrays.asList(Command.values()).toString().contains(commandArgs[0])) {
                throw new ValidateException();
            } else if (!Arrays.asList(Currency.values()).toString().contains(commandArgs[1])) {
                throw new ValidateException();
            } else if (!Arrays.asList(Period.values()).toString().contains(commandArgs[2])) {
                throw new ValidateException();
            } else {
                this.command = Command.valueOf(commandArgs[0]);
                this.currency = Currency.valueOf(commandArgs[1]);
                this.period = Period.valueOf(commandArgs[2]);
            }
        }
    }
}

