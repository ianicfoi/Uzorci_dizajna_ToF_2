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
public class ConcreteAlgorithm extends AlgorithmFactory {

    @Override
    public Algorithm createAlgorithm(String name, int seed) {
        if (name.equals("AlgoritamSlijedni")) {
            return new AlgoritamSlijedni();
        } else if (name.equals("AlgoritamObrnuti")) {
            return new AlgoritamObrnuti();
        } else if (name.equals("AlgoritamAbecedni")) {
            return new AlgoritamAbecedni();
        } else {
            return null;
        }
    }

    

}
