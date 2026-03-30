import java.util.*;

/**
 * MAIN CLASS
 */
public class Username {

    public static void main(String[] args) {

        System.out.println("Hotel Room Initialization\n");
        System.out.println("Hotel Room Inventory Status\n");

        RoomInventory inventory = new RoomInventory();

        // Create objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display details
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: 5\n");
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("SingleRoom") + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: 3\n");
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("DoubleRoom") + "\n");

        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: 2");
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("SuiteRoom"));
    }
}

/**
 * ABSTRACT CLASS - Room
 */
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sq ft");
        System.out.println("Price per night: ₹" + pricePerNight);
    }
}

/**
 * SingleRoom
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * DoubleRoom
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * SuiteRoom
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * INVENTORY CLASS
 */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("SingleRoom", 5);
        roomAvailability.put("DoubleRoom", 3);
        roomAvailability.put("SuiteRoom", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}