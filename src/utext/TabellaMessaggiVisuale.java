package utext;

import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class TabellaMessaggiVisuale extends TableView<Messaggio>{
    private ObservableList<Messaggio> listaMessaggi;
    
    public TabellaMessaggiVisuale(){//01
        super();
        TableColumn colonnaTimestamp = new TableColumn("TIMESTAMP");
        TableColumn colonnaMittente = new TableColumn("MITTENTE");
        TableColumn colonnaTesto = new TableColumn("TESTO");
        
        colonnaTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestampInvio"));
        colonnaMittente.setCellValueFactory(new PropertyValueFactory<>("mittente"));
        colonnaTesto.setCellValueFactory(new PropertyValueFactory<>("testo"));
        
        colonnaTimestamp.setSortable(false);
        colonnaMittente.setSortable(false);
        colonnaTesto.setSortable(false);
        
        this.getColumns().addAll(colonnaTimestamp, colonnaMittente,  colonnaTesto);
        
        listaMessaggi = FXCollections.observableArrayList();
        this.setItems(listaMessaggi);
        
        this.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        this.setMaxSize(700, 300);
        this.setMinSize(700, 300);
        this.setStyle(".table-view{-fx-padding: 3 3 3 3;-fx-font-family:" + ParametriConfigurazione.stile.fontMessaggi + ";"
                                + "-fx-font-size:"+ParametriConfigurazione.stile.dimensioneMessaggi+"pt;"
                                + "-fx-text-background-color:"+ParametriConfigurazione.stile.coloreMessaggi+";}");
        
    }
    
    public void aggiornaTabellaMessaggi(List<Messaggio> lista){
        listaMessaggi.clear();
        listaMessaggi.addAll(lista);
    }
    
    public ObservableList<Messaggio> getListaMessaggi(){
        return listaMessaggi;
    }
}

/*

NOTE:
(01) Costruttore che inizializza i parametri della tabella e delle sue colonne


*/