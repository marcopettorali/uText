package utext;

import java.io.*;
import java.util.*;
import javafx.collections.*;

class ParametriCache implements Serializable{//01
    public ArrayList<Amico> amici;
    public String ultimoAmicoSelezionato;
    public ArrayList<Messaggio> messaggiScambiati;
    public String ultimoCampoMessaggio;
}

public abstract class CacheUltimiMessaggi {
    public static List<Amico> amici;
    public static String ultimoAmicoSelezionato;
    public static List<Messaggio> messaggiScambiati;
    public static String ultimoCampoMessaggio;
   
     public static void caricaDatiDaFileDiCache(){//02
        System.out.print("Sto caricando il file di cache...");
        CacheUltimiMessaggi.amici = FXCollections.observableArrayList();//03
        CacheUltimiMessaggi.messaggiScambiati = FXCollections.observableArrayList();
        CacheUltimiMessaggi.ultimoAmicoSelezionato = "";
        CacheUltimiMessaggi.ultimoCampoMessaggio = "";
        
        try{//04
            FileInputStream fis = new FileInputStream("cache.bin");
            ObjectInputStream dis = new ObjectInputStream(fis);
            ParametriCache parametri = (ParametriCache) dis.readObject();
            CacheUltimiMessaggi.amici = FXCollections.observableArrayList(parametri.amici);
            CacheUltimiMessaggi.messaggiScambiati = FXCollections.observableArrayList(parametri.messaggiScambiati);
            CacheUltimiMessaggi.ultimoAmicoSelezionato = parametri.ultimoAmicoSelezionato;
            CacheUltimiMessaggi.ultimoCampoMessaggio = parametri.ultimoCampoMessaggio;
           
        }catch(Exception ioe){
            System.err.println(ioe.getMessage());
        }
        System.out.println("fatto");
    }
   
    public static void scriviDatiInFileDiCache(List<Amico> listaAmici, String ultimoAmico, List<Messaggio> listaMessaggi, String campoMessaggio){//05
        try{
            FileOutputStream fos = new FileOutputStream("cache.bin");
            ObjectOutputStream dos = new ObjectOutputStream(fos);
            
            ParametriCache parametri = new ParametriCache();
            parametri.amici = new ArrayList<Amico>(listaAmici);
            parametri.messaggiScambiati = new ArrayList<Messaggio>(listaMessaggi);
            parametri.ultimoAmicoSelezionato = ultimoAmico;
            parametri.ultimoCampoMessaggio = campoMessaggio;
            
            dos.writeObject(parametri);
           
        }catch(Exception ioe){
            System.err.println(ioe.getMessage());
        }
    }
    
}

/*

NOTE:
(01) Classe di appoggio per recuperare e serializzare i dati in cache binaria. Scelta derivata dal fatto che CacheUltimiMessaggi e' abstract e contiene membri statici.

(02) Metodo statico che importa i dati dal file di cache dentro l'applicazione, appoggiandosi alla classe ParametriCache. La distinzione tra i membri della classe ParametriCache
     e quelli di CacheUltimiMessaggi e' una scelta voluta per rendere piu' leggibile e modulare il codice, in modo tale che all'esterno di questo file, tutte le operazioni e i membri
     relativi alla cache locale binaria siano accessibili da una sola classe che li raccoglie.

(03) Parametri di default inizializzati: sono necessari in caso di primo avvio dell'applicazione, in cui il file di cache non esiste.

(04) Apertura del file 'cache.bin' in lettura, e successiva lettura dei parametri di cache.

(05) Metodo statico che scrive sul file binario di cache i parametri passatogli dalla classe InserimentoMessaggi al momento della chiusura.

*/