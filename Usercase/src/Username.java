import java.util.*;

// Represents a reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public boolean isCancelled() { return cancelled; }

    public void cancel() { this.cancelled = true; }

    @Override
    public String toString() {
        return "Reservation{" +
                "ID='" + reservationId + '\'' +
                ", Guest='" + guestName + '\'' +
                ", RoomType='" + roomType + '\'' +
                ", RoomID='" + roomId + '\'' +
                ", Cancelled=" + cancelled +
                '}';
    }
}

// Manages inventory counts
class Inventory {
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

    public void restoreRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

// Handles cancellations and rollback
class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();
    private Inventory inventory;
    private Map<String, Reservation> reservations;

    public CancellationService(Inventory inventory, Map<String, Reservation> reservations) {
        this.inventory = inventory;
        this.reservations = reservations;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }
        if (reservation.isCancelled()) {
            System.out.println("Cancellation failed: Reservation already cancelled.");
            return;
        }

        // Perform rollback
        reservation.cancel();
        rollbackStack.push(reservation.getRoomId());
        inventory.restoreRoom(reservation.getRoomType());

        System.out.println("Cancellation successful. Room " + reservation.getRoomId() +
                " released back to inventory.");
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack (released rooms): " + rollbackStack);
    }
}

// Main class must match the filename: Username.java
public class Username {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();
        Map<String, Reservation> reservations = new HashMap<>();
        CancellationService cancellationService = new CancellationService(inventory, reservations);

        System.out.print("Enter number of bookings to confirm: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        // Confirm bookings
        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for booking " + i + ":");
            System.out.print("Reservation ID: ");
            String id = scanner.nextLine();

            System.out.print("Guest Name: ");
            String guest = scanner.nextLine();

            System.out.print("Room Type (Deluxe / Suite / Standard): ");
            String roomType = scanner.nextLine();

            if (inventory.allocateRoom(roomType)) {
                String roomId = roomType.substring(0, 3).toUpperCase() + i; // simple room ID
                Reservation reservation = new Reservation(id, guest, roomType, roomId);
                reservations.put(id, reservation);
                System.out.println("Booking confirmed: " + reservation);
            } else {
                System.out.println("Booking failed: No rooms available for " + roomType);
            }
        }

        inventory.displayInventory();

        // Cancellation flow
        System.out.print("\nEnter Reservation ID to cancel: ");
        String cancelId = scanner.nextLine();
        cancellationService.cancelReservation(cancelId);

        // Show rollback stack and inventory after cancellation
        cancellationService.displayRollbackStack();
        inventory.displayInventory();

        // Show all reservations
        System.out.println("\n=== Reservations State ===");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }

        scanner.close();
    }
}