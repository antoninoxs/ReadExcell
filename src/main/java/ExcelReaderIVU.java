import Entity.StrisciaIvu;
import Entity.TurnoMacchina;
import Utility.Utility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

import static Utility.Utility.*;

public class ExcelReaderIVU {
    public static final String[] dep500 = {"MSDL", "MIMA", "NAIF"};
    public static final String[] dep1000 = {"MIMA", "NAIF"};
    public static final String[] dep700 = {"MIMA"};
    public static final String[] dep600 = {"RMOMV"};

    public static final ArrayList<String> dep500AL = new ArrayList<>(Arrays.asList(dep500));
    public static final ArrayList<String> dep1000AL = new ArrayList<>(Arrays.asList(dep1000));
    public static final ArrayList<String> dep700AL = new ArrayList<>(Arrays.asList(dep700));
    public static final ArrayList<String> dep600AL = new ArrayList<>(Arrays.asList(dep600));

    int size500 = 61;
    int size1000 = 51;
    int size700 = 18;
    int size600 = 50;


    public ArrayList<StrisciaIvu> giriGiornalieri500[] = new ArrayList[size500];
    public ArrayList<StrisciaIvu> giriGiornalieri1000[] = new ArrayList[size1000];
    public ArrayList<StrisciaIvu> giriGiornalieri700[] = new ArrayList[size700];
    public ArrayList<StrisciaIvu> giriGiornalieri600[] = new ArrayList[size600];

    public ArrayList<StrisciaIvu> listTurnoFuoriImpianto500 = new ArrayList<StrisciaIvu>();
    public ArrayList<StrisciaIvu> listTurnoFuoriImpianto1000 = new ArrayList<StrisciaIvu>();
    public ArrayList<StrisciaIvu> listTurnoFuoriImpianto700 = new ArrayList<StrisciaIvu>();
    public ArrayList<StrisciaIvu> listTurnoFuoriImpianto600 = new ArrayList<StrisciaIvu>();

    public ArrayList<Integer> numMatFermiDa24H500 = new ArrayList<>();
    public ArrayList<Integer> numMatFermiDa24H1000 = new ArrayList<>();
    public ArrayList<Integer> numMatFermiDa24H700 = new ArrayList<>();
    public ArrayList<Integer> numMatFermiDa24H600 = new ArrayList<>();


    public void ExcelREaderIVU(String path, Date searchDate, ArrayList<TurnoMacchina> turnoMacchinaAL) throws IOException, InvalidFormatException {

//      INIZIALIZZO GLI ARRAY LIST
        for (int i = 0; i<size500; i++){
            giriGiornalieri500[i] = new ArrayList<StrisciaIvu>();
        }
        for (int i = 0; i<size1000; i++){
            giriGiornalieri1000[i] = new ArrayList<StrisciaIvu>();
        }
        for (int i = 0; i<size700; i++){
            giriGiornalieri700[i] = new ArrayList<StrisciaIvu>();
        }
        for (int i = 0; i<size600; i++){
            giriGiornalieri600[i] = new ArrayList<StrisciaIvu>();
        }


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
                Row row = rowIterator.next();
                salta = false;
            }
            Row row = rowIterator.next();
            // Now let's iterate over the columns of the current row

//            String pattern = "yyyy-MM-dd";
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            StrisciaIvu sIvu = new StrisciaIvu();
            Date tmpDate = row.getCell(0).getDateCellValue();

//            tmpDate.parse
            sIvu.setDataPartenzaPrev(tmpDate);
            sIvu.setDenominazioneTurnoMacc(dataFormatter.formatCellValue(row.getCell(2)));
            sIvu.setDepositoPartenza(dataFormatter.formatCellValue(row.getCell(4)));
            sIvu.setDepositoArrivo(dataFormatter.formatCellValue(row.getCell(10)));

            String oraPartPrev = dataFormatter.formatCellValue(row.getCell(5));
            LocalTime localTimePartPrev = LocalTime.of(Integer.parseInt(oraPartPrev.substring(0,2)), Integer.parseInt(oraPartPrev.substring(3,5)));
            sIvu.setPartenzaPrevista(localTimePartPrev);

//            System.out.println(localTimePartPrev.toString());
//            System.out.println(oraPartPrev + "    " + oraPartPrev.substring(0,2) + " : " + oraPartPrev.substring(3,5));
//            System.out.println(Integer.parseInt(oraPartPrev.substring(0,2)) + " : " + Integer.parseInt(oraPartPrev.substring(2,2)));
            String oraArrPrev = dataFormatter.formatCellValue(row.getCell(13));
            LocalTime localTimeArrPrev = LocalTime.of(Integer.parseInt(oraArrPrev.substring(0,2)), Integer.parseInt(oraArrPrev.substring(3,5)));
            sIvu.setArrivoPrevisto(localTimeArrPrev);

//            String tipologiaVeicolo = dataFormatter.formatCellValue(row.getCell(7));
//            if(tipologiaVeicolo.equals("ETR500AV"))
//                tipologiaVeicolo = tipologiaVeicolo.substring(0,tipologiaVeicolo.length()-2);
//            sIvu.setTipologiaVeicolo(tipologiaVeicolo);

            String numMaterialeCompleto = dataFormatter.formatCellValue(row.getCell(34));
//            System.out.println(numMaterialeCompleto);
            if(numMaterialeCompleto.length()==0)
                continue;

            String[] parts = numMaterialeCompleto.split("\\.");

            String tempTipMateriale = parts[0];
            String tempNumMateriale = parts[1];

            // parso il dato numeroMateriale per togliere l'orientamento
            if (tempNumMateriale.length()!=0)
                sIvu.setNumeroMateriale(Integer.parseInt(tempNumMateriale));

            if(tempTipMateriale.length()!=0)
                sIvu.setTipologiaVeicolo("ETR"+tempTipMateriale);

            sIvu.setNumeroTreno(dataFormatter.formatCellValue(row.getCell(11)));
            sIvu.setNumeroTrenoArrivo(dataFormatter.formatCellValue(row.getCell(39)));

//            listTurnoIvu.add(sIvu);

//            System.out.println("*********************************************************");
//            System.out.println(sIvu.toString());
//            System.out.println("*********************************************************");


            if(sIvu.getDataPartenzaPrev().equals(searchDate)){
                if(sIvu.getTipologiaVeicolo().equals("ETR500")) {
                    giriGiornalieri500[sIvu.getNumeroMateriale()].add(sIvu);
                }

                if(sIvu.getTipologiaVeicolo().equals("ETR1000")) {
                    giriGiornalieri1000[sIvu.getNumeroMateriale()].add(sIvu);
                }

                if(sIvu.getTipologiaVeicolo().equals("ETR700")) {
                    giriGiornalieri700[sIvu.getNumeroMateriale()].add(sIvu);
                }

                if(sIvu.getTipologiaVeicolo().equals("ETR460") || sIvu.getTipologiaVeicolo().equals("ETR463") ||  sIvu.getTipologiaVeicolo().equals("ETR485")||sIvu.getTipologiaVeicolo().equals("ETR600")) {
                    giriGiornalieri600[sIvu.getNumeroMateriale()].add(sIvu);

                }
            }
        }

        printGiriGiornalieri();

        materialiFuoriImpianto(turnoMacchinaAL);

        printMaterialiFUoriImpianto(listTurnoFuoriImpianto500);
        printMaterialiFUoriImpianto(listTurnoFuoriImpianto1000);
        printMaterialiFUoriImpianto(listTurnoFuoriImpianto700);
        printMaterialiFUoriImpianto(listTurnoFuoriImpianto600);

        materialiFermiDa24H();


        // Closing the workbook
        workbook.close();
    }

    public void ExcelREaderIVUmultiDate(String path, Date dataPartenzaEsportazione, ArrayList<TurnoMacchina> turnoMacchinaAL) throws IOException, InvalidFormatException {

//      INIZIALIZZO GLI ARRAY LIST
        for (int i = 0; i<size500; i++){
            giriGiornalieri500[i] = new ArrayList<StrisciaIvu>();
        }
        for (int i = 0; i<size1000; i++){
            giriGiornalieri1000[i] = new ArrayList<StrisciaIvu>();
        }
        for (int i = 0; i<size700; i++){
            giriGiornalieri700[i] = new ArrayList<StrisciaIvu>();
        }
        for (int i = 0; i<size600; i++){
            giriGiornalieri600[i] = new ArrayList<StrisciaIvu>();
        }


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
                Row row = rowIterator.next();
                salta = false;
            }
            Row row = rowIterator.next();
            // Now let's iterate over the columns of the current row

//            String pattern = "yyyy-MM-dd";
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            StrisciaIvu sIvu = new StrisciaIvu();
            Date tmpDate = row.getCell(0).getDateCellValue();

//            tmpDate.parse
            sIvu.setDataPartenzaPrev(tmpDate);
            sIvu.setDenominazioneTurnoMacc(dataFormatter.formatCellValue(row.getCell(2)));
            sIvu.setDepositoPartenza(dataFormatter.formatCellValue(row.getCell(4)));
            sIvu.setDepositoArrivo(dataFormatter.formatCellValue(row.getCell(10)));

            String oraPartPrev = dataFormatter.formatCellValue(row.getCell(5));
            LocalTime localTimePartPrev = LocalTime.of(Integer.parseInt(oraPartPrev.substring(0,2)), Integer.parseInt(oraPartPrev.substring(3,5)));
            sIvu.setPartenzaPrevista(localTimePartPrev);

//            System.out.println(localTimePartPrev.toString());
//            System.out.println(oraPartPrev + "    " + oraPartPrev.substring(0,2) + " : " + oraPartPrev.substring(3,5));
//            System.out.println(Integer.parseInt(oraPartPrev.substring(0,2)) + " : " + Integer.parseInt(oraPartPrev.substring(2,2)));
            String oraArrPrev = dataFormatter.formatCellValue(row.getCell(13));
            LocalTime localTimeArrPrev = LocalTime.of(Integer.parseInt(oraArrPrev.substring(0,2)), Integer.parseInt(oraArrPrev.substring(3,5)));
            sIvu.setArrivoPrevisto(localTimeArrPrev);

//            String tipologiaVeicolo = dataFormatter.formatCellValue(row.getCell(7));
//            if(tipologiaVeicolo.equals("ETR500AV"))
//                tipologiaVeicolo = tipologiaVeicolo.substring(0,tipologiaVeicolo.length()-2);
//            sIvu.setTipologiaVeicolo(tipologiaVeicolo);

            String numMaterialeCompleto = dataFormatter.formatCellValue(row.getCell(34));
//            System.out.println(numMaterialeCompleto);
            if(numMaterialeCompleto.length()==0)
                continue;

            String[] parts = numMaterialeCompleto.split("\\.");

            String tempTipMateriale = parts[0];
            String tempNumMateriale = parts[1];

            // parso il dato numeroMateriale per togliere l'orientamento
            if (tempNumMateriale.length()!=0)
                sIvu.setNumeroMateriale(Integer.parseInt(tempNumMateriale));

            if(tempTipMateriale.length()!=0)
                sIvu.setTipologiaVeicolo("ETR"+tempTipMateriale);

            sIvu.setNumeroTreno(dataFormatter.formatCellValue(row.getCell(11)));
            sIvu.setNumeroTrenoArrivo(dataFormatter.formatCellValue(row.getCell(39)));

//            listTurnoIvu.add(sIvu);

//            System.out.println("*********************************************************");
//            System.out.println(sIvu.toString());
//            System.out.println("*********************************************************");


            if(sIvu.getDataPartenzaPrev().after(dataPartenzaEsportazione)){
                if(sIvu.getTipologiaVeicolo().equals("ETR500")) {
                    giriGiornalieri500[sIvu.getNumeroMateriale()].add(sIvu);
                }

                if(sIvu.getTipologiaVeicolo().equals("ETR1000")) {
                    giriGiornalieri1000[sIvu.getNumeroMateriale()].add(sIvu);
                }

                if(sIvu.getTipologiaVeicolo().equals("ETR700")) {
                    giriGiornalieri700[sIvu.getNumeroMateriale()].add(sIvu);
                }

                if(sIvu.getTipologiaVeicolo().equals("ETR460") || sIvu.getTipologiaVeicolo().equals("ETR463") ||  sIvu.getTipologiaVeicolo().equals("ETR485")||sIvu.getTipologiaVeicolo().equals("ETR600")) {
                    giriGiornalieri600[sIvu.getNumeroMateriale()].add(sIvu);

                }
            }
        }

        printGiriGiornalieri();

        materialiFuoriImpianto(turnoMacchinaAL);

        printMaterialiFUoriImpianto(listTurnoFuoriImpianto500);
        printMaterialiFUoriImpianto(listTurnoFuoriImpianto1000);
        printMaterialiFUoriImpianto(listTurnoFuoriImpianto700);
        printMaterialiFUoriImpianto(listTurnoFuoriImpianto600);

        materialiFermiDa24H();


        // Closing the workbook
        workbook.close();
    }

    public void printTurnoIvu(){
//        System.out.println("Dimensione turnoIvu: " + listTurnoIvu.size());
//        for (int i=0; i<listTurnoIvu.size(); i++){
//            System.out.println(i + " " + listTurnoIvu.get(i).toString());
//        }
//        System.out.println();
        System.out.println("------------------------------------------------------------------------");

        System.out.println();
        System.out.println("500 fuori deposito: " + listTurnoFuoriImpianto500.size());
        for (int i = 0; i< listTurnoFuoriImpianto500.size(); i++){
            System.out.println(i + " " + listTurnoFuoriImpianto500.get(i).toString());
        }

//        System.out.println();
//        System.out.println("1000 fuori deposito: " + listTurnoIvuOdierno1000.size());
//        for (int i=0; i<listTurnoIvuOdierno1000.size(); i++){
//            System.out.println(i + " " + listTurnoIvuOdierno1000.get(i).toString());
//        }

        System.out.println();
//        System.out.println("700 fuori deposito: " + listTurnoIvuOdierno700.size());
//        for (int i=0; i<listTurnoIvuOdierno700.size(); i++){
//            System.out.println(i + " " + listTurnoIvuOdierno700.get(i).toString());
//        }
//
//        System.out.println();
//        System.out.println("FA fuori deposito: " + listTurnoIvuOdierno600.size());
//        for (int i=0; i<listTurnoIvuOdierno600.size(); i++){
//            System.out.println(i + " " + listTurnoIvuOdierno600.get(i).toString());
//        }

        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    private static String parseNumeroMateriale2(String nMateriale) {

        String numeroMateriale = "";
        int l = nMateriale.length();
        if(l == 0)
            return "";
        else {
//            System.out.println(nMateriale);
            String[] parts = nMateriale.split("\\.");
//            System.out.println(parts[0]);
//            System.out.println(parts[1]);
            String subString = parts[1];
            if (subString.charAt(subString.length()-1) == '<' || subString.charAt(subString.length()-1) == '>' ){
                numeroMateriale = subString.substring(0,subString.length()-1);
            }
            else numeroMateriale = subString;
        }
//        System.out.println("ORIGINALE: " + nMateriale);
//        System.out.println("ESTRAZIONE: " + numeroMateriale);
        return numeroMateriale;
    }

    public void printGiriGiornalieri() {

        System.out.println("**********************************************************  GIRO 500  *****************************************************************************************");
        for (int i = 0; i < size500; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri500[i];
            int sizeGiri = giriMateriale.size();
            System.out.println("MATERIALE # " + i + "");
            for (int j = 0; j < sizeGiri; j++) {
                System.out.println(giriMateriale.get(j).toString());
            }
            System.out.println();
        }

        System.out.println("**********************************************************  GIRO 1000  *****************************************************************************************");
        for (int i = 0; i < size1000; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri1000[i];
            int sizeGiri = giriMateriale.size();
            System.out.println("MATERIALE # " + i + "");
            for (int j = 0; j < sizeGiri; j++) {
                System.out.println(giriMateriale.get(j).toString());
            }
            System.out.println();
        }

        System.out.println("**********************************************************  GIRO 700  *****************************************************************************************");
        for (int i = 0; i < size700; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri700[i];
            int sizeGiri = giriMateriale.size();
            System.out.println("MATERIALE # " + i + "");
            for (int j = 0; j < sizeGiri; j++) {
                System.out.println(giriMateriale.get(j).toString());
            }
            System.out.println();
        }

        System.out.println("**********************************************************  GIRO FA  *****************************************************************************************");
        for (int i = 0; i < size600; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri600[i];
            int sizeGiri = giriMateriale.size();
            System.out.println("MATERIALE # " + i + "");
            for (int j = 0; j < sizeGiri; j++) {
                System.out.println(giriMateriale.get(j).toString());
            }
            System.out.println();
        }
    }

    public void printMaterialiFUoriImpianto(ArrayList<StrisciaIvu> al){
        System.out.println("-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/");
        for (int i=0; i<al.size(); i++){
            System.out.println(al.get(i).toString());
        }
        System.out.println();
        System.out.println("-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/-*/");
        System.out.println();
        System.out.println();
    }

    public void materialiFuoriImpianto(){
        for (int i = 0; i < size500; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri500[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep500AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        listTurnoFuoriImpianto500.add(tempStrisciaIvu);
                    }
                }
            }
        }

        for (int i = 0; i < size1000; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri1000[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep1000AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        listTurnoFuoriImpianto1000.add(tempStrisciaIvu);
                    }
                }
            }
        }
        for (int i = 0; i < size700; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri700[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep700AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        listTurnoFuoriImpianto700.add(tempStrisciaIvu);
                    }
                }
            }
        }
        for (int i = 0; i < size600; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri600[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep600AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        listTurnoFuoriImpianto600.add(tempStrisciaIvu);
                    }
                }
            }
        }

    }

    public void materialiFuoriImpianto(ArrayList<TurnoMacchina> turnoMacchinaAL){
        for (int i = 0; i < size500; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri500[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep500AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        String denomTurnoMacchina = tempStrisciaIvu.getDenominazioneTurnoMacc();
                        for(TurnoMacchina tm : turnoMacchinaAL){
                            if(tm.getDenominazioneTurnoMacc().equals(denomTurnoMacchina) && tm.getNumeroMateriale() == tempStrisciaIvu.getNumeroMateriale()){
                                if(!tempStrisciaIvu.getTreniStrisciaIVU().contains(tm.getNumeroCorsa())){
                                    tempStrisciaIvu.addTrenoTurnoMacchina(tm.getNumeroCorsa());
                                }
                            }
                        }
                        listTurnoFuoriImpianto500.add(tempStrisciaIvu);
                    }
                }
                tempStrisciaIvu.printTreniTurnoMacchina();
            }
        }

        for (int i = 0; i < size1000; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri1000[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep1000AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        String denomTurnoMacchina = tempStrisciaIvu.getDenominazioneTurnoMacc();
                        for(TurnoMacchina tm : turnoMacchinaAL){
                            if(tm.getDenominazioneTurnoMacc().equals(denomTurnoMacchina) && tm.getNumeroMateriale() == tempStrisciaIvu.getNumeroMateriale()){
                                if(!tempStrisciaIvu.getTreniStrisciaIVU().contains(tm.getNumeroCorsa())){
                                    tempStrisciaIvu.addTrenoTurnoMacchina(tm.getNumeroCorsa());
                                }
                            }
                        }
                        listTurnoFuoriImpianto1000.add(tempStrisciaIvu);
                    }
                }
                tempStrisciaIvu.printTreniTurnoMacchina();
            }
        }
        for (int i = 0; i < size700; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri700[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep700AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        String denomTurnoMacchina = tempStrisciaIvu.getDenominazioneTurnoMacc();
                        for(TurnoMacchina tm : turnoMacchinaAL){
                            if(tm.getDenominazioneTurnoMacc().equals(denomTurnoMacchina) && tm.getNumeroMateriale() == tempStrisciaIvu.getNumeroMateriale()){
                                if(!tempStrisciaIvu.getTreniStrisciaIVU().contains(tm.getNumeroCorsa())){
                                    tempStrisciaIvu.addTrenoTurnoMacchina(tm.getNumeroCorsa());
                                }
                            }
                        }
                        listTurnoFuoriImpianto700.add(tempStrisciaIvu);
                    }
                }
                tempStrisciaIvu.printTreniTurnoMacchina();
            }
        }
        for (int i = 0; i < size600; i++) {
            ArrayList<StrisciaIvu> giriMateriale = giriGiornalieri600[i];
            int sizeGiri = giriMateriale.size();

            if(sizeGiri != 0){
                StrisciaIvu tempStrisciaIvu = giriMateriale.get(sizeGiri-1);
                if(tempStrisciaIvu.getNumeroTrenoArrivo().equals(tempStrisciaIvu.getNumeroTrenoArrivo())){
                    if (!dep600AL.contains(tempStrisciaIvu.getDepositoArrivo())){
                        String denomTurnoMacchina = tempStrisciaIvu.getDenominazioneTurnoMacc();
                        for(TurnoMacchina tm : turnoMacchinaAL){
                            if(tm.getDenominazioneTurnoMacc().equals(denomTurnoMacchina) && tm.getNumeroMateriale() == tempStrisciaIvu.getNumeroMateriale()){
                                if(!tempStrisciaIvu.getTreniStrisciaIVU().contains(tm.getNumeroCorsa())){
                                    tempStrisciaIvu.addTrenoTurnoMacchina(tm.getNumeroCorsa());
                                }
                            }
                        }
                        listTurnoFuoriImpianto600.add(tempStrisciaIvu);
                    }
                }
                tempStrisciaIvu.printTreniTurnoMacchina();
            }
        }

    }

    public void materialiFermiDa24H(){
        Utility.compilaALnumeriMateriali();

        System.out.println();
        for (int i : numMateriali500){
            if (giriGiornalieri500[i].isEmpty()){
                numMatFermiDa24H500.add(i);
            }
        }

        for (int i : numMateriali1000){
            if (giriGiornalieri1000[i].isEmpty()){
                numMatFermiDa24H1000.add(i);
            }
        }

        for (int i : numMateriali700){
            if (giriGiornalieri700[i].isEmpty()){
                numMatFermiDa24H700.add(i);
            }
        }

        for (int i : numMateriali600){
            if (giriGiornalieri600[i].isEmpty()){
                numMatFermiDa24H600.add(i);
            }
        }
        printMaterialiFermiDa24H();
    }

    public void printMaterialiFermiDa24H(){
        compilaALnumeriMateriali();
        System.out.println();
        String print = "";
        for (int i : numMatFermiDa24H500){
            print += i + ", ";
        }
        System.out.println(print);

        System.out.println();
        print = "";
        for (int i : numMatFermiDa24H1000){
            print += i + ", ";
        }
        System.out.println(print);

        System.out.println();
        print = "";
        for (int i : numMatFermiDa24H700){
            print += i + ", ";
        }
        System.out.println(print);

        System.out.println();
        print = "";
        for (int i : numMatFermiDa24H600){
            print += i + ", ";
        }
        System.out.println(print);
    }
}