package view;

import controller.ConverterController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import model.CurrencyCode;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter.Change;

public class ConverterUI extends Application {

    // Controller
    ConverterController controller;

    // Create TextFields
    TextField textFieldAmount;
    TextField textFieldResult;

    // ComboBoxes
    ComboBox<CurrencyCode> comboBoxFrom;
    ComboBox<CurrencyCode> comboBoxTo;

    @Override
    public void start(Stage primaryStage) {
        // Two choice boxes or combo boxes for selecting the source currency and the target currency.
        // from
        comboBoxFrom = new ComboBox<>();
        // The choice boxes should contain a list of available currencies that the user can select from.
        // The list of currencies should be retrieved from the model, which should contain the abbreviation, name, and conversion rate to a fixed currency (e.g., USD).
        comboBoxFrom.getItems().addAll(CurrencyCode.values());
        comboBoxFrom.getSelectionModel().selectFirst(); // Select the first item by default

        // to
        comboBoxTo = new ComboBox<>();
        comboBoxTo.getItems().addAll(CurrencyCode.values());
        comboBoxTo.getSelectionModel().select(1); // Select the second item by default

        // Two text fields for inputting the amount to convert and displaying the converted amount.
        textFieldAmount = new TextField();
        textFieldResult = new TextField();
        textFieldResult.setEditable(false); // Result field should not be editable

        // prevent user from entering non-numeric values
        UnaryOperator<Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([0-9]*\\.?[0-9]*)")) { // Allow only numbers and at most one decimal point
                return change;
            }
            return null; // Reject the change
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textFieldAmount.setTextFormatter(textFormatter);


        // Labels to indicate the purpose of each input field, choice box, and the result field.
        Label labelFrom = new Label("From");
        Label labelTo = new Label("To");
        Label labelAmount = new Label("Amount");
        Label labelResult = new Label("Result");

        // Create Buttons
        Button swapButton = new Button("Swap");
        Button convertButton = new Button("Convert");

        // Event handlers (good example of swapping values also for DSA :))
        swapButton.setOnAction(e -> {
            CurrencyCode temp = comboBoxFrom.getValue();
            comboBoxFrom.setValue(comboBoxTo.getValue());
            comboBoxTo.setValue(temp);
        });

        // A button labeled "Convert" to initiate the currency conversion.
        convertButton.setOnAction(e -> {
            try {
                controller.convert();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid amount.");
                alert.showAndWait();
            }
        });

        // Layouts
        GridPane leftPane = new GridPane();
        leftPane.setVgap(10);
        leftPane.setHgap(10);
        leftPane.setPadding(new Insets(10));
        leftPane.add(comboBoxFrom, 0, 0);
        leftPane.add(textFieldAmount, 0, 1);
        leftPane.add(labelAmount, 0, 2);

        GridPane rightPane = new GridPane();
        rightPane.setVgap(10);
        rightPane.setHgap(10);
        rightPane.setPadding(new Insets(10));
        rightPane.add(comboBoxTo, 0, 0);
        rightPane.add(textFieldResult, 0, 1);
        rightPane.add(labelResult, 0, 2);

        // The labels for the choice boxes should appear right on top of each choice box.
        VBox leftBox = new VBox(10, labelFrom, comboBoxFrom, textFieldAmount, labelAmount);
        VBox rightBox = new VBox(10, labelTo, comboBoxTo, textFieldResult, labelResult);

        // Center section for swap button
        VBox centerBox = new VBox(swapButton);
        centerBox.setAlignment(Pos.CENTER);

        // Bottom section for convert button
        HBox bottomBox = new HBox(convertButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        // Main layout
        HBox mainLayout = new HBox(20, leftBox, centerBox, rightBox);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        // Creating the final layout including the menu bar
        VBox root = new VBox();
        MenuBar menuBar = new MenuBar();

        // File Menu
        Menu menuFile = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());  // Closes the application
        menuFile.getItems().add(exitItem);

        // Help Menu
        // The user interface should provide clear instructions on how to use the converter.
        Menu menuHelp = new Menu("Help");
        MenuItem helpItem = new MenuItem("How to use");
        helpItem.setOnAction(e -> showHelpDialog()); // Show instructions when clicked
        menuHelp.getItems().add(helpItem);

        menuBar.getMenus().addAll(menuFile, menuHelp);

        // Add all the components to the root layout
        // appropriate layout(s) to organize the components effectively
        root.getChildren().addAll(menuBar, mainLayout, bottomBox);

        // a window with appropriate dimensions to display the converter components
        Scene scene = new Scene(root, 500, 250);
        // Apply the CSS file to the scene
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // a title indicating the purpose of the application, such as "Currency Converter."
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void init() {
        controller = new ConverterController(this);
    }

    // Method to display help dialog with instructions
    // The user interface should provide clear instructions on how to use the converter.
    private void showHelpDialog() {
        Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
        helpDialog.setTitle("How to use the Converter");
        helpDialog.setHeaderText(null);
        helpDialog.setContentText("1. Select the units in the 'From' and 'To' dropdowns.\n"
                + "2. Enter the amount in the 'Amount' field.\n"
                + "3. Click 'Convert' to see the result.\n"
                + "4. Use 'Swap' to switch the 'From' and 'To' units.");
        DialogPane dialogPane = helpDialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        helpDialog.showAndWait();
    }

    public double getAmount() {
        return Double.parseDouble(textFieldAmount.getText());
    }

    public void showResult(double result) {
        textFieldResult.setText(String.format("%.2f", result));
    }

    public CurrencyCode getFrom() {
        return comboBoxFrom.getValue();
    }

    public CurrencyCode getTo() {
        return comboBoxTo.getValue();
    }
}
