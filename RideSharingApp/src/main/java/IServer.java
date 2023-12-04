interface IServer {
    void registerDriver(IDriver driver);
    void unregisterDriver(IDriver driver);
    void sendOptionsRequest(int clientId, String startPoint, String endPoint, String mode);
    void chooseOption(int clientId, int optionId);
    void sendRequestToDriver(int clientId, int driverId);
    void receiveResponseFromDriver(int clientId, boolean isAccepted);
    void sendScooterOptions(int clientId, String startPoint, String endPoint);

}