package utext;

import java.sql.*;
import java.util.*;
import javafx.scene.chart.*;

public abstract class ArchiviazioneEStatisticheMessaggi {
    private static final String caricaMessaggiQuery = "SELECT timestampInvio, mittente, destinatario, testo " 
                                                    + "FROM messaggio "
                                                    + "WHERE ((mittente = ? AND destinatario = ?) OR (mittente = ? AND destinatario = ?)) " 
                                                    + "ORDER BY idMessaggio DESC "
                                                    + "LIMIT ?;";
    
    private static final String salvaMessaggioQuery = "INSERT INTO Messaggio(timestampInvio, mittente, destinatario, testo) VALUES(?,?,?,?)";

    private static final String statisticheRicevutiQuery = "SELECT mittente, COUNT(*) "
                                                         + "FROM messaggio "
                                                         + "WHERE destinatario = ? AND timestampInvio > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL " + ParametriConfigurazione.impostazioni.numeroGiorniGrafico + " DAY) "
                                                         + "GROUP BY mittente";
            
    private static final String statisticheInviatiQuery = "SELECT destinatario, COUNT(*) "
                                                        + "FROM messaggio "
                                                        + "WHERE mittente = ? AND timestampInvio > DATE_SUB(CURRENT_TIMESTAMP, INTERVAL " + ParametriConfigurazione.impostazioni.numeroGiorniGrafico + " DAY) "
                                                        + "GROUP BY destinatario";
    
    private static Connection connessioneDB;
    static{//01
        try{
            connessioneDB = DriverManager.getConnection(ParametriConfigurazione.network.indirizzoDBMS + ":" + 
                                                        ParametriConfigurazione.network.portaDBMS + "/utext", 
                                                        ParametriConfigurazione.network.usernameDBMS, 
                                                        ParametriConfigurazione.network.passwordDBMS); 
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
    }
    
    public static List<Messaggio> caricaMessaggi(String amico, int limite){//02
        System.out.print("Carico messaggi dal database...");
        List<Messaggio> listaMessaggi = null;
        try{
            PreparedStatement caricaMessaggi = connessioneDB.prepareStatement(caricaMessaggiQuery);//02.a
            
            caricaMessaggi.setString(1, amico);
            caricaMessaggi.setString(2, ParametriConfigurazione.impostazioni.usernameUtente);
            caricaMessaggi.setString(3, ParametriConfigurazione.impostazioni.usernameUtente);
            caricaMessaggi.setString(4, amico);
            caricaMessaggi.setInt(5, limite);
            
            ResultSet rs = caricaMessaggi.executeQuery();
            
            listaMessaggi = new ArrayList();
            
            while(rs.next()){
                listaMessaggi.add(new Messaggio(rs.getTimestamp(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            
        }catch(SQLException sqle){
            System.err.println(sqle.getMessage());
        }
        System.out.println("fatto");
        return listaMessaggi;
    }
    
    public static void salvaMessaggio(Timestamp timestampInvio, String mittente, String destinatario, String testo){//03
        System.out.print("Salvo un messaggio nel database...");
        try{
            PreparedStatement salvaMessaggio = connessioneDB.prepareStatement(salvaMessaggioQuery);
        
            salvaMessaggio.setTimestamp(1, timestampInvio);
            salvaMessaggio.setString(2, mittente);
            salvaMessaggio.setString(3, destinatario);
            salvaMessaggio.setString(4, testo);
            
            salvaMessaggio.executeUpdate();
            
        }catch(SQLException sqle){
            System.err.println(sqle.getMessage());
        }
        System.out.println("fatto");
    }
    
    public static List<PieChart.Data> caricaStatisticheMessaggi(){//04
        System.out.print("Carico statistiche dei messaggi scambiati...");
        List<PieChart.Data> listaStatistiche = null;
        try{
            PreparedStatement statisticheRicevuti = connessioneDB.prepareStatement(statisticheRicevutiQuery);
            PreparedStatement statisticheInviati = connessioneDB.prepareStatement(statisticheInviatiQuery);

            statisticheRicevuti.setString(1, ParametriConfigurazione.impostazioni.usernameUtente);//04.a
            statisticheInviati.setString(1, ParametriConfigurazione.impostazioni.usernameUtente);//04.a
            
            ResultSet rsRicevuti = statisticheRicevuti.executeQuery();
            
            listaStatistiche = new ArrayList();
            
            while(rsRicevuti.next()){
                listaStatistiche.add(new PieChart.Data(rsRicevuti.getString(1), rsRicevuti.getInt(2)));
            }
           
            ResultSet rsInviati = statisticheInviati.executeQuery();
            
            while(rsInviati.next()){//04.b
                boolean trovato = false;
                for(int i = 0; i<listaStatistiche.size(); i++){
                    if(listaStatistiche.get(i).getName().equals(rsInviati.getString(1))){//04.b.1
                        double vecchioValore = listaStatistiche.get(i).getPieValue();//04.b.2
                        listaStatistiche.get(i).setPieValue(vecchioValore + rsInviati.getInt(2));//04.b.2
                        trovato = true;
                        break;
                    }
                }
                if(trovato == false){
                    listaStatistiche.add(new PieChart.Data(rsInviati.getString(1), rsInviati.getInt(2)));//04.b.3
                }
            }

        }catch(SQLException sqle){
            System.err.println(sqle.getMessage());
        }
        System.out.println("fatto");
        System.out.println(listaStatistiche.size());
        return listaStatistiche;
    }
}

/*

NOTE:
(01) Inizializzazione della connessione con il DBMS locale, con i parametri definiti nel file di configurazione XML

(02) Metodo statico che carica i messaggi dal database per mostrarli nella TabellaMessaggiVisuale
     (02.a) Prelevo tutti i messaggi scambiati tra l'utente e l'amico selezionato nella tabella degli amici

(03) Metodo statico che salva un messaggio (inviato o ricevuto) nel database locale

(04) Metodo statico che carica dal database le statistiche dei messaggi scambiati con tutti gli amici.
     (04.a) Creo due statement separati, il cui result set verra' salvato in due liste separate che andro' ad unire successivamente. Questo per evitare di scrivere
            una query complessa in MySQL: scrivo due query semplici ed in modo programmatico le unisco.
     (04.b) Vado ad unire i risultati dei due result set. listaStatistiche e' la lista che restituisce il metodo, attualmente parzialmente riempito dai soli messaggi inviati dall'utente
            Scorro il result set delle statistiche sui messaggi ricevuti dall'utente per verificare se devo creare o modificare un nuovo oggetto nella listaStatistiche.
            (04.b.1) Controllo se l'amico di un record nella lista messaggiRicevuti e' gia' presente nella lista
            (04.b.2) Se l'utente e' presente, allora aggiorno il valore dei messaggi scambiati con quell'amico nella listaStatistiche, sommandogli il numero dei messaggi ricevuti dall'amico
            (04.b.3) Altrimenti, creo un nuovo oggetto nella listaStatistiche. Questo significa che l'utente, con quel particolare amico, ha soltanto ricevuto messaggi, e non ne ha mai inviati.    

*/