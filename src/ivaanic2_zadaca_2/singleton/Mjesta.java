/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivaanic2_zadaca_2.singleton;

import ivaanic2_zadaca_2.Ivaanic2_zadaca_2;
import static ivaanic2_zadaca_2.Ivaanic2_zadaca_2.datotekaAktuatora;
import ivaanic2_zadaca_2.UpisUDatoteku;
import ivaanic2_zadaca_2.model.Aktuatori;
import ivaanic2_zadaca_2.model.Senzori;
import ivaanic2_zadaca_2.model.Uredaji;
import ivaanic2_zadaca_2.builder.Aktuator;
import ivaanic2_zadaca_2.builder.AktuatorBuilder;
import ivaanic2_zadaca_2.builder.AktuatorBuilderImpl;
import ivaanic2_zadaca_2.builder.Senzor;
import ivaanic2_zadaca_2.builder.SenzorBuilder;
import ivaanic2_zadaca_2.builder.SenzorBuilderImpl;
import ivaanic2_zadaca_2.iterator.Container;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ivaanic2_zadaca_2.iterator.IteratorMj;
import java.util.Iterator;


/**
 *
 * @author Ivan
 */
public class Mjesta implements Comparable<Mjesta>, Container{

    private int idMjesta;
    private String naziv;
    private int tip;
    private int brojSenzora;
    private int brojAktuatora;
    
    

    public static List<Mjesta> listaMjesta = new ArrayList<>();
    private List<Senzor> listaSenzora = new ArrayList<>();
    private List<Aktuator> listaAktuatora = new ArrayList<>();
    private List<Senzor> listaSenzoraZaMjesto = new ArrayList<>();
    private List<Aktuator> listaAktuatoraZaMjesto = new ArrayList<>();
    
    GeneratorSlucajnogBrojaSingleton generator = GeneratorSlucajnogBrojaSingleton.getInstance();
    StatistikaSingleton stat = StatistikaSingleton.getInstance();

    //varijabla instance
    private static Mjesta instanca = null;

    //privatni konstruktor
    private Mjesta() {
    }

    public Mjesta(String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
    }

    //metoda za dohvacanje instance
    public static Mjesta getInstance() {
        if (instanca == null) {
            instanca = new Mjesta();
        }
        return instanca;
    }

    
    
    

    public void konfigurirajSustav() throws UnsupportedEncodingException, FileNotFoundException {
        
        

        UpisUDatoteku zapis = new UpisUDatoteku(Ivaanic2_zadaca_2.datotekaRezultata);
        UpisUDatoteku.upisiUDatoteku("Zapocela konfiguracija");

        //provjera ispravnosti podataka u datoteci senzora
        //punjenje liste senzora iz datoteke senzora
        //preskakanje neispravnih redaka
        try {
            //BufferedReader in = new BufferedReader(new FileReader("DZ_1_senzori.txt"));
            BufferedReader in = new BufferedReader(new FileReader(Ivaanic2_zadaca_2.datotekaSenzora));
            String sintaksa = "^(.+);(0|1|2);(0|1|2|3);(.+);(.+);(.*)$";
            String str;
            str = in.readLine();
            String[] ar = null;
            while ((str = in.readLine()) != null) {
                Pattern pattern = Pattern.compile(sintaksa);
                Matcher m = pattern.matcher(str);
                if (m.matches() == true) {
                    ar = str.split(";");
                    SenzorBuilder senzBuilder = new SenzorBuilderImpl();
                    senzBuilder.setNaziv(ar[0]);
                    senzBuilder.setTip(Integer.valueOf(ar[1]));
                    senzBuilder.setVrsta(Integer.valueOf(ar[2]));
                    senzBuilder.setMinVrijednost(Float.valueOf(ar[3]));
                    senzBuilder.setMaxVrijednost(Float.valueOf(ar[4]));
                    if (ar.length != 6) {
                        senzBuilder.setKomentar("");
                    } else {
                        senzBuilder.setKomentar(ar[5]);
                    }
                    Senzor s = senzBuilder.build();
                    listaSenzora.add(new Senzor(s));
                    stat.setBrojIspravnihSenzora(stat.getBrojIspravnihSenzora()+1);
                } else {
                    System.out.println("Neispravan redak " + str + " preskočen.");
                    UpisUDatoteku.upisiUDatoteku("Neispravan redak " + str + " preskočen.");
                    stat.setBrojNeispravnihSenzora(stat.getBrojNeispravnihSenzora()+1);
                    continue; //else samo ispise neispr redak i nema add naredbe i ne treba cont?
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Pogreska u citanju datoteke senzora");
            UpisUDatoteku.upisiUDatoteku("Pogreska u citanju datoteke senzora");
        }

        System.out.println("SENZORI: ");
        UpisUDatoteku.upisiUDatoteku("SENZORI: ");
        for (Senzor senzori : listaSenzora) {
            System.out.println(senzori.toString());//za zadnji senzor se ispisuje komentar
            UpisUDatoteku.upisiUDatoteku(senzori.toString());
        }
        System.out.println("");

        //provjera ispravnosti podataka u datoteci aktuatora
        //punjenje liste aktuatora iz datoteke aktuatora
        //preskakanje neispravnih redaka
        try {
            BufferedReader in = new BufferedReader(new FileReader(Ivaanic2_zadaca_2.datotekaAktuatora));
            String sintaksa = "^(.+);(0|1|2);(0|1|2|3);(.+);(.+);(.*)$";
            String str;
            str = in.readLine();
            String[] ar = null;
            while ((str = in.readLine()) != null) {
                Pattern pattern = Pattern.compile(sintaksa);
                Matcher m = pattern.matcher(str);
                if (m.matches() == true) {
                    ar = str.split(";");
                    AktuatorBuilder aktBuilder = new AktuatorBuilderImpl();
                    aktBuilder.setNaziv(ar[0]);
                    aktBuilder.setTip(Integer.valueOf(ar[1]));
                    aktBuilder.setVrsta(Integer.valueOf(ar[2]));
                    aktBuilder.setMinVrijednost(Float.valueOf(ar[3]));
                    aktBuilder.setMaxVrijednost(Float.valueOf(ar[4]));
                    if (ar.length != 6) {
                        aktBuilder.setKomentar("");
                    } else {
                        aktBuilder.setKomentar(ar[5]);
                    }
                    Aktuator a = aktBuilder.build();
                    listaAktuatora.add(new Aktuator(a));
                    stat.setBrojIspravnihAktuatora(stat.getBrojIspravnihAktuatora()+1);
                } else {
                    System.out.println("Neispravan redak " + str + " preskočen.");
                    UpisUDatoteku.upisiUDatoteku("Neispravan redak " + str + " preskočen.");
                    stat.setBrojNeispravnihAktuatora(stat.getBrojNeispravnihAktuatora()+1);
                    continue;
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Pogreska u citanju datoteke aktuatora");
            UpisUDatoteku.upisiUDatoteku("Pogreska u citanju datoteke aktuatora");
        }

        System.out.println("AKTUATORI: ");
        UpisUDatoteku.upisiUDatoteku("AKTUATORI: ");
        for (Aktuator aktuatori : listaAktuatora) {
            System.out.println(aktuatori.toString());
            UpisUDatoteku.upisiUDatoteku(aktuatori.toString());
        }
        System.out.println("");

        //provjera ispravnosti podataka u datoteci mjesta
        //punjenje liste mjesta iz datoteke mjesta
        //preskakanje neispravnih redaka
        try {
            Mjesta mjesto;
            BufferedReader in = new BufferedReader(new FileReader(Ivaanic2_zadaca_2.datotekaMjesta));
            String sintaksa = "^(.+);(0|1);(\\d+);(\\d+)$";
            String str;
            str = in.readLine();
            String[] ar = null;
            while ((str = in.readLine()) != null) {
                Pattern pattern = Pattern.compile(sintaksa);
                Matcher m = pattern.matcher(str);
                if (m.matches() == true) {
                    ar = str.split(";");
                    mjesto = new Mjesta(ar[0], Integer.valueOf(ar[1]), Integer.valueOf(ar[2]), Integer.valueOf(ar[3]));
                    mjesto.setIdMjesta(generator.dajSlucajniBroj(1, 1000));
                    listaMjesta.add(mjesto);
                    stat.setBrojIspravnihMjesta(stat.getBrojIspravnihMjesta()+1);
                } else {
                    System.out.println("Neispravan redak " + str + " preskočen.");
                    UpisUDatoteku.upisiUDatoteku("Neispravan redak " + str + " preskočen.");
                    stat.setBrojNeispravnihMjesta(stat.getBrojNeispravnihMjesta()+1);
                    continue;
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Pogreska u citanju datoteke mjesta");
            UpisUDatoteku.upisiUDatoteku("Pogreska u citanju datoteke mjesta");
        }

        System.out.println("MJESTA: ");
        UpisUDatoteku.upisiUDatoteku("MJESTA: ");
        for (Mjesta mjestaSingleton : listaMjesta) {
            System.out.println(mjestaSingleton.toString());
            UpisUDatoteku.upisiUDatoteku(mjestaSingleton.toString());
        }
        System.out.println("");


        
        
        //PRIDRUZIVANJE SENZORA MJESTIMA
        
        Random rand = new Random();
        int randomBroj = rand.nextInt(5) + 1;
        System.out.println("");
        System.out.println("Senzori pridruzeni mjestima: ");
        UpisUDatoteku.upisiUDatoteku("Senzori pridruzeni mjestima: ");

        for (Mjesta mjestaSingleton : listaMjesta) {//lista mjesta napunjena iz datoteke
            Collections.shuffle(listaSenzora, new Random(generator.dajSlucBroj()));
            for (Senzor senzori : listaSenzora) {//lista senzora napunjena iz datoteke
                    if (mjestaSingleton.getTip() == senzori.getTip() || senzori.getTip() == 2) {
                        senzori.setIdSenzora(generator.dajSlucajniBroj(1, 1000));
                        System.out.println("id senzora: " + senzori.getIdSenzora() + ", Senzor za mjesto: " + mjestaSingleton.getNaziv() + ": " + senzori.getNaziv());
                        UpisUDatoteku.upisiUDatoteku("id senzora: " + senzori.getIdSenzora() + ", Senzor za mjesto: " + mjestaSingleton.getNaziv() + ": " + senzori.getNaziv());
                        mjestaSingleton.listaSenzoraZaMjesto.add(senzori);
                        stat.setBrojDodijeljenihSenzora(stat.getBrojDodijeljenihSenzora()+1);
                    }
                    if (mjestaSingleton.listaSenzoraZaMjesto.size() == mjestaSingleton.getBrojSenzora()) {
                        break;
                }
            }
        }

        

        //PRIDRUZIVANJE AKTUATORA MJESTIMA

        System.out.println("");
        System.out.println("Aktuatori pridruzeni mjestima: ");
        UpisUDatoteku.upisiUDatoteku("Aktuatori pridruzeni mjestima: ");

        for (Mjesta mjestaSingleton : listaMjesta) {//lista mjesta napunjena iz datoteke
            Collections.shuffle(listaAktuatora, new Random(generator.dajSlucBroj()));
            for (Aktuator aktuatori : listaAktuatora) {//lista aktuatora napunjena iz datoteke
                    if (mjestaSingleton.getTip() == aktuatori.getTip() || aktuatori.getTip() == 2) {
                        aktuatori.setIdAktuatora(generator.dajSlucajniBroj(1, 1000));
                        System.out.println("id aktuatora: " + aktuatori.getIdAktuatora()+ ", Aktuator za mjesto: " + mjestaSingleton.getNaziv() + ": " + aktuatori.getNaziv());
                        UpisUDatoteku.upisiUDatoteku("id aktuatora: " + aktuatori.getIdAktuatora()+ ", Aktuator za mjesto: " + mjestaSingleton.getNaziv() + ": " + aktuatori.getNaziv());
                        mjestaSingleton.listaAktuatoraZaMjesto.add(aktuatori);
                        stat.setBrojDodijeljenihAktuatora(stat.getBrojDodijeljenihAktuatora()+1);
                    }
                    if (mjestaSingleton.listaAktuatoraZaMjesto.size() == mjestaSingleton.getBrojAktuatora()) {
                        break;
                }
                
            }
        }
        
       

        UpisUDatoteku.zatvoriDatoteku();
    }

    //svakom uredaju poslati inicijalizacijsku poruku, uredaj vraca 0 ili 1
    //neispravan uredaj ne koristi se u nastavku
    public void inicijalizirajSustav() throws UnsupportedEncodingException, FileNotFoundException {


        
        UpisUDatoteku zapis = new UpisUDatoteku(Ivaanic2_zadaca_2.datotekaRezultata);
        
        
        System.out.println("");
        System.out.println("Postavljanje statusa senzorima iterator: ");
        UpisUDatoteku.upisiUDatoteku("Postavljanje statusa senzorima iterator: ");
        for (IteratorMj iter = getIterator(); iter.hasNext() ;) {
            Mjesta mjesto = (Mjesta) iter.next();
            for (Senzor senzori : mjesto.listaSenzoraZaMjesto) {
                int status = Senzor.vratiStatus();
                senzori.setStatus(status);
                System.out.println("Mjesto: " + mjesto.getNaziv() + ", senzor: " + senzori.getNaziv()
                            + ", status: " + senzori.dohvatiStatus());
                    UpisUDatoteku.upisiUDatoteku("Mjesto: " + mjesto.getNaziv() + ", senzor: " + senzori.getNaziv()
                            + ", status: " + senzori.dohvatiStatus());
            }
        }
        

        
        
        System.out.println("");
        System.out.println("Postavljanje statusa aktuatorima iterator: ");
        UpisUDatoteku.upisiUDatoteku("Postavljanje statusa aktuatorima iterator: ");
        for (IteratorMj iter = getIterator(); iter.hasNext() ;) {
            Mjesta mjesto = (Mjesta) iter.next();
            for (Aktuator aktuatori : mjesto.listaAktuatoraZaMjesto) {
                    int status = Aktuator.vratiStatus();
                    aktuatori.setStatus(status);
                    System.out.println("Mjesto: " + mjesto.getNaziv() + ", aktuator: " + aktuatori.getNaziv()
                            + ", status: " + aktuatori.dohvatiStatus());
                    UpisUDatoteku.upisiUDatoteku("Mjesto: " + mjesto.getNaziv() + ", aktuator: " + aktuatori.getNaziv()
                            + ", status: " + aktuatori.dohvatiStatus());
            }
        }


        
             System.out.println("");
        System.out.println("Brisanje neispravnih senzora: ");
        UpisUDatoteku.upisiUDatoteku("Brisanje neispravnih senzora: ");
        List<Senzori> neispravniSenzori = new ArrayList<Senzori>();
        for (Mjesta mjestaSingleton : listaMjesta) {//lista mjesta napunjena iz datoteke
            for (Iterator<Senzor> itr = mjestaSingleton.listaSenzoraZaMjesto.iterator();itr.hasNext();) {
                Senzor senz = itr.next();
                    if (senz.dohvatiStatus()== 0) {
                    System.out.println("Mjesto: " + mjestaSingleton.getNaziv() + ", neispravan senzor: " + senz.getNaziv()
                            + ", status: " + senz.dohvatiStatus());
                    UpisUDatoteku.upisiUDatoteku("Mjesto: " + mjestaSingleton.getNaziv() + ", neispravan senzor: " + senz.getNaziv()
                            + ", status: " + senz.dohvatiStatus());
                    stat.setBrojNeispravnihSenzora(stat.getBrojNeispravnihSenzora()+1);
                   itr.remove();
                }
            }
           // mjestaSingleton.listaSenzoraZaMjesto.removeAll(neispravniSenzori);//za prvo mjesto u listi mjesta u listi senzorazamj obrisi sve neispr   
        }//vratiti
        
        
             System.out.println("");
        System.out.println("Brisanje neispravnih senzora: ");
        UpisUDatoteku.upisiUDatoteku("Brisanje neispravnih senzora: ");
        List<Aktuatori> neispravniAktuatori = new ArrayList<Aktuatori>();
        for (Mjesta mjestaSingleton : listaMjesta) {//lista mjesta napunjena iz datoteke
            for (Iterator<Aktuator> itr = mjestaSingleton.listaAktuatoraZaMjesto.iterator();itr.hasNext();) {
                Aktuator akt = itr.next();
                    if (akt.dohvatiStatus()== 0) {
                    System.out.println("Mjesto: " + mjestaSingleton.getNaziv() + ", neispravan aktuator: " + akt.getNaziv()
                            + ", status: " + akt.dohvatiStatus());
                    UpisUDatoteku.upisiUDatoteku("Mjesto: " + mjestaSingleton.getNaziv() + ", neispravan aktuator: " + akt.getNaziv()
                            + ", status: " + akt.dohvatiStatus());
                    stat.setBrojNeispravnihAktuatora(stat.getBrojNeispravnihAktuatora()+1);
                   itr.remove();
                }
            }
           // mjestaSingleton.listaSenzoraZaMjesto.removeAll(neispravniSenzori);//za prvo mjesto u listi mjesta u listi senzorazamj obrisi sve neispr   
        }
        
        
        
        
        
        System.out.println("");
        System.out.println("Senzori nakon brisanja: ");
        UpisUDatoteku.upisiUDatoteku("Senzori nakon brisanja: ");
        for (Mjesta mjestaSingleton : listaMjesta) {//lista mjesta napunjena iz datoteke
            for (Senzor senzori : mjestaSingleton.listaSenzoraZaMjesto) {
                    System.out.println("Mjesto: " + mjestaSingleton.getNaziv() + ", senzor: " + senzori.getNaziv()
                            + ", status: " + senzori.dohvatiStatus());//ne ovu liniju jer ce dati random vrijednost
                    UpisUDatoteku.upisiUDatoteku("Mjesto: " + mjestaSingleton.getNaziv() + ", senzor: " + senzori.getNaziv()
                            + ", status: " + senzori.dohvatiStatus());
            }
        }
        
        
        System.out.println("");
        System.out.println("Aktuatori nakon brisanja: ");
        UpisUDatoteku.upisiUDatoteku("Aktuatori nakon brisanja: ");
        for (Mjesta mjestaSingleton : listaMjesta) {//lista mjesta napunjena iz datoteke
            for (Aktuator aktuatori : mjestaSingleton.listaAktuatoraZaMjesto) {
                    System.out.println("Mjesto: " + mjestaSingleton.getNaziv() + ", senzor: " + aktuatori.getNaziv()
                            + ", status: " + aktuatori.dohvatiStatus());//ne ovu liniju jer ce dati random vrijednost
                    UpisUDatoteku.upisiUDatoteku("Mjesto: " + mjestaSingleton.getNaziv() + ", senzor: " + aktuatori.getNaziv()
                            + ", status: " + aktuatori.dohvatiStatus());
            }
        }



        UpisUDatoteku.zatvoriDatoteku();
    }
    
    public void opremiMjesta() throws UnsupportedEncodingException, FileNotFoundException {
        
        UpisUDatoteku zapis = new UpisUDatoteku(Ivaanic2_zadaca_2.datotekaRezultata);
        
        for (Mjesta mjesta : listaMjesta) {
            
            for (Aktuator aktuator : mjesta.listaAktuatoraZaMjesto) {
                int broj = generator.dajSlucajniBroj(1, mjesta.listaSenzoraZaMjesto.size()-1);
                Collections.shuffle(mjesta.listaSenzoraZaMjesto, new Random(generator.dajSlucBroj()));
                if (aktuator.getTip() == mjesta.listaSenzoraZaMjesto.get(broj).getTip() || aktuator.getTip() == 2) {
                    aktuator.popisSenzora.add(mjesta.listaSenzoraZaMjesto.get(broj));
                }
                    
                if (broj == aktuator.popisSenzora.size()) {
                    break;
                }
            }
        }
        
        System.out.println("");
        System.out.println("OPREMI MJESTA SENZORI ZA AKTUATORE----------");
        for (Mjesta mjesta : listaMjesta) {
            for (Aktuator aktuator : mjesta.listaAktuatoraZaMjesto) {
                for (Senzor senzor : aktuator.popisSenzora) {
                    System.out.println("Mjesto " + mjesta.getNaziv() + ", aktuator: " + aktuator.getNaziv() + ", ima senzor: " + senzor.getNaziv());
                }
            }
        }
    
    }
    
    
    @Override
    public String toString() {
        return this.idMjesta + " " + this.naziv + " " + this.tip + " " + this.brojSenzora + " " + this.brojAktuatora;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getBrojSenzora() {
        return brojSenzora;
    }

    public void setBrojSenzora(int brojSenzora) {
        this.brojSenzora = brojSenzora;
    }

    public int getBrojAktuatora() {
        return brojAktuatora;
    }

    public void setBrojAktuatora(int brojAktuatora) {
        this.brojAktuatora = brojAktuatora;
    }

    public List<Mjesta> getListaMjesta() {
        return listaMjesta;
    }

    public void setListaMjesta(ArrayList<Mjesta> listaMjesta) {
        this.listaMjesta = listaMjesta;
    }

    public ArrayList<Senzor> getListaSenzora() {
        return (ArrayList<Senzor>) listaSenzora;
    }

    public void setListaSenzora(ArrayList<Senzor> listaSenzora) {
        this.listaSenzora = listaSenzora;
    }

    public List<Aktuator> getListaAktuatora() {
        return listaAktuatora;
    }

    public void setListaAktuatora(ArrayList<Aktuator> listaAktuatora) {
        this.listaAktuatora = listaAktuatora;
    }

    public List<Senzor> getListaSenzoraZaMjesto() {
        return listaSenzoraZaMjesto;
    }

    public void setListaSenzoraZaMjesto(List<Senzor> listaSenzoraZaMjesto) {
        this.listaSenzoraZaMjesto = listaSenzoraZaMjesto;
    }

    public List<Aktuator> getListaAktuatoraZaMjesto() {
        return listaAktuatoraZaMjesto;
    }

    public void setListaAktuatoraZaMjesto(List<Aktuator> listaAktuatoraZaMjesto) {
        this.listaAktuatoraZaMjesto = listaAktuatoraZaMjesto;
    }
    
    

    @Override
    public int compareTo(Mjesta o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdMjesta() {
        return idMjesta;
    }

    public void setIdMjesta(int idMjesta) {
        this.idMjesta = idMjesta;
    }

    @Override
    public IteratorMj getIterator() {
        return new IteratorMjesta();
    }
    
    private class IteratorMjesta implements IteratorMj {

        int indeks;

        @Override
        public boolean hasNext() {
            if (indeks < Mjesta.listaMjesta.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return Mjesta.listaMjesta.get(indeks++);
            }
            return null;
        }

    }

}
