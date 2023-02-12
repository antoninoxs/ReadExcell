package Entity;

import java.util.ArrayList;

// classe turno macchina utilizzato per creare dai treni il turno striscia come visto su ivu
public class TurnoMacchina {
    private String denominazioneTurnoMacc;
    private String depositoPartenza;
    private String depositoArrivo;
    private ArrayList<Treno> listTreni = new ArrayList<>();

    public TurnoMacchina(String denTurnoMacc) {
        denominazioneTurnoMacc=denTurnoMacc;
        listTreni = new ArrayList<>();
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

    public ArrayList<Treno> getListTreni() {
        return listTreni;
    }

    public void setListTreni(ArrayList<Treno> listTreni) {
        this.listTreni = listTreni;
    }

    public void addTreno (Treno t){
        if (listTreni.isEmpty()){
            listTreni.add(t);
            depositoPartenza = t.getDepositoPartenza();
        }
        else {
            listTreni.add(t);
        }
    }

    public void printTurnoMacchina() {
        System.out.println( denominazioneTurnoMacc);
        for (Treno t :listTreni){
            System.out.println(" - " + t.toString());
        }
    }
}
