package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Platform;

public class ConverterUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create ComboBoxes
        ComboBox<String> comboBoxFrom = new ComboBox<>();
        comboBoxFrom.setPromptText("From");
        comboBoxFrom.setMinWidth(150);
        ComboBox<String> comboBoxTo = new ComboBox<>();
        comboBoxTo.setPromptText("To");
        comboBoxTo.setMinWidth(150);

        // Create TextFields
        TextField textFieldAmount = new TextField();
        TextField textFieldResult = new TextField();
        textFieldResult.setEditable(false); // Result field should not be editable

        // Create Labels
        Label labelFrom = new Label("From");
        Label labelTo = new Label("To");
        Label labelAmount = new Label("Amount");
        Label labelResult = new Label("Result");

        // Create Buttons
        Button swapButton = new Button("Swap");
        Button convertButton = new Button("Convert");

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
        Menu menuHelp = new Menu("Help");
        MenuItem helpItem = new MenuItem("How to use");
        helpItem.setOnAction(e -> showHelpDialog()); // Show instructions when clicked
        menuHelp.getItems().add(helpItem);

        menuBar.getMenus().addAll(menuFile, menuHelp);

        // Add all the components to the root layout
        root.getChildren().addAll(menuBar, mainLayout, bottomBox);

        // Set the Scene and Stage
        Scene scene = new Scene(root, 450, 250);
        primaryStage.setTitle("Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to display help dialog with instructions
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
}
