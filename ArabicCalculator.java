package testovoe.kata.academy.calculator;

import testovoe.kata.academy.exception.NumberTooBigException;
import testovoe.kata.academy.exception.NumberTooSmallException;

public class ArabicCalculator implements Calculator {
    private Integer firstOperand;
    private Integer secondOperand;
    private Operator operator;

    @Override
    public String calculate() {
        if (firstOperand == null || secondOperand == null || operator == null) {
            throw new RuntimeException("ArabicCalculator isn't initialized");
        }

        return switch (operator) {
            case PLUS -> String.valueOf(firstOperand + secondOperand);
            case MINUS -> String.valueOf(firstOperand - secondOperand);
            case DIVIDE -> String.valueOf(firstOperand / secondOperand);
            case MULTIPLY -> String.valueOf(firstOperand * secondOperand);
        };
    }

    @Override
    public boolean validateAndInitialize(String equation) {
        String[] splitEquation = equation.split(" ");

        if (splitEquation.length != 3) {
            return false;
        }

        try {
            int firstOperand = Integer.parseInt(splitEquation[0]);
            int secondOperand = Integer.parseInt(splitEquation[2]);
            Operator parsedOperator = Operator.of(splitEquation[1]);

            if (firstOperand > 10 || secondOperand > 10) {
                throw new NumberTooBigException();
            } else if (firstOperand < 1 || secondOperand < 1) {
                throw new NumberTooSmallException();
            }

            this.firstOperand = firstOperand;
            this.secondOperand = secondOperand;
            this.operator = parsedOperator;
            return true;
        } catch (NumberFormatException ignore) {
            // ArabicCalculator is not able to work with provided operands
            return false;
        }
    }
}
