import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


class Product {
    private String productID;
    private String name;
    private double costOfSale;
    private double costOfPurchase;
    private int quantity;
    private int sales;
    private String date_column;
    private int sales_in_past_6_months;
    public String getIdentifier() {
        return productID;
    }

    public Product(String productID, String name, double costOfSale, double costOfPurchase, int quantity) {
        this.productID = productID;
        this.name = name;
        this.costOfSale = costOfSale;
        this.costOfPurchase = costOfPurchase;
        this.quantity = quantity;
        this.sales = 0; 
        this.date_column = ""; 
        this.sales_in_past_6_months = 0;
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
    
    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getDateColumn() {
        return date_column;
    }

    public void setDateColumn(String dateColumn) {
        this.date_column = dateColumn;
    }

    public int getSalesInPast6Months() {
        return sales_in_past_6_months;
    }

    public void setSalesInPast6Months(int salesInPast6Months) {
        this.sales_in_past_6_months = salesInPast6Months;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", costOfSale=" + costOfSale +
                ", costOfPurchase=" + costOfPurchase +
                ", quantity=" + quantity +
                '}';
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
    
    private static List<Product> inventory = new ArrayList<>();
    
    public static void main(String[] args) {
    	logger.info("Application started.");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> inventory = new ArrayList<>();       
        loadInventoryFromFile(inventory);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveInventoryToFile(inventory)));

        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Sell Item");
            System.out.println("4. Delete from Inventory");
            System.out.println("5. Search by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct(scanner, inventory);
                    break;
                case 2:
                    viewInventory(inventory);
                    break;
                case 3:
                    sellItem(inventory, scanner);
                    break;
                case 4:
                    deleteProduct(inventory, scanner);
                    break;
                case 5:
                    System.out.print("Enter product name to search: ");
                    String searchName = scanner.nextLine();
                    searchByName(searchName, inventory);
                    break;
                case 6:  
                	saveInventoryToFile(inventory);
                    logger.info("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    logger.severe("Invalid choice. Please enter a valid option.");
            }
        }
    }
    
    private static void loadInventoryFromFile(ArrayList<Product> inventory) {
        try (Scanner scanner = new Scanner(new File("inventory.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String productID = parts[0];
                    String name = parts[1];
                    double costOfSale = Double.parseDouble(parts[2]);
                    double costOfPurchase = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);

                    Product product = new Product(productID, name, costOfSale, costOfPurchase, quantity);
                    inventory.add(product);
                } else {
                    System.out.println("Invalid data in inventory file: " + line);
                }
            }
            System.out.println("Inventory loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous inventory found.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load inventory from file.");
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
    
    private static void sellItem(List<Product> inventory, Scanner scanner) {
        System.out.println("Inventory:");
        for (Product product : inventory) {
            System.out.println(product.getProductID() + ": " + product.getName() + " - Quantity: " + product.getQuantity());
        }

        System.out.print("Enter the Product ID to sell: ");
        String productIDToSell = scanner.nextLine();

        // Find the product in the inventory
        for (Product product : inventory) {
            if (product.getProductID().equalsIgnoreCase(productIDToSell)) {
                System.out.print("Enter the quantity to sell: ");
                int quantityToSell = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (quantityToSell <= product.getQuantity()) {
                    // Update sales-related columns in the product
                    product.setSales(product.getSales() + quantityToSell);
                    product.setDateColumn(DateUtils.getCurrentDate());
                    product.setSalesInPast6Months(product.getSalesInPast6Months() + quantityToSell);
                    DatabaseConnector.sellItem(product, quantityToSell);
                    product.setQuantity(product.getQuantity() - quantityToSell);
                    System.out.println("Item sold successfully!");
                    
                } else {
                    System.out.println("Insufficient quantity in inventory.");
                }

                return;
            }
        }

        System.out.println("Product with ID " + productIDToSell + " not found in inventory.");
    }
    private static void searchByName(String name, List<Product> inventory) {
        // Search for the product by name and display details
        boolean found = false;

        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("Product found:");
                System.out.println(product);
                found = true;
                break; // Stop searching once a match is found
            }
        }

        if (!found) {
            System.out.println("Product with name '" + name + "' not found in the inventory.");
        }
    }
    private static void deleteProduct(ArrayList<Product> inventory, Scanner scanner) {
        System.out.print("Enter the Product ID to delete: ");
        String productIDToDelete = scanner.next();

        // Find the product in the inventory
        for (Product product : inventory) {
            if (product.getIdentifier().equals(productIDToDelete)) {
            	DatabaseConnector.deleteProduct(productIDToDelete);
                inventory.remove(product);
                System.out.println("Product deleted successfully.");
                return;
            }
        }

        System.out.println("Product with ID " + productIDToDelete + " not found in inventory.");
    }
    
    private static void saveInventoryToFile(ArrayList<Product> inventory) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("inventory.txt"))) {
            for (Product product : inventory) {
                writer.println(product.getProductID() + "," +
                               product.getName() + "," +
                               product.getCostOfSale() + "," +
                               product.getCostOfPurchase() + "," +
                               product.getQuantity());
            }
            System.out.println("Inventory saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save inventory to file.");
        }
    }
}


