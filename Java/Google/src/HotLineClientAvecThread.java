import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class HotLineClientAvecThread {
  public static void main(String[] args) throws IOException {
    try (Socket s = new Socket("localhost", 53200)) {
      System.out.println("Connexion établie");
      BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
      PrintStream ps = new PrintStream(s.getOutputStream(), true, "utf-8");
      
      String msgClient="";
      Scanner sc = new Scanner(System.in);
      do{
        System.out.println("Serveur > "+br.readLine());
        System.out.print("Client > ");
        msgClient = sc.nextLine();
        ps.println(msgClient);
      }while(!msgClient.equals("."));
      
      s.close();
    } catch (IOException e) {
      System.err.println("Connexion au serveur impossible");
    }
  }
}
