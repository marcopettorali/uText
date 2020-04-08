package utext;

import java.util.*;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class InserimentoMessaggi extends Application {
    
    private static TabellaMessaggiVisuale tabellaMessaggi;
    private static GraficoStatisticheMessaggiScambiati graficoMessaggi;
    private static TabellaAmiciVisuale tabellaAmici;
    
    private Label etichettaAmici;
    private Label etichettaGrafico;
    private Label etichettaMessaggio;
    private TextField campoMessaggio; 
    private Button bottoneInviaMessaggio;
    
    public static void visualizzaNotifica(String amico){//01
        tabellaAmici.impostaColoreAmico(amico, "yellow");
        if(amico.equals(ListaAmiciSelezionati.getAmicoSelezionato())){
            tabellaMessaggi.aggiornaTabellaMessaggi(ArchiviazioneEStatisticheMessaggi.caricaMessaggi(amico, ParametriConfigurazione.impostazioni.numeroMessaggiTabella));
        }
    }
    
    public static void visualizzaAmicoOnline(String amico){//02
        tabellaAmici.impostaColoreAmico(amico, "green");
    }
    
    public static void visualizzaAmicoOffline(String amico){//02
        tabellaAmici.impostaColoreAmico(amico, "red");
    }
    
    private void inizializzaElementiGrafici(){//03
        System.out.print("Inizializzando gli elementi della GUI...");
        tabellaMessaggi = new TabellaMessaggiVisuale();
        
        graficoMessaggi = new GraficoStatisticheMessaggiScambiati();
        graficoMessaggi.aggiornaGrafico(ArchiviazioneEStatisticheMessaggi.caricaStatisticheMessaggi());
        graficoMessaggi.setMaxSize(300, 300);
                      
        tabellaAmici = new TabellaAmiciVisuale(tabellaMessaggi);
                
        etichettaAmici = new Label("AMICI");
        
        etichettaGrafico = new Label("Statistiche dei messaggi scambiati:");
        
        etichettaMessaggio = new Label("Messaggio:");
        
        campoMessaggio = new TextField();
        campoMessaggio.setPromptText("Inserisci qui il tuo messaggio");
        campoMessaggio.setMinSize(300, 100);    
        
        bottoneInviaMessaggio = new Button("Invia Messaggio");
        System.out.println("fatto");
    }
    
    private void inizializzaEventiSuElementiGrafici(){//04
        System.out.print("Inizializzando gli eventi sugli elementi della GUI...");
        campoMessaggio.setOnMouseClicked(e -> {//04.a
            ConnessioneAServerLog.inviaEventoLog("Focus su campo messaggio");
        });
        
        bottoneInviaMessaggio.setOnAction(e -> {//04.b
            Messaggio messaggio = new Messaggio(ParametriConfigurazione.impostazioni.usernameUtente, ListaAmiciSelezionati.getAmicoSelezionato(), campoMessaggio.getText());//04.b.1
            ConnessioneAServer.inviaMessaggio(messaggio.getDestinatario(), messaggio.getTesto());
            ConnessioneAServerLog.inviaEventoLog("Inviato Messaggio");
            ArchiviazioneEStatisticheMessaggi.salvaMessaggio(messaggio.getTimestampInvio(), messaggio.getMittente(), messaggio.getDestinatario(), messaggio.getTesto());//04.b.2
            tabellaMessaggi.aggiornaTabellaMessaggi(ArchiviazioneEStatisticheMessaggi.caricaMessaggi(ListaAmiciSelezionati.getAmicoSelezionato(), ParametriConfigurazione.impostazioni.numeroMessaggiTabella));//04.b.3
            graficoMessaggi.incrementaMessaggiScambiati(messaggio.getDestinatario());//04.b.4
            campoMessaggio.clear();
        });
        System.out.println("fatto");
    }
    
    private void inizializzaLayoutStatico(Stage stage){//05
        System.out.print("Inizializzando il layout della GUI...");
        VBox inviaMessaggioVBox = new VBox();
        inviaMessaggioVBox.getChildren().addAll(etichettaMessaggio, campoMessaggio, bottoneInviaMessaggio);
        inviaMessaggioVBox.setSpacing(20);
        inviaMessaggioVBox.setAlignment(Pos.CENTER);
        
        VBox graficoMessaggiVBox = new VBox();
        graficoMessaggiVBox.getChildren().addAll(etichettaGrafico, graficoMessaggi);
        graficoMessaggiVBox.setAlignment(Pos.CENTER);
        
        HBox parteBassaHBox = new HBox();
        parteBassaHBox.getChildren().addAll(graficoMessaggiVBox, inviaMessaggioVBox);
        parteBassaHBox.setAlignment(Pos.CENTER);
        
        VBox parteCentraleVBox = new VBox();
        parteCentraleVBox.getChildren().addAll(tabellaMessaggi, parteBassaHBox);
        
        VBox parteLateraleVBox = new VBox();
        parteLateraleVBox.getChildren().addAll(etichettaAmici, tabellaAmici);
        parteLateraleVBox.setAlignment(Pos.TOP_CENTER);
        
        HBox HBoxPrincipale = new HBox();
        HBoxPrincipale.getChildren().addAll(parteLateraleVBox, parteCentraleVBox);
        
        Group root = new Group(HBoxPrincipale);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("μText");
        System.out.println("fatto");
    }
    
    public void ripristinaUltimaSessione(){//06
        System.out.print("Ripristinando l'ultima sessione...");
        tabellaMessaggi.aggiornaTabellaMessaggi(CacheUltimiMessaggi.messaggiScambiati);//06.a
        campoMessaggio.setText(CacheUltimiMessaggi.ultimoCampoMessaggio);
        
        List<Amico> listaAmici = CacheUltimiMessaggi.amici;//06.b
        int amiciMancanti = (ParametriConfigurazione.impostazioni.numeroAmiciTabella - CacheUltimiMessaggi.amici.size());
        for(int i = 0; i < amiciMancanti; i++){
            listaAmici.add(new Amico(""));
        }
        tabellaAmici.aggiornaTabellaAmiciVisuale(listaAmici);
        
        for(String username : ListaAmiciSelezionati.getUsernames()){//06.c
            if(!username.isEmpty())
                ConnessioneAServer.richiestaAmicoOnline(username);
        }
        
        ListaAmiciSelezionati.setAmicoSelezionato(CacheUltimiMessaggi.ultimoAmicoSelezionato);//06.d
        Platform.runLater(()->{tabellaAmici.impostaColoreAmico(ListaAmiciSelezionati.getAmicoSelezionato(), "blue");});
        System.out.println("fatto");
    }
    
    @Override
    public void start(Stage stage) {
        System.out.println("uText Client: Sto avviando l'applicazione.");
        ParametriConfigurazione.caricaDatiDaFileConfigurazione();
        CacheUltimiMessaggi.caricaDatiDaFileDiCache();
                
        inizializzaElementiGrafici();
        inizializzaEventiSuElementiGrafici();
        inizializzaLayoutStatico(stage);
        
        ConnessioneAServer.connessione();
        ConnessioneAServerLog.connessione();
        ConnessioneAServerLog.inviaEventoLog("Avvio applicazione");
        
        ripristinaUltimaSessione();
        
        stage.setOnCloseRequest(e -> {//07
            System.out.println("Sto chiudendo l'applicazione...");
            CacheUltimiMessaggi.scriviDatiInFileDiCache(ListaAmiciSelezionati.getListaAmici(), ListaAmiciSelezionati.getAmicoSelezionato(), tabellaMessaggi.getListaMessaggi(), campoMessaggio.getText());
            ConnessioneAServerLog.inviaEventoLog("Chiusura applicazione");
            ConnessioneAServerLog.disconnessione();
            ConnessioneAServer.disconnessione();
        });
        
        System.out.println("Pronto.");
        stage.show();
    }   
    
    public static void main(String[] args) {
        launch(args);
    }
     
}

/*

NOTE:
(01) Metodo statico invocato da RicevitoreMessaggiDaServer all'arrivo di un nuovo messaggio. 
     Evidenzia di giallo il nome del mittente del messaggio, se questi e' presente nella lista amici dell'utente.

(02) Questi due metodi, pur svolgendo una simile funzione, sono stati separati per rendere il codice piu' leggibile e dichiarativo.
     Sono due metodi statici invocati da RicevitoreMessaggiDaServer all'arrivo di una notifica dello stato online/offline di un utente.
     Evidenziano di verde un utente amico che e' attualmente online, di rosso se e' offline.

(03) Metodo che semplicemente inizializza gli elementi grafici della GUI.

(04) Metodo che aggiunge dinamica agli elementi dell'interfaccia:
     (04.a) Un click sull'area di inserimento di un messaggio invia un corrispettivo messaggio di log al server remoto di log.
     (04.b) Gestione del click sul bottone 'Invia Messaggio':
            (04.b.1) Creazione di un messaggio da spedire prelevando gli elementi necessari dalla GUI e dalle strutture dati.
            (04.b.2) Salvataggio del messaggio da inviare nel database locale per la successiva visualizzazione
            (04.b.3) Aggiornamento della tabella dei messaggi per visualizzare il messaggio inviato
            (04.b.4) In quanto e' stato scambiato un ulteriore messaggio con un certo amico, aggiornamento del grafico delle statistiche sui messaggi scambiati 

(05) Metodo che inizializza il layout della GUI. Ho usato VBox e HBox come contenitori statici e non modificabili per allineare automaticamente
     gli elementi dell' interfaccia.

(06) Ripristino dell'ultima sessione dell'utente caricando i dati dalla cache locale
     (06.a) Ripristino degli ultimi messaggi scambiati con l'ultimo amico selezionato la precedente sessione
     (06.b) Riempimento della TabellaAmiciVisuale con gli ultimi amici selezionati dall'utente. Se questi dovessero essere inferiori in numero
            alla quantita' impostata nel file di configurazione locale in XML, allora la tabella amici viene saturata fino a raggiungere il valore indicato
     (06.c) Richiesta al server per conoscere se gli amici selezionati sono attualmente online o offline
     (06.d) Visualizzazione grafica dell' ultimo amico selezionato, evidenziato con il colore blu.
            
(07) Operazioni da effettuare quando l'applicazione si chiude, ovvero salvataggio in cache locale per ripristino successivo di questa sessione di lavoro,
     invio dell'evento di chiusura applicazione e disconnessione dal server di log, disconnessione dal server.

*/