package Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utility {
    public static int size500 = 61;
    public static int size1000 = 70;
    public static int size700 = 18;
    public static int size600 = 50;

    public static GregorianCalendar parseDate(String date) {
        int[] parsed = new int[3];
        int j = 0;
        String token = "";
        for (int i = 0; i < 10; i++) {
            if (date.charAt(i) != '/') {
                token += date.charAt(i);
//                System.out.println(token);
            }
            else {
//                System.out.println("toke: "+token);
                parsed[j++] = Integer.parseInt(token);
                token = "";
            }
        }
        parsed[2] = Integer.parseInt(token);
//        System.out.println(parsed[2] + " " + parsed[1] + " " + parsed[0]);
        return new GregorianCalendar(parsed[2], parsed[1] - 1, parsed[0]);
    }

    public static boolean isValidDate(String date) {
//        System.out.println("DATE: " + date);
        GregorianCalendar check = parseDate(date);
        check.setLenient(false);
        try {
            check.get(Calendar.DATE);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static Date stringToDate(String dateToSearch){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy H:m", Locale.ITALY);
        Date searchDate = null;
        try {
            searchDate = format.parse(dateToSearch);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println("Search DATE: "+searchDate); // Sat Jan 02 00:00:00 GMT 2010

        return searchDate;
    }

    public static void main(String[] args) {
        System.out.println("Inserisci una data nel formato dd/mm/yyyy: ");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
//        String date = "01/01/2021";
        System.out.println("Ho letto la data: " + date);
        if (isValidDate(date))
            System.out.println("La data è corretta");
        else System.out.println("La data non è corretta");
    }

    public static ArrayList<Integer> numMateriali500 = new ArrayList<>();
    public static ArrayList<Integer> numMateriali1000 = new ArrayList<>();
    public static ArrayList<Integer> numMateriali700 = new ArrayList<>();
    public static ArrayList<Integer> numMateriali600 = new ArrayList<>();

//    Classe utilizzate per la funzione che trova i materiali fermi da 24H. Ho bisogno di avere tutti i materliali in circolazione.
    public static void compilaALnumeriMateriali() {
        for (int i = 1; i <= 60; i++) {
            if (i != 17) {
                numMateriali500.add(i);
            }
        }
        for (int i = 1; i <= 50; i++) {
            numMateriali1000.add(i);
        }
//      aggiungo i 700
        for (int i = 1; i <= 17; i++) {
            if (i == 3) {
                continue;
            }
            if (i == 8){
                continue;
            }

            numMateriali700.add(i);
        }
//      Aggiungo i 600
        for (int i = 1; i <= 12; i++) {
            numMateriali600.add(i);

        }
//      Aggiungo i 460-463
        for (int i = 21; i <= 28; i++) {
            if (i != 3) {
                numMateriali600.add(i);
            }
        }
        numMateriali600.add(30);

//      Aggiungo i 485
        for (int i = 31; i <= 45; i++) {
            if (i != 3) {
                numMateriali600.add(i);
            }
        }
    }

    public static Map<String, String> creaMappaLocalita() {
        Map<String, String> mapLocalità = new HashMap<>();

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
            mapLocalità.put("GESM","GENOVA SAN PIER D'ARENA");
            mapLocalità.put("LESMC","LECCE");
            mapLocalità.put("MICL","MILANO CENTRALE");
            mapLocalità.put("MIPAC","MILANO PARCO CENTRALE");
            mapLocalità.put("MICE","MILANO MILANO CERTOSA");
            mapLocalità.put("MN","MANTOVA");
            mapLocalità.put("MSDL","VENEZIA MESTRE");
            mapLocalità.put("MSCL","MESTRE CENTRALE");
            mapLocalità.put("NACL","NAPOLI CENTRALE");
            mapLocalità.put("MODA","MODANE");
            mapLocalità.put("PAGLY","PARIGI");
            mapLocalità.put("PECL","PESCARA");
            mapLocalità.put("PG","PERUGIA");
            mapLocalità.put("RA","RAVENNA");
            mapLocalità.put("RCCL","REGGIO CALABRIA CENTRALE");
            mapLocalità.put("RCDL","REGGIO CALABRIA DL");
            mapLocalità.put("RMOMV","ROMA MAV");
            mapLocalità.put("RMTM","ROMA TERMINI");
            mapLocalità.put("RMOS","ROMA OSTIENSE");
            mapLocalità.put("SA","SALERNO");
            mapLocalità.put("SIB","SIBARI");
            mapLocalità.put("SRBM","LECCE SURBO");
            mapLocalità.put("TA","TARANTO");
            mapLocalità.put("TOSN","TORINO SMISTAMENTO");
            mapLocalità.put("TOPN","TORINO PORTA NUOVA");
            mapLocalità.put("TSCL","TRIESTE CENTRALE");
            mapLocalità.put("UD","UDINE");
            mapLocalità.put("VI","VICENZA");

        return mapLocalità;
    }

}
