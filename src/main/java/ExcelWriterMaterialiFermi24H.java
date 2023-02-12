import Entity.SegnalazioniSO;
import Entity.StrisciaIvu;
import Entity.Treno;
import Utility.Utility;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelWriterMaterialiFermi24H {


    public void write(ArrayList<Treno> ultimoTrenoMatFermiDa24H, String dateToSearch)  throws IOException, ParseException {


        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Materiali Fermi 24H");


        XSSFFont font= workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);

//      Cellstrule per impostare testo a capo
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);

//      Allinemanto verticale e orizzontale al centro
        CellStyle cs2 =  workbook.createCellStyle();
        cs2.setAlignment(HorizontalAlignment.CENTER);
        cs2.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2.setWrapText(true);

//      STYLE per l'intestazione
        CellStyle cs3 =  workbook.createCellStyle();
        cs3.setAlignment(HorizontalAlignment.CENTER);
        cs3.setVerticalAlignment(VerticalAlignment.CENTER);
        cs3.setFont(font);
        cs3.setWrapText(true);


        int rowCount = 0;
        Row row = sheet.createRow(++rowCount);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateToSearch));
        c.add(Calendar.DATE, 1);  // number of days to add
        String nextDate = sdf.format(c.getTime());  // dt is now the new date

        Cell cell = row.createCell(2);
        cell.setCellValue((String) "MATERIALI FERMI DA 24H DEL: " + dateToSearch);
        cell.setCellStyle(cs3);

        row = sheet.createRow(++rowCount);
        cell = row.createCell(0);
        cell.setCellValue((String) "LOCALITA'");
        cell.setCellStyle(cs3);

        cell = row.createCell(1);
        cell.setCellValue((String) "ULTIMO SERVIZIO");
        cell.setCellStyle(cs3);

        cell = row.createCell(2);
        cell.setCellValue((String) "CONVOGLIO");
        cell.setCellStyle(cs3);

        cell = row.createCell(3);
        cell.setCellValue((String) "MOTIVAZIONE");
        cell.setCellStyle(cs3);

        for (Treno treno : ultimoTrenoMatFermiDa24H){
            row = sheet.createRow(++rowCount);

//            System.out.println(treno.toString());

            cell = row.createCell(0);
//          spazio dedicato alla località nella quale il materiale è fermo
            String depArrivo = treno.getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs2);

            cell = row.createCell(1);
//          Spazio dedicato ultimo servizio
            cell.setCellValue(treno.getNumeroCorsa());
            cell.setCellStyle(cs2);

            cell = row.createCell(2);
//          Spazio dedicato alla convoglio
            cell.setCellValue(treno.getTipologiaMateriale()+"."+treno.getNumeroMateriale());
            cell.setCellStyle(cs2);

            cell = row.createCell(3);
//          Spazio dedicato alla motivazione del fermo
            cell.setCellValue("");
            cell.setCellStyle(cs2);

        }

//      adjust column width to fit the content
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);



        try (FileOutputStream outputStream = new FileOutputStream("Tabella treni Fermi 24H.xlsx")) {
            workbook.write(outputStream);
        }
    }

    public void write(ArrayList<Integer> list500, ArrayList<Integer> list1000, ArrayList<Integer> list700, ArrayList<Integer> list600,  ArrayList<Treno> ultimoTrenoMatFermiDa24H, String dateToSearch) throws IOException, ParseException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Materiali Fermi 24H");


        XSSFFont font= workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);

//      Cellstrule per impostare testo a capo
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);

//      Allinemanto verticale e orizzontale al centro
        CellStyle cs2 =  workbook.createCellStyle();
        cs2.setAlignment(HorizontalAlignment.CENTER);
        cs2.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2.setWrapText(true);

//      STYLE per l'intestazione
        CellStyle cs3 =  workbook.createCellStyle();
        cs3.setAlignment(HorizontalAlignment.CENTER);
        cs3.setVerticalAlignment(VerticalAlignment.CENTER);
        cs3.setFont(font);
        cs3.setWrapText(true);


        int rowCount = 0;
        Row row = sheet.createRow(++rowCount);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateToSearch));
        c.add(Calendar.DATE, 1);  // number of days to add
        String nextDate = sdf.format(c.getTime());  // dt is now the new date

        Cell cell = row.createCell(2);
        cell.setCellValue((String) "MATERIALI FERMI DA 24H DEL: " + dateToSearch);
        cell.setCellStyle(cs3);

        row = sheet.createRow(++rowCount);
        cell = row.createCell(0);
        cell.setCellValue((String) "LOCALITA'");
        cell.setCellStyle(cs3);

        cell = row.createCell(1);
        cell.setCellValue((String) "ULTIMO SERVIZIO");
        cell.setCellStyle(cs3);

        cell = row.createCell(2);
        cell.setCellValue((String) "CONVOGLIO");
        cell.setCellStyle(cs3);

        cell = row.createCell(3);
        cell.setCellValue((String) "MOTIVAZIONE");
        cell.setCellStyle(cs3);

        for (Treno treno : ultimoTrenoMatFermiDa24H){
            row = sheet.createRow(++rowCount);

            System.out.println(treno.toString());

            cell = row.createCell(0);
//          spazio dedicato alla località nella quale il materiale è fermo
            cell.setCellValue(treno.getDepositoArrivo());
            cell.setCellStyle(cs2);

            cell = row.createCell(1);
//          Spazio dedicato ultimo servizio
            cell.setCellValue(treno.getNumeroCorsa());
            cell.setCellStyle(cs2);

            cell = row.createCell(2);
//          Spazio dedicato alla convoglio
            cell.setCellValue(treno.getTipologiaMateriale()+"."+treno.getNumeroMateriale());
            cell.setCellStyle(cs2);

            cell = row.createCell(3);
//          Spazio dedicato alla motivazione del fermo
            cell.setCellValue("");
            cell.setCellStyle(cs2);

        }

//        //      Scrivo list500
//        for (int i=0; i<list500.size(); i++) {
//            row = sheet.createRow(++rowCount);
//
//            cell = row.createCell(0);
////          spazio dedicato alla località nella quale il materiale è fermo
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(1);
//            cell.setCellValue((String) "500.0" + list500.get(i));
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(2);
////          Spazio dedicato alla motivazione del fermo
//            cell.setCellStyle(cs2);
//        }
//
//        //      Scrivo list1000
//        for (int i=0; i<list1000.size(); i++) {
//            row = sheet.createRow(++rowCount);
//
//            cell = row.createCell(0);
////          spazio dedicato alla località nella quale il materiale è fermo
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(1);
//            cell.setCellValue((String) "1000.0" + list1000.get(i));
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(2);
////          Spazio dedicato alla motivazione del fermo
//            cell.setCellStyle(cs2);
//
//        }
//
//        //      Scrivo list700
//        for (int i=0; i<list700.size(); i++) {
//            row = sheet.createRow(++rowCount);
//
//            cell = row.createCell(0);
////          spazio dedicato alla località nella quale il materiale è fermo
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(1);
//            cell.setCellValue((String) "700.0" + list700.get(i));
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(2);
////          Spazio dedicato alla motivazione del fermo
//            cell.setCellStyle(cs2);
//        }
//
//        //      Scrivo list600
//        for (int i=0; i<list600.size(); i++) {
//            row = sheet.createRow(++rowCount);
//
//            cell = row.createCell(0);
////          spazio dedicato alla località nella quale il materiale è fermo
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(1);
//            int tempNumMat = list600.get(i);
//            if (tempNumMat>=1 && tempNumMat<=12) {
//                cell.setCellValue((String) "600.0" + tempNumMat);
//            }
//            else if (tempNumMat>=21 && tempNumMat<=28) {
//                cell.setCellValue((String) "460.0" + tempNumMat);
//            }
//            else if (tempNumMat==30) {
//                cell.setCellValue((String) "460.0" + tempNumMat);
//            }
//            else if (tempNumMat>=31 && tempNumMat<=45) {
//                cell.setCellValue((String) "485.0" + tempNumMat);
//            }
//            cell.setCellStyle(cs2);
//
//            cell = row.createCell(2);
////          Spazio dedicato alla motivazione del fermo
//            cell.setCellStyle(cs2);

//        }

//      adjust column width to fit the content
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);



        try (FileOutputStream outputStream = new FileOutputStream("Tabella treni Fermi 24H.xlsx")) {
            workbook.write(outputStream);
        }
    }
    private static void mergeAndCenter(Cell startCell, CellRangeAddress range) {
        startCell.getSheet().addMergedRegion(range);
        CellStyle style = startCell.getSheet().getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        startCell.setCellStyle(style);
    }
}
