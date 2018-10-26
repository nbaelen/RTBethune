import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.net.Socket;

public class AnswerThread extends Thread {

    private Socket serverSocket;
    private PrintStream ps;
    private BufferedReader br;

    //Constructeur
    public AnswerThread(Socket pSocket){
        this.serverSocket = pSocket;
        try {
            this.ps = new PrintStream(serverSocket.getOutputStream(), true, "utf-8");
            this.br = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Boolean repeat = true;

            while (repeat == true) {
                //Demande le mot à rechercher
                String request = "Quel mot clé souhaitez-vous rechercher ?";
                this.ps.println(request);
                System.out.println("Serveur > " + request);

                //Récupère la réponse du client
                /*String clientAnswer = this.br.readLine();
                System.out.println("Client > Je souhaite rechercher le mot clé suivant : " + clientAnswer);
                System.out.println("3");

                //Traite la réponse du client
                ArrayList<String> serverAnswer = new ArrayList<String>();
                System.out.println(Server.getSites());
                for (final String URLname : Server.getSites()) {
                    CodeSourceURL URL = new CodeSourceURL(URLname);
                    if (URL.checkContent(clientAnswer) == true) {
                        serverAnswer.add(URLname);
                    }
                }

                //Envoie la réponse au client
                System.out.println("Server > Les sites suivant contiennent le mot clé " + clientAnswer + " : " + serverAnswer);
                ps.println(serverAnswer);
                ps.println("Si vous souhaitez faire une nouvelle recherche, tapez 'y', sinon tapez tout autre caractère");*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}











