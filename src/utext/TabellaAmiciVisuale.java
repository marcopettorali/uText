package utext;

import java.util.*;
import static javafx.application.Application.STYLESHEET_MODENA;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;

public class TabellaAmiciVisuale extends ListView<String>{
    
    TabellaMessaggiVisuale tabellaMessaggi;
    
    public TabellaAmiciVisuale(TabellaMessaggiVisuale tabellaMessaggi){//01
        super();
        this.tabellaMessaggi = tabellaMessaggi;
        this.setEditable(true);
        this.setCellFactory(TextFieldListCell.forListView());
        
        this.setFixedCellSize(25);
        this.setMaxHeight(ParametriConfigurazione.impostazioni.numeroAmiciTabella * this.getFixedCellSize() + 2);//01.a
        
        this.setMaxWidth(200);
        this.applyCss();//01.b
        
        impostaGestoriModificaAmico();
        impostaGestoreClickSuAmico();
        
    }
    
    private void impostaGestoriModificaAmico(){//02
        EventHandler defaultHandler = this.getOnEditCommit();//02.a
       
        this.setOnEditCancel(e ->{//02.b
            ListaAmiciSelezionati.setAmicoSelezionato(ListaAmiciSelezionati.getUsernames().get(e.getIndex()));
        });
        
        this.setOnEditCommit(e -> {//02.c
            String amicoPrecedente = ListaAmiciSelezionati.getUsernames().get(e.getIndex());//02.c.1
            String nuovoAmico = e.getNewValue();
            
            defaultHandler.handle(e);//02.c.2
            ListaAmiciSelezionati.getAmicoDaUsername(amicoPrecedente).setUsername(nuovoAmico);//02.c.3
            ListaAmiciSelezionati.setAmicoSelezionato(nuovoAmico);//02.c.4
            if(!nuovoAmico.isEmpty())
                ConnessioneAServer.richiestaAmicoOnline(nuovoAmico);//02.c.5
            tabellaMessaggi.aggiornaTabellaMessaggi(ArchiviazioneEStatisticheMessaggi.caricaMessaggi(nuovoAmico, ParametriConfigurazione.impostazioni.numeroMessaggiTabella));//02.c.6
        });
    }
    
    private Cell ottieniCellaSelezionata(MouseEvent e){//03
        Cell cella = null;
            if(e.getPickResult().getIntersectedNode() instanceof Text){//03.a
                Text testo = (Text)e.getPickResult().getIntersectedNode();
                if(testo.getParent() instanceof Cell)
                    cella = (Cell)testo.getParent();
                else
                    return null;
            }else if(e.getPickResult().getIntersectedNode() instanceof Cell){//03.b
                cella = (Cell)e.getPickResult().getIntersectedNode();
            }else{//03.c
                return null;
            }
        return cella;
    }
    
    private void impostaGestoreClickSuAmico(){//04
        this.setOnMouseClicked(e -> {
            
            Cell cellaSelezionata = ottieniCellaSelezionata(e);
            
            if(cellaSelezionata == null){
                return;
            }
            
            String amicoSelezionatoPrecedente = ListaAmiciSelezionati.getAmicoSelezionato();
            if(ListaAmiciSelezionati.getAmicoDaUsername(amicoSelezionatoPrecedente).getNotificaAttiva()){//04.a
                ListaAmiciSelezionati.rimuoviNotifica(amicoSelezionatoPrecedente);
            }
            if(!amicoSelezionatoPrecedente.isEmpty()){
                if(ListaAmiciSelezionati.getAmicoDaUsername(amicoSelezionatoPrecedente).getOnline()){//04.b
                    impostaColoreAmico(amicoSelezionatoPrecedente, "green");
                }else{
                    impostaColoreAmico(amicoSelezionatoPrecedente, "red");
                }      
            }else{
                impostaColoreAmico(amicoSelezionatoPrecedente, "default");
            }
            ConnessioneAServerLog.inviaEventoLog("Selezionato amico");//04.c
            ListaAmiciSelezionati.setAmicoSelezionato(cellaSelezionata.getText());//04.d
            impostaColoreAmico(ListaAmiciSelezionati.getAmicoSelezionato(), "blue");//04.e
            tabellaMessaggi.aggiornaTabellaMessaggi(ArchiviazioneEStatisticheMessaggi.caricaMessaggi(ListaAmiciSelezionati.getAmicoSelezionato(), ParametriConfigurazione.impostazioni.numeroMessaggiTabella));//04.f
        });
    }
       
    public void aggiornaTabellaAmiciVisuale(List<Amico> lista){
        ListaAmiciSelezionati.setListaAmici(lista);
        this.getItems().clear();
        this.setItems(ListaAmiciSelezionati.getUsernames());
    }
    
    public void impostaColoreAmico(String usernameAmico, String colore){//05
        Amico amico = ListaAmiciSelezionati.getAmicoDaUsername(usernameAmico);
        if(amico == null) return;
        
        if(!colore.equals("blue") && amico.getNotificaAttiva()){
            colore = "yellow";//05.a
        }     
        for(Node n: this.lookupAll("*")){//05.b
            if(n instanceof Cell){//05.c
                Cell c = (Cell) n;
                if(c.getText()!= null && c.getText().equals(usernameAmico)){//05.d
                    String stile = " .list-cell {-fx-padding: 3 3 3 3;-fx-background-color: " + colore + ";}";
                    if(colore.equals("default"))
                        c.setStyle(STYLESHEET_MODENA);
                    else
                        c.setStyle(stile);
                }
            }
        }
    }
}

/*

NOTE:
(01) Costruttore della classe, inizializza i parametri della ListView:
     (01.a) Imposto l'altezza massima della tabella in base a quanti amici possono essere presenti al piu' nella tabella. 
            Questo in modo che non si mostrino a video linee vuote
     (01.b) applyCss() e' necessario per far si' che il metodo lookupAll() nel metodo impostaColoreAmico() abbia effetto

(02) Metodo che imposta i gestori della modifica di un amico della tabella:
     (02.a) Prelevo il vecchio handler del click sulla ListView, per invocarlo successivamente. Se omettessi questo passaggio, il mio handler personalizzato andrebbe a
            rimpiazzare quello di default, e il click sulla cella non avrebbe alcun effetto.
     (02.b) Gestore dell'evento di modifica annullata: se l'utente, mentre e' in fase di editing della cella, clicca al di fuori della TextField visualizzata, il sistema
            non aggiorna l'amico
     (02.c) Gestore dell'effettiva modifica da parte dell'utente di un campo della tabella:
            (02.c.1) Non avendo ancora, di fatto, modificato l'amico selezionato, ne recupero lo username
            (02.c.2) Invoco l'handler di default (vedi sopra al punto 02.a)
            (02.c.3) Imposto il nuovo amico, in base allo username inserito dall'utente dentro la TextField che compare in fase di modifica
            (02.c.4) Rendo il nuovo amico inserito dall'utente come amico selezionato
            (02.c.5) Chiedo al server se il nuovo amico inserito e' attualmente online o offline
            (02.c.6) Aggiorno il contenuto della tabella dei messaggi in modo da visualizzare i messaggi scambiati con il nuovo amico inserito nella tabella

(03) Metodo che restituisce la cella selezionata durante un evento di click sulla ListView: 
     (04.a) Prelevando l'oggetto effettivamente cliccato all'interno della ListView, se questi e' un oggetto Text, recupero la cella che lo contiene tramite il metodo getParent()
     (04.b) Se invece l'oggetto cliccato e' proprio la cella restituisco la cella stessa
     (04.c) Altrimenti, l' oggetto cliccato non e' una cella: in questo caso non faccio niente.

(04) Metodo che imposta il gestore dell'evento di click su un amico nella tabella:
     (04.a) Se l'amico precedentemente selezionato stava visualizzando una otifica, questa viene tolta dalla GUI: si puo' assumere, infatti, che l'utente abbia gia' letto il messaggio
     (04.b) Ripristina il colore della cella del precedente amico selezionato in base al suo stato onliune/offline
     (04.c) Invio al server di log l'evento che un nuovo amico e' stato cliccato
     (04.d) Imposto come nuovo amico selezionato l'amico cliccato dall'utente
     (04.e) Evidenzio di blu il nuovo amico selezionato
     (04.f) Aggiorno il contenuto della tabella dei messaggi in modo da visualizzare i messaggi scambiati con il nuovo amico selezionato

(05) Metodo che evidenzia la cella di un determinato amico, di un colore passato come parametro come stringa CSS:
     (05.a) Se un amico ha una notifica attiva, e non si vuole selezionare quell'amico, la richiesta di modificare il colore viene ignorata. Questo per far sì che una eventuale ricezione
            di una comunicazione sullo stato online/offline di quell'amico, non interrompa la visualizzazione della precedente notifica.
     (05.b) Scorro tutti gli elementi CSS che compongono la tabella. Questo metodo e' efficace solo se si ha la certezza che la GUI ha applicato lo stile CSS alla tabella
            (vedi punto 01.b)
     (05.c) Se l'elemento CSS in questione non e' una cella, me ne disinteresso
     (05.d) Se la cella contiene del testo, e il suo contenuto e' quello dello username passato per parametro allora appliuco il colore.

*/