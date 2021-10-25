package Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Utility {

    public static GregorianCalendar parseDate(String date) {
        int[] parsed = new int[3];
        int j = 0;
        String token = "";
        for (int i = 0; i < date.length(); i++) {
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


}
