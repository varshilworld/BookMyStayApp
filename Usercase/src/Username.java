import java.util.*;

// Represents a confirmed reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
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

// Stores booking history in insertion order
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getHistory() {
        return Collections.unmodifiableList(history);
    }
}

// Generates reports from booking history
class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void generateSummaryReport() {
        List<Reservation> reservations = bookingHistory.getHistory();
        System.out.println("\n=== Booking Summary Report ===");
        System.out.println("Total bookings: " + reservations.size());
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

// Main class must match the filename: Username.java
public class Username {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingHistory history = new BookingHistory();

        System.out.print("Enter number of bookings to confirm: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 1; i <= n; i++) {
            System.out.println("\nEnter details for booking " + i + ":");
            System.out.print("Reservation ID: ");
            String id = scanner.nextLine();

            System.out.print("Guest Name: ");
            String guest = scanner.nextLine();

            System.out.print("Room Type: ");
            String room = scanner.nextLine();

            Reservation reservation = new Reservation(id, guest, room);
            history.addReservation(reservation);
        }

        // Admin requests report
        BookingReportService reportService = new BookingReportService(history);
        reportService.generateSummaryReport();

        scanner.close();
    }
}