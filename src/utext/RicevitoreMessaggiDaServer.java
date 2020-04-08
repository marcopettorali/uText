package utext;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;

public class RicevitoreMessaggiDaServer extends Thread{//01
    Socket socket;
    ObjectInputStream ois;
    DataInputStream dis;
    boolean fineEsecuzione = false;
    
    public RicevitoreMessaggiDaServer(Socket sock){
        socket = sock;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void riceviMessaggio(){//02
        Messaggio messaggio;
        
        try {
            messaggio = (Messaggio) ois.readObject();
        
            ArchiviazioneEStatisticheMessaggi.salvaMessaggio(messaggio.getTimestampInvio(), messaggio.getMittente(), messaggio.getDestinatario(), messaggio.getTesto()); //02.a
            ListaAmiciSelezionati.impostaNotifica(messaggio.getMittente());//02.b

            Platform.runLater(() -> InserimentoMessaggi.visualizzaNotifica(messaggio.getMittente()));//02.c
            System.out.println("Ricevuto nuovo messaggio da " + messaggio.getMittente()); 
        }catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void riceviRispostaAmicoOnline(){//03
        ComunicazioneAmicoOnline comunicazione;
        try {
            comunicazione = (ComunicazioneAmicoOnline) ois.readObject();
            ListaAmiciSelezionati.impostaAmicoOnline(comunicazione.getUsername(), comunicazione.getOnline());
            if(!comunicazione.getUsername().equals(ListaAmiciSelezionati.getAmicoSelezionato())){//03.a
                Platform.runLater(() -> {
                    if(comunicazione.getOnline()){
                        InserimentoMessaggi.visualizzaAmicoOnline(comunicazione.getUsername());
                    }else{
                        InserimentoMessaggi.visualizzaAmicoOffline(comunicazione.getUsername());
                    }
                });
            }
            System.out.println("Ricevuta risposta da server: " + comunicazione.getUsername() + " e' online: " + comunicazione.getOnline());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean controllaFineEsecuzione(){
        return fineEsecuzione;
    }
    
    public void esci(){
        fineEsecuzione = true;
        try {
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public void run(){
        int i = 0;
        while(!fineEsecuzione){//04
            String comando = null;
            
            try {
                comando = dis.readUTF();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
            switch(comando){
                case "MESSAGE":
                    riceviMessaggio();
                    break;
                case "ONLINE ANSWER":
                    riceviRispostaAmicoOnline();
                    break;
            }
        }
    }
}

/*

NOTE:
(01) Classe che, estendendo la classe Thread, riceve in modo asincrono messaggi e comandi dal server. Esegue le azioni necessarie in base al tipo di comando ricevuto.

(02) Metodo che preleva il messaggio inviato dal server e destinato all'utente:
     (02.a) Archivia il messaggio ricevuto nel database locale
     (02.b) Imposta una nuova notifica proveniente dallo username mittente nella ListaAmiciSelezionati
     (02.c) Suggerisce alla GUI di visualizzare una nuova notifica dal mittente del messaggio. Il metodo Platform.runLater() serve per una semplice sincronizzazione 
            con il main thread, per evitare che l'aggiornamento della GUI non avvenga in modo concorrente, ma schedulato.
     
(03) Metodo che preleva un messaggio di tipo ComunicazioneAmicoOnline, ne preleva lo username e lo stato online/offline e aggiorna opportunamente le strutture dati
     (03.a) Se si riceve un'informazione riguardante l'amico selezionato, non si aggiorna la GUI, in modo tale che l'amico selezionato resti sempre evidenziato in blu.

(04) Continuamente, il ricevitore cerca messaggi dal server. In base al tipo del comando ricevuto, invoca il metodo specifico che funge da handler per quella richiesta. 
     

*/