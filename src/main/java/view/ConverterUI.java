package view;

import controller.ConverterController;
import dao.CurrencyDao;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter.Change;

public class ConverterUI extends Application {

    // Controller
    ConverterController controller;

    // DAO
    CurrencyDao currencyDao = new CurrencyDao();

    // Create TextFields
    TextField textFieldAmount;
    TextField textFieldResult;

    // ComboBoxes
    ComboBox<String> comboBoxFrom;
    ComboBox<String> comboBoxTo;

    @Override
    public void start(Stage primaryStage) {
        List<String> codes = currencyDao.getAllCodes();

        comboBoxFrom = new ComboBox<>();
        comboBoxFrom.getItems().addAll(currencyDao.getAllCodes());
        comboBoxFrom.getSelectionModel().selectFirst(); // Select the first item by default

        comboBoxTo = new ComboBox<>();
        comboBoxTo.getItems().addAll(currencyDao.getAllCodes());
        comboBoxTo.getSelectionModel().select(1); // Select the second item by default

        textFieldAmount = new TextField();
        textFieldResult = new TextField();
        textFieldResult.setEditable(false);

        UnaryOperator<Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([0-9]*\\.?[0-9]*)")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textFieldAmount.setTextFormatter(textFormatter);

        Label labelFrom = new Label("From");
        Label labelTo = new Label("To");
        Label labelAmount = new Label("Amount");
        Label labelResult = new Label("Result");

        Button swapButton = new Button("Swap");
        Button convertButton = new Button("Convert");

        swapButton.setOnAction(e -> {
            String temp = comboBoxFrom.getValue();
            comboBoxFrom.setValue(comboBoxTo.getValue());
            comboBoxTo.setValue(temp);
        });

        convertButton.setOnAction(e -> {
            try {
                controller.convert();
            } catch (NumberFormatException ex) {
                showError("Please enter a valid amount.");
            }
        });

        GridPane leftPane = new GridPane();
        leftPane.setVgap(10);
        leftPane.setHgap(10);
        leftPane.setPadding(new Insets(10));

        GridPane rightPane = new GridPane();
        rightPane.setVgap(10);
        rightPane.setHgap(10);
        rightPane.setPadding(new Insets(10));

        VBox leftBox = new VBox(10, labelFrom, comboBoxFrom, textFieldAmount, labelAmount);
        VBox rightBox = new VBox(10, labelTo, comboBoxTo, textFieldResult, labelResult);

        VBox centerBox = new VBox(swapButton);
        centerBox.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(convertButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        HBox mainLayout = new HBox(20, leftBox, centerBox, rightBox);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        VBox root = new VBox();
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());
        menuFile.getItems().add(exitItem);

        Menu menuHelp = new Menu("Help");
        MenuItem helpItem = new MenuItem("How to use");
        helpItem.setOnAction(e -> showHelpDialog());
        menuHelp.getItems().add(helpItem);

        menuBar.getMenus().addAll(menuFile, menuHelp);

        root.getChildren().addAll(menuBar, mainLayout, bottomBox);

        Scene scene = new Scene(root, 500, 250);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        controller = new ConverterController(this);
    }

    private void showHelpDialog() {
        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        helpDialog.setTitle("How to use the Converter");
        helpDialog.setHeaderText(null);
        helpDialog.setContentText("1. Select the units in the 'From' and 'To' dropdowns.\n"
                + "2. Enter the amount in the 'Amount' field.\n"
                + "3. Click 'Convert' to see the result.\n"
                + "4. Use 'Swap' to switch the 'From' and 'To' units.");
        helpDialog.showAndWait();
    }

    public double getAmount() {
        return Double.parseDouble(textFieldAmount.getText());
    }

    public void showResult(double result) {
        textFieldResult.setText(String.format("%.2f", result));
    }

    public String getFrom() {
        return comboBoxFrom.getValue();
    }

    public String getTo() {
        return comboBoxTo.getValue();
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
