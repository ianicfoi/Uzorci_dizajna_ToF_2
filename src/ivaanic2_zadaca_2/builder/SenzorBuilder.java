/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivaanic2_zadaca_2.builder;

/**
 *
 * @author Ivan
 */
public interface SenzorBuilder {
    
    Senzor build();
    SenzorBuilder setIdSenzora(final int idSenzora);
    SenzorBuilder setNaziv(final String naziv);
    SenzorBuilder setTip(final int tip);
    SenzorBuilder setVrsta(final int vrsta);
    SenzorBuilder setMinVrijednost(final Float minVrijednost);
    SenzorBuilder setMaxVrijednost(final Float maxVrijednost);
    SenzorBuilder setVrijednost(final Float vrijednost);
    SenzorBuilder setKomentar(final String komentar);
    SenzorBuilder setStatus(final int status);
    SenzorBuilder setBrojGreski(final int brojGreski);
    
}
