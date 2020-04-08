package utext;

import com.thoughtworks.xstream.*;
import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.xml.sax.SAXException;

class Impostazioni{//01
    public String usernameUtente;
    public int numeroGiorniGrafico;
    public int numeroAmiciTabella;
    public int numeroMessaggiTabella;
}

class Network{//01
    public String indirizzoDBMS;
    public int portaDBMS;
    public String usernameDBMS;
    public String passwordDBMS;
    
    public String indirizzoServer;
    public int portaServer;
    
    public String indirizzoServerLog;
    public int portaServerLog;
}

class Stile{//01
    public String fontMessaggi;
    public int dimensioneMessaggi;
    public String coloreMessaggi;
}

class Parametri{//01
    public Impostazioni impostazioni;
    public Network network;
    public Stile stile;
      
    public Parametri(String xml){
        Parametri parametri = (Parametri) new XStream().fromXML(xml);
        this.impostazioni = parametri.impostazioni;
        this.network = parametri.network;
        this.stile = parametri.stile;
    }
    
    @Override
    public String toString(){
        return (new XStream().toXML(this));
    }
}

public abstract class ParametriConfigurazione {
    
    public static Impostazioni impostazioni;
    public static Network network;
    public static Stile stile;
    
    public static boolean validaXML(){//02
        
        File fileSchemaXML = new File("configurazione.xsd");
        Source fileConfigurazione = new StreamSource(new File("configurazione.xml")); //02.a
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);//02.b
        try {
            Schema schemaParametriConfigurazione = schemaFactory.newSchema(fileSchemaXML);//02.c
            Validator validatore = schemaParametriConfigurazione.newValidator();//02.d
            validatore.validate(fileConfigurazione);//02.d
            System.out.println("Validazione riuscita: " + fileConfigurazione.getSystemId());
            return true;
        } catch (SAXException | IOException e) {
            System.out.println("Validazione non possibile: " + fileConfigurazione.getSystemId());
            return false;
        }    
    }
    
    public static void caricaDatiDaFileConfigurazione(){//03
        
        System.out.print("Sto caricando il file di configurazione...");
        if(!validaXML()) return;
       
        String xml = new String();
        File file = null;
        try {
            xml = new String(Files.readAllBytes(Paths.get("configurazione.xml")));
        } catch (Exception ex) {
            System.err.println(ex.getStackTrace());
        }
        
        Parametri p = new Parametri(xml);
        ParametriConfigurazione.impostazioni = p.impostazioni;
        ParametriConfigurazione.network = p.network;
        ParametriConfigurazione.stile = p.stile;
        System.out.println("fatto");
    } 
}

/*

NOTE:
(01) Classi di appoggio per strutturare il file di configurazione locale con sottoelementi attinenti alla categoria in cui ogni parametro
     di configurazione semanticamente appartiene

(02) Metodo che permette di validare il file di configurazione:
     (02.a) Apro il file di configurazione in un oggetto Source per poter essere validato dal metodo validate() della classe Validator
     (02.b) Creo un nuovo schema di confronto per la validazione come schema XML
     (02.c) Aggiungo al validatore il file XSD che definisce la struttura del file di configurazione
     (02.d) Valido il file di configurazione in base allo schema XSD

(03) Metodo statico che carica i parametri di configurazione dal file di configurazione, dopo averlo validato, e li mette a disposizione di tutte le classi
     come parametri statici.

*/