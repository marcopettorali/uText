package utext;

import java.util.*;
import javafx.collections.*;

public abstract class ListaAmiciSelezionati {//01
    private static ObservableList<Amico> listaAmici;
    private static String amicoSelezionato;
    
    static{
        listaAmici = FXCollections.observableArrayList();
    }
    
    public static void setListaAmici(List<Amico> lista){
        listaAmici.clear();
        listaAmici.setAll(lista);
    }
    public static ObservableList<Amico> getListaAmici(){
        return listaAmici;
    }
    public static void impostaNotifica(String amico){
        Amico a = getAmicoDaUsername(amico);
        if(a != null){
            a.setNotificaAttiva(true);
        }
    }
    public static void impostaAmicoOnline(String amico, boolean online){
        
        Amico a = getAmicoDaUsername(amico);
        if(a != null){
            a.setOnline(online);
            if(amico.equals(ListaAmiciSelezionati.getAmicoSelezionato())){
                return;
            }
        }
    }
    
    public static void rimuoviNotifica(String amico){
        Amico a = getAmicoDaUsername(amico);
        if(a != null){
            a.setNotificaAttiva(false);
        }
    }    
    
    public synchronized static void setAmicoSelezionato(String amico){
        amicoSelezionato = amico;
    }
    
    public synchronized static String getAmicoSelezionato(){
        return amicoSelezionato;
    }
    
    public static Amico getAmicoDaUsername(String amico){
        for(int i = 0; i< listaAmici.size(); i++){
            if(listaAmici.get(i).getUsername().equals(amico)){
                return listaAmici.get(i);
            }
        }
        return null;
    }
    
    public static ObservableList<String> getUsernames(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (Amico amico : listaAmici) {
            lista.add(amico.getUsername());
        }
        return lista;
    }
}

/*

NOTE:
(01) Questa classe astratta mantiene e gestisce la lista degli amici selezionati correntemente dall'utente tramite metodi statici.
     La classe ListaAmiciSelezionati e' necessaria all'applicazione, poiche' classi diverse nell'applicazione devono poter modificare
     gli amici selezionati e la loro visualizzazione: la classe RicevitoreMessaggiDaServer deve poter impostare un amico come online/offline oppure impostare
     una notifica per un messaggio in arrivo da un certo amico, la classe TabellaAmiciVisuale deve poter sostituire un amico presente nella lista, con un altro
     username selezionato dall'utente.


*/