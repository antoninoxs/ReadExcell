import Entity.Materiale;
import Entity.Treno;
import Entity.TurnoMacchina;
import Utility.Utility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import static Utility.Utility.*;

public class ExcelReaderIVUDaCercaPartFuoriImpianto {
    public static final String[] dep500 = {"MSDL", "MIMA", "NAIF"};
    public static final String[] dep1000 = {"MIMA", "NAIF"};
    public static final String[] dep700 = {"MIMA", "MSDL"};
    public static final String[] dep600 = {"RMOMV"};
    public static final String[] dep = {"MSDL", "MIMA", "NAIF", "RMOMV"};

    public static final ArrayList<String> dep500AL = new ArrayList<>(Arrays.asList(dep500));
    public static final ArrayList<String> dep1000AL = new ArrayList<>(Arrays.asList(dep1000));
    public static final ArrayList<String> dep700AL = new ArrayList<>(Arrays.asList(dep700));
    public static final ArrayList<String> dep600AL = new ArrayList<>(Arrays.asList(dep600));
    public static final ArrayList<String> depAL = new ArrayList<>(Arrays.asList(dep));

    int size500 = Utility.size500;
    int size1000 = Utility.size1000;
    int size700 = Utility.size700;
    int size600 = Utility.size600;

    public ArrayList<Treno> listTreni500[] = new ArrayList[size500];
    public ArrayList<Treno> listTreni1000[] = new ArrayList[size1000];
    public ArrayList<Treno> listTreni700[] = new ArrayList[size700];
    public ArrayList<Treno> listTreni600[] = new ArrayList[size600];

    public ArrayList<Treno> listTreniNoImpianto500[] = new ArrayList[size500];
    public ArrayList<Treno> listTreniNoImpianto1000[] = new ArrayList[size1000];
    public ArrayList<Treno> listTreniNoImpianto700[] = new ArrayList[size700];
    public ArrayList<Treno> listTreniNoImpianto600[] = new ArrayList[size600];

    public ArrayList<Treno> ultimoTrenoMaterialiFermiDa24H = new ArrayList<>();

    public ArrayList<Integer> numMatFermiDa24H500 = new ArrayList<>();
    public ArrayList<Integer> numMatFermiDa24H1000 = new ArrayList<>();
    public ArrayList<Integer> numMatFermiDa24H700 = new ArrayList<>();
    public ArrayList<Integer> numMatFermiDa24H600 = new ArrayList<>();

    public ArrayList<TurnoMacchina> turnoMacchinaArrayList = new ArrayList<>();

    public ArrayList<TurnoMacchina> listTurnoMacchinaTreni500[] = new ArrayList[size500];
    public ArrayList<TurnoMacchina> listTurnoMacchinaTreni1000[] = new ArrayList[size1000];
    public ArrayList<TurnoMacchina> listTurnoMacchinaTreni700[] = new ArrayList[size700];
    public ArrayList<TurnoMacchina> listTurnoMacchinaTreni600[] = new ArrayList[size600];

    public ArrayList<Materiale> listMateriali = new ArrayList<>();



    ArrayList<Treno> listTrenoCompleto = new ArrayList<>();

    public void ExcelReaderIVUdaCercaTreniInPartenzaFuoriImpianto(String path, Date searchDate) throws IOException, InvalidFormatException {

        //      INIZIALIZZO GLI ARRAY LIST
        for (int i = 0; i<size500; i++){
            listTreniNoImpianto500[i] = new ArrayList<Treno>();
            listTreni500[i] = new ArrayList<Treno>();

            Materiale etr = new Materiale(i, "ETR500");
            listMateriali.add(etr);
        }
        for (int i = 0; i<size1000; i++){
            listTreniNoImpianto1000[i] = new ArrayList<Treno>();
            listTreni1000[i] = new ArrayList<Treno>();

            Materiale etr = new Materiale(i, "ETR1000");
            listMateriali.add(etr);
        }
        for (int i = 0; i<size700; i++){
            listTreniNoImpianto700[i] = new ArrayList<Treno>();
            listTreni700[i] = new ArrayList<Treno>();

            Materiale etr = new Materiale(i, "ETR700");
            listMateriali.add(etr);
        }
        for (int i = 0; i<size600; i++){
            listTreniNoImpianto600[i] = new ArrayList<Treno>();
            listTreni600[i] = new ArrayList<Treno>();
        }

        for (int i = 22; i<=30; i++) {
            Materiale etr = new Materiale(i, "ETR460");
            listMateriali.add(etr);
        }

        Materiale etr = new Materiale(21, "ETR463");
        listMateriali.add(etr);
        etr = new Materiale(27, "ETR463");
        listMateriali.add(etr);
        etr = new Materiale(28, "ETR463");
        listMateriali.add(etr);

        for (int i = 31; i<=45; i++) {
            etr = new Materiale(i, "ETR485");
            listMateriali.add(etr);
        }

        for (int i = 1; i<=12; i++) {
            etr = new Materiale(i, "ETR600");
            listMateriali.add(etr);
        }


        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(path));

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
        }

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        Iterator<Row> rowIterator = sheet.rowIterator();

        boolean salta =true;
        while (rowIterator.hasNext()) {
            // salta le prime  2 righe
            if(salta == true){
                Row row = rowIterator.next();
                row = rowIterator.next();
                salta = false;
            }
            Row row = rowIterator.next();

            Treno trenoTemp = new Treno();

            String tmpDatePart = dataFormatter.formatCellValue(row.getCell(10));
            String[] dataPartParts = tmpDatePart.split(" ");

            String tempDatePartTotal = dataPartParts[0];
            String tempOraPartTotal = dataPartParts[1];

            String[] dataParts = tempDatePartTotal.split("\\.");

            String tempGiorno = dataParts[0];
            String tempMese = dataParts[1];
            String tempAnno = dataParts[2];

//            LocalTime localTimePartPrev = LocalTime.of(Integer.parseInt(tempOraPartTotal.substring(0,2)), Integer.parseInt(tempOraPartTotal.substring(3,5)));
            String tempOra = tempOraPartTotal.substring(0,2);
            String tempMin = tempOraPartTotal.substring(3,5);

            trenoTemp.setDataPartenza(stringToDate(tempGiorno+"/"+tempMese+"/"+tempAnno+" "+tempOra+":"+tempMin));
//            trenoTemp.setPartenzaPrevista(localTimePartPrev);

            String tmpDateArr = dataFormatter.formatCellValue(row.getCell(11));
            String[] dataArrParts = tmpDateArr.split(" ");

            String tempDateArrTotal = dataArrParts[0];
            String tempOraArrTotal = dataArrParts[1];

            String[] dataPartsArr = tempDateArrTotal.split("\\.");

            tempGiorno = dataPartsArr[0];
            tempMese = dataPartsArr[1];
            tempAnno = dataPartsArr[2];

            tempOra = tempOraArrTotal.substring(0,2);
            tempMin = tempOraArrTotal.substring(3,5);

            trenoTemp.setDataArrivo(stringToDate(tempGiorno+"/"+tempMese+"/"+tempAnno+" "+tempOra+":"+tempMin));

            trenoTemp.setTipologiaCorsa(dataFormatter.formatCellValue(row.getCell(3)));
            trenoTemp.setDenominazioneTurnoMacc(dataFormatter.formatCellValue(row.getCell(4)));
            trenoTemp.setNumeroCorsa(dataFormatter.formatCellValue(row.getCell(5)));
            trenoTemp.setDepositoPartenza(dataFormatter.formatCellValue(row.getCell(12)));
            trenoTemp.setDepositoArrivo(dataFormatter.formatCellValue(row.getCell(13)));

            String numMaterialeCompleto = dataFormatter.formatCellValue(row.getCell(15));
            if(numMaterialeCompleto.length()==0)
                continue;

            String[] parts = numMaterialeCompleto.split("\\.");

            String tempTipMateriale = parts[0];
            String tempNumMateriale = parts[1];

            // parso il dato numeroMateriale per togliere l'orientamento
            if (tempNumMateriale.length()!=0) {
                if(tempNumMateriale.charAt(0)!='Y')
                    trenoTemp.setNumeroMateriale(Integer.parseInt(tempNumMateriale));
                else
                    continue;
            }

            if(tempTipMateriale.length()!=0)
                trenoTemp.setTipologiaMateriale("ETR"+tempTipMateriale);

//            compare = 0 se le date sono uguali
//            compare < 1 se la data in esame è minore della data in argomento
//            compare > 1 se la data in esame è maggiore della data in argomento
            int compare = trenoTemp.getDataPartenza().compareTo(searchDate);

            if(compare > 0) {

                for (int i =0; i< listMateriali.size(); i++) {
                    Materiale mat = listMateriali.get(i);
                    if (mat.getNumeroMateriale() == trenoTemp.getNumeroMateriale() && mat.getTipologiaMateriale().equals(trenoTemp.getTipologiaMateriale())) {
                        mat.addTreno(trenoTemp);
                        listMateriali.set(i,mat);
                    }
                }
            }




//
//
//
//            if(compare > 0 && trenoTemp.getTipologiaMateriale().equals("ETR500")) {
////                System.out.println(trenoTemp.toString());
//                if (!dep500AL.contains(trenoTemp.getDepositoPartenza())){
////                    System.out.println("aggiungo: " + trenoTemp.toString());
////                    System.out.println(" - " + trenoTemp.toString());
//                    listTreniNoImpianto500[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                    listTreni500[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                }else {
////                    System.out.println("     CLEAR   -> " + trenoTemp.getNumeroMateriale());
////                    listTreniNoImpianto500[trenoTemp.getNumeroMateriale()].clear();
//                }
////                listTrenoCompleto.add(trenoTemp);
//            }
//
//            if(compare > 0 && trenoTemp.getTipologiaMateriale().equals("ETR1000")) {
//                if (!dep1000AL.contains(trenoTemp.getDepositoPartenza())){
////                    System.out.println("aggiungo: " + trenoTemp.toString());
//                    listTreniNoImpianto1000[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                    listTreni1000[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                }else {
////                    System.out.println("     CLEAR   -> " + trenoTemp.getNumeroMateriale());
////                    listTreniNoImpianto1000[trenoTemp.getNumeroMateriale()].clear();
//                }
////                listTrenoCompleto.add(trenoTemp);
//            }
//
//            if(compare > 0 && trenoTemp.getTipologiaMateriale().equals("ETR700")) {
//                if (!dep700AL.contains(trenoTemp.getDepositoPartenza())){
////                    System.out.println("aggiungo: " + trenoTemp.toString());
//                    listTreniNoImpianto700[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                    listTreni700[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                }else {
////                    System.out.println("     CLEAR   -> " + trenoTemp.getNumeroMateriale());
//                    listTreniNoImpianto700[trenoTemp.getNumeroMateriale()].clear();
//                }
////                listTrenoCompleto.add(trenoTemp);
//            }
//
//            if(compare > 0 && (trenoTemp.getTipologiaMateriale().equals("ETR460") || trenoTemp.getTipologiaMateriale().equals("ETR463") || trenoTemp.getTipologiaMateriale().equals("ETR485") || trenoTemp.getTipologiaMateriale().equals("ETR600"))) {
//                if (!dep600AL.contains(trenoTemp.getDepositoPartenza())){
////                    System.out.println("aggiungo: " + trenoTemp.toString());
//                    listTreniNoImpianto600[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                    listTreni600[trenoTemp.getNumeroMateriale()].add(trenoTemp);
//                }else {
////                    System.out.println("     CLEAR   -> " + trenoTemp.getNumeroMateriale());
//                    listTreniNoImpianto600[trenoTemp.getNumeroMateriale()].clear();
//                }
////                listTrenoCompleto.add(trenoTemp);
//            }
        }

        for (Materiale mat : listMateriali) {
            ArrayList<TurnoMacchina> turnoMacc = mat.getListTurnoMacchina();
            if(!turnoMacc.isEmpty() && !depAL.contains(turnoMacc.get(0).getDepositoPartenza())) {
                mat.stampaMateriale();
            }
        }


        // Closing the workbook
        workbook.close();
    }

    public static void main (String[] args){
        String file = "./exportCerca.xlsx";
        Date dateAvanzata = Utility.stringToDate("05/03/2022 0:0");

        long timestamp = dateAvanzata.getTime();
        dateAvanzata.setTime(timestamp + 90000000);
        System.out.println(dateAvanzata.toString());

        ExcelReaderIVUDaCercaPartFuoriImpianto excelReaderCerca = new ExcelReaderIVUDaCercaPartFuoriImpianto();
        try {
//            excelReaderCerca.ExcelReaderIVUdaCercaMultipleDate(file,dateAvanzata);

            excelReaderCerca.ExcelReaderIVUdaCercaTreniInPartenzaFuoriImpianto(file,dateAvanzata);
            excelReaderCerca.printListTurnoMacchina();
            excelReaderCerca.materialiFermiDa24H(dateAvanzata);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public Materiale cercaMateriale (ArrayList<Materiale> listMateriali, int numMat, String tipologiaMateriale){
        for(Materiale mat: listMateriali){
            if (mat.getNumeroMateriale() == numMat && mat.getTipologiaMateriale().equals(tipologiaMateriale)){
                return mat;
            }
        }
        return null;
    }

    public void printListTurnoMacchina() {
        System.out.println("");
        printTreniMateriali(listTreniNoImpianto500, "ETR500");
        System.out.println("");

        System.out.println("");
        printTreniMateriali(listTreniNoImpianto1000, "ETR1000");
        System.out.println("");

        System.out.println("");
        printTreniMateriali(listTreniNoImpianto700, "ETR700");
        System.out.println("");

        System.out.println("");
        printTreniMateriali(listTreniNoImpianto600, "ETR600 - ETR485 - ETR460 - ETR463");
        System.out.println("");
    }

    public void printTreniMateriali(ArrayList<Treno> listTreniNoImpianto[] , String tipoMateriale){

        System.out.println("-------------------------------- "+ tipoMateriale +" ----------------------------------------");
        for (int i=0; i<listTreniNoImpianto.length; i++) {
            if (listTreniNoImpianto[i].size()!=0)
                System.out.println("TRENI del MATERIALE "+ tipoMateriale+ " #" + i);

            for (Treno treno : listTreniNoImpianto[i]) {
                System.out.println(treno.toString());
            }
            if (listTreniNoImpianto[i].size()!=0)
                System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------------------");

    }

    public void materialiFermiDa24H(Date date){
        compilaALnumeriMateriali();
        Date dateOriginal = date;
        long timestamp = date.getTime();
        dateOriginal.setTime(timestamp - 90000000);
        System.out.println(date.toString());


        System.out.println();
        for (int i : numMateriali500){
            ArrayList<Treno> list500 = listTreni500[i];
            int sizeList500 = list500.size();
            if (!list500.isEmpty()){
                Treno t = list500.get(sizeList500-1);
                if (t.getDataArrivo().before(dateOriginal)) {
                    numMatFermiDa24H500.add(i);
                    ultimoTrenoMaterialiFermiDa24H.add(t);
                }
            }
            else{
                Treno t = new Treno();
                t.setNumeroMateriale(i);
                t.setTipologiaMateriale("ETR500");
                ultimoTrenoMaterialiFermiDa24H.add(t);
                numMatFermiDa24H500.add(i);
            }
        }
        for (int i : numMateriali1000){
            ArrayList<Treno> list1000 = listTreni1000[i];
            int sizeList500 = list1000.size();
            if (!list1000.isEmpty()){
                Treno t = list1000.get(sizeList500-1);
                if (t.getDataArrivo().before(dateOriginal)) {
                    numMatFermiDa24H1000.add(i);
                    ultimoTrenoMaterialiFermiDa24H.add(t);
                }
            }
            else{
                Treno t = new Treno();
                t.setNumeroMateriale(i);
                t.setTipologiaMateriale("ETR1000");
                ultimoTrenoMaterialiFermiDa24H.add(t);
                numMatFermiDa24H1000.add(i);
            }
        }
        for (int i : numMateriali700){
            ArrayList<Treno> list700 = listTreni700[i];
            int sizeList500 = list700.size();
            if (!list700.isEmpty()){
                Treno t = list700.get(sizeList500-1);
                if (t.getDataArrivo().before(dateOriginal)) {
                    numMatFermiDa24H700.add(i);
                    ultimoTrenoMaterialiFermiDa24H.add(t);
                }
            }
            else{
                Treno t = new Treno();
                t.setNumeroMateriale(i);
                t.setTipologiaMateriale("ETR700");
                ultimoTrenoMaterialiFermiDa24H.add(t);
                numMatFermiDa24H700.add(i);
            }
        }
        for (int i : numMateriali600){
            ArrayList<Treno> list600 = listTreni600[i];
            int sizeList500 = list600.size();
            if (!list600.isEmpty()){
                Treno t = list600.get(sizeList500-1);
                if (t.getDataArrivo().before(dateOriginal)) {
                    numMatFermiDa24H600.add(i);
                    ultimoTrenoMaterialiFermiDa24H.add(t);
                }
            }
            else{
                Treno t = new Treno();
                t.setNumeroMateriale(i);
                if (i>=1 && i<=12) {
                    t.setTipologiaMateriale("ETR600");
                }
                else if (i>=21 && i<=28) {
                    t.setTipologiaMateriale("ETR460");
                }
                else if (i==30) {
                    t.setTipologiaMateriale("ETR460");
                }
                else if (i>=31 && i<=45) {
                    t.setTipologiaMateriale("ETR485");
                }

                ultimoTrenoMaterialiFermiDa24H.add(t);
                numMatFermiDa24H600.add(i);
            }
        }
        printMaterialiFermiDa24H();
    }

    public void printMaterialiFermiDa24H(){
        compilaALnumeriMateriali();
        System.out.println("Materiali Fermi da 24 H");
        String print = "";
        for (int i : numMatFermiDa24H500){
            print += i + ", ";
        }
        System.out.println(print);

        System.out.println();
        print = "";
        for (int i : numMatFermiDa24H1000){
            print += i + ", ";
        }
        System.out.println(print);

        System.out.println();
        print = "";
        for (int i : numMatFermiDa24H700){
            print += i + ", ";
        }
        System.out.println(print);

        System.out.println();
        print = "";
        for (int i : numMatFermiDa24H600){
            print += i + ", ";
        }
        System.out.println(print);

        for (Treno t : ultimoTrenoMaterialiFermiDa24H){
            System.out.println(t.toString());
        }
    }
}