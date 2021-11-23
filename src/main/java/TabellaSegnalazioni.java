import Entity.SegnalazioniPDB;
import Entity.SegnalazioniSO;
import Entity.StrisciaIvu;
import Entity.Treno;
import Utility.Utility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class TabellaSegnalazioni {

    public static final String XLSX_FILE_PATH_IVU = "./export.xlsx";
    public static final String XLSX_FILE_PATH_IVUCERCA = "./exportCerca.xlsx";
    public static final String XLSX_FILE_PATH_SO = "./ListaSegnalazioniSO.xls";
    public static final String XLSX_FILE_PATH_PDB = "./ListaSegnalazioniPDB.xls";


    public static final String XLSX_FILE_PATH_SO_FOLDER = "./FileEsportazioniPDPSO";
    public static final String XLSX_FILE_PATH_PDB_FOLDER = "./FileEsportazioniPDPPDB";

    public static int size500;
    public static int size1000;
    public static int size700;
    public static int size600;

    public static ArrayList<Treno> listTreniNoImpianto500[];
    public static ArrayList<Treno> listTreniNoImpianto1000[];
    public static ArrayList<Treno> listTreniNoImpianto700[];
    public static ArrayList<Treno> listTreniNoImpianto600[];

    public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
//        String dateToSearch = "19/11/2021";
//       Date searchDate = Utility.stringToDate(dateToSearch);

        // funzione inserimento data di ricerca da Command Line
        Date searchDate = inputDataCommandLine();

        System.out.println("Sto eseguendo il programma da = " + System.getProperty("user.dir"));
//      Verifico la presenza dei file necessari
//        Boolean fileIVUexists = verificaFile(XLSX_FILE_PATH_IVU);
//        Boolean fileSOexists = verificaFile(XLSX_FILE_PATH_SO);
//        Boolean filePDBexists = verificaFile(XLSX_FILE_PATH_PDB);
        Boolean fileIVUCERCAexists = verificaFile(XLSX_FILE_PATH_IVUCERCA);

//        ExcelReaderIVU excelReaderIVU = new ExcelReaderIVU();
        ExcelReaderSO excelReaderSO = new ExcelReaderSO();
        ExcelReaderPDB excelReaderPDB = new ExcelReaderPDB();

        ExcelReaderIVUDaCerca excelReaderIVUDaCerca = new ExcelReaderIVUDaCerca();
        excelReaderIVUDaCerca.ExcelReaderIVUdaCercaMultipleDate(XLSX_FILE_PATH_IVUCERCA,searchDate);


        ExcelWriter excelWriter = new ExcelWriter();



//        ExcelWriterMaterialiFermi24H excelWriterMaterialiFermi24H = new ExcelWriterMaterialiFermi24H();

        size500 = excelReaderIVUDaCerca.size500;
        size1000 = excelReaderIVUDaCerca.size1000;
        size700 = excelReaderIVUDaCerca.size700;
        size600 = excelReaderIVUDaCerca.size600;

        listTreniNoImpianto500 = excelReaderIVUDaCerca.listTreniNoImpianto500;
        listTreniNoImpianto1000 = excelReaderIVUDaCerca.listTreniNoImpianto1000;
        listTreniNoImpianto700 = excelReaderIVUDaCerca.listTreniNoImpianto700;
        listTreniNoImpianto600 = excelReaderIVUDaCerca.listTreniNoImpianto600;


//        excelReaderIVU.ExcelREaderIVU(XLSX_FILE_PATH_IVU, searchDate, turnoMacchinaArrayList);
//        excelReaderIVU.ExcelREaderIVUmultiDate(XLSX_FILE_PATH_IVU, searchDate, searchDate, trenoArrayList);

        ArrayList<SegnalazioniSO> listSegnalazioniSO = new ArrayList<>();

        File folder = new File(XLSX_FILE_PATH_SO_FOLDER);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                listSegnalazioniSO = excelReaderSO.ExcelReaderSO(XLSX_FILE_PATH_SO_FOLDER+"/"+file.getName());
            }
        }

        ArrayList<SegnalazioniPDB> listSegnalazioniPDB = new ArrayList<>();

        folder = new File(XLSX_FILE_PATH_PDB_FOLDER);
        listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                listSegnalazioniPDB = excelReaderPDB.ExcelReaderPDB(XLSX_FILE_PATH_PDB_FOLDER+"/"+file.getName());
            }
        }
        assegnaSegnalazioniAlTreno(listTreniNoImpianto500, size500, listSegnalazioniSO, listSegnalazioniPDB);
        assegnaSegnalazioniAlTreno(listTreniNoImpianto1000, size1000, listSegnalazioniSO, listSegnalazioniPDB);
        assegnaSegnalazioniAlTreno(listTreniNoImpianto700, size700, listSegnalazioniSO, listSegnalazioniPDB);
        assegnaSegnalazioniAlTreno(listTreniNoImpianto600, size600, listSegnalazioniSO, listSegnalazioniPDB);

        stampaSegnalazioniAlTreno(listTreniNoImpianto500, size500);
        stampaSegnalazioniAlTreno(listTreniNoImpianto1000, size1000);
        stampaSegnalazioniAlTreno(listTreniNoImpianto700, size700);
        stampaSegnalazioniAlTreno(listTreniNoImpianto600, size600);


//        excelWriter.writeMultiDate(excelReaderIVU.giriPrimaRientroImpianto500, list1000, list700, list600, listSegnalazioni, listSegnalazioniPDB, dateToSearch);

        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

        excelWriter.writeMultiDate(listTreniNoImpianto500, listTreniNoImpianto1000, listTreniNoImpianto700, listTreniNoImpianto600, listSegnalazioniSO,listSegnalazioniPDB, dt.format(searchDate));

//        Materiali fermi da 24H
//        ArrayList<Integer> listTreniFermi24H500 = excelReaderIVU.numMatFermiDa24H500;
//        ArrayList<Integer> listTreniFermi24H1000 = excelReaderIVU.numMatFermiDa24H1000;
//        ArrayList<Integer> listTreniFermi24H700 = excelReaderIVU.numMatFermiDa24H700;
//        ArrayList<Integer> listTreniFermi24H600 = excelReaderIVU.numMatFermiDa24H600;

//        excelWriterMaterialiFermi24H.write(listTreniFermi24H500,listTreniFermi24H1000, listTreniFermi24H700, listTreniFermi24H600, dateToSearch);


    }

    private static void assegnaSegnalazioniAlTreno(ArrayList<Treno>[] listTreniNoImpianto, int sizeListaTreno, ArrayList<SegnalazioniSO> listSegnalazioniSO, ArrayList<SegnalazioniPDB> listSegnalazioniPDB) {

        for (int i=0; i<sizeListaTreno; i++){
            ArrayList<Treno> alTreno = listTreniNoImpianto[i];
            for (Treno treno : alTreno) {
                for (SegnalazioniSO sSO : listSegnalazioniSO) {
                    //devo comparare data, tipo materiale, numero materiale, nemero treno

                    if (treno.getDataPartenza().equals(sSO.getDataTreno()) &&
                            treno.getTipologiaMateriale().equals(sSO.getTipologiaVeicolo()) &&
                            treno.getNumeroMateriale() == sSO.getNumeroMateriale() &&
                            treno.getNumeroCorsa().equals(sSO.getNumeroTreno()) &&
                            treno.getTipologiaCorsa().equals("Corsa di linea")){

                        treno.addSegnalazioneSO(sSO);
                    }
                }
                for (SegnalazioniPDB sPDB : listSegnalazioniPDB) {
                    //devo comparare data, tipo materiale, numero materiale, nemero treno

                    if (treno.getDataPartenza().equals(sPDB.getDataTreno()) &&
                            treno.getTipologiaMateriale().equals(sPDB.getTipologiaVeicolo()) &&
                            treno.getNumeroMateriale() == sPDB.getNumeroMateriale() &&
                            treno.getNumeroCorsa().equals(sPDB.getNumeroTreno()) &&
                            treno.getTipologiaCorsa().equals("Corsa di linea")){

                        treno.addSegnalazionePDB(sPDB);
                    }
                }
            }
        }
    }

    private static void stampaSegnalazioniAlTreno(ArrayList<Treno>[] listTreniNoImpianto, int sizeListaTreno) {
        System.out.println("---------------------SEGNALAZIONI TRENI--------------------------------------------");
        for (int i=0; i<sizeListaTreno; i++){
            ArrayList<Treno> alTreno = listTreniNoImpianto[i];
            for (Treno treno : alTreno) {
                treno.printTrenoConSegnalazioni();
            }
            System.out.println();
        }
    }



    private static Date inputDataCommandLine() {
        System.out.println("Inserisci la data per la creazione della tabella nel formato gg/mm/aaaa es 26/04/2021");
        Scanner input = new Scanner(System.in);
        String dateToSearch = input.nextLine();
        System.out.println("Creo la tabella per la data: " + dateToSearch);

        if(!Utility.isValidDate(dateToSearch)){
            System.out.println("La data inserita NON è corretta!!!");
        }

        return Utility.stringToDate(dateToSearch);
    }



    //  Funzione per verificare la presenza dei file necessari
    private static boolean verificaFile(String path) {
        File f = new File(path);
        if (!f.exists()){
            System.out.println("Path assoluto del file: " + f.getAbsolutePath());
            System.out.println();
            System.out.println("FILE MANCANTE");
            System.out.println("Il file "+path+" NON è presente nella cartella di esecuzione");
            System.exit(0);
            return false;
        }
        return true;
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
    public static void printListTurnoIvuAggregataMultiDate(ArrayList<StrisciaIvu> list2[], ArrayList<SegnalazioniSO> listSegnalazioni, String materiali){

        System.out.println("/*-----------------------------------------------------AGGREGATA MULTI DATE " + materiali +" --------------------------------------------------------------------------------------------------*/");

        for (int k=0 ; k<list2.length; k++) {
            ArrayList<StrisciaIvu> list = list2[k];
            if (list.size()!=0)
                System.out.println("MATERIALE ETR N" + list.get(0).getNumeroMateriale());
            for (int i = 0; i < list.size(); i++) {
                StrisciaIvu tempStriscia = list.get(i);
                System.out.println(tempStriscia.toString());
                for (int j = 0; j < listSegnalazioni.size(); j++) {

                    if (tempStriscia.getDataPartenzaPrev().equals(listSegnalazioni.get(j).getDataTreno()) &&
                                tempStriscia.getTipologiaVeicolo().equals(listSegnalazioni.get(j).getTipologiaVeicolo()) && tempStriscia.getNumeroMateriale() == listSegnalazioni.get(j).getNumeroMateriale()) {
                        System.out.println(" -   " + listSegnalazioni.get(j).toString());
                    }
                }
            }
        }
        System.out.println();
        System.out.println("**************************************************************************************************************************************************************************************");
        System.out.println();
        System.out.println();
    }
}


