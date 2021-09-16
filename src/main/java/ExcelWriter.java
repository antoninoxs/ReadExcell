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

    public void write(ArrayList<StrisciaIvu> list500, ArrayList<StrisciaIvu> list1000, ArrayList<StrisciaIvu> list700, ArrayList<StrisciaIvu> list600, ArrayList<SegnalazioniSO> listSegnalazioni, String dateToSearch) throws IOException, ParseException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("FLOTTA FUORI IMPIANTO");

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

        Cell cell = row.createCell(3);
        cell.setCellValue((String) "NOTTE DEL: " + dateToSearch + " su " + nextDate);
        cell.setCellStyle(cs3);

        row = sheet.createRow(++rowCount);
        cell = row.createCell(0);
        cell.setCellValue((String) "LOCALITA'");
        cell.setCellStyle(cs3);

        cell = row.createCell(1);
        cell.setCellValue((String) "SERVIZIO IN \n ARRIVO");
        cell.setCellStyle(cs3);

        cell = row.createCell(2);
        cell.setCellValue((String) "CONVOGLIO");
        cell.setCellStyle(cs3);

        cell = row.createCell(3);
        cell.setCellValue((String) "NUMERO \n CONVOGLIO");
        cell.setCellStyle(cs3);

        cell = row.createCell(4);
        cell.setCellValue((String) "DEGRADO");
        cell.setCellStyle(cs3);

        //      Scrivo list500
        for (int i=0; i<list500.size(); i++) {
            row = sheet.createRow(++rowCount);

            StrisciaIvu tempStriscia = list500.get(i);

            cell = row.createCell(0);
            String depArrivo = list500.get(i).getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs2);


            cell = row.createCell(1);
            cell.setCellValue((String) list500.get(i).getNumeroTrenoArrivo());
            cell.setCellStyle(cs2);

            cell = row.createCell(2);
            cell.setCellValue((String) list500.get(i).getTipologiaVeicolo());
            cell.setCellStyle(cs2);

            cell = row.createCell(3);
            cell.setCellValue((String) "0"+list500.get(i).getNumeroMateriale());
            cell.setCellStyle(cs2);

            cell = row.createCell(4);
            cell.setCellStyle(cs);

            int offset = 1;
            String nota = "";
            for (int j=0; j< listSegnalazioni.size(); j++){
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioni.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioni.get(j).getNumeroMateriale()){
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offset++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    nota = nota + (String) listSegnalazioni.get(j).getNota() + "\n";
                }
            }
            //increase row height to accommodate two lines of text
            row.setHeightInPoints((offset*sheet.getDefaultRowHeightInPoints()));
            cell.setCellValue(nota);

        }

        //      Scrivo list1000
        for (int i=0; i<list1000.size(); i++) {
            row = sheet.createRow(++rowCount);

            StrisciaIvu tempStriscia = list1000.get(i);

            cell = row.createCell(0);
            String depArrivo = list1000.get(i).getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs2);

            cell = row.createCell(1);
            cell.setCellValue((String) list1000.get(i).getNumeroTrenoArrivo() );
            cell.setCellStyle(cs2);

            cell = row.createCell(2);
            cell.setCellValue((String) list1000.get(i).getTipologiaVeicolo() );
            cell.setCellStyle(cs2);

            cell = row.createCell(3);
            cell.setCellValue((String) "0"+list1000.get(i).getNumeroMateriale() );
            cell.setCellStyle(cs2);

            cell = row.createCell(4);
            cell.setCellStyle(cs);

            int offset = 1;
            String nota = "";
            for (int j=0; j< listSegnalazioni.size(); j++){
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioni.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale()==listSegnalazioni.get(j).getNumeroMateriale()){
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offset++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    nota = nota + (String) listSegnalazioni.get(j).getNota() + "\n";
                }
            }
            //increase row height to accommodate two lines of text
            row.setHeightInPoints((offset*sheet.getDefaultRowHeightInPoints()));
            cell.setCellValue(nota);

        }

        //      Scrivo list700
        for (int i=0; i<list700.size(); i++) {
            row = sheet.createRow(++rowCount);

            StrisciaIvu tempStriscia = list700.get(i);

            cell = row.createCell(0);
            String depArrivo = list700.get(i).getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs2);

            cell = row.createCell(1);
            cell.setCellValue((String) list700.get(i).getNumeroTrenoArrivo() );
            cell.setCellStyle(cs2);

            cell = row.createCell(2);
            cell.setCellValue((String) list700.get(i).getTipologiaVeicolo() );
            cell.setCellStyle(cs2);

            cell = row.createCell(3);
            cell.setCellValue((String) "0"+list700.get(i).getNumeroMateriale() );
            cell.setCellStyle(cs2);

            cell = row.createCell(4);
            cell.setCellStyle(cs);

            int offset = 1;
            String nota = "";
            for (int j=0; j< listSegnalazioni.size(); j++){
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioni.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale()== listSegnalazioni.get(j).getNumeroMateriale()){
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offset++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    nota = nota + (String) listSegnalazioni.get(j).getNota() + "\n";
                }
            }
            //increase row height to accommodate two lines of text
            row.setHeightInPoints((offset*sheet.getDefaultRowHeightInPoints()));
            cell.setCellValue(nota);
        }

        //      Scrivo list600
        for (int i=0; i<list600.size(); i++) {
            row = sheet.createRow(++rowCount);

            StrisciaIvu tempStriscia = list600.get(i);

            cell = row.createCell(0);
            String depArrivo = list600.get(i).getDepositoArrivo();
            if (mapLocalità.get(depArrivo) != null)
                cell.setCellValue((String) mapLocalità.get(depArrivo));
            else
                cell.setCellValue((String) depArrivo);
            cell.setCellStyle(cs2);

            cell = row.createCell(1);
            cell.setCellValue((String) list600.get(i).getNumeroTrenoArrivo() );
            cell.setCellStyle(cs2);

            cell = row.createCell(2);
            cell.setCellValue((String) list600.get(i).getTipologiaVeicolo() );
            cell.setCellStyle(cs2);

            cell = row.createCell(3);
            cell.setCellValue((String) "0"+list600.get(i).getNumeroMateriale() );
            cell.setCellStyle(cs2);

            cell = row.createCell(4);
            cell.setCellStyle(cs);

            int offset = 1;
            String nota = "";
            for (int j=0; j< listSegnalazioni.size(); j++){
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioni.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale()== listSegnalazioni.get(j).getNumeroMateriale()){
//                    System.out.println(listSegnalazioni.get(j).getNota());
                    offset++;
//                    cell = row.createCell(3+offset);
//                    cell.setCellValue((String) listSegnalazioni.get(j).getNota());
                    nota = nota + (String) listSegnalazioni.get(j).getNota() + "\n";
                }
            }
            //increase row height to accommodate two lines of text
            row.setHeightInPoints((offset*sheet.getDefaultRowHeightInPoints()));
            cell.setCellValue(nota);

        }

//      adjust column width to fit the content
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);



        try (FileOutputStream outputStream = new FileOutputStream("Tabella guasti Notturni.xlsx")) {
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
