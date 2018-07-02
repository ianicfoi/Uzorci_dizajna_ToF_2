/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivaanic2_zadaca_2.visitor;

import ivaanic2_zadaca_2.builder.Aktuator;
import ivaanic2_zadaca_2.builder.Senzor;
import ivaanic2_zadaca_2.singleton.Mjesta;

/**
 *
 * @author Ivan
 */
public interface Visitor {
    
    public void visit(Mjesta mjesto);
    public void visit(Senzor senzor);
    public void visit(Aktuator aktuator);
    
    
}
