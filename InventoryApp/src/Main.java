import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

class Product {
    private String productID;
    private String name;
    private double costOfSale;
    private double costOfPurchase;
    private int quantity;

    public Product(String productID, String name, double costOfSale, double costOfPurchase, int quantity) {
        this.productID = productID;
        this.name = name;
        this.costOfSale = costOfSale;
        this.costOfPurchase = costOfPurchase;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getCostOfSale() {
        return costOfSale;
    }

    public double getCostOfPurchase() {
        return costOfPurchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + productID +
                "\nName: " + name +
                "\nCost of Sale: $" + costOfSale +
                "\nCost of Purchase: $" + costOfPurchase +
                "\nQuantity: " + quantity;
    }
}

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

    static {
        // Configure the LogManager to read the logging.properties file
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("/logging.properties")
            );

            // Set the global logging level (optional)
            Logger.getLogger("").setLevel(Level.ALL);

            // Get the handlers attached to the root logger
            Handler[] handlers = Logger.getLogger("").getHandlers();

            // Set the level and formatter for each handler
            for (Handler handler : handlers) {
                handler.setLevel(Level.ALL);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	logger.info("Application started.");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> inventory = new ArrayList<>();

        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct(scanner, inventory);
                    break;
                case 2:
                    viewInventory(inventory);
                    break;
                case 3:
                	logger.info("Exiting program. Goodbye!");
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                	logger.severe("Invalid choice. Please enter a valid option.");
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addProduct(Scanner scanner, ArrayList<Product> inventory) {
    	logger.info("Adding product process started.");
        System.out.print("Enter Product ID (type '0' to exit): ");
        String productID = scanner.next();

        if (productID.equals("0")) {
            return; // Exit product addition process
        }

        System.out.print("Enter Product Name (type '0' to exit): ");
        String name = scanner.next();

        if (name.equals("0")) {
            return; // Exit product addition process
        }

        System.out.print("Enter Cost of Sale (type '0' to exit): $");
        double costOfSale;
        try {
            costOfSale = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear the input buffer
            return; // Exit product addition process
        }

        if (costOfSale == 0) {
            return; // Exit product addition process
        }

        System.out.print("Enter Cost of Purchase (type '0' to exit): $");
        double costOfPurchase;
        try {
            costOfPurchase = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear the input buffer
            return; // Exit product addition process
        }

        if (costOfPurchase == 0) {
            return; // Exit product addition process
        }

        System.out.print("Enter Quantity (type '0' to exit): ");
        int quantity;
        try {
            quantity = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear the input buffer
            return; // Exit product addition process
        }

        if (quantity == 0) {
            return; // Exit product addition process
        }

        Product newProduct = new Product(productID, name, costOfSale, costOfPurchase, quantity);
        inventory.add(newProduct);
        // Add the product to the database
        DatabaseConnector.addProduct(newProduct);
        logger.info("Product added successfully: " + newProduct);

        System.out.println("Product added successfully!");
    }

    private static void viewInventory(ArrayList<Product> inventory) {
    	logger.info("Viewing inventory.");
        if (inventory.isEmpty()) {
        	logger.info("Inventory is empty.");
            System.out.println("Inventory is empty.");
        } else {
            for (Product product : inventory) {
                System.out.println(product);
                System.out.println("------------------------");
            }
        }
    }
}