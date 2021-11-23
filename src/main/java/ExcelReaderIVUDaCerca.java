import Entity.StrisciaIvu;
import Entity.Treno;
import Utility.Utility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import static Utility.Utility.*;

public class ExcelReaderIVUDaCerca {
    public static final String[] dep500 = {"MSDL", "MIMA", "NAIF"};
    public static final String[] dep1000 = {"MIMA", "NAIF"};
    public static final String[] dep700 = {"MIMA"};
    public static final String[] dep600 = {"RMOMV"};

    public static final ArrayList<String> dep500AL = new ArrayList<>(Arrays.asList(dep500));
    public static final ArrayList<String> dep1000AL = new ArrayList<>(Arrays.asList(dep1000));
    public static final ArrayList<String> dep700AL = new ArrayList<>(Arrays.asList(dep700));
    public static final ArrayList<String> dep600AL = new ArrayList<>(Arrays.asList(dep600));

    int size500 = 61;
    int size1000 = 51;
    int size700 = 18;
    int size600 = 50;

    public ArrayList<Treno> listTreniNoImpianto500[] = new ArrayList[size500];
    public ArrayList<Treno> listTreniNoImpianto1000[] = new ArrayList[size1000];
    public ArrayList<Treno> listTreniNoImpianto700[] = new ArrayList[size700];
    public ArrayList<Treno> listTreniNoImpianto600[] = new ArrayList[size600];

    ArrayList<Treno> listTrenoCompleto = new ArrayList<>();


    public ArrayList<Treno> ExcelReaderIVUdaCerca(String path, Date searchDate) throws IOException, InvalidFormatException {

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

            Treno turnMacc = new Treno();

            String tmpDate = dataFormatter.formatCellValue(row.getCell(2));
            String[] dataParts = tmpDate.split("\\.");

            String tempGiorno = dataParts[0];
            String tempMese = dataParts[1];
            String tempAnno = dataParts[2];
            turnMacc.setDataPartenza(stringToDate(tempGiorno+"/"+tempMese+"/"+tempAnno));

            turnMacc.setTipologiaCorsa(dataFormatter.formatCellValue(row.getCell(3)));
            turnMacc.setDenominazioneTurnoMacc(dataFormatter.formatCellValue(row.getCell(4)));
            turnMacc.setNumeroCorsa(dataFormatter.formatCellValue(row.getCell(5)));
            turnMacc.setDepositoPartenza(dataFormatter.formatCellValue(row.getCell(12)));
            turnMacc.setDepositoArrivo(dataFormatter.formatCellValue(row.getCell(13)));

            String numMaterialeCompleto = dataFormatter.formatCellValue(row.getCell(15));
            if(numMaterialeCompleto.length()==0)
                continue;

            String[] parts = numMaterialeCompleto.split("\\.");

            String tempTipMateriale = parts[0];
            String tempNumMateriale = parts[1];

            // parso il dato numeroMateriale per togliere l'orientamento
            if (tempNumMateriale.length()!=0)
                turnMacc.setNumeroMateriale(Integer.parseInt(tempNumMateriale));

            if(tempTipMateriale.length()!=0)
                turnMacc.setTipologiaMateriale("ETR"+tempTipMateriale);

            if(turnMacc.getDataPartenza().equals(searchDate) && turnMacc.getTipologiaCorsa().equals("Corsa di linea")) {
                listTrenoCompleto.add(turnMacc);
            }
        }

        // Closing the workbook
        workbook.close();

        return listTrenoCompleto;
    }


    public void ExcelReaderIVUdaCercaMultipleDate(String path, Date searchDate) throws IOException, InvalidFormatException {

        //      INIZIALIZZO GLI ARRAY LIST
        for (int i = 0; i<size500; i++){
            listTreniNoImpianto500[i] = new ArrayList<Treno>();
        }
        for (int i = 0; i<size1000; i++){
            listTreniNoImpianto1000[i] = new ArrayList<Treno>();
        }
        for (int i = 0; i<size700; i++){
            listTreniNoImpianto700[i] = new ArrayList<Treno>();
        }
        for (int i = 0; i<size600; i++){
            listTreniNoImpianto600[i] = new ArrayList<Treno>();
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
            trenoTemp.setDataPartenza(stringToDate(tempGiorno+"/"+tempMese+"/"+tempAnno));

            LocalTime localTimePartPrev = LocalTime.of(Integer.parseInt(tempOraPartTotal.substring(0,2)), Integer.parseInt(tempOraPartTotal.substring(3,5)));
            trenoTemp.setPartenzaPrevista(localTimePartPrev);


            String tmpDateArr = dataFormatter.formatCellValue(row.getCell(10));
            String[] dataPartArr = tmpDateArr.split(" ");

            String tempDateArrTotal = dataPartArr[0];
            String tempOraArrTotal = dataPartArr[1];

            String[] dataPartsArr = tempDateArrTotal.split("\\.");

            tempGiorno = dataPartsArr[0];
            tempMese = dataPartsArr[1];
            tempAnno = dataPartsArr[2];
//            trenoTemp.setDataArrivo(stringToDate(tempGiorno+"/"+tempMese+"/"+tempAnno));

            LocalTime localTimeArrPrev = LocalTime.of(Integer.parseInt(tempOraArrTotal.substring(0,2)), Integer.parseInt(tempOraArrTotal.substring(3,5)));
            trenoTemp.setArrivoPrevisto(localTimeArrPrev);

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
            if (tempNumMateriale.length()!=0)
                trenoTemp.setNumeroMateriale(Integer.parseInt(tempNumMateriale));

            if(tempTipMateriale.length()!=0)
                trenoTemp.setTipologiaMateriale("ETR"+tempTipMateriale);

//            compare = 0 se le date sono uguali
//            compare < 1 se la data in esame è minore della data in argomento
//            compare > 1 se la data in esame è maggiore della data in argomento
            int compare = trenoTemp.getDataPartenza().compareTo(searchDate);
            if(compare<=0 && trenoTemp.getTipologiaMateriale().equals("ETR500")) {
                if (!dep500AL.contains(trenoTemp.getDepositoArrivo())){
                    System.out.println("aggiungo: " + trenoTemp.toString());
                    listTreniNoImpianto500[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }else {
                    System.out.println("     CLEAR");
                    listTreniNoImpianto500[trenoTemp.getNumeroMateriale()].clear();
                }
//                listTrenoCompleto.add(trenoTemp);
            }

            if(compare<=0 && trenoTemp.getTipologiaMateriale().equals("ETR1000")) {
                if (!dep1000AL.contains(trenoTemp.getDepositoArrivo())){
                    System.out.println("aggiungo: " + trenoTemp.toString());
                    listTreniNoImpianto1000[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }else {
                    System.out.println("     CLEAR");
                    listTreniNoImpianto1000[trenoTemp.getNumeroMateriale()].clear();
                }
//                listTrenoCompleto.add(trenoTemp);
            }

            if(compare<=0 && trenoTemp.getTipologiaMateriale().equals("ETR700")) {
                if (!dep700AL.contains(trenoTemp.getDepositoArrivo())){
                    System.out.println("aggiungo: " + trenoTemp.toString());
                    listTreniNoImpianto700[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }else {
                    System.out.println("     CLEAR");
                    listTreniNoImpianto700[trenoTemp.getNumeroMateriale()].clear();
                }
//                listTrenoCompleto.add(trenoTemp);
            }

            if(compare<=0 && (trenoTemp.getTipologiaMateriale().equals("ETR460") || trenoTemp.getTipologiaMateriale().equals("ETR463") || trenoTemp.getTipologiaMateriale().equals("ETR485") || trenoTemp.getTipologiaMateriale().equals("ETR600"))) {
                if (!dep600AL.contains(trenoTemp.getDepositoArrivo())){
                    System.out.println("aggiungo: " + trenoTemp.toString());
                    listTreniNoImpianto600[trenoTemp.getNumeroMateriale()].add(trenoTemp);
                }else {
                    System.out.println("     CLEAR   -> " + trenoTemp.getNumeroMateriale());
                    listTreniNoImpianto600[trenoTemp.getNumeroMateriale()].clear();
                }
//                listTrenoCompleto.add(trenoTemp);
            }
        }

        // Closing the workbook
        workbook.close();
    }

    public static void main (String[] args){
        String file = "./exportCerca.xlsx";
        Date date = Utility.stringToDate("08/11/2021");
        ExcelReaderIVUDaCerca excelReaderCerca = new ExcelReaderIVUDaCerca();
        try {
            excelReaderCerca.ExcelReaderIVUdaCercaMultipleDate(file,date);
            excelReaderCerca.printListTurnoMacchina();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void printListTurnoMacchina() {
        System.out.println("****************************************************************************************");
        printTreniMateriali(listTreniNoImpianto500, "ETR500");
        System.out.println("****************************************************************************************");

        System.out.println("****************************************************************************************");
        printTreniMateriali(listTreniNoImpianto1000, "ETR1000");
        System.out.println("****************************************************************************************");

        System.out.println("****************************************************************************************");
        printTreniMateriali(listTreniNoImpianto700, "ETR700");
        System.out.println("****************************************************************************************");

        System.out.println("****************************************************************************************");
        printTreniMateriali(listTreniNoImpianto600, "ETR600 - ETR485 - ETR460 - ETR463");
        System.out.println("****************************************************************************************");
    }

    public void printTreniMateriali(ArrayList<Treno> listTreniNoImpianto[] , String tipoMateriale){

        System.out.println("--------------------------------"+ tipoMateriale +"----------------------------------------");
        for (int i=0; i<listTreniNoImpianto.length; i++) {
            System.out.println("TRENI del MATERIALE "+ tipoMateriale+ " #" + i);
            for (Treno treno : listTreniNoImpianto[i]) {
                System.out.println(treno.toString());
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------------------");

    }

}