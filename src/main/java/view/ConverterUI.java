package view;

import controller.ConverterController;
import entity.Currency;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.UnaryOperator;

public class ConverterUI extends Application {

    // Controller
    ConverterController controller;

    // Create TextFields
    TextField textFieldAmount;
    TextField textFieldResult;

    // ComboBoxes
    ComboBox<String> comboBoxFrom;
    ComboBox<String> comboBoxTo;

    @Override
    public void start(Stage primaryStage) {
        comboBoxFrom = new ComboBox<>();
        comboBoxTo = new ComboBox<>();
        textFieldAmount = new TextField();
        textFieldResult = new TextField();
        textFieldResult.setEditable(false);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([0-9]*\\.?[0-9]*)")) {
                return change;
            }
            return null;
        };
        textFieldAmount.setTextFormatter(new TextFormatter<>(filter));

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

        // Layout
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

        Menu configFile = new Menu("Config");
        MenuItem addCur = new MenuItem("Add / Update / Delete");
        addCur.setOnAction(e -> showAddNewCurrencyDialog());
        configFile.getItems().add(addCur);

        Menu menuHelp = new Menu("Help");
        MenuItem helpItem = new MenuItem("How to use");
        helpItem.setOnAction(e -> showHelpDialog());
        menuHelp.getItems().add(helpItem);

        menuBar.getMenus().addAll(menuFile, configFile, menuHelp);

        root.getChildren().addAll(menuBar, mainLayout, bottomBox);

        Scene scene = new Scene(root, 500, 250);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize the UI with currency data
        controller.initializeUI();
    }

    @Override
    public void init() {
        controller = new ConverterController(this);
    }

    private void showAddNewCurrencyDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Update, Add, or Delete Currency");

        // Labels
        Label labelCode = new Label("Currency Code:");
        Label labelName = new Label("Currency Name:");
        Label labelRate = new Label("Rate to USD:");

        // TextFields
        TextField newCurrencyCode = new TextField();
        newCurrencyCode.setPromptText("e.g., USD");

        TextField newNameField = new TextField();
        newNameField.setPromptText("e.g., US Dollar");

        TextField rateToUsd = new TextField();
        rateToUsd.setPromptText("e.g., 1.00");

        // Input validation
        UnaryOperator<TextFormatter.Change> codeFilter = change -> {
            String newText = change.getControlNewText();
            // allow 0-3 alphabetic characters
            if (newText.matches("[A-Za-z]{0,3}")) {
                return change;
            }
            return null;
        };
        newCurrencyCode.setTextFormatter(new TextFormatter<>(codeFilter));

        UnaryOperator<TextFormatter.Change> rateFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([0-9]*\\.?[0-9]*)")) {
                return change;
            }
            return null;
        };
        rateToUsd.setTextFormatter(new TextFormatter<>(rateFilter));

        // Automatically populate fields when a matching code is entered
        newCurrencyCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 3) {
                Currency currency = controller.getCurrency(newValue.toUpperCase());
                if (currency != null) {
                    newNameField.setText(currency.getName());
                    rateToUsd.setText(String.valueOf(currency.getRate()));
                } else {
                    newNameField.clear();
                    rateToUsd.clear();
                }
            } else {
                newNameField.clear();
                rateToUsd.clear();
            }
        });

        // Update / Add Button
        Button saveButton = new Button("Add / Update");
        saveButton.setPadding(new Insets(10, 20, 10, 20));
        saveButton.setDisable(true); // Disable save button initially

        // Delete Button
        Button deleteButton = new Button("Delete");
        deleteButton.setPadding(new Insets(10, 20, 10, 20));
        deleteButton.setDisable(true); // Disable delete button initially

        // Enable save and delete buttons only if the code is valid
        newCurrencyCode.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(newCurrencyCode, newNameField, rateToUsd, saveButton);
            deleteButton.setDisable(newValue.trim().isEmpty() || newValue.length() != 3);
        });

        newNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(newCurrencyCode, newNameField, rateToUsd, saveButton);
            deleteButton.setDisable(newValue.trim().isEmpty());
        });
        rateToUsd.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(newCurrencyCode, newNameField, rateToUsd, saveButton);
            deleteButton.setDisable(newValue.trim().isEmpty());
        });

        // Save Button Action
        saveButton.setOnAction(e -> {
            String newCode = newCurrencyCode.getText().toUpperCase();
            String newName = newNameField.getText();
            double newRate = Double.parseDouble(rateToUsd.getText());

            controller.updateCurrency(newCode, newName, newRate);
            dialog.close();
        });

        // Delete Button Action
        deleteButton.setOnAction(e -> {
            String codeToDelete = newCurrencyCode.getText().toUpperCase();
            controller.deleteCurrency(codeToDelete);
            dialog.close();
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.add(labelCode, 0, 0);
        gridPane.add(newCurrencyCode, 1, 0);
        gridPane.add(labelName, 0, 1);
        gridPane.add(newNameField, 1, 1);
        gridPane.add(labelRate, 0, 2);
        gridPane.add(rateToUsd, 1, 2);

        HBox buttonBox = new HBox(10, saveButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, gridPane, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 250);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void validateInputs(TextField codeField, TextField nameField, TextField rateField, Button saveButton) {
        // use trim() to remove leading and trailing whitespaces
        boolean isCodeValid = !codeField.getText().trim().isEmpty() && codeField.getText().matches("[A-Za-z]{3}");
        boolean isNameValid = !nameField.getText().trim().isEmpty();
        boolean isRateValid = !rateField.getText().trim().isEmpty() && rateField.getText().matches("([0-9]*\\.?[0-9]+)");

        saveButton.setDisable(!(isCodeValid && isNameValid && isRateValid));
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

    public void updateCurrencyComboBoxes(List<String> codes) {
        comboBoxFrom.getItems().clear();
        comboBoxFrom.getItems().addAll(codes);
        comboBoxFrom.getSelectionModel().selectFirst();

        comboBoxTo.getItems().clear();
        comboBoxTo.getItems().addAll(codes);
        comboBoxTo.getSelectionModel().select(1);
    }

    private void showHelpDialog() {
        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        helpDialog.setTitle("How to Use the Currency Converter");
        helpDialog.setHeaderText("Quick Guide to Using the Currency Converter");

        StringBuilder helpText = new StringBuilder();
        helpText.append("Follow these steps to convert currencies:\n\n");
        helpText.append("1. **Select Currencies**: Choose the currency you want to convert from in the 'From' dropdown and the currency you want to convert to in the 'To' dropdown.\n");
        helpText.append("2. **Enter Amount**: Enter the amount you want to convert in the 'Amount' field.\n");
        helpText.append("3. **Click 'Convert'**: Press the 'Convert' button to see the result in the 'Result' field.\n");
        helpText.append("4. **Swap Currencies**: Use the 'Swap' button to quickly switch between the 'From' and 'To' currencies.\n");
        helpText.append("5. **Add/Update/Delete Currencies**: Go to the 'Config' menu and select 'Add / Update / Delete' to manage available currencies.\n\n");
        helpText.append("Tips:\n");
        helpText.append("- Ensure that the amount entered is numeric.\n");
        helpText.append("- If you don't see the currency you need, add it through the 'Config' menu.");

        helpDialog.setContentText(helpText.toString());
        helpDialog.showAndWait();
    }
}
