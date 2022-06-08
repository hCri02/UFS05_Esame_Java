package org.example;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTCPHandler {

    private Socket clientSocket;
    private static BufferedReader in = null;
    private static PrintWriter out = null;

    public ClientTCPHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {
        in = allocateReader(clientSocket);
        out = allocateWriter(clientSocket);
        buildList();
        handleInput();
    }

    public void handleInput() {
        String userInput;
        try {
            out.println("Digita un opzione tra le seguenti:\n");
            out.println(" - more_expensive");
            out.println(" - all");
            out.println(" - all_sorted\n");

            while ((userInput = in.readLine()) != null) {
                out.println(process(userInput));
                out.println("\n");
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    static ArrayList<Car> cars = new ArrayList<>();
    static void buildList() {
        cars.add(new Car(123, "bmw", 3594.9));
        cars.add(new Car(3634, "audi", 38346.9));
        cars.add(new Car(135, "ferrari", 130000));
    }

    public String process(String input) {

        Gson gson = new Gson();
        String result;

        if (input.equals("more_expensive")) {
            sortPrice();
            result = gson.toJson(cars);
            return result;

        } else if (input.equals("all")) {
            result = gson.toJson(cars);
            return result;

        } else if (input.equals("all_sorted")) {
            sortBrand();
            result = gson.toJson(cars);
            return result;
        } else {
            result = "Opzione non valida!";
            return result;
        }
    }

    private BufferedReader allocateReader(Socket clientSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed" + e);
            return null;
        } return in;
    }

    private PrintWriter allocateWriter(Socket clientSocket) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return out;
    }

    private void sortPrice() {
        cars.sort((Car car1, Car car2) -> {
            if (car1.getPrice() > car2.getPrice())
                return 1;
            if (car1.getPrice() < car2.getPrice())
                return -1;
            return 0;
        });
    }

    private void sortBrand() {
        cars.sort((Car car1, Car car2) -> {
            return car1.getBrand().compareTo(car2.getBrand());
        });
    }

}
