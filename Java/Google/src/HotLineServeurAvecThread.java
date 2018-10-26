import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Dav
 */
public class HotLineServeurAvecThread {

  public static void main(String[] args) {
    try (ServerSocket socketEcoute = new ServerSocket(53200)) {
      System.out.println(".... Serveur à l'écoute ....");
      while (true) {
        Socket socketService = socketEcoute.accept();
        ClientThread client = new ClientThread(socketService);
        client.start();
      }
    } catch (IOException ex) {
      System.out.println("Connexion impossible.");
    }
  }
}

class ClientThread extends Thread {

  private Socket ss;      // Socket de service du client
  private PrintStream ps; // PrintStream du client
  private BufferedReader br; // BufferedReader du client
  
  public ClientThread(Socket ss) {
    this.ss = ss;
  }

  @Override
  public void run() {
    try {
  
  }
}
