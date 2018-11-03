import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerAvecThread {
  public static void main(String[] args) {
    try (ServerSocket socketEcoute = new ServerSocket(50000)) {
      System.out.println(".... Serveur à l'écoute ....");
      while (true) {
        Socket socketService = socketEcoute.accept();
        ServerThread client = new ServerThread(socketService);
        client.start();
      }
    } catch (IOException ex) {
      System.out.println("Connexion impossible.");
    }
  }
}

