import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class InventoryManagementGUI extends Application {

    private List<Product> inventory = new ArrayList<>(); // Assuming Product is a class representing your products

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management");

        Button addButton = new Button("Add Product");
        Button viewButton = new Button("View Inventory");
        Button deleteButton = new Button("Delete from Inventory");
        Button exitButton = new Button("Exit");

        addButton.setOnAction(e -> addProduct());
        viewButton.setOnAction(e -> viewInventory());
        deleteButton.setOnAction(e -> deleteProduct());
        exitButton.setOnAction(e -> exit());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(addButton, viewButton, deleteButton, exitButton);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addProduct() {
        // Create a new window for adding a product
        Stage addProductStage = new Stage();
        addProductStage.setTitle("Add Product");

        // Create UI components for product details
        TextField productIDField = new TextField();
        TextField nameField = new TextField();
        TextField costOfSaleField = new TextField();
        TextField costOfPurchaseField = new TextField();
        TextField quantityField = new TextField();

        Label productIDLabel = new Label("Product ID:");
        Label nameLabel = new Label("Name:");
        Label costOfSaleLabel = new Label("Cost of Sale:");
        Label costOfPurchaseLabel = new Label("Cost of Purchase:");
        Label quantityLabel = new Label("Quantity:");

        Button addButton = new Button("Add");

        // Set action for the "Add" button
        addButton.setOnAction(e -> {
            // Retrieve values from fields
            String productID = productIDField.getText();
            String name = nameField.getText();
            double costOfSale = Double.parseDouble(costOfSaleField.getText());
            double costOfPurchase = Double.parseDouble(costOfPurchaseField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            // Call your logic to add the product (replace the print statement)
            System.out.println("Adding product: " + productID + ", " + name + ", " + costOfSale + ", " + costOfPurchase + ", " + quantity);

            // Close the addProductStage
            addProductStage.close();
        });

        // Create layout for the add product window
        GridPane addProductLayout = new GridPane();
        addProductLayout.setPadding(new Insets(10));
        addProductLayout.setVgap(8);
        addProductLayout.setHgap(10);

        // Add components to the layout
        addProductLayout.add(productIDLabel, 0, 0);
        addProductLayout.add(productIDField, 1, 0);
        addProductLayout.add(nameLabel, 0, 1);
        addProductLayout.add(nameField, 1, 1);
        addProductLayout.add(costOfSaleLabel, 0, 2);
        addProductLayout.add(costOfSaleField, 1, 2);
        addProductLayout.add(costOfPurchaseLabel, 0, 3);
        addProductLayout.add(costOfPurchaseField, 1, 3);
        addProductLayout.add(quantityLabel, 0, 4);
        addProductLayout.add(quantityField, 1, 4);
        addProductLayout.add(addButton, 1, 5);

        // Create scene and set it to the stage
        Scene addProductScene = new Scene(addProductLayout);
        addProductStage.setScene(addProductScene);

        // Set modality to APPLICATION_MODAL to block input events to other windows
        addProductStage.initModality(Modality.APPLICATION_MODAL);

        // Show the add product window
        addProductStage.showAndWait();
    }

    private void viewInventory() {
    	// Create a new window for viewing the inventory
        Stage viewInventoryStage = new Stage();
        viewInventoryStage.setTitle("View Inventory");

        TextArea inventoryTextArea = new TextArea();
        inventoryTextArea.setEditable(false);

        // Populate the text area with inventory details
        for (Product product : inventory) {
            inventoryTextArea.appendText(product.toString() + "\n");
        }

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> viewInventoryStage.close());

        VBox viewInventoryLayout = new VBox(10);
        viewInventoryLayout.getChildren().add(inventoryTextArea);
        viewInventoryLayout.getChildren().add(closeButton); // Add button separately
        viewInventoryLayout.setPadding(new Insets(10));
        viewInventoryLayout.setAlignment(Pos.CENTER);

        Scene viewInventoryScene = new Scene(viewInventoryLayout, 400, 300);
        viewInventoryStage.setScene(viewInventoryScene);

        // Set modality to APPLICATION_MODAL to block input events to other windows
        viewInventoryStage.initModality(Modality.APPLICATION_MODAL);

        // Show the view inventory window
        viewInventoryStage.showAndWait();

    }

    private void deleteProduct() {
        // Implement the functionality for deleting a product
        System.out.println("Delete from Inventory button clicked");
        // Replace the print statement with your logic
    }

    private void exit() {
        // Implement the functionality for exiting the program
        System.out.println("Exit button clicked");
        // Replace the print statement with your logic
        System.exit(0);
    }
}
