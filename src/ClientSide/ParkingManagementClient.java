package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ParkingManagementClient {

    public static void main(String[] args) {
        ParkingManagementClient client = new ParkingManagementClient();
        client.startClient("localhost", 9040);
    }

    public void startClient(String serverAddress, int serverPort) {
        try (
                Socket socket = new Socket(serverAddress, serverPort);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to server. Enter vehicle information (licensePlate,in/out):");

            while (true) {
                String userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                // Send vehicle information to the server
                writer.println(userInput);

                // Receive and display the server's response
                String response = reader.readLine();
                if(response==null){
                    break;
                }
                System.out.println("Server response: \n"+ response+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

