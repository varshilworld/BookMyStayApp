import java.util.*;

/* MAIN CLASS */
public class Username {

    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");
        System.out.println("Room Search\n");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Search and display available rooms
        searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }

    /**
     * Searches and displays available rooms
     */
    public static void searchAvailableRooms(RoomInventory inventory,
                                            Room singleRoom,
                                            Room doubleRoom,
                                            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Single Room
        if (availability.get("SingleRoom") > 0) {
            System.out.println("Single Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("SingleRoom") + "\n");
        }

        // Double Room
        if (availability.get("DoubleRoom") > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("DoubleRoom") + "\n");
        }

        // Suite Room
        if (availability.get("SuiteRoom") > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("SuiteRoom"));
        }
    }
}

/* ABSTRACT CLASS */
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
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: ₹" + pricePerNight);
    }
}

/* ROOM TYPES */
class SingleRoom extends Room {
    public SingleRoom() { super(1, 250, 1500.0); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 400, 2500.0); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 750, 5000.0); }
}

/* INVENTORY CLASS */
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