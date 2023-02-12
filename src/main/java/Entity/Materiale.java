package Entity;

import java.util.ArrayList;

// classe che rappresenta il materiale e tutti i turni macchina a lui assegnato
public class Materiale {
    private int numeroMateriale;
    private String tipologiaMateriale;
    private ArrayList<TurnoMacchina> listTurnoMacchina;


    public Materiale(int i, String tipo) {
        numeroMateriale=i;
        tipologiaMateriale = tipo;
        listTurnoMacchina = new ArrayList<>();
    }

    // aggiunge il treno al giusto turnoMacchina
    public void addTreno(Treno t){
        String denTurnoMacc = t.getDenominazioneTurnoMacc();
        boolean trovato = false;
        for (TurnoMacchina turnoMacchina : listTurnoMacchina){
            if(turnoMacchina.getDenominazioneTurnoMacc().equals(denTurnoMacc)) {
                turnoMacchina.addTreno(t);
                trovato = true;
            }
        }
        if (!trovato){
            TurnoMacchina turnoMacchina = new TurnoMacchina(denTurnoMacc);
            turnoMacchina.addTreno(t);

            listTurnoMacchina.add(turnoMacchina);
        }
    }

    public int getNumeroMateriale() {
        return numeroMateriale;
    }

    public void setNumeroMateriale(int numeroMateriale) {
        this.numeroMateriale = numeroMateriale;
    }

    public String getTipologiaMateriale() {
        return tipologiaMateriale;
    }

    public void setTipologiaMateriale(String tipologiaMateriale) {
        this.tipologiaMateriale = tipologiaMateriale;
    }

    public ArrayList<TurnoMacchina> getListTurnoMacchina() {
        return listTurnoMacchina;
    }

    public void setListTurnoMacchina(ArrayList<TurnoMacchina> listTurnoMacchina) {
        this.listTurnoMacchina = listTurnoMacchina;
    }

    public void stampaMateriale(){
        System.out.println("MATERIALE #" + numeroMateriale);
        for (TurnoMacchina turnoMacchina : listTurnoMacchina) {
            turnoMacchina.printTurnoMacchina();
        }
    }
}
