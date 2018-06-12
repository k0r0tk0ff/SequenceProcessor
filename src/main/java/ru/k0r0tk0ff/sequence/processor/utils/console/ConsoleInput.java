package ru.k0r0tk0ff.sequence.processor.utils.console;

import java.util.Scanner;

public class ConsoleInput implements InputInteface {

    public Integer getMaxValue() throws ConsoleInputException {
        Integer maxValue;
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter max value of sequence: \n");
        maxValue = scanner.nextInt();
        scanner.close();
        if(maxValue == 0 || maxValue < 0 ) {
            throw new ConsoleInputException(" Entered max value is incorrect! ");
        }
        return  maxValue;
    }
}
