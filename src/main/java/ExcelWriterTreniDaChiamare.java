import Entity.*;
import Utility.Utility;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

public class ExcelWriterTreniDaChiamare {
    XSSFSheet sheet;
    Row row;
    int rowCount;

    public static final String[] dep = {"MSDL", "MIMA", "NAIF", "RMOMV"};
    public static final ArrayList<String> depAL = new ArrayList<>(Arrays.asList(dep));

    public void write(ArrayList<Materiale> listMateriali) throws IOException, ParseException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TRENI DA CHIAMARE");


        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

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

//      Allinemanto sinistra e orizzontale al centro
        CellStyle cs2l =  workbook.createCellStyle();
        cs2l.setAlignment(HorizontalAlignment.LEFT);
        cs2l.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2l.setWrapText(true);

//      Allinemanto sinistra e orizzontale al centro + DATA
        CellStyle data =  workbook.createCellStyle();
        data.setAlignment(HorizontalAlignment.LEFT);
        data.setVerticalAlignment(VerticalAlignment.CENTER);
        data.setWrapText(true);
        CreationHelper createHelper = workbook.getCreationHelper();
        data.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));


//      STYLE per l'intestazione
        CellStyle cs3 =  workbook.createCellStyle();
        cs3.setAlignment(HorizontalAlignment.CENTER);
        cs3.setVerticalAlignment(VerticalAlignment.CENTER);
        cs3.setFont(font);
        cs3.setWrapText(true);

        rowCount = 0;
        row = sheet.createRow(++rowCount);


        writeIntestazioneExcell(cs3);

        writeListToExcel(listMateriali, cs2, cs2l,data);

//      adjust column width to fit the content
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        try (FileOutputStream outputStream = new FileOutputStream("TreniDaChiamare.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private void writeIntestazioneExcell(CellStyle cs) {
        Cell cell = row.createCell(0);
        cell.setCellValue((String) "Corsa Partenza'");
        cell.setCellStyle(cs);

        cell = row.createCell(1);
        cell.setCellValue((String) "ORIGINE");
        cell.setCellStyle(cs);

        cell = row.createCell(2);
        cell.setCellValue((String) "DATA e ORARIO");
        cell.setCellStyle(cs);

        cell = row.createCell(3);
        cell.setCellValue((String) "ETR");
        cell.setCellStyle(cs);

        cell = row.createCell(4);
        cell.setCellValue((String) "NUMERO MATERIALE");
        cell.setCellStyle(cs);

        cell = row.createCell(5);
        cell.setCellValue((String) "TURNO MACCHINA");
        cell.setCellStyle(cs);
    }

    private void writeListToExcel(ArrayList<Materiale> listMateriali, CellStyle cs, CellStyle csl, CellStyle data) {

        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

        for (Materiale mat : listMateriali) {
            ArrayList<TurnoMacchina> listTurnoMacc = mat.getListTurnoMacchina();
            if(!listTurnoMacc.isEmpty()) {
                TurnoMacchina turnoMacchina = listTurnoMacc.get(0);
                if (!depAL.contains(turnoMacchina.getDepositoPartenza())) {

                    row = sheet.createRow(++rowCount);
                    Cell cell;

                    for (Treno treno : turnoMacchina.getListTreni()) {
                        if (treno.getTipologiaCorsa().equals("Corsa per punti d'esercizio")) {
                            continue;
                        }

                        cell = row.createCell(0);
                        String corsa = treno.getNumeroCorsa();
                        cell.setCellValue(corsa);
                        cell.setCellStyle(cs);

                        cell = row.createCell(1);
                        String depArrivo = treno.getDepositoPartenza();
                        if (mapLocalità.get(treno.getDepositoPartenza()) != null)
                            cell.setCellValue((String) mapLocalità.get(depArrivo));
                        else
                            cell.setCellValue((String) depArrivo);
                        cell.setCellStyle(cs);

                        cell = row.createCell(2);
                        cell.setCellValue(treno.getDataPartenza());
                        cell.setCellStyle(data);

                        cell = row.createCell(3);
                        cell.setCellValue(treno.getTipologiaMateriale());
                        cell.setCellStyle(cs);

                        cell = row.createCell(4);
                        cell.setCellValue((String) "0" + treno.getNumeroMateriale());
                        cell.setCellStyle(cs);

                        cell = row.createCell(5);
                        cell.setCellValue(treno.getDenominazioneTurnoMacc());
                        cell.setCellStyle(cs);
                        break;
                    }
                }
            }
        }
    }
}
