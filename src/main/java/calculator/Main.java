package calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private TextField display = new TextField();
    private double num1 = 0;
    private String operator = "";

    @Override
    public void start(Stage stage) {

        // --- Display
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPrefHeight(50);
        display.setStyle(
            "-fx-font-size: 18px; " +
            "-fx-background-color: lightgray; " +
            "-fx-border-color: #848482; " + 
            "-fx-border-width: 2px;"
        );

        // --- Buttons
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        String[] buttons = {
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0","C","=","+"
        };

        int k = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Button b = new Button(buttons[k]);
                b.setPrefSize(80, 80);
                b.setFont(Font.font("System", FontWeight.BOLD, 18));

                // --- Set colors based on type
                if ("+-*/".contains(buttons[k])) {
                    b.setStyle("-fx-background-color: #303030; -fx-text-fill: #A9A9A9;"); // Anthracite
                } else if ("C=".contains(buttons[k])) {
                    b.setStyle("-fx-background-color: #928E85; -fx-text-fill: white;"); // Stone Gray
                } else { 
                    b.setStyle("-fx-background-color: #726E6D; -fx-text-fill: white;"); // Numbers on Smokey Gray
                }

                // --- Hover effect
                b.setOnMouseEntered(e -> b.setOpacity(0.7));
                b.setOnMouseExited(e -> b.setOpacity(1.0));

                b.setOnAction(e -> handle(b.getText()));
                grid.add(b, c, r);
                k++;
            }
        }

        VBox calcBox = new VBox(10, display, grid);
        calcBox.setAlignment(Pos.CENTER);
        calcBox.setPadding(new Insets(10));
        calcBox.setStyle("-fx-background-color: #726E6D;"); // Smokey Gray background

        // --- Identity Section
        Label name = new Label("Youssef AlaaEldin Laillee");
        name.setFont(Font.font("System", FontWeight.BOLD, 16));
        name.setTextFill(Color.WHITE);

        Label id = new Label("Student ID: 2300649");
        id.setFont(Font.font("System", FontWeight.BOLD, 14));
        id.setTextFill(Color.WHITE);

        // --- Load Image
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream("/images/image.jpg"));
        } catch (Exception e) {
            image = null;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(180);
        imageView.setPreserveRatio(true);

        VBox identityBox = new VBox(10, imageView, name, id);
        identityBox.setAlignment(Pos.CENTER);
        identityBox.setPadding(new Insets(10));
        identityBox.setStyle("-fx-background-color: #2F2F2F;"); // Dark Gray

        // --- Main Layout
        BorderPane root = new BorderPane();
        root.setLeft(identityBox);
        root.setCenter(calcBox);

        Scene scene = new Scene(root, 550, 350);
        stage.setTitle("Identity Calculator");
        stage.setScene(scene);
        stage.show();
    }

    private void handle(String v) {
        try {
            if (v.matches("[0-9]")) {
                display.setText(display.getText() + v);
            } 
            else if (v.matches("[+\\-*/]")) {
            if (display.getText().isEmpty()) return;
             num1 = Double.parseDouble(display.getText());
             operator = v;
             display.clear();
            }
            else if (v.equals("=")) {
                double num2 = Double.parseDouble(display.getText());
                double res = 0;

                switch (operator) {
                    case "+": res = num1 + num2; break;
                    case "-": res = num1 - num2; break;
                    case "*": res = num1 * num2; break;
                    case "/":
                        if (num2 == 0) {
                            display.setText("Error");
                            return;
                        }
                        res = num1 / num2;
                        break;
                }
                display.setText(String.valueOf(res));
            } 
            else if (v.equals("C")) {
                display.clear();
                num1 = 0;
                operator = "";
            }
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
