package Entity;

import java.util.Date;

public class SegnalazioniPDB {

    private String idSegnalazione;
    private Date   dataTreno;
    private String numeroTreno;
    private String codice;
    private String organo;
    private String ubicazione;
    private String stato;
    private String descrizione;
    private String posizione;
    private int numeroMateriale;
    private String tipologiaVeicolo;

    public String toString(){
        return idSegnalazione +"\t "+ stato +"\t "+ numeroTreno +"\t "+ dataTreno +"\t "+ codice +"\t "+ organo +"\t "+ ubicazione +"\t "+ stato +"\t "
                + descrizione+"\t "+ posizione +"\t "+ numeroMateriale +"\t "+ tipologiaVeicolo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getOrgano() {
        return organo;
    }

    public void setOrgano(String organo) {
        this.organo = organo;
    }

    public String getUbicazione() {
        return ubicazione;
    }

    public void setUbicazione(String ubicazione) {
        this.ubicazione = ubicazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
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
