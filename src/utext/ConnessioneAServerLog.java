package utext;

import java.io.*;
import java.net.*;

public abstract class ConnessioneAServerLog{//01
    private static Socket socket;
    private static DataOutputStream dos;
    
    public static void connessione(){//02
        System.out.print("Mi sto connettendo al server di log...");
        try{
            socket = new Socket(ParametriConfigurazione.network.indirizzoServerLog, ParametriConfigurazione.network.portaServerLog);
            dos = new DataOutputStream(socket.getOutputStream());
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }   
        System.out.println("fatto");
    }
        
    public static void inviaEventoLog(String evento){//03
        System.out.println("Invio al server di log di un evento: " + evento);
        EventoNavigazioneGUI eventoLog = new EventoNavigazioneGUI(evento, ParametriConfigurazione.impostazioni.usernameUtente);
        
        try{
            dos.writeUTF("<?xml version=\"1.0\"?>\n" + eventoLog.toString());
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
    
    public static void disconnessione(){//04
        System.out.println("Disconnessione dal server di log...");
        try{
            dos.writeUTF("LOGOUT");
            dos.close();
           
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        } 
    }
}
/*

NOTE:
(01) Classe che raccoglie i metodi per gestire lato client la connessione con il server di log. Ho scelto un approccio TCP persistente perche' gli eventi di log inviati in una sessione
     potrebbero essere molti e ad alta frequenza, quindi stabilire una connessione TCP per ogni evento inviato sarebbe sicuramente poco efficiente e molto inutile.

(02) Metodo statico che inizializza la connessione TCP tra client e server di log

(03) Metodo statico che invia un oggetto di tipo EventoNavigazioneGUI serializzato in XML al server di log

(04) Disconnessione al server di log, necessaria per comunicare al server di log che deve rimuovere la entry corrispondente a questo utente dalla sua lista di connessioni aperte.
     

*/