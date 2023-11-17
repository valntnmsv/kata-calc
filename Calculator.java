package testovoe.kata.academy.calculator;

public interface Calculator {
    /**
     * Calculate the equation with values provided during initialization phase
     *
     * @return result of calculation
     */
    String calculate ();

    /**
     * Validate provided equation and initialize the calculator with parsed values
     *
     * @param equation String which contains two operands and an operator between them
     * @return true if the calculator was successfully initialized, false calculator is not able to parse provided equation
     */
    boolean validateAndInitialize(String equation);
}
