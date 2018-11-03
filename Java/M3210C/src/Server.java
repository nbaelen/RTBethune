import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket listenerSocket = new ServerSocket(50000);
        getSites();
        while (true) {
            System.out.println("... En attente de client ...");
            try (Socket serviceSocket = listenerSocket.accept()) {
                //Notifie la connection
                System.out.println(" Un client s'est connecté (" + serviceSocket.getRemoteSocketAddress() + ")");

                //Création des objets
                PrintStream ps = new PrintStream(serviceSocket.getOutputStream(), true, "utf-8");
                BufferedReader br = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream(), "utf-8"));
                Boolean repeat = true;

                while (repeat == true) {
                    //Demande le mot à rechercher
                    String request = "Quel mot clé souhaitez-vous rechercher ?";
                    ps.println(request);
                    System.out.println("Serveur > " + request);

                    //Récupère la réponse du client
                    String clientAnswer = br.readLine();
                    System.out.println("Client > Je souhaite rechercher le mot clé suivant : " + clientAnswer);

                    //Traite la réponse du client
                    ArrayList<String> serverAnswer = new ArrayList<String>();
                    for (final String URLname : getSites()) {
                        CodeSourceURL URL = new CodeSourceURL(URLname);
                        if (URL.checkContent(clientAnswer) == true) {
                            serverAnswer.add(URLname);
                        }
                    }

                    //Envoie la réponse au client
                    System.out.println("Server > Les sites suivant contiennent le mot clé " + clientAnswer + " : " + serverAnswer);
                    ps.println(serverAnswer);
                    ps.println("Si vous souhaitez faire une nouvelle recherche, tapez 'y', sinon tapez tout autre caractère");

                    if (!(br.readLine().equals("y"))) {
                        repeat = false;
                    }
                }
                System.out.println("Client > Déconnexion du serveur");
                serviceSocket.close();
            } catch (IOException e) {
                System.err.println("Connexion impossible");
            }
        }
    }

    public static ArrayList<String> getSites() throws IOException {
        // Construct BufferedReader from FileReader
        BufferedReader br = new BufferedReader(new FileReader("sites.txt"));

        ArrayList<String> sites = new ArrayList<String>();
        String line = null;
        while ((line = br.readLine()) != null) {
            sites.add(line);
        }

        br.close();

        return sites;
    }
}

