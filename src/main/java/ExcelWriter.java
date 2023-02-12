import Entity.SegnalazioniPDB;
import Entity.SegnalazioniSO;
import Entity.StrisciaIvu;
import Entity.Treno;
import Utility.Utility;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelWriter {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet;
    Row row;
    int rowCount;

    CellStyle yellowCSL;


    public void write(ArrayList<StrisciaIvu> list500, ArrayList<StrisciaIvu> list1000, ArrayList<StrisciaIvu> list700, ArrayList<StrisciaIvu> list600, ArrayList<SegnalazioniSO> listSegnalazioni, ArrayList<SegnalazioniPDB> listSegnalazioniPDB, String dateToSearch) throws IOException, ParseException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("FLOTTA FUORI IMPIANTO");


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

    public void writeMultiDate(ArrayList<StrisciaIvu>[] giriPrimaRientroImpianto500, ArrayList<StrisciaIvu> list1000, ArrayList<StrisciaIvu> list700, ArrayList<StrisciaIvu> list600, ArrayList<SegnalazioniSO> listSegnalazioni, ArrayList<SegnalazioniPDB> listSegnalazioniPDB, String dateToSearch) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("FLOTTA FUORI IMPIANTO");


        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);

//      Cellstrule per impostare testo a capo
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);

//      Allinemanto verticale e orizzontale al centro
        CellStyle cs2 = workbook.createCellStyle();
        cs2.setAlignment(HorizontalAlignment.CENTER);
        cs2.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2.setWrapText(true);

//      Allinemanto sinistra e orizzontale al centro
        CellStyle cs2l = workbook.createCellStyle();
        cs2l.setAlignment(HorizontalAlignment.LEFT);
        cs2l.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2l.setWrapText(true);

//      STYLE per l'intestazione
        CellStyle cs3 = workbook.createCellStyle();
        cs3.setAlignment(HorizontalAlignment.CENTER);
        cs3.setVerticalAlignment(VerticalAlignment.CENTER);
        cs3.setFont(font);
        cs3.setWrapText(true);

        sheet.setColumnWidth(4, 100 * 256);
        sheet.setColumnWidth(5, 100 * 256);

        rowCount = 0;
        row = sheet.createRow(++rowCount);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateToSearch));
        c.add(Calendar.DATE, 1);  // number of days to add
        String nextDate = sdf.format(c.getTime());  // dt is now the new date

        Cell cell;

        writeIntestazioneExcell(dateToSearch, nextDate, cs3);

//        writeListToExcelMultiDate(giriPrimaRientroImpianto500, listSegnalazioni, listSegnalazioniPDB, cs2, cs2l);
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

        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

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
            cell.setCellValue((String) list.get(i).getNumeroCorsaArrivo());
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

        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

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
            cell.setCellValue((String) list.get(i).getNumeroCorsaArrivo());
            cell.setCellStyle(cs);

            cell = row.createCell(2);
            cell.setCellValue((String) list.get(i).getTipologiaVeicolo());
            cell.setCellStyle(cs);

            cell = row.createCell(3);
            cell.setCellValue((String) "0" + list.get(i).getNumeroMateriale());
            cell.setCellStyle(cs);

            cell = row.createCell(4);
            cell.setCellStyle(csl);


            ArrayList<String> treniDaCercare = tempStriscia.getTreniStrisciaIVU();

            int offsetSO = 1;
            String nota = "";
            for (String trenoTmp : treniDaCercare){
                for (int j = 0; j < listSegnalazioniSO.size(); j++) {
//                    if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioniSO.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioniSO.get(j).getNumeroMateriale()) {
////                    System.out.println(listSegnalazioni.get(j).getNota());
//                        offsetSO++;
////                    cell = row.createCell(3+offset);
////                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
//                        nota = nota + "-  " + (String) listSegnalazioniSO.get(j).getNota() + "\n";
//                    }
                    SegnalazioniSO tempSegnSO = listSegnalazioniSO.get(j);
                    System.out.println(trenoTmp + " - " + tempSegnSO.getNumeroTreno());
                    if(trenoTmp.equals(tempSegnSO.getNumeroTreno())){
                        offsetSO++;
                        nota = nota + "  [" +trenoTmp+ "] "+ (String) tempSegnSO.getNota() + "\n";
                    }
                }
            }

            cell.setCellValue(nota);

            cell = row.createCell(5);
            cell.setCellStyle(csl);

            int offsetPDB = 1;
            nota = "";
            for (String trenoTmp : treniDaCercare) {
                for (int j = 0; j < listSegnalazioniPDB.size(); j++) {
//                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioniPDB.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioniPDB.get(j).getNumeroMateriale()) {
////                    System.out.println(listSegnalazioni.get(j).getNota());
//                    offsetPDB++;
////                    cell = row.createCell(3+offset);
////                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
//                    SegnalazioniPDB segnPDB= listSegnalazioniPDB.get(j);
//                    nota = nota + "-  " + (String) segnPDB.getCodice() +" - "+ segnPDB.getOrgano() +" - "+ segnPDB.getUbicazione() +" - "+ segnPDB.getDescrizione() +" - "+ segnPDB.getPosizione() + "\n";
//                }
                    SegnalazioniPDB tempSegnPDB = listSegnalazioniPDB.get(j);
                    if (trenoTmp.equals(tempSegnPDB.getNumeroTreno())) {
                        offsetPDB++;
                        SegnalazioniPDB segnPDB= listSegnalazioniPDB.get(j);
                        nota = nota + "  [" + trenoTmp + "] " + (String) segnPDB.getCodice() +" - "+ segnPDB.getOrgano() +" - "+ segnPDB.getUbicazione() +" - "+ segnPDB.getStato() +" - "+ segnPDB.getDescrizione() +" - "+ segnPDB.getPosizione() + "\n";
                    }
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


    public void writeMultiDate(ArrayList<Treno>[] listTreniNoImpianto500, ArrayList<Treno>[] listTreniNoImpianto1000, ArrayList<Treno>[] listTreniNoImpianto700, ArrayList<Treno>[] listTreniNoImpianto600, ArrayList<SegnalazioniSO> listSegnalazioniSO, ArrayList<SegnalazioniPDB> listSegnalazioniPDB, String dateToSearch) throws ParseException, IOException {

        sheet = workbook.createSheet("FLOTTA FUORI IMPIANTO");



        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);

//      Cellstrule per impostare testo a capo
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);

//      Allinemanto verticale e orizzontale al centro
        CellStyle cs2 = workbook.createCellStyle();
        cs2.setAlignment(HorizontalAlignment.CENTER);
        cs2.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2.setWrapText(true);

//      Allinemanto sinistra e orizzontale al centro
        CellStyle cs2l = workbook.createCellStyle();
        cs2l.setAlignment(HorizontalAlignment.LEFT);
        cs2l.setVerticalAlignment(VerticalAlignment.CENTER);
        cs2l.setWrapText(true);

//      STYLE per l'intestazione
        CellStyle cs3 = workbook.createCellStyle();
        cs3.setAlignment(HorizontalAlignment.CENTER);
        cs3.setVerticalAlignment(VerticalAlignment.CENTER);
        cs3.setFont(font);
        cs3.setWrapText(true);

//      STYLE per evidenziare la CELLA IN GIALLO
        yellowCSL = workbook.createCellStyle();
        yellowCSL.setAlignment(HorizontalAlignment.LEFT);
        yellowCSL.setVerticalAlignment(VerticalAlignment.CENTER);
        yellowCSL.setWrapText(true);
        yellowCSL.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowCSL.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        rowCount = 0;
        row = sheet.createRow(++rowCount);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dateToSearch));
        c.add(Calendar.DATE, -1);  // numero di giorni da aggiungere
        String precDate = sdf.format(c.getTime());

        writeIntestazioneExcell(precDate, dateToSearch, cs3);

        writeListToExcelMultiDate(listTreniNoImpianto500, listSegnalazioniSO, listSegnalazioniPDB, cs2, cs2l);
        writeListToExcelMultiDate(listTreniNoImpianto1000, listSegnalazioniSO, listSegnalazioniPDB, cs2, cs2l);
        writeListToExcelMultiDate(listTreniNoImpianto700, listSegnalazioniSO, listSegnalazioniPDB, cs2, cs2l);
        writeListToExcelMultiDate(listTreniNoImpianto600, listSegnalazioniSO, listSegnalazioniPDB, cs2, cs2l);

//      adjust column width to fit the content
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        sheet.setColumnWidth(4, 100 * 256);
        sheet.setColumnWidth(5, 100 * 256);
//        sheet.setColumnWidth(6, 70 * 256);

        try (FileOutputStream outputStream = new FileOutputStream("Tabella guasti Notturni.xlsx")) {
            workbook.write(outputStream);
        }
    }

    private void writeListToExcelMultiDate(ArrayList<Treno>[] arrayTreno, ArrayList<SegnalazioniSO> listSegnalazioniSO, ArrayList<SegnalazioniPDB> listSegnalazioniPDB, CellStyle cs, CellStyle csl) {

        Map<String, String> mapLocalità = Utility.creaMappaLocalita();

        for (ArrayList<Treno> listTreni : arrayTreno) {
            int sizeListTreni = listTreni.size();

            if(sizeListTreni!=0){

                Treno lastTreno = listTreni.get(sizeListTreni-1);
                row = sheet.createRow(++rowCount);
                Cell cell;


                cell = row.createCell(0);
                String depArrivo = lastTreno.getDepositoArrivo();
                if (mapLocalità.get(depArrivo) != null)
                    cell.setCellValue((String) mapLocalità.get(depArrivo));
                else
                    cell.setCellValue((String) depArrivo);
                cell.setCellStyle(cs);

//                String numeroCorsa = lastTreno.getNumeroCorsa();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                int offsetTreni = 1;
                int offsetSO = 1;
                int offsetPDB = 1;
                String notaSO = "";
                String notaPDB = "";
                String segnWCHK = "";
                boolean wcHKKO = false;
                ArrayList<String> listNumeroCorsa = new ArrayList<>();
                for (Treno tempTreno : listTreni){
                    offsetTreni++;
                    if(!listNumeroCorsa.contains(tempTreno.getNumeroCorsa()))
                        listNumeroCorsa.add(tempTreno.getNumeroCorsa());

                    ArrayList<SegnalazioniSO> listSSO = tempTreno.getSegnalazioniSO();
                    for (SegnalazioniSO sSO : listSSO) {
                        offsetSO++;
                        notaSO = notaSO + "  [" + sSO.getNumeroTreno() + "  " + dateFormat.format(sSO.getDataTreno()) + "] " + (String) sSO.getNota() + "\n";
                    }

                    ArrayList<SegnalazioniPDB> listSPDB = tempTreno.getSegnalazioniPDB();
                    for (SegnalazioniPDB sPDB : listSPDB) {
                        offsetPDB++;
                        notaPDB = notaPDB + "  [" + sPDB.getNumeroTreno() + "  " + dateFormat.format(sPDB.getDataTreno()) + "]  " + sPDB.getPosizione() + "  - "+ (String) sPDB.getCodice() +" - "+ sPDB.getOrgano() +" - "+ sPDB.getUbicazione() +" - "+ sPDB.getDescrizione() + "\n";
                        if(sPDB.getTipologiaVeicolo().equals("ETR700")){
                            if(sPDB.getPosizione().equals("w2") && sPDB.getCodice().equals("143 - ETR Organo Ritirate") ){
                                wcHKKO = true;
                                segnWCHK = segnWCHK + "  [" + sPDB.getNumeroTreno() + "  " + dateFormat.format(sPDB.getDataTreno()) + "]  " + sPDB.getDescrizione() + "\n";
                            }
                        }
                        else{
                            if(sPDB.getPosizione().equals("w3") && sPDB.getCodice().equals("143 - ETR Organo Ritirate") ){
                                wcHKKO = true;
                                segnWCHK = segnWCHK + "  [" + sPDB.getNumeroTreno() + "  " + dateFormat.format(sPDB.getDataTreno()) + "]  " + sPDB.getDescrizione() + "\n";
                            }
                        }
                    }
                }


                cell = row.createCell(1);
                String numeroCorsa = "";
                for(String tempCorsa : listNumeroCorsa)
                    numeroCorsa += tempCorsa +"\n";
                cell.setCellValue(numeroCorsa);
                cell.setCellStyle(cs);

                cell = row.createCell(2);
                cell.setCellValue((String) lastTreno.getTipologiaMateriale());
                cell.setCellStyle(cs);

                cell = row.createCell(3);
                if (lastTreno.getNumeroMateriale() < 10)
                    cell.setCellValue((String) "00" + lastTreno.getNumeroMateriale());
                else
                    cell.setCellValue((String) "0" + lastTreno.getNumeroMateriale());
                cell.setCellStyle(cs);

                cell = row.createCell(4);
                cell.setCellStyle(csl);
                cell.setCellValue(notaSO);


                cell = row.createCell(5);

                if(wcHKKO) {
                    cell.setCellStyle(yellowCSL);
                    cell.setCellValue(notaPDB);
                }
                else{
                    cell.setCellStyle(csl);
                    cell.setCellValue(notaPDB);
                }

//                if(wcHKKO) {
//                    cell = row.createCell(6);
//                    cell.setCellStyle(csl);
//                    cell.setCellValue("VERIFICARE WC HK \n" + segnWCHK);
//                }

                int maxTemp = Integer.max(offsetSO, offsetPDB);
                int maxOffset = Integer.max(maxTemp,offsetTreni);
                //increase row height to accommodate two lines of text
                row.setHeightInPoints(((maxOffset + 1) * sheet.getDefaultRowHeightInPoints()));
                }
            }
        }
    }
