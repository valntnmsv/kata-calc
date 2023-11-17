package testovoe.kata.academy.calculator;

import testovoe.kata.academy.exception.InvalidOperatorException;

public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/");
    private final String value;

    Operator(String value) {
        this.value = value;
    }

    public static Operator of(String input) {
        for (Operator operator : Operator.values()) {
            if (operator.value.equals(input)) {
                return operator;
            }
        }
        throw new InvalidOperatorException();
    }
}
