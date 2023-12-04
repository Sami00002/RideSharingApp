import java.util.HashMap;
import java.util.Map;

class Server implements IServer {
    private Map<Integer, IDriver> drivers = new HashMap<>();
    private IDriver selectedOption;

    public void registerDriver(IDriver driver) {
        if (!drivers.containsKey(driver.getId())) {
            drivers.put(driver.getId(), driver);
        }
    }

    public void unregisterDriver(IDriver driver) {
        drivers.remove(driver.getId());
    }

    public void sendOptionsRequest(int clientId, String startPoint, String endPoint, String mode) {
        if ("car".equalsIgnoreCase(mode)) {
            sendCarOptions(clientId, startPoint, endPoint);
        } else if ("scooter".equalsIgnoreCase(mode)) {
            sendScooterOptions(clientId, startPoint, endPoint);
        } else {
            System.out.println("Invalid mode selected.");
        }
    }

    private void sendCarOptions(int clientId, String startPoint, String endPoint) {
        int count = 0;
        double shortestDistance = Double.MAX_VALUE;
        IDriver shortestDriver = null;

        for (IDriver driver : drivers.values()) {
            if (count >= 3) {
                break;
            }
            if (driver.isAvailable()) {
                double distance = driver.getDistanceToLocation(startPoint);
                System.out.println("Sending car option to client " + clientId +
                        ": Car " + driver.getId() + " - Distance: " + distance);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    shortestDriver = driver;
                }

                count++;
            }
        }

        if (shortestDriver != null) {
            chooseOption(clientId, shortestDriver.getId());
        }
    }

    public void sendScooterOptions(int clientId, String startPoint, String endPoint) {
        double shortestDistance = Double.MAX_VALUE;
        IDriver shortestDriver = null;

        for (IDriver driver : drivers.values()) {
            if (driver.isAvailable()) {
                double distance = driver.getDistanceToLocation(startPoint);
                double maxDistance = driver.getChargeLevel();
                if (maxDistance >= calculateDistance(startPoint, endPoint) / 2.0) {
                    // Send scooter information to the client
                    System.out.println("Sending scooter option to client " + clientId +
                            ": Scooter " + driver.getId() + " - Charge Level: " + driver.getChargeLevel());

                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        shortestDriver = driver;
                    }
                }
            }
        }

        if (shortestDriver != null) {
            chooseOption(clientId, shortestDriver.getId());
        }
    }
    private double calculateDistance(String startPoint, String endPoint) {
        return 100.0;
    }

    public void chooseOption(int clientId, int optionId) {
        selectedOption = drivers.get(optionId);
        if (selectedOption != null && selectedOption.isAvailable()) {
            sendRequestToDriver(clientId, optionId);
        } else {
            System.out.println("Invalid option selection or option not available.");
        }
    }

    public void sendRequestToDriver(int clientId, int driverId) {
        IDriver chosenDriver = drivers.get(driverId);
        if (chosenDriver != null) {
            chosenDriver.receiveRequest(clientId, "Start Point", "End Point");
        }
    }

    public void receiveResponseFromDriver(int clientId, boolean isAccepted) {
        System.out.println("Received response from driver to client " + clientId +
                ": " + (isAccepted ? "Accepted" : "Rejected"));

        if (selectedOption != null) {
            System.out.println("Selected Option: " + selectedOption.getName());
        }
    }
}
