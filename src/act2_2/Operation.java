package act2_2;

import java.io.Serializable;

public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double operand1;
    private String operator;
    private double operand2;
    private double result;
    private String errorMessage;
    
    public Operation(double operand1, String operator, double operand2) {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }
    
  
    public double getOperand1() { return operand1; }
    public void setOperand1(double operand1) { this.operand1 = operand1; }
    
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    
    public double getOperand2() { return operand2; }
    public void setOperand2(double operand2) { this.operand2 = operand2; }
    
    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public boolean isValid() {
        if (operator == null || (!operator.equals("+") && !operator.equals("-") && 
                                !operator.equals("*") && !operator.equals("/"))) {
            this.errorMessage = "Opérateur non supporté. Utilisez +, -, *, /";
            return false;
        }
        
        if (operator.equals("/") && operand2 == 0) {
            this.errorMessage = "Division par zéro impossible";
            return false;
        }
        
        return true;
    }
    
    
    public void calculate() {
        if (!isValid()) {
            return;
        }
        
        switch (operator) {
            case "+":
                this.result = operand1 + operand2;
                break;
            case "-":
                this.result = operand1 - operand2;
                break;
            case "*":
                this.result = operand1 * operand2;
                break;
            case "/":
                this.result = operand1 / operand2;
                break;
        }
    }
    
    @Override
    public String toString() {
        if (errorMessage != null) {
            return String.format("%.2f %s %.2f = ERREUR: %s", operand1, operator, operand2, errorMessage);
        }
        return String.format("%.2f %s %.2f = %.2f", operand1, operator, operand2, result);
    }
}