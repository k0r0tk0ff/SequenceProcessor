package ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters;

import java.util.Scanner;

public class ConsoleInput implements InputSequenceParameters {

    public Integer getMaxValue() {
        Integer maxValue = null;
        boolean flag = false;
        Scanner scanner;
        String errorMessage = "Entered max value is incorrect! Enter a positive integer greater than 0";
        while (!flag) {
            System.out.println("\nEnter max value of sequence: ");
            scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                maxValue = scanner.nextInt();
                if (maxValue == 0 || maxValue < 0) {
                    System.out.println(errorMessage);
                } else {
                    scanner.close();
                    flag = true;
                }
            } else {
                System.out.println(errorMessage);
            }
        }
        return maxValue;
    }
}
