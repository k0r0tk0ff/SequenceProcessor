package ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInput implements InputInterface {

    public Integer getMaxValue() throws ConsoleInputException {
        Integer maxValue;
        String errorText = "Entered max value is incorrect! Enter a positive integer greater than 0";
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter max value of sequence: ");
        try {
            maxValue = scanner.nextInt();
            scanner.close();
            if(maxValue == 0 || maxValue < 0 ) {
                throw new ConsoleInputException(errorText);
            }
        } catch (InputMismatchException e) {
            throw new ConsoleInputException(errorText);
        }
        return  maxValue;
    }
}
