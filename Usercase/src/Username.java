import java.io.*;
import java.util.*;

// Reservation must be Serializable to persist
class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "ID='" + reservationId + '\'' +
                ", Guest='" + guestName + '\'' +
                ", RoomType='" + roomType + '\'' +
                '}';
    }
}

// Inventory must also be Serializable
class Inventory implements Serializable {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public Inventory() {
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 2);
        roomInventory.put("Standard", 2);
    }

    public boolean allocateRoom(String roomType) {
        int count = roomInventory.getOrDefault(roomType, 0);
        if (count > 0) {
            roomInventory.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

// Persistence service for saving and loading state
class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public static void saveState(List<Reservation> reservations, Inventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(reservations);
            oos.writeObject(inventory);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Object[] loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Reservation> reservations = (List<Reservation>) ois.readObject();
            Inventory inventory = (Inventory) ois.readObject();
            System.out.println("System state restored successfully.");
            return new Object[]{reservations, inventory};
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return new Object[]{new ArrayList<Reservation>(), new Inventory()};
        }
    }
}

// Main class must match the filename: Username.java
public class Username {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load previous state if available
        Object[] state = PersistenceService.loadState();
        List<Reservation> reservations = (List<Reservation>) state[0];
        Inventory inventory = (Inventory) state[1];

        System.out.print("Enter number of bookings to confirm: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for booking " + i + ":");
            System.out.print("Reservation ID: ");
            String id = scanner.nextLine();

            System.out.print("Guest Name: ");
            String guest = scanner.nextLine();

            System.out.print("Room Type (Deluxe / Suite / Standard): ");
            String roomType = scanner.nextLine();

            if (inventory.allocateRoom(roomType)) {
                Reservation reservation = new Reservation(id, guest, roomType);
                reservations.add(reservation);
                System.out.println("Booking confirmed: " + reservation);
            } else {
                System.out.println("Booking failed: No rooms available for " + roomType);
            }
        }

        // Display current state
        System.out.println("\n=== Current Reservations ===");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
        inventory.displayInventory();

        // Save state before shutdown
        PersistenceService.saveState(reservations, inventory);

        scanner.close();
    }
}