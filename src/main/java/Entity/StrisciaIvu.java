package Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

// la classe rappresenta i giri materiali del turno su IVU
public class StrisciaIvu {
    private Date dataPartenzaPrev;
    private String denominazioneTurnoMacc;
    private String depositoPartenza;
    private String depositoArrivo;
    private LocalTime partenzaPrevista;
    private LocalTime arrivoPrevisto;
    private String tipologiaVeicolo;
    private int numeroMateriale;
    private String numeroTreno;
    private String numeroTrenoArrivo;
    private ArrayList<String> treniStrisciaIVU;

    public Date getDataPartenzaPrev() {
        return dataPartenzaPrev;
    }

    public void setDataPartenzaPrev(Date dataPartenzaPrev) {
        this.dataPartenzaPrev = dataPartenzaPrev;
    }

    public StrisciaIvu(){
        dataPartenzaPrev = new Date();
        denominazioneTurnoMacc = "";
        depositoPartenza = "";
        depositoArrivo = "";
        partenzaPrevista = LocalTime.of(0,0);
        arrivoPrevisto = LocalTime.of(0,0);
        tipologiaVeicolo = "";
        numeroMateriale = 0;
        numeroTreno = "";
        numeroTrenoArrivo = "";
        treniStrisciaIVU = new ArrayList<>();
    }

    public boolean isEmpty(){
        boolean result = false;
        if (depositoPartenza.isEmpty() || depositoArrivo.isEmpty() || numeroTreno.isEmpty())
            result = true;
        return result;
    }

    public static void printStrisciaIvu(StrisciaIvu s){
        System.out.printf("%s %s %s %s %s %s %s %s \n",s.dataPartenzaPrev, s.denominazioneTurnoMacc, s.depositoPartenza, s.depositoArrivo, s.tipologiaVeicolo, s.numeroMateriale, s.numeroTreno, s.numeroTrenoArrivo);
    }

    public String toString(){
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       return dateFormat.format(dataPartenzaPrev) +"\t "+ depositoPartenza +"\t "+ depositoArrivo +"\t "+ tipologiaVeicolo +"\t "+ numeroMateriale +"\t "+ numeroTreno +"\t "+ numeroTrenoArrivo +"\t "+ partenzaPrevista +"\t "+ arrivoPrevisto;
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

    public LocalTime getPartenzaPrevista() {
        return partenzaPrevista;
    }

    public void setPartenzaPrevista(LocalTime partenzaPrevista) {
        this.partenzaPrevista = partenzaPrevista;
    }

    public LocalTime getArrivoPrevisto() {
        return arrivoPrevisto;
    }

    public void setArrivoPrevisto(LocalTime arrivoPrevisto) {
        this.arrivoPrevisto = arrivoPrevisto;
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

    public String getNumeroTreno() {
        return numeroTreno;
    }

    public void setNumeroTreno(String numeroTreno) {
        this.numeroTreno = numeroTreno;
    }

    public String getNumeroTrenoArrivo() {
        return numeroTrenoArrivo;
    }

    public void setNumeroTrenoArrivo(String numeroTrenoArrivo) {
        this.numeroTrenoArrivo = numeroTrenoArrivo;
    }

    public ArrayList<String> getTreniStrisciaIVU() {
        return treniStrisciaIVU;
    }

    public void setTreniStrisciaIVU(ArrayList<String> treniStrisciaIVU) {
        this.treniStrisciaIVU = treniStrisciaIVU;
    }

    public void addTrenoTurnoMacchina(String numeroTreno) {
        treniStrisciaIVU.add(numeroTreno);
    }

    public void printTreniTurnoMacchina(){
        String s = "";
        for(String tsi : treniStrisciaIVU){
            s = s + tsi + "; ";
        }
        System.out.println(denominazioneTurnoMacc + "-> " + s + "    " + tipologiaVeicolo+numeroMateriale);
    }
}
