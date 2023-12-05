package ParkingManagementSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * NetServiceToPeripheralDevice class implements the Runnable interface
 * to create a server for communication with peripheral devices.
 */
public class NetServiceToPeripheralDevice implements Runnable {
    private ServerSocket serverSocket;
    final int port;
    final ParkingManagement vehicleProcessor;
    final SQLBasedFinancialManagement financialManager;

    private boolean isRunning;

    /**
     * Constructor for NetServiceToPeripheralDevice.
     *
     * @param port              The port number on which the server will listen.
     * @param vehicleProcessor The ParkingManagement instance for vehicle processing.
     * @param financialManager The FinancialManagement instance for managing financial records.
     */
    public NetServiceToPeripheralDevice(int port, ParkingManagement vehicleProcessor,
                                        SQLBasedFinancialManagement financialManager) {
        this.port = port;
        this.vehicleProcessor = vehicleProcessor;
        this.financialManager = financialManager;
        this.isRunning = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " + port);
            this.serverSocket = serverSocket;
        } catch (IOException e) {
            System.out.println("The " + port + " is occupied.");
        }
    }

    public void stop(){
        isRunning = false;
        // Close the BufferedReader to interrupt the blocking readLine()
    }

    /**
     * Implementation of the run method from the Runnable interface.
     * Waits for client connections and handles them by invoking handleClient.
     */
    public void run() {
        while(isRunning){
            try(Socket clientSocket = serverSocket.accept()){
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Handles the communication with a connected client.
     *
     * @param clientSocket The socket for communication with the client.
     */
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                if(!isRunning){
                    break;
                }
                processMessage(message, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes a message received from the client, which is assumed to be in the format "licensePlate,in/out".
     *
     * @param message The message received from the client.
     * @param writer  The PrintWriter for sending responses to the client.
     */
    private void processMessage(String message, PrintWriter writer) {
        // Assuming the message format is "licensePlate,in/out"
        String[] parts = message.split(",");
        if (parts.length == 2) {
            String licensePlate = parts[0];

            // Process the vehicle entry/exit using the provided functions
            if (parts[1].equals("in")) {
                try {
                    vehicleProcessor.parkVehicle(licensePlate);
                    // Send response to the client
                    writer.println("Success!");
                } catch (Exception e) {
                    writer.println("failure");
                }
            }
            else if(parts[1].equals("out")){
                try {
                    ParkingTicket ticket = vehicleProcessor.exitParking(licensePlate);
                    writer.println("Success!The cost of " + licensePlate + " is " + ticket.getFee());
                    financialManager.addBill(new Bill(ticket));
                } catch (Exception e) {
                    writer.println("failure");
                }
            }else{
                writer.println("failure");
            }
        }else{
            writer.println("failure");
        }
    }
}

