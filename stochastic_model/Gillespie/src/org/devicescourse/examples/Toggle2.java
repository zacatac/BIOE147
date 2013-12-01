/*
 Copyright (c) 2009 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS..
 */

package org.devicescourse.examples;

import org.devicescourse.gillespie.Chain;
import org.devicescourse.gillespie.Reaction;
import org.devicescourse.gillespie.Specie;
import org.devicescourse.charts.PlotSpecies;

/**
 * This is a simplified version of the Toggle switch.  Instead of explicitly
 * having the transcription factors bind to the promoters, it is randomly determined
 *
 * In this one, we'll only do one run through the simulation, in the other
 * Toggle# classes, we'll call this code but do more iterations
 *
 * @author jcanderson
 */
public class Toggle2 {

    public static void main(String[] args) {
        Toggle2 toggler = new Toggle2();
        Chain chain = toggler.generateChain(100, 1, 500);
        chain.start();

        Specie[] proteinsonly = new Specie[3];
        proteinsonly[0] = chain.getSpecie("G");
        proteinsonly[1] = chain.getSpecie("R");
        proteinsonly[2] = chain.getSpecie("S");

        new PlotSpecies(proteinsonly, 50);

    }

    public Chain generateChain(int numRepS, int numRepR, double endtime) {
        //Create a cell (one chain) with its end time as 1200 milliseconds
        Chain chain = new Chain(endtime);

        //Create 9 species with their initial values and put them into the cell
        final Specie GFP  = new Specie(chain, 0, "G");
        final Specie RepS = new Specie(chain, numRepS, "S");
        final Specie RepR = new Specie(chain, numRepR, "R");

        final Specie Ggene = new Specie(chain, 1, "G gene");
        final Specie Rgene = new Specie(chain, 1, "R gene");
        final Specie Sgene = new Specie(chain, 1, "S gene");

        //Create the reactions that define each type of molecular interaction
        Reaction[] reactions = new Reaction[6];

        /**
         * Generation of GFP from a P_S promoter
         * Whether or not S is bound to the promoter is determined
         * when it runs
         */
        reactions[0] = new Reaction() {

            @Override
            public void updateValues() {
                GFP.increment();
            }

            @Override
            public double calculateQuantities() {
                /**
                 * The concentration of total S is its Moles divided by the volume of the cell
                 * The number of moles is the number of molecules divided by avogadros number:
                 */
                double concS = RepS.getValue()/avagadroNumber/volumeOfEcoli;

                /**
                 * The concentration of the dimer (the thing that actually binds) is based
                 * on the equilibrium S + S --> dimerS  which reduces to:
                 */
                double dimerS = KeqForSS*concS*concS / (KeqForSS*concS*concS + 1);

                /**
                 * The concentration of promoter DNAs is the moles over volume, again
                 * you get moles as # molecules / avogadros number:
                 */
                double concPromoter = (Rgene.getValue()/avagadroNumber)/volumeOfEcoli;

                /**
                 * The equilibrium expression for active amount of Rgene is given by
                 * a 1/(1+x) type function, the probability that the reaction occurs
                 * is therefore the number of promoters times the probability that one
                 * of them is in the right state
                 */
                double probability = Ggene.getValue() * 1 / (1 + KeqForS * dimerS);

                /* If the probability is greater than a random number, then the
                 * then a protein gets made
                 */
                if(probability < Math.random()) {
                    return 0;
                }
                return BetaForG * Ggene.getValue();
            }
        };

        /**
         * R gene which depends on S protein
         */
        reactions[1] = new Reaction() {

            @Override
            public void updateValues() {
                RepR.increment();
            }

            @Override
            public double calculateQuantities() {
                double concS = RepS.getValue()/avagadroNumber/volumeOfEcoli;  //Total S protein conc
                double dimerS = KeqForSS*concS*concS / (KeqForSS*concS*concS + 1);
                double probability = Rgene.getValue() * 1 / (1 + KeqForS * dimerS);
                if(probability < Math.random()) {
                    return 0;
                }
                return BetaForR * Rgene.getValue();
            }
        };

        /**
         * S gene which depends on R protein
         */
        reactions[2] = new Reaction() {

            @Override
            public void updateValues() {
                RepS.increment();
            }

            @Override
            public double calculateQuantities() {
                double concR = RepR.getValue()/avagadroNumber/volumeOfEcoli;  //Total S protein conc
                double dimerR = KeqForRR*concR*concR / (KeqForRR*concR*concR + 1);
                double probability = Sgene.getValue() * 1 / (1 + KeqForR * dimerR);
                if(probability < Math.random()) {
                    return 0;
                }
                return BetaForS * Sgene.getValue();
            }
        };

        //For G --> null
        reactions[3] = new Reaction() {

            @Override
            public void updateValues() {
                GFP.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegG * GFP.getValue();
            }
        };

        //For R --> null
        reactions[4] = new Reaction() {

            @Override
            public void updateValues() {
                RepR.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegR * RepR.getValue();
            }
        };

        //For S --> null
        reactions[5] = new Reaction() {

            @Override
            public void updateValues() {
                RepS.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegS * RepS.getValue();
            }
        };
        
        //Install the reactions into the chain, then run it
        chain.setReactions(reactions);

        //Return the new chain (but don't run it yet)
        return chain;
    }


/*-----------------
     variables
 -----------------*/
    private static final double avagadroNumber = 6.022E23;

    double volumeOfEcoli = 4.76E-18;    //Volume of an E. coli cell

    double KeqForS = 1E9;     //Binding Keq of S to its promoter
    double KeqForR = 1E9;     //Binding Keq of R to its promoter

    double KeqForRR = 1E16;   //Formation of the dimer Keq
    double KeqForSS = 1E16;   //Formation of the dimer Keq

    double BetaForR = 100;    //Formation of protein rate
    double BetaForS = 100;    //Formation of protein rate
    double BetaForG = 120;    //Formation of protein rate

    double DegR = 5;         //Degradation of protein
    double DegS = 5;         //Degradation of protein
    double DegG = 5;         //Degradation of protein
}
