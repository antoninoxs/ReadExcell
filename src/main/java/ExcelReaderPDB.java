import Entity.SegnalazioniPDB;
import Entity.SegnalazioniSO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class ExcelReaderPDB {
    public static ArrayList<SegnalazioniPDB> listSegnalazioniPDB= new ArrayList<>();

    public ArrayList <SegnalazioniPDB> ExcelReaderPDB(String path) throws IOException, InvalidFormatException {

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
            // salta la prima riga
            if(salta == true){
                for(int i = 0; i<4; i++) {
//                    System.out.println("SALTA ");
                    Row row = rowIterator.next();
                }
                salta = false;
            }

            Row row = rowIterator.next();
            // Now let's iterate over the columns of the current row

            SegnalazioniPDB segnPDB = new SegnalazioniPDB();

//            System.out.println("ciao " + dataFormatter.formatCellValue(row.getCell(2)));
            if(dataFormatter.formatCellValue(row.getCell(1)).length() !=0){
                segnPDB.setIdSegnalazione(dataFormatter.formatCellValue(row.getCell(1)));
                segnPDB.setNumeroTreno(dataFormatter.formatCellValue(row.getCell(3)));

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                Date date = null;
                try {
                    date = format.parse(dataFormatter.formatCellValue(row.getCell(4)));
                    segnPDB.setDataTreno(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                segnPDB.setCodice(dataFormatter.formatCellValue(row.getCell(6)));
                segnPDB.setOrgano(dataFormatter.formatCellValue(row.getCell(9)));
                segnPDB.setUbicazione(dataFormatter.formatCellValue(row.getCell(10)));
                segnPDB.setStato(dataFormatter.formatCellValue(row.getCell(11)));
                segnPDB.setDescrizione(dataFormatter.formatCellValue(row.getCell(12)));
                segnPDB.setPosizione("w"+dataFormatter.formatCellValue(row.getCell(13)));

                String temp = dataFormatter.formatCellValue(row.getCell(14));
                if (temp.length() !=0) {
//                    System.out.println(temp);
                    segnPDB.setTipologiaVeicolo(temp.substring(0, temp.length()-3));
                    segnPDB.setNumeroMateriale(Integer.parseInt(temp.substring(temp.length()-3, temp.length())));
                }
                if (isSegnalazioneUtile(segnPDB))
                    listSegnalazioniPDB.add(segnPDB);
            }
        }

        // Closing the workbook
        workbook.close();

        return listSegnalazioniPDB;
    }

//    Funzione utilizzata per filtrare le segnalazioni utili
    public boolean isSegnalazioneUtile(SegnalazioniPDB segnPDB){
        boolean utile = false;

        String codice = segnPDB.getCodice();

        switch (codice){
            case "114 - ETR Guasto porte - generico" :
                if (segnPDB.getOrgano().equals("Porta salita"))
                    utile = true;
                break;
            case "140 - Ritirata fuori servizio mat. ORD" :
                if (segnPDB.getOrgano().equals("WC"))
                    utile = true;
                break;
            case "143 - ETR Organo Ritirate" :
                if (segnPDB.getOrgano().equals("Centralina"))
                    utile = true;
                if (segnPDB.getOrgano().equals("Porta ritirata"))
                    utile = true;
                break;
            case "160 - Anormalità Veicoli" :
                if (segnPDB.getOrgano().equals("WC"))
                    utile = true;
                if (segnPDB.getOrgano().equals("Climatizzazione"))
                    utile = true;
                if (segnPDB.getOrgano().equals("Convertitore"))
                    utile = true;
                break;
        }
        return utile;
    }
    public static void printListPDB(){
        System.out.println("Dimensione listSegnalazioniSO: " + listSegnalazioniPDB.size());
        for (int i=0; i<listSegnalazioniPDB.size(); i++){
            System.out.println(i+1 + " " + listSegnalazioniPDB.get(i).toString());
        }
    }


    public static void main(String[] args){
        String file = "./ListaSegnalazioniPDB.xls";
        ExcelReaderPDB excelReaderPDB = new ExcelReaderPDB();
        try {
            ArrayList<SegnalazioniPDB> listSegnalazioni = excelReaderPDB.ExcelReaderPDB(file);
            printListPDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}