import java.util.*;

// Custom exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Represents a reservation
class Reservation {
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
                ", Room='" + roomType + '\'' +
                '}';
    }
}

// Validator class
class BookingValidator {
    private static final Set<String> VALID_ROOM_TYPES =
            new HashSet<>(Arrays.asList("Deluxe", "Suite", "Standard"));

    public static void validate(String reservationId, String guestName, String roomType)
            throws InvalidBookingException {

        if (reservationId == null || reservationId.trim().isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType +
                    ". Valid options are: " + VALID_ROOM_TYPES);
        }
    }
}

// Main class must match the filename: Username.java
public class Username {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Reservation> confirmedBookings = new ArrayList<>();

        System.out.print("Enter number of bookings to confirm: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for booking " + i + ":");
            System.out.print("Reservation ID: ");
            String id = scanner.nextLine();

            System.out.print("Guest Name: ");
            String guest = scanner.nextLine();

            System.out.print("Room Type (Deluxe / Suite / Standard): ");
            String room = scanner.nextLine();

            try {
                // Validate input before creating reservation
                BookingValidator.validate(id, guest, room);

                Reservation reservation = new Reservation(id, guest, room);
                confirmedBookings.add(reservation);
                System.out.println("Booking confirmed: " + reservation);

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        // Display all confirmed bookings
        System.out.println("\n=== Confirmed Bookings ===");
        for (Reservation r : confirmedBookings) {
            System.out.println(r);
        }

        scanner.close();
    }
}