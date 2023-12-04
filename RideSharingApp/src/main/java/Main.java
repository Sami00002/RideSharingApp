public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Driver car1 = new Driver(1, "Car1", 50.0, 0.0);
        Driver car2 = new Driver(2, "Car2", 8.0, 0.0);
        Driver car3 = new Driver(3, "Car3", 10.0, 0.0);
        Driver scooter1 = new Driver(4, "Scooter1", 10.0, 50.0);
        Driver scooter2 = new Driver(5, "Scooter2", 6.0, 50.0);

        server.registerDriver(car1);
        server.registerDriver(car2);
        server.registerDriver(car3);
        server.registerDriver(scooter1);
        server.registerDriver(scooter2);

        int clientId = 1;
        String startPoint = "ClientStartPoint";
        String endPoint = "ClientEndPoint";
        String mode = "car";
        server.sendOptionsRequest(clientId, startPoint, endPoint, mode);

        car1.sendResponse(true, server, clientId);

        mode = "scooter";
        server.sendOptionsRequest(clientId, startPoint, endPoint, mode);

        scooter1.sendResponse(true, server, clientId);
    }
}