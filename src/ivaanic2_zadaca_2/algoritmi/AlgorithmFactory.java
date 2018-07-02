/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivaanic2_zadaca_2.algoritmi;

/**
 *
 * @author Ivan
 */
public abstract class AlgorithmFactory {

    public AlgorithmFactory() {
    }
    
    protected abstract Algorithm createAlgorithm(String name, int seed);
    
}
