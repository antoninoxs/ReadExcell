package Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

// la classe rappresenta i giri materiali del turno su IVU prelevato da CERCA
public class TurnoMacchina {
    private Date dataPartenza;
    private String tipologiaCorsa;
    private String denominazioneTurnoMacc;
    private String numeroCorsa;
    private String depositoPartenza;
    private String depositoArrivo;
    private String tipologiaVeicolo;
    private int numeroMateriale;

    public Date getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(Date dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public TurnoMacchina(){
        dataPartenza = new Date();
        denominazioneTurnoMacc = "";
        depositoPartenza = "";
        depositoArrivo = "";
        tipologiaCorsa = "";
        tipologiaVeicolo = "";
        numeroMateriale = 0;
        numeroCorsa = "";
    }

    public boolean isEmpty(){
        boolean result = false;
        if (depositoPartenza.isEmpty() || depositoArrivo.isEmpty() || numeroCorsa.isEmpty())
            result = true;
        return result;
    }

    public static void printTurnoMacchina(TurnoMacchina s){
        System.out.printf("%s %s %s %s %s %s %s \n", s.denominazioneTurnoMacc, s.tipologiaCorsa, s.numeroCorsa, s.depositoPartenza, s.depositoArrivo, s.tipologiaVeicolo, s.numeroMateriale);
    }

    public String getTipologiaCorsa() {
        return tipologiaCorsa;
    }

    public void setTipologiaCorsa(String tipologiaCorsa) {
        this.tipologiaCorsa = tipologiaCorsa;
    }

    public String toString(){
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       return dateFormat.format(dataPartenza) +"\t "+ denominazioneTurnoMacc +"\t "+ tipologiaCorsa +"\t "+ numeroCorsa +"\t "+ depositoPartenza +"\t "+ depositoArrivo +"\t "+ tipologiaVeicolo +"\t "+ numeroMateriale;
    }

    public String getDenominazioneTurnoMacc() {
        return denominazioneTurnoMacc;
    }

    public void setDenominazioneTurnoMacc(String denominazioneTurnoMacc) {
        this.denominazioneTurnoMacc = denominazioneTurnoMacc;
    }

    public String getDepositoPartenza() {
        return depositoPartenza;
    }

    public void setDepositoPartenza(String depositoPartenza) {
        this.depositoPartenza = depositoPartenza;
    }

    public String getDepositoArrivo() {
        return depositoArrivo;
    }

    public void setDepositoArrivo(String depositoArrivo) {
        this.depositoArrivo = depositoArrivo;
    }


    public String getTipologiaVeicolo() {
        return tipologiaVeicolo;
    }

    public void setTipologiaVeicolo(String tipologiaVeicolo) {
        this.tipologiaVeicolo = tipologiaVeicolo;
    }

    public int getNumeroMateriale() {
        return numeroMateriale;
    }

    public void setNumeroMateriale(int numeroMateriale) {
        this.numeroMateriale = numeroMateriale;
    }

    public String getNumeroCorsa() {
        return numeroCorsa;
    }

    public void setNumeroCorsa(String numeroCorsa) {
        this.numeroCorsa = numeroCorsa;
    }

}
