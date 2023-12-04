interface IDriver {
    int getId();
    String getName();
    boolean isAvailable();
    double getDistanceToLocation(String location);
    double getChargeLevel();
    void receiveRequest(int clientId, String startPoint, String endPoint);
    void sendResponse(boolean isAccepted, IServer server, int clientId);
}