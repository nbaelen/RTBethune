import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

class ServerThread extends Thread {

    private Socket ss;      // Socket de service du client
    private PrintStream ps; // PrintStream du client
    private BufferedReader br; // BufferedReader du client

    public ServerThread (Socket ss) {
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
            do {
                String answer = this.askWordToClient();
                this.sendInformationToClient(this.searchWord(answer));
            } while (wantToRepeat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String askWordToClient() throws IOException {
        //Demande le mot à rechercher au client
        String request = "Quel mot souhaitez-vous rechercher ?";
        this.ps.println(request);
        System.out.println("Server > Client " + ServerThread.activeCount() + " : " + request);

        //Récupère la réponse du client
        String clientAnswer = this.br.readLine();
        System.out.println("Client " + ServerThread.activeCount() + " > Server : Je souhaite rechercher le mot clé suivant : " + clientAnswer);

        //Retourne la réponse du client
        return clientAnswer;
    }

    private String searchWord(String pWord) throws IOException {
        ArrayList<String> serverAnswer = new ArrayList<String>();
        for (final String URLname : Server.getSites()) {
            CodeSourceURL URL = new CodeSourceURL(URLname);
            if (URL.checkContent(pWord) == true) {
                serverAnswer.add(URLname);
            }
        }
        String answerString = "Server > Client " + ServerThread.activeCount() + " : " + serverAnswer;
        System.out.println(answerString);
        return answerString;
    }

    private void sendInformationToClient(String pMessage) {
        this.ps.println(pMessage);
    }

    private boolean wantToRepeat() throws IOException {
        String message = "Server > Client " + ServerThread.activeCount() + " : Si vous souhaitez faire une nouvelle recherche, tapez 'y', sinon tapez tout autre caractère";
        this.sendInformationToClient(message);
        System.out.println(message);
        return this.br.readLine().equals("y");
    }
}