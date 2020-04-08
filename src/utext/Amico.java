package utext;

import java.io.*;

public class Amico implements Serializable{
    private String username;//01
    private boolean online;
    private boolean notificaAttiva;
    
    public Amico(String nome){
        username = nome;
        online = false;
        notificaAttiva = false;
    }
    
    public String getUsername(){
        return username;
    }
    
    public boolean getOnline(){
        return online;
    }
    
    public boolean getNotificaAttiva(){
        return notificaAttiva;
    }
    
    public void setOnline(boolean valore){
        online = valore;
    }
    
    public void setNotificaAttiva(boolean valore){
        notificaAttiva = valore;
    }
    
    public void setUsername(String nome){
        username = nome;
    }
}

/*

NOTE:
(01) Ho usato String invece che SimpleStringProperty perche' non ho bisogno della osservabilita' del parametro username.
*/