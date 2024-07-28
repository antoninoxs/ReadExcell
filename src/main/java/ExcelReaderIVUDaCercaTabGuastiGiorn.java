import Entity.Treno;
import Utility.Utility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static Utility.Utility.*;
//Funzione principale per la creazione della tabella Situazione ETR Giornaliera

public class ExcelReaderIVUDaCercaTabGuastiGiorn {
    public static final String[] dep500 = {"MSDL", "MIMA", "NAIF"};
    public static final String[] dep1000 = {"MIMA", "NAIF"};
    public static final String[] dep700 = {"MIMA", "MSDL"};
    public static final String[] dep600 = {"RMOMV"};

    public static final ArrayList<String> dep500AL = new ArrayList<>(Arrays.asList(dep500));
    public static final ArrayList<String> dep1000AL = new ArrayList<>(Arrays.asList(dep1000));
    public static final ArrayList<String> dep700AL = new ArrayList<>(Arrays.asList(dep700));
    public static final ArrayList<String> dep600AL = new ArrayList<>(Arrays.asList(dep600));

    int size500 = Utility.size500;
    int size1000 = Utility.size1000;
    int size700 = Utility.size700;
    int size600 = Utility.size600;

    public ArrayList<Treno> listTreni500[] = new ArrayList[size500];
    public ArrayList<Treno> listTreni1000[] = new ArrayList[size1000];
    public ArrayList<Treno> listTreni700[] = new ArrayList[size700];
    public ArrayList<Treno> listTreni600[] = new ArrayList[size600];

    public ArrayList<Treno> listTreniGuastiGiornalieri500[] = new ArrayList[size500];
    public ArrayList<Treno> listTreniGuastiGiornalieri1000[] = new ArrayList[size1000];
    public ArrayList<Treno> listTreniGuastiGiornalieri700[] = new ArrayList[size700];
    public ArrayList<Treno> listTreniGuastiGiornalieri600[] = new ArrayList[size600];

    public ArrayList<Treno> ultimoTrenoMaterialiFermiDa24H = new ArrayList<>();


    ArrayList<Treno> listTrenoCompleto = new ArrayList<>();

    public void ExcelReaderIVUdaCercaMultipleDate(String path, Date searchDate) throws IOException, InvalidFormatException {

        //      INIZIALIZZO GLI ARRAY LIST
        for (int i = 0; i<size500; i++){
            listTreniGuastiGiornalieri500[i] = new ArrayList<Treno>();
            listTreni500[i] = new ArrayList<Treno>();
        }
        for (int i = 0; i<size1000; i++){
            listTreniGuastiGiornalieri1000[i] = new ArrayList<Treno>();
            listTreni1000[i] = new ArrayList<Treno>();
        }
        for (int i = 0; i<size700; i++){
            listTreniGuastiGiornalieri700[i] = new ArrayList<Treno>();
            listTreni700[i] = new ArrayList<Treno>();
        }
        for (int i = 0; i<size600; i++){
            listTreniGuastiGiornalieri600[i] = new ArrayList<Treno>();
            listTreni600[i] = new ArrayList<Treno>();
        }

        Date minDate = Utility.stringToDate(searchDate.getDate()+"/"+(searchDate.getMonth()+1)+"/"+(searchDate.getYear()+1900)+ " 00:01");
//        Date minDate = stringToDate("23/07/2024 00:01");
        Date maxDate = Utility.stringToDate(searchDate.getDate()+"/"+(searchDate.getMonth()+1)+"/"+(searchDate.getYear()+1900)+ " 23:59");

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

        int contatoreRiga = 3;
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

            // salto tutti le note messe su ivu identificate come Tipologia corsa Stallo
            if(dataFormatter.formatCellValue(row.getCell(14)).compareTo("Stallo")==0) {
                contatoreRiga++;
                continue;
            }

            String tmpDatePart = dataFormatter.formatCellValue(row.getCell(10));

            try{

                String[] dataPartParts = tmpDatePart.split(" ");
                String tempDatePartTotal = dataPartParts[0];
                String tempOraPartTotal = dataPartParts[1];

//                System.out.println(tempOraPartTotal);
//                System.out.println(contatoreRiga);
//                System.out.println("NUMERO CORSA:" + dataFormatter.formatCellValue(row.getCell(5)));

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

                trenoTemp.setTipologiaCorsa(dataFormatter.formatCellValue(row.getCell(14)));
                trenoTemp.setDenominazioneTurnoMacc(dataFormatter.formatCellValue(row.getCell(4)));
                trenoTemp.setNumeroCorsa(dataFormatter.formatCellValue(row.getCell(5)));
                trenoTemp.setDepositoPartenza(dataFormatter.formatCellValue(row.getCell(12)));
                trenoTemp.setDepositoArrivo(dataFormatter.formatCellValue(row.getCell(13)));

                String numMaterialeCompleto = dataFormatter.formatCellValue(row.getCell(15));
                if(numMaterialeCompleto.length()==0) {
                    contatoreRiga++;
                    continue;
                }

                String[] parts = numMaterialeCompleto.split("\\.");

                String tempTipMateriale = parts[0];
                String tempNumMateriale = parts[1];

                // parso il dato numeroMateriale per togliere l'orientamento
                if (tempNumMateriale.length()!=0) {
                    if(tempNumMateriale.charAt(0)!='Y')
                        trenoTemp.setNumeroMateriale(Integer.parseInt(tempNumMateriale));
                    else {
                        contatoreRiga++;
                        continue;
                    }
                }

                if(tempTipMateriale.length()!=0)
                    trenoTemp.setTipologiaMateriale("ETR"+tempTipMateriale);


                contatoreRiga++;
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println();
                System.out.println("FUNZIONE ExcelReaderIVUDaCerca");
                System.out.println("ERRORE ALLA RIGA: " + contatoreRiga + " del file exportCerca.xlsx");
                System.out.println("Aprire il file excel e verificare la riga. E riavviare il programma");
                System.out.println(e.toString());
                System.out.println();
            }

//            compare = 0 se le date sono uguali
//            compare < 1 se la data in esame è minore della data in argomento
//            compare > 1 se la data in esame è maggiore della data in argomento

            int compareDataPartenzaConSearchDate = trenoTemp.getDataPartenza().compareTo(searchDate);
            int compareDataPartenzaConMinDate = trenoTemp.getDataPartenza().compareTo(minDate);
            int compareDataArrivo = trenoTemp.getDataArrivo().compareTo(maxDate);

            // è un 500. La data di partenza è compresa tra mezzanotte e le 17:00
            if(trenoTemp.getTipologiaMateriale().equals("ETR500") ){
                if(compareDataPartenzaConMinDate >= 0 && compareDataPartenzaConSearchDate<=0) {
//                   System.out.println("COMPARE SearchDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + searchDate + " -> " +compareDataPartenzaConSearchDate);
//                    System.out.println("COMPARE MINDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + minDate + " -> " +compareDataPartenzaConSearchDate);

                    listTreniGuastiGiornalieri500[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                    listTreni500[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }
            }

            if(trenoTemp.getTipologiaMateriale().equals("ETR1000")) {
                if(compareDataPartenzaConMinDate >= 0 && compareDataPartenzaConSearchDate<=0) {
//                   System.out.println("COMPARE SearchDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + searchDate + " -> " +compareDataPartenzaConSearchDate);
//                    System.out.println("COMPARE MINDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + minDate + " -> " +compareDataPartenzaConSearchDate);

                    listTreniGuastiGiornalieri1000[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                    listTreni1000[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }
//                listTrenoCompleto.add(trenoTemp);
            }

            if(trenoTemp.getTipologiaMateriale().equals("ETR700")) {
                if(compareDataPartenzaConMinDate >= 0 && compareDataPartenzaConSearchDate<=0) {
//                  System.out.println("COMPARE SearchDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + searchDate + " -> " +compareDataPartenzaConSearchDate);
//                  System.out.println("COMPARE MINDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + minDate + " -> " +compareDataPartenzaConSearchDate);

                    listTreniGuastiGiornalieri700[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                    listTreni700[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }
//                listTrenoCompleto.add(trenoTemp);
            }

            if((trenoTemp.getTipologiaMateriale().equals("ETR460") || trenoTemp.getTipologiaMateriale().equals("ETR463") || trenoTemp.getTipologiaMateriale().equals("ETR485") || trenoTemp.getTipologiaMateriale().equals("ETR600"))) {
                if(compareDataPartenzaConMinDate >= 0 && compareDataPartenzaConSearchDate<=0) {
//                  System.out.println("COMPARE SearchDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + searchDate + " -> " +compareDataPartenzaConSearchDate);
//                  System.out.println("COMPARE MINDATE - "+ trenoTemp.getNumeroCorsa()+ "   " +trenoTemp.getDataPartenza() + " - " + minDate + " -> " +compareDataPartenzaConSearchDate);

                    listTreniGuastiGiornalieri600[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                    listTreni600[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }
//                listTrenoCompleto.add(trenoTemp);
            }
        }
        // Closing the workbook
        workbook.close();
    }

    public static void main (String[] args){
        String file = "./exportCerca.xlsx";
        Date dateAvanzata = Utility.stringToDate("23/07/2024 17:00");

        long timestamp = dateAvanzata.getTime();
//        dateAvanzata.setTime(timestamp + 90000000);
        System.out.println(dateAvanzata.toString());

        ExcelReaderIVUDaCercaTabGuastiGiorn excelReaderCerca = new ExcelReaderIVUDaCercaTabGuastiGiorn();
        try {
            excelReaderCerca.ExcelReaderIVUdaCercaMultipleDate(file,dateAvanzata);
            excelReaderCerca.printListTurnoMacchina();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public void printListTurnoMacchina() {
        System.out.println("");
        printTreniMateriali(listTreniGuastiGiornalieri500, "ETR500");
        System.out.println("");

        System.out.println("");
        printTreniMateriali(listTreniGuastiGiornalieri1000, "ETR1000");
        System.out.println("");

        System.out.println("");
        printTreniMateriali(listTreniGuastiGiornalieri700, "ETR700");
        System.out.println("");

        System.out.println("");
        printTreniMateriali(listTreniGuastiGiornalieri600, "ETR600 - ETR485 - ETR460 - ETR463");
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

    public void printListMaterialiServizio(){
        System.out.println("");
        printMaterialeServizi(listTreniGuastiGiornalieri500, "ETR500");
        System.out.println("");

        System.out.println("");
        printMaterialeServizi(listTreniGuastiGiornalieri1000, "ETR1000");
        System.out.println("");

        System.out.println("");
        printMaterialeServizi(listTreniGuastiGiornalieri700, "ETR700");
        System.out.println("");

        System.out.println("");
        printMaterialeServizi(listTreniGuastiGiornalieri600, "ETR600 - ETR485 - ETR460 - ETR463");
        System.out.println("");
    }
    public void printMaterialeServizi(ArrayList<Treno> listTreniNoImpianto[] , String tipoMateriale){

        System.out.println("-------------------------------- "+ tipoMateriale +" ----------------------------------------");
        for (int i=0; i<listTreniNoImpianto.length; i++) {

            String tString = "" + tipoMateriale +"."+i+ ":  ";

            for (Treno treno : listTreniNoImpianto[i]) {
                if(treno.getTipologiaCorsa().equals("Corsa di linea")) {
                    tString = tString.concat(treno.getNumeroCorsa() + " -> ");
                }
            }
            System.out.println(tString);
        }
        System.out.println("-------------------------------------------------------------------------------------------");

    }

}