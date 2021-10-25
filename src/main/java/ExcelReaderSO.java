import Entity.SegnalazioniSO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReaderSO {
    public static ArrayList<SegnalazioniSO> listSegnalazioniSO= new ArrayList<>();

    public ArrayList <SegnalazioniSO> ExcelReaderSO(String path) throws IOException, InvalidFormatException {

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

            SegnalazioniSO segnSo = new SegnalazioniSO();

//            System.out.println("ciao " + dataFormatter.formatCellValue(row.getCell(2)));
            if(dataFormatter.formatCellValue(row.getCell(1)).length() !=0){
                segnSo.setIdSegnalazione(dataFormatter.formatCellValue(row.getCell(1)));
                segnSo.setStato(dataFormatter.formatCellValue(row.getCell(2)));
                segnSo.setNumeroTreno(dataFormatter.formatCellValue(row.getCell(4)));


                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                Date date = null;
                try {
                    date = format.parse(dataFormatter.formatCellValue(row.getCell(5)));
                    segnSo.setDataTreno(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                segnSo.setCodiceEdescrizione(dataFormatter.formatCellValue(row.getCell(9)));
                segnSo.setNota(dataFormatter.formatCellValue(row.getCell(10)));

                String temp = dataFormatter.formatCellValue(row.getCell(11));
                if (temp.length() !=0) {
//                    System.out.println(temp);
                    segnSo.setTipologiaVeicolo(temp.substring(0, temp.length()-3));
                    segnSo.setNumeroMateriale(Integer.parseInt(temp.substring(temp.length()-3, temp.length())));
                }
                listSegnalazioniSO.add(segnSo);
            }
        }

        // Closing the workbook
        workbook.close();

        return listSegnalazioniSO;
    }

    public static void printListSO(){
        System.out.println("Dimensione listSegnalazioniSO: " + listSegnalazioniSO.size());
        for (int i=0; i<listSegnalazioniSO.size(); i++){
            System.out.println(i+1 + " " + listSegnalazioniSO.get(i).toString());
        }
    }

}