package utext;

import java.io.*;
import java.net.*;

public abstract class ConnessioneAServer{//01
    private static Socket socket;
    private static String username;
    private static RicevitoreMessaggiDaServer ricevitore;
    private static ObjectOutputStream oos;
    private static DataOutputStream dos;
    
    public static void connessione(){//02
        System.out.print("Mi sto connettendo al server...");
        username = ParametriConfigurazione.impostazioni.usernameUtente;
        
        try{
            socket = new Socket(ParametriConfigurazione.network.indirizzoServer, ParametriConfigurazione.network.portaServer);
            oos = new ObjectOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }   
        
        ricevitore = new RicevitoreMessaggiDaServer(socket);//02.a
        ricevitore.setDaemon(true);
        ricevitore.start();
        
        try{
            dos.writeUTF("LOGIN");//02.b
            dos.writeUTF(username);
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }   
        System.out.println("fatto");
    }
    
    public static void richiestaAmicoOnline(String user){//03
        System.out.println("Richiesta al server: stato online di " + user);
        try{
            dos.writeUTF("ONLINE REQUEST");
            dos.writeUTF(user);
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }   
    }
    
    public static void inviaMessaggio(String destinatario, String testo){//04
        System.out.println("Richiesta al server: invio messaggio \""+ testo +"\" a " + destinatario);
        Messaggio messaggio = new Messaggio(username, destinatario, testo);
        
        try{
            dos.writeUTF("MESSAGE");
            oos.writeObject(messaggio);
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
    
    public static void disconnessione(){//05
        System.out.println("Disconnessione dal server...");
        try{
            dos.writeUTF("LOGOUT");
            dos.close();
            oos.close();
            socket.close();
            ricevitore.esci();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        } 
    }
}

/*

NOTE:
(01) Classe astratta che contiene i metodi per comunicare con il server dell'applicazione. Il dialogo con il server si basa su un semplicissimo protocollo, in cui client e server
     inviano un primo messaggio contenente il tipo di un comando, seguito dalle informazioni richieste dal client.
     I comandi sono:
        TIPO            |INFORMAZIONI                           |DESCRIZIONE
        ----------------|---------------------------------------|-------------------------------------------------------------------------------------------------------------------
        LOGIN           | (String) usernameUtente               |Il client comunica al server che e' raggiungibile tramite lo username specificato
        ONLINE REQUEST  | (String) usernameAmico                |Il client chiede al server se l'amico specificato e' attualmente online
        ONLINE ANSWER   | (ComunicazioneAmicoOnline)risposta    |Il server invia al client un messaggio di tipo ComunicazioneAmicoOnline che indica se un utente e' online e offline.
        MESSAGE         | (Messaggio) messaggio                 |Il client invia un messaggio al server, che lo recapita all'utente destinatario se questi e' online, 
                        |                                       |lo bufferizza se il destinatario e' offline
        LOGOUT          |               /                       |Il client comunica al server che non sara' piu' raggiungibile al suo username

(02) Metodo statico che inizializza la connessione con il server
     (04.a) Viene creato un thread RicevitoreMessaggiDaServer per la ricezione asincrona dei messaggi. Il thread e' daemon per evitare che alla chiusura della GUI, questo thread resti attivo.
     (04.b) Viene inviato il messaggio di tipo LOGIN, seguito dallo username recuperato dai parametri di configurazione in XML.

(03) Metodo statico che chiede al server se l'amico il cui username e' passato come parametro e' attualmente online.

(04) Metodo statico che crea un messaggio a partire dalle informazioni passate per parametro e lo invia al server. Notare che il timestamp di invio del messaggio e' quello dell'invio effettivo,
     non quello del click sul bottone InviaMessaggio della GUI.
     
(05) Metodo statico che comunica al server che questo client non sara' piu' raggiungibile.


*/