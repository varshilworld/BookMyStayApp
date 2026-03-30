import java.util.*;

// Shared inventory class with synchronized methods
class Inventory {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public Inventory() {
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 2);
        roomInventory.put("Standard", 2);
    }

    // Thread-safe allocation
    public synchronized boolean allocateRoom(String roomType, String guestName) {
        int count = roomInventory.getOrDefault(roomType, 0);
        if (count > 0) {
            roomInventory.put(roomType, count - 1);
            System.out.println("Booking successful for " + guestName + " in " + roomType);
            return true;
        } else {
            System.out.println("Booking failed for " + guestName + ": No " + roomType + " rooms left.");
            return false;
        }
    }

    public synchronized void displayInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

// Represents a booking request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Processor that runs in a thread
class BookingProcessor implements Runnable {
    private Inventory inventory;
    private BookingRequest request;

    public BookingProcessor(Inventory inventory, BookingRequest request) {
        this.inventory = inventory;
        this.request = request;
    }

    @Override
    public void run() {
        inventory.allocateRoom(request.roomType, request.guestName);
    }
}

// Main class must match the filename: Username.java
public class Username {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        System.out.print("Enter number of concurrent booking requests: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        List<Thread> threads = new ArrayList<>();

        // Collect booking requests
        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for booking request " + i + ":");
            System.out.print("Guest Name: ");
            String guest = scanner.nextLine();

            System.out.print("Room Type (Deluxe / Suite / Standard): ");
            String roomType = scanner.nextLine();

            BookingRequest request = new BookingRequest(guest, roomType);
            Thread t = new Thread(new BookingProcessor(inventory, request));
            threads.add(t);
        }

        // Start all threads simultaneously
        for (Thread t : threads) {
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        // Display final inventory state
        System.out.println("\n=== Final Inventory State ===");
        inventory.displayInventory();

        scanner.close();
    }
}