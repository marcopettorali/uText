package utext;

import java.util.*;
import javafx.collections.*;
import javafx.scene.chart.*;

public class GraficoStatisticheMessaggiScambiati extends PieChart{
    private ObservableList<PieChart.Data> listaStatistiche;
    
    public GraficoStatisticheMessaggiScambiati(){
        super();
        listaStatistiche = FXCollections.observableArrayList();
        this.setData(listaStatistiche);
    }
    
    public void aggiornaGrafico(List<PieChart.Data> lista){
        listaStatistiche.clear();
        listaStatistiche.addAll(lista);
    }
    
    public void incrementaMessaggiScambiati(String amico){//01
        boolean trovato = false;
        for(int i = 0; i< listaStatistiche.size(); i++){
            if(listaStatistiche.get(i).getName().equals(amico)){
                double vecchioValore = listaStatistiche.get(i).getPieValue();
                listaStatistiche.get(i).setPieValue(vecchioValore + 1);
                trovato = true;
                break;
            }
        }
        
        if(trovato == false){
            aggiornaGrafico(ArchiviazioneEStatisticheMessaggi.caricaStatisticheMessaggi());
        }
    }
}

/*

NOTE:
(01) Per ottimizare le prestazioni, non si ricaricano le statistiche dei messaggi dal database ogni volta che si invia/riceve un messaggio, ma se l'amico con cui si e' scambiato
     un nuovo messaggio e' gia' presente nel grafico allora basta incrementare il contatore corrispondente da listaStatistiche. Se questo non e' stato trovato, invece, si ricaricano
     tutte le statistiche dal database.


*/