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
public interface AktuatorBuilder {
    
    Aktuator build();
    AktuatorBuilder setId(final int idAktuatora);
    AktuatorBuilder setNaziv(final String naziv);
    AktuatorBuilder setTip(final int tip);
    AktuatorBuilder setVrsta(final int vrsta);
    AktuatorBuilder setMinVrijednost(final Float minVrijednost);
    AktuatorBuilder setMaxVrijednost(final Float maxVrijednost);
    AktuatorBuilder setVrijednost(final Float vrijednost);
    AktuatorBuilder setKomentar(final String komentar);
    AktuatorBuilder setStatus(final int status);
    AktuatorBuilder setBrojGreski(final int brojGreski);
    
}
