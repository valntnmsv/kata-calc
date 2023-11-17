package testovoe.kata.academy.calculator;

import testovoe.kata.academy.exception.InvalidResultOfCalculationException;
import testovoe.kata.academy.exception.InvalidRomanNumberException;
import testovoe.kata.academy.exception.NumberTooBigException;
import testovoe.kata.academy.exception.NumberTooSmallException;

import java.util.HashMap;
import java.util.Map;

public class RomanCalculator implements Calculator {
    //it's able to check a number up to 4000. This limit is ok in our case
    private static final String ROMAN_NUMBER_PATTERN = "^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
    private Integer firstOperand;
    private Integer secondOperand;
    private Operator operator;

    private static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();

    static {
        ROMAN_VALUES.put('I', 1);
        ROMAN_VALUES.put('V', 5);
        ROMAN_VALUES.put('X', 10);
        ROMAN_VALUES.put('L', 50);
        ROMAN_VALUES.put('C', 100);
        ROMAN_VALUES.put('D', 500);
        ROMAN_VALUES.put('M', 1000);
    }

    @Override
    public String calculate() {
        if (firstOperand == null || secondOperand == null || operator == null) {
            throw new RuntimeException("RomanCalculator isn't initialized");
        }

        return switch (operator) {
            case PLUS -> convertToRomanNumeral(firstOperand + secondOperand);
            case MINUS -> {
                int resultOfCalculation = firstOperand - secondOperand;
                if (resultOfCalculation < 0) {
                    throw new InvalidResultOfCalculationException();
                }
               yield convertToRomanNumeral(resultOfCalculation);
            }
            case DIVIDE -> convertToRomanNumeral(firstOperand / secondOperand);
            case MULTIPLY -> convertToRomanNumeral(firstOperand * secondOperand);
        };
    }

    @Override
    public boolean validateAndInitialize(String equation) {
        String[] splitEquation = equation.split(" ");

        if (splitEquation.length != 3) {
            return false;
        }

        try {
            int firstOperand = parseRomanNumeral(splitEquation[0]);
            int secondOperand = parseRomanNumeral(splitEquation[2]);
            Operator parsedOperator = Operator.of(splitEquation[1]);

            if (firstOperand > 10 || secondOperand > 10) {
                throw new NumberTooBigException();
            } else if (firstOperand < 1 || secondOperand < 1) {
                throw new NumberTooSmallException();
            }

            this.operator = parsedOperator;
            this.firstOperand = firstOperand;
            this.secondOperand = secondOperand;
            return true;
        } catch (InvalidRomanNumberException ignore) {
            // RomanCalculator is not able to work with provided operands
            return false;
        }
    }

    private static int parseRomanNumeral(String romanNumeral) {
        int result = 0;
        int previousValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            char currentChar = romanNumeral.charAt(i);

            if (!ROMAN_VALUES.containsKey(currentChar)) {
                throw new InvalidRomanNumberException();
            }

            int currentValue = ROMAN_VALUES.get(currentChar);
            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            previousValue = currentValue;
        }

        return result;
    }

    private static String convertToRomanNumeral(int number) {
        StringBuilder sb = new StringBuilder();

        while (number >= 1000) {
            sb.append("M");
            number -= 1000;
        }
        if (number >= 900) {
            sb.append("CM");
            number -= 900;
        }
        if (number >= 500) {
            sb.append("D");
            number -= 500;
        }
        if (number >= 400) {
            sb.append("CD");
            number -= 400;
        }
        while (number >= 100) {
            sb.append("C");
            number -= 100;
        }
        if (number >= 90) {
            sb.append("XC");
            number -= 90;
        }
        if (number >= 50) {
            sb.append("L");
            number -= 50;
        }
        if (number >= 40) {
            sb.append("XL");
            number -= 40;
        }
        while (number >= 10) {
            sb.append("X");
            number -= 10;
        }
        if (number >= 9) {
            sb.append("IX");
            number -= 9;
        }
        if (number >= 5) {
            sb.append("V");
            number -= 5;
        }
        if (number >= 4) {
            sb.append("IV");
            number -= 4;
        }
        while (number >= 1) {
            sb.append("I");
            number -= 1;
        }

        return sb.toString();
    }
}
