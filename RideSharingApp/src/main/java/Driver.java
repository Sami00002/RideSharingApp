class Driver implements IDriver {
    private int id;
    private String name;
    private boolean available;
    private double distanceToLocation;
    private double chargeLevel;

    public Driver(int id, String name, double distanceToLocation, double chargeLevel) {
        this.id = id;
        this.name = name;
        this.available = true;
        this.distanceToLocation = distanceToLocation;
        this.chargeLevel = chargeLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getDistanceToLocation(String location) {
        return distanceToLocation;
    }

    public double getChargeLevel() {
        return chargeLevel;
    }

    public void receiveRequest(int clientId, String startPoint, String endPoint) {
        System.out.println("Received ride request from client " + clientId +
                " for the journey starting from " + startPoint + " to " + endPoint);
        sendResponse(true, null, clientId);
    }

    public void sendResponse(boolean isAccepted, IServer server, int clientId) {
        if (server != null) {
            server.receiveResponseFromDriver(clientId, isAccepted);
            this.available = !isAccepted;
        }
    }
}