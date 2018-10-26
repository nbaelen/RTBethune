import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

class ClientThread extends Thread {

    private Socket ss;      // Socket de service du client
    private PrintStream ps; // PrintStream du client
    private BufferedReader br; // BufferedReader du client

    public ClientThread(Socket ss) {
        this.ss = ss;
        try {
            this.ps = new PrintStream(ss.getOutputStream(), true, "utf-8");
            this.br = new BufferedReader(new InputStreamReader(ss.getInputStream(), "utf-8"));
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
                String clientAnswer = this.br.readLine();
                System.out.println("Client > Je souhaite rechercher le mot clé suivant : " + clientAnswer);

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
                ps.println("Si vous souhaitez faire une nouvelle recherche, tapez 'y', sinon tapez tout autre caractère");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}