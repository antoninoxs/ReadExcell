import Entity.SegnalazioniPDB;
import Entity.StrisciaIvu;
import Entity.TurnoMacchina;
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

    ArrayList<TurnoMacchina> listTurnoMacchinaCompleto = new ArrayList<>();


    public ArrayList<TurnoMacchina> ExcelReaderIVUdaCerca(String path, Date searchDate) throws IOException, InvalidFormatException {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(path));


        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
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

            TurnoMacchina turnMacc = new TurnoMacchina();

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
                turnMacc.setTipologiaVeicolo("ETR"+tempTipMateriale);

            if(turnMacc.getDataPartenza().equals(searchDate) && turnMacc.getTipologiaCorsa().equals("Corsa di linea")) {
                listTurnoMacchinaCompleto.add(turnMacc);
            }
        }

        // Closing the workbook
        workbook.close();

        return listTurnoMacchinaCompleto;

    }

    public static void main (String[] args){
        String file = "./exportCerca.xlsx";
        Date date = Utility.stringToDate("07/10/2021");
        ExcelReaderIVUDaCerca excelReaderCerca = new ExcelReaderIVUDaCerca();
        try {
            excelReaderCerca.ExcelReaderIVUdaCerca(file,date);
            excelReaderCerca.printListTurnoMacchina();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void printListTurnoMacchina() {
        System.out.println("------------------------------------------------------------------------");

        for (TurnoMacchina turnoMacchina : listTurnoMacchinaCompleto){
            System.out.println(turnoMacchina.toString());
        }

        System.out.println();
    }

    public void printTurnoMacchina(){
        System.out.println("------------------------------------------------------------------------");



        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

}