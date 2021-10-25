import Entity.SegnalazioniPDB;
import Entity.SegnalazioniSO;
import Entity.StrisciaIvu;
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

public class ExcelWriter {

    public Map<String, String> mapLocalità = new HashMap<>();

    XSSFSheet sheet;
    Row row;
    int rowCount;

    private void creaMappaLocalita() {
        mapLocalità.put("AN","ANCONA");
        mapLocalità.put("AVER","AVERSA");
        mapLocalità.put("BACL","BARI");
        mapLocalità.put("BADL","BARI");
        mapLocalità.put("BATT","BATTIPAGLIA");
        mapLocalità.put("BG","BERGAMO");
        mapLocalità.put("BN","BENEVENTO");
        mapLocalità.put("BOCL","BOLOGNA CENTRALE");
        mapLocalità.put("BORAV","BOLOGNA RAVONE");
        mapLocalità.put("BS","BRESCIA");
        mapLocalità.put("BZ","BOLZANO");
        mapLocalità.put("BZDL","BOLZANO");
        mapLocalità.put("FICM","FIRENZE CAMPO MARTE");
        mapLocalità.put("FISM","FIRENZE S.M. NOVELLA");
        mapLocalità.put("GEBR","GENOVA BRIGNOLE");
        mapLocalità.put("GEPP","GENOVA PIAZZA PRINCIPE");
        mapLocalità.put("LESMC","LECCE");
        mapLocalità.put("MICL","MILANO CENTRALE");
        mapLocalità.put("MIPAC","MILANO PARCO CENTRALE");
        mapLocalità.put("MICE","MILANO MILANO CERTOSA");
        mapLocalità.put("MN","MANTOVA");
        mapLocalità.put("MSDL","VENEZIA MESTRE");
        mapLocalità.put("NACL","NAPOLI CENTRALE");
        mapLocalità.put("PECL","PESCARA");
        mapLocalità.put("PG","PERUGIA");
        mapLocalità.put("RA","RAVENNA");
        mapLocalità.put("RCCL","REGGIO CALABRIA");
        mapLocalità.put("RCDL","REGGIO CALABRIA");
        mapLocalità.put("RMOMV","ROMA MAV");
        mapLocalità.put("RMTM","ROMA TERMINI");
        mapLocalità.put("RMOS","ROMA OSTIENSE");
        mapLocalità.put("SIB","SIBARI");
        mapLocalità.put("TA","TARANTO");
        mapLocalità.put("TOSN","TORINO SMISTAMENTO");
        mapLocalità.put("TOPN","TORINOO PORTA NUOVA");
        mapLocalità.put("TSCL","TRIESTE CENTRALE");
        mapLocalità.put("UD","UDINE");
        mapLocalità.put("VI","VICENZA");
    }

    public void write(ArrayList<StrisciaIvu> list500, ArrayList<StrisciaIvu> list1000, ArrayList<StrisciaIvu> list700, ArrayList<StrisciaIvu> list600, ArrayList<SegnalazioniSO> listSegnalazioni, ArrayList<SegnalazioniPDB> listSegnalazioniPDB, String dateToSearch) throws IOException, ParseException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("FLOTTA FUORI IMPIANTO");

        creaMappaLocalita();

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

//      STYLE per l'intestazione
        CellStyle cs3 =  workbook.createCellStyle();
        cs3.setAlignment(HorizontalAlignment.CENTER);
        cs3.setVerticalAlignment(VerticalAlignment.CENTER);
        cs3.setFont(font);
        cs3.setWrapText(true);

        sheet.setColumnWidth(4, 100*256);
        sheet.setColumnWidth(5, 100*256);

        rowCount = 0;
        row = sheet.createRow(++rowCount);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateToSearch));
        c.add(Calendar.DATE, 1);  // number of days to add
        String nextDate = sdf.format(c.getTime());  // dt is now the new date

        Cell cell;

        writeIntestazioneExcell(dateToSearch, nextDate, cs3);

        writeListToExcel(list500, listSegnalazioni, listSegnalazioniPDB, cs2, cs2l);
        writeListToExcel(list1000, listSegnalazioni, listSegnalazioniPDB, cs2, cs2l);
        writeListToExcel(list700, listSegnalazioni, listSegnalazioniPDB, cs2, cs2l);
        writeListToExcel(list600, listSegnalazioni, listSegnalazioniPDB, cs2, cs2l);

//      adjust column width to fit the content
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
//        sheet.autoSizeColumn(4);

        try (FileOutputStream outputStream = new FileOutputStream("Tabella guasti Notturni.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private void writeIntestazioneExcell(String dateToSearch, String nextDate, CellStyle cs) {
        Cell cell = row.createCell(4);
        cell.setCellValue((String) "NOTTE DEL: " + dateToSearch + " su " + nextDate);
        cell.setCellStyle(cs);

        row = sheet.createRow(++rowCount);
        cell = row.createCell(0);
        cell.setCellValue((String) "LOCALITA'");
        cell.setCellStyle(cs);

        cell = row.createCell(1);
        cell.setCellValue((String) "SERVIZIO IN \n ARRIVO");
        cell.setCellStyle(cs);

        cell = row.createCell(2);
        cell.setCellValue((String) "CONVOGLIO");
        cell.setCellStyle(cs);

        cell = row.createCell(3);
        cell.setCellValue((String) "NUMERO \n CONVOGLIO");
        cell.setCellStyle(cs);

        cell = row.createCell(4);
        cell.setCellValue((String) "DEGRADO SO");
        cell.setCellStyle(cs);

        cell = row.createCell(5);
        cell.setCellValue((String) "DEGRADO PDB");
        cell.setCellStyle(cs);
    }

    private void writeListToExcel(ArrayList<StrisciaIvu> list, ArrayList<SegnalazioniSO> listSegnalazioniSO, CellStyle cs, CellStyle csl) {
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(++rowCount);
            Cell cell;

            StrisciaIvu tempStriscia = list.get(i);

            cell = row.createCell(0);
            String depArrivo = list.get(i).getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs);


            cell = row.createCell(1);
            cell.setCellValue((String) list.get(i).getNumeroTrenoArrivo());
            cell.setCellStyle(cs);

            cell = row.createCell(2);
            cell.setCellValue((String) list.get(i).getTipologiaVeicolo());
            cell.setCellStyle(cs);

            cell = row.createCell(3);
            cell.setCellValue((String) "0" + list.get(i).getNumeroMateriale());
            cell.setCellStyle(cs);

            cell = row.createCell(4);
            cell.setCellStyle(csl);

            int offset = 1;
            String nota = "";
            for (int j = 0; j < listSegnalazioniSO.size(); j++) {
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioniSO.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioniSO.get(j).getNumeroMateriale()) {
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offset++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    nota = nota + (String) listSegnalazioniSO.get(j).getNota() + "\n";
                }
            }

            cell.setCellValue(nota);

            //increase row height to accommodate two lines of text
            row.setHeightInPoints((offset+1) * sheet.getDefaultRowHeightInPoints());
        }
    }

    private void writeListToExcel(ArrayList<StrisciaIvu> list, ArrayList<SegnalazioniSO> listSegnalazioniSO, ArrayList<SegnalazioniPDB> listSegnalazioniPDB, CellStyle cs, CellStyle csl) {
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(++rowCount);
            Cell cell;

            StrisciaIvu tempStriscia = list.get(i);

            cell = row.createCell(0);
            String depArrivo = list.get(i).getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs);


            cell = row.createCell(1);
            cell.setCellValue((String) list.get(i).getNumeroTrenoArrivo());
            cell.setCellStyle(cs);

            cell = row.createCell(2);
            cell.setCellValue((String) list.get(i).getTipologiaVeicolo());
            cell.setCellStyle(cs);

            cell = row.createCell(3);
            cell.setCellValue((String) "0" + list.get(i).getNumeroMateriale());
            cell.setCellStyle(cs);

            cell = row.createCell(4);
            cell.setCellStyle(csl);

            int offsetSO = 1;
            String nota = "";
            for (int j = 0; j < listSegnalazioniSO.size(); j++) {
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioniSO.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioniSO.get(j).getNumeroMateriale()) {
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offsetSO++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    nota = nota + "-  " + (String) listSegnalazioniSO.get(j).getNota() + "\n";
                }
            }
            cell.setCellValue(nota);

            cell = row.createCell(5);
            cell.setCellStyle(csl);

            int offsetPDB = 1;
            nota = "";
            for (int j = 0; j < listSegnalazioniPDB.size(); j++) {
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioniPDB.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioniPDB.get(j).getNumeroMateriale()) {
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offsetPDB++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    SegnalazioniPDB segnPDB= listSegnalazioniPDB.get(j);
                    nota = nota + "-  " + (String) segnPDB.getCodice() +" - "+ segnPDB.getOrgano() +" - "+ segnPDB.getUbicazione() +" - "+ segnPDB.getDescrizione() +" - "+ segnPDB.getPosizione() + "\n";
                }

            }
            cell.setCellValue(nota);

            int maxOffset = Integer.max(offsetSO, offsetPDB);
            //increase row height to accommodate two lines of text
            row.setHeightInPoints(((maxOffset + 1) * sheet.getDefaultRowHeightInPoints()));
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
