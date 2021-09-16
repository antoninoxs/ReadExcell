import Entity.SegnalazioniSO;
import Entity.StrisciaIvu;
import Utility.Utility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class TabellaSegnalazioni {

    public static final String XLSX_FILE_PATH_SO = "./ListaSegnalazioniSO.xls";
    public static final String XLSX_FILE_PATH_IVU = "./export.xlsx";


    public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
        String dateToSearch = "12/09/2021";

        ExcelReaderSO excelReaderSO = new ExcelReaderSO();

        System.out.println("Inserisci la data per la creazione della tabella nel formato gg/mm/aaaa es 26/04/2021");
        Scanner input = new Scanner(System.in);
        dateToSearch = input.nextLine();
        System.out.println("Creo la tabella per la data: " + dateToSearch);

        if(!Utility.isValidDate(dateToSearch)){
            System.out.println("La data inserita NON è corretta!!!");
            throw input.ioException();
        }


        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        Date searchDate = null;
        try {
            searchDate = format.parse(dateToSearch);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Search DATE: "+searchDate); // Sat Jan 02 00:00:00 GMT 2010

        ArrayList<SegnalazioniSO> listSegnalazioni = excelReaderSO.ExcelReaderSO(XLSX_FILE_PATH_SO);


        ExcelReaderIVU excelReaderIVU = new ExcelReaderIVU();
        excelReaderIVU.ExcelREaderIVU(XLSX_FILE_PATH_IVU, searchDate);

        ExcelWriter excelWriter = new ExcelWriter();

        ArrayList<StrisciaIvu> list500 = excelReaderIVU.listTurnoFuoriImpianto500;
        ArrayList<StrisciaIvu> list1000 = excelReaderIVU.listTurnoFuoriImpianto1000;
        ArrayList<StrisciaIvu> list700 = excelReaderIVU.listTurnoFuoriImpianto700;
        ArrayList<StrisciaIvu> list600 = excelReaderIVU.listTurnoFuoriImpianto600;

        printListTurnoIvuAggregata(list500, listSegnalazioni, "ETR500");
        printListTurnoIvuAggregata(list1000, listSegnalazioni, "ETR1000");
        printListTurnoIvuAggregata(list700, listSegnalazioni, "ETR700");
        printListTurnoIvuAggregata(list600, listSegnalazioni, "ETR600");

        excelWriter.write(list500, list1000, list700, list600, listSegnalazioni, dateToSearch);

        ArrayList<Integer> listTreniFermi24H500 = excelReaderIVU.numMatFermiDa24H500;
        ArrayList<Integer> listTreniFermi24H1000 = excelReaderIVU.numMatFermiDa24H1000;
        ArrayList<Integer> listTreniFermi24H700 = excelReaderIVU.numMatFermiDa24H700;
        ArrayList<Integer> listTreniFermi24H600 = excelReaderIVU.numMatFermiDa24H600;

        ExcelWriterMaterialiFermi24H excelWriterMaterialiFermi24H = new ExcelWriterMaterialiFermi24H();
        excelWriterMaterialiFermi24H.write(listTreniFermi24H500,listTreniFermi24H1000, listTreniFermi24H700, listTreniFermi24H600, dateToSearch);


    }

    public static void printListTurnoIvuAggregata(ArrayList<StrisciaIvu> list, ArrayList<SegnalazioniSO> listSegnalazioni, String materiali){

        System.out.println("/*-----------------------------------------------------AGGREGATA " + materiali +" --------------------------------------------------------------------------------------------------*/");

        for (int i=0; i<list.size(); i++){
            StrisciaIvu tempStriscia = list.get(i);
            System.out.println(tempStriscia.toString());
            for (int j=0; j< listSegnalazioni.size(); j++){
                if (tempStriscia.getTipologiaVeicolo().equals(listSegnalazioni.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale()== listSegnalazioni.get(j).getNumeroMateriale()){
                    System.out.println(" -   " + listSegnalazioni.get(j).toString());
                }
            }
        }
        System.out.println();
        System.out.println("**************************************************************************************************************************************************************************************");
        System.out.println();
        System.out.println();
    }
}


