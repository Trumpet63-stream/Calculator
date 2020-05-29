package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {

    private static Stack calculationStack = new Stack();
    TextField screen = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculator");
        VBox calculator = createCalculator();
        primaryStage.setScene(new Scene(calculator, 230, 320));
        primaryStage.show();
    }

    public VBox createCalculator() {
        GridPane grid = new GridPane();
        addButtons(grid);
        VBox calculator = new VBox();
        calculator.getChildren().addAll(screen, grid);
        return calculator;
    }

    public void addButtons(GridPane grid) {
        grid.add(createButton("0"), 0, 5);
        grid.add(createButton("."), 1, 5);
        grid.add(createButton("="), 2, 5);
        grid.add(createButton("1"), 0, 4);
        grid.add(createButton("2"), 1, 4);
        grid.add(createButton("3"), 2, 4);
        grid.add(createButton("+"), 3, 4);
        grid.add(createButton("4"), 0, 3);
        grid.add(createButton("5"), 1, 3);
        grid.add(createButton("6"), 2, 3);
        grid.add(createButton("-"), 3, 3);
        grid.add(createButton("7"), 0, 2);
        grid.add(createButton("8"), 1, 2);
        grid.add(createButton("9"), 2, 2);
        grid.add(createButton("x"), 3, 2);
        grid.add(createButton("÷"), 3, 1);
        grid.add(screen, 0, 0);
    }

    public void addNumberEvent(Button number) {
        number.setOnAction(e -> {
            if (calculationStack.isEmpty()) {
                calculationStack.push(number.getText());
                screen.setText(number.getText());
            } else if (!calculationStack.isEmpty() && (calculationStack.peek().toString().equals("x") ||
                    calculationStack.peek().toString().equals("+") || calculationStack.peek().toString().equals("-")
                    || calculationStack.peek().toString().equals("÷"))) {
                System.out.println("PUSHING");
                calculationStack.push(number.getText());
                screen.setText(number.getText());
            } else {
                System.out.println("Peek: " + calculationStack.peek().toString());
                String newValue = calculationStack.peek().toString();
                System.out.println("Adding this on: " + number.getText());
                newValue += number.getText();
                calculationStack.pop();
                calculationStack.push(newValue);
                screen.setText(newValue);
                System.out.println("New Value: " + newValue);
            }
        });
    }

    public void addArithmeticEvent(Button arithmetic) {
        arithmetic.setOnAction(e -> {
            if (!calculationStack.isEmpty()) {
                System.out.println(arithmetic.getText());
                calculationStack.push(arithmetic.getText());
            } else {
                System.out.println("ERR NULL!");
            }
        });
    }

    public void addEquals(Button equals) {
        equals.setOnAction((e -> {
            if (!calculationStack.isEmpty()) {
                while (!calculationStack.isEmpty()) {
                    System.out.println(calculationStack.pop());
                }
            } else {
                System.out.println("ERR NULL!");
            }
        }));
    }

    public Button createButton(String value) {
        Button calculatorButton = new Button(value);
        calculatorButton.setMinWidth(25);
        calculatorButton.setMaxHeight(25);
        if (value.equals("=")) {
            addEquals(calculatorButton);
        } else if (value.equals("+") || value.equals("-") || value.equals("x") ||
                value.equals("÷")) {
            addArithmeticEvent(calculatorButton);
        } else {
            addNumberEvent(calculatorButton);
        }
        return calculatorButton;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
