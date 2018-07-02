/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivaanic2_zadaca_2;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class UpisUDatoteku {

    
    private String datoteka;
    static Writer writer;
    
    private static String izlaznaDatoteka;
    private static int izlazniSpremnik;
    private static int duljinaIzlaznogSpremnika = 0;
    private static String izlazniTekst = "";

    public UpisUDatoteku(String nazivDatoteke) throws UnsupportedEncodingException, FileNotFoundException {

        this.datoteka = nazivDatoteke;
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(datoteka, true), "utf-8"));

    }
    
    //spremnik
    public void spremiPodatke(String tekst) throws IOException {
        duljinaIzlaznogSpremnika++;
        izlazniTekst += tekst + System.lineSeparator();
        
        if (duljinaIzlaznogSpremnika == izlazniSpremnik) {
            Path path = Paths.get(izlaznaDatoteka);
            Files.write(path, (izlazniTekst + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.C‌​REATE, StandardOpenOp‌​tion.APPEND);
            duljinaIzlaznogSpremnika = 0;
            izlazniTekst = "";
        }
    }
    
    
    
    public static void upisiUDatoteku(String text) {
        try {
            writer.write(text);
            writer.append(System.lineSeparator());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UpisUDatoteku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UpisUDatoteku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpisUDatoteku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void zatvoriDatoteku() {
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(UpisUDatoteku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDatoteka() {
        return datoteka;
    }

    public void setDatoteka(String datoteka) {
        this.datoteka = datoteka;
    }
    
    
    public static String getIzlaznaDatoteka() {
        return izlaznaDatoteka;
    }

    public static void setIzlaznaDatoteka(String aIzlaznaDatoteka) {
        izlaznaDatoteka = aIzlaznaDatoteka;
    }

    public static int getIzlazniSpremnik() {
        return izlazniSpremnik;
    }

    public static void setIzlazniSpremnik(int aIzlazniSpremnik) {
        izlazniSpremnik = aIzlazniSpremnik;
    }

    public static int getDuljinaIzlaznogSpremnika() {
        return duljinaIzlaznogSpremnika;
    }

    public static void setDuljinaIzlaznogSpremnika(int aDuljinaIzlaznogSpremnika) {
        duljinaIzlaznogSpremnika = aDuljinaIzlaznogSpremnika;
    }

    public static String getIzlazniTekst() {
        return izlazniTekst;
    }

    public static void setIzlazniTekst(String aIzlazniTekst) {
        izlazniTekst = aIzlazniTekst;
    }
    
}
