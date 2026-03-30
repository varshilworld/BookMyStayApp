import java.util.*;

/**
 * MAIN CLASS - Booking Request Queue & Room Allocation
 */
public class Username {

    public static void main(String[] args) {

        System.out.println("Booking Request Queue");
        System.out.println("Room Allocation Processing\n");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Double"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Allocation service
        RoomAllocationService allocator = new RoomAllocationService();

        // Process FIFO
        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: " +
                    r.getGuestName() + ", Room Type: " + r.getRoomType());

            allocator.allocateRoom(r, inventory);
        }
    }
}

/* =========================
   Reservation
========================= */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* =========================
   Booking Queue (FIFO)
========================= */
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/* =========================
   Inventory
========================= */
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 3);
        availability.put("Suite", 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

/* =========================
   Allocation Service
========================= */
class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    private Map<String, Integer> counters;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
        counters = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();

        // Check availability
        if (inventory.getAvailable(type) <= 0) {
            System.out.println("No rooms available for " + reservation.getGuestName() + "\n");
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Track allocations
        allocatedRoomIds.add(roomId);
        assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);

        // Reduce inventory
        inventory.reduceRoom(type);

        // Print confirmation
        System.out.println("Booking confirmed for Guest: " +
                reservation.getGuestName() + ", Room ID: " + roomId + "\n");
    }

    private String generateRoomId(String roomType) {
        int count = counters.getOrDefault(roomType, 0) + 1;
        counters.put(roomType, count);
        return roomType + "-" + count;
    }
}