import java.util.*;

// Represents an optional service (e.g., breakfast, spa, airport pickup)
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// Manages mapping between reservations and their selected services
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Attach services to a reservation
    public void addServices(String reservationId, List<Service> services) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).addAll(services);
    }

    // Retrieve services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, Collections.emptyList());
    }

    // Calculate total additional cost
    public double calculateAdditionalCost(String reservationId) {
        return getServices(reservationId).stream()
                .mapToDouble(Service::getCost)
                .sum();
    }
}

// Main class must match the filename: Username.java
public class Username {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs
        String reservation1 = "RES123";
        String reservation2 = "RES456";

        // Example services
        Service breakfast = new Service("Breakfast", 500.0);
        Service spa = new Service("Spa Access", 1500.0);
        Service airportPickup = new Service("Airport Pickup", 800.0);

        // Guest selects services
        manager.addServices(reservation1, Arrays.asList(breakfast, spa));
        manager.addServices(reservation2, Arrays.asList(airportPickup));

        // Display results
        System.out.println("Reservation " + reservation1 + " services: " + manager.getServices(reservation1));
        System.out.println("Additional cost: ₹" + manager.calculateAdditionalCost(reservation1));

        System.out.println("Reservation " + reservation2 + " services: " + manager.getServices(reservation2));
        System.out.println("Additional cost: ₹" + manager.calculateAdditionalCost(reservation2));
    }
}