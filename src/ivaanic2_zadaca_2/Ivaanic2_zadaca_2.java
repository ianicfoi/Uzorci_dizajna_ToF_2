/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivaanic2_zadaca_2;

import ivaanic2_zadaca_2.dretve.DretvaProvjeraMjesta;
import ivaanic2_zadaca_2.singleton.GeneratorSlucajnogBrojaSingleton;
import ivaanic2_zadaca_2.singleton.Mjesta;
import ivaanic2_zadaca_2.singleton.StatistikaSingleton;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author Ivan
 */
public class Ivaanic2_zadaca_2 {

    public static String datotekaMjesta;
    public static String datotekaSenzora;
    public static String datotekaAktuatora;
    public static String datotekaRezultata;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        UpisUDatoteku zapis = new UpisUDatoteku("izlaz.txt");
        GeneratorSlucajnogBrojaSingleton generator = GeneratorSlucajnogBrojaSingleton.getInstance();
        StatistikaSingleton stat = StatistikaSingleton.getInstance();
        Random rand = new Random();

        int sjemeGenerator = 0;
        int trajanjeCiklusaDretve = 0;
        int brojCiklusaDretve = 0;
        int brojLinijaSpremnika = 0;
        String klasaAlgoritma = null;

        try {
            if (args.length == 1) {
                if (args[0].equals("--help")) {
                    help();
                } else {
                    System.out.println("Uneseni su pogresni argumenti.");
                }
            } else {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-g")) {
                        sjemeGenerator = Integer.parseInt(args[i + 1]);
                    } else if (args[i].equals("-m")) {
                        datotekaMjesta = args[i + 1];
                    } else if (args[i].equals("-s")) {
                        datotekaSenzora = args[i + 1];
                    } else if (args[i].equals("-a")) {
                        datotekaAktuatora = args[i + 1];
                    } else if (args[i].equals("-alg")) {
                        klasaAlgoritma = args[i + 1];
                    } else if (args[i].equals("-tcd")) {
                        trajanjeCiklusaDretve = Integer.parseInt(args[i + 1]);
                    } else if (args[i].equals("-bcd")) {
                        brojCiklusaDretve = Integer.parseInt(args[i + 1]);
                    } else if (args[i].equals("-i")) {
                        datotekaRezultata = (args[i + 1]);
                    } else if (args[i].equals("-brl")) {
                        brojLinijaSpremnika = Integer.parseInt(args[i + 1]);
                    }
                }

                if (datotekaMjesta == null || datotekaSenzora == null || datotekaAktuatora == null || datotekaRezultata == null) {
                    System.out.println("Moraju biti unesena imena svih datoteka.");
                    System.exit(1);
                } else {
                    if (sjemeGenerator < 100 || sjemeGenerator > 65535) {
                        Calendar cal = Calendar.getInstance();
                        int sec = cal.get(Calendar.SECOND);
                        int msec = cal.get(Calendar.MILLISECOND);
                        sjemeGenerator = sec * 1000 + msec;
                    }

                    GeneratorSlucajnogBrojaSingleton.sjeme = sjemeGenerator;

                    if (trajanjeCiklusaDretve == 0) {
                        trajanjeCiklusaDretve = generator.dajSlucajniBroj(1, 17);
                    }

                    if (brojCiklusaDretve == 0) {
                        brojCiklusaDretve = generator.dajSlucajniBroj(1, 23);
                    }

                    if (brojLinijaSpremnika == 0) {
                        brojLinijaSpremnika = generator.dajSlucajniBroj(100, 999);
                    }

                    System.out.println("Sjeme za generator: " + sjemeGenerator);
                    System.out.println("Naziv datoteke mjesta: " + datotekaMjesta);
                    System.out.println("Naziv datoteke senzora: " + datotekaSenzora);
                    System.out.println("Naziv datoteke aktuatora: " + datotekaAktuatora);
                    System.out.println("Naziv algoritma: " + klasaAlgoritma);
                    System.out.println("Trajanje ciklusa dretve: " + trajanjeCiklusaDretve);
                    System.out.println("Broj ciklusa dretve: " + brojCiklusaDretve);
                    System.out.println("Datoteka izlaza: " + datotekaRezultata);
                    System.out.println("Broj linija spremnika: " + brojLinijaSpremnika);

                    Mjesta ms = Mjesta.getInstance();

                    ms.konfigurirajSustav();
                    ms.inicijalizirajSustav();
                    ms.opremiMjesta();

                    DretvaProvjeraMjesta dretva = new DretvaProvjeraMjesta(brojCiklusaDretve, trajanjeCiklusaDretve, klasaAlgoritma, sjemeGenerator);
                    dretva.start();

                    UpisUDatoteku.zatvoriDatoteku();
                }

            }

        } catch (NumberFormatException e) {
            System.out.println("Krivo upisani argumenti.");
        }

    }

    private static void help() {
        System.out.println("-g      sjeme za generator slucajnog broja (100-65535)");
        System.out.println("-m      naziv datoteke mjesta");
        System.out.println("-s      naziv datoteke senzora");
        System.out.println("-a      naziv datoteke aktuatora");
        System.out.println("-alg    puni naziv klase algoritma provjere koja se dinamicki ucitava");
        System.out.println("-tcd    trajanje ciklusa dretve u sek (1-17)");
        System.out.println("-bcd    broj ciklusa dretve (1-23)");
        System.out.println("-i      naziv datoteke u koju se sprema izlaz programa");
        System.out.println("-brl    broj linija u spremniku za upis u datoteku za izlaz (100-999)");
    }

}
