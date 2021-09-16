package Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SegnalazioniSO {

    private String idSegnalazione;
    private String stato;
    private String numeroTreno;
    private Date   dataTreno;
    private String codiceEdescrizione;
    private String nota;
    private String tipologiaVeicolo;
    private int numeroMateriale;

    public String toString(){
        return idSegnalazione +"\t "+ stato +"\t "+ numeroTreno +"\t "+ dataTreno +"\t "+ codiceEdescrizione +"\t "+ nota +"\t "+ tipologiaVeicolo +"\t "+ numeroMateriale;
    }
    public String getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(String idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getNumeroTreno() {
        return numeroTreno;
    }

    public Date getDataTreno() {
        return dataTreno;
    }

    public void setDataTreno(Date dataTreno) {
        this.dataTreno = dataTreno;
    }

    public void setNumeroTreno(String numeroTreno) {
        this.numeroTreno = numeroTreno;
    }


    public String getCodiceEdescrizione() {
        return codiceEdescrizione;
    }

    public void setCodiceEdescrizione(String codiceEdescrizione) {
        this.codiceEdescrizione = codiceEdescrizione;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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





}
