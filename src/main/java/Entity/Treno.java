package Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

// la classe rappresenta i giri materiali del turno su IVU prelevato da CERCA
public class Treno {
    private Date dataPartenza;
    private Date dataArrivo;
    private String denominazioneTurnoMacc;
    private String depositoPartenza;
    private String depositoArrivo;
    private String tipologiaMateriale;
    private int numeroMateriale;
    private String numeroCorsa;
    private String tipologiaCorsa;

    public ArrayList<SegnalazioniSO> getSegnalazioniSO() {
        return segnalazioniSO;
    }

    public void setSegnalazioniSO(ArrayList<SegnalazioniSO> segnalazioniSO) {
        this.segnalazioniSO = segnalazioniSO;
    }

    public ArrayList<SegnalazioniPDB> getSegnalazioniPDB() {
        return segnalazioniPDB;
    }

    public void setSegnalazioniPDB(ArrayList<SegnalazioniPDB> segnalazioniPDB) {
        this.segnalazioniPDB = segnalazioniPDB;
    }

    private ArrayList<SegnalazioniSO> segnalazioniSO;
    private ArrayList<SegnalazioniPDB> segnalazioniPDB;

    public Date getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(Date dataPartenza) {
        this.dataPartenza = dataPartenza;
    }


    public Treno(){
        dataPartenza = new Date();
        dataArrivo = new Date();
        denominazioneTurnoMacc = "";
        depositoPartenza = "";
        depositoArrivo = "";
        tipologiaCorsa = "";
        tipologiaMateriale = "";
        numeroMateriale = 0;
        numeroCorsa = "";
        segnalazioniSO = new ArrayList<>();
        segnalazioniPDB = new ArrayList<>();
    }

    public boolean isEmpty(){
        boolean result = false;
        if (depositoPartenza.isEmpty() || depositoArrivo.isEmpty() || numeroCorsa.isEmpty())
            result = true;
        return result;
    }

    public static void printTreno(Treno s){
        System.out.printf("%s %s %s %s %s %s %s \n", s.denominazioneTurnoMacc, s.tipologiaCorsa, s.numeroCorsa, s.depositoPartenza, s.depositoArrivo, s.tipologiaMateriale, s.numeroMateriale);
    }
    public void printTrenoConSegnalazioni(){
        System.out.printf("%s %s %s %s %s %s %s \n", denominazioneTurnoMacc, tipologiaCorsa, numeroCorsa, depositoPartenza, depositoArrivo, tipologiaMateriale, numeroMateriale);
        for(SegnalazioniSO sSO : segnalazioniSO) {
            System.out.printf("     %s\n", sSO.toString());
        }
    }

    public String getTipologiaCorsa() {
        return tipologiaCorsa;
    }

    public void setTipologiaCorsa(String tipologiaCorsa) {
        this.tipologiaCorsa = tipologiaCorsa;
    }

    public String toString(){
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");

       return dateFormat.format(dataPartenza) +"\t "+ "["+dateFormat1.format(dataPartenza) +"-"+ dateFormat1.format(dataArrivo)+"]" + "\t "+  denominazioneTurnoMacc +"\t "+ tipologiaCorsa +"\t "+ numeroCorsa +"\t "+ depositoPartenza +"\t "+ depositoArrivo +"\t "+ tipologiaMateriale +"\t "+ numeroMateriale;
    }

    public String getDenominazioneTurnoMacc() {
        return denominazioneTurnoMacc;
    }

    public void setDenominazioneTurnoMacc(String denominazioneTurnoMacc) {
        this.denominazioneTurnoMacc = denominazioneTurnoMacc;
    }

    public Date getDataArrivo() {
        return dataArrivo;
    }

    public void setDataArrivo(Date dataArrivo) {
        this.dataArrivo = dataArrivo;
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

    public String getTipologiaMateriale() {
        return tipologiaMateriale;
    }

    public void setTipologiaMateriale(String tipologiaMateriale) {
        this.tipologiaMateriale = tipologiaMateriale;
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

    public void addSegnalazioneSO(SegnalazioniSO sSO){
        segnalazioniSO.add(sSO);
    }

    public void addSegnalazionePDB(SegnalazioniPDB sPDB){
        segnalazioniPDB.add(sPDB);
    }
}
