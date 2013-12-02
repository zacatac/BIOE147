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
import org.devicescourse.charts.AllSpeciesOverTime;

/**
 * This is Michaelis-Menton standard kinetics, but done stochastically
 * So, it encodes E, S, ES, and P (4 Species)
 * and the reactions:
 *
 *      E + S <==> ES
 *      ES ==> P
 *
 * @author jcanderson
 */
public class MichaelisMenton {

    public static void main(String[] args) {
        new MichaelisMenton();
    }

    public MichaelisMenton() {

        //Create a cell (one chain) with its end time as 500 seconds
        Chain chain = new Chain(1200000);

        //Create 4 species with their initial values and put them into the cell
        final Specie Enzyme = new Specie(chain, 100, "E");
        final Specie ESComplex = new Specie(chain, 0, "ES");
        final Specie Substrate = new Specie(chain, 1000, "S");
        final Specie Product = new Specie(chain, 0, "P");

        //Create the reactions that define each type of molecular interaction
        Reaction[] reactions = new Reaction[3];

        //For E + S --> ES
        reactions[0] = new Reaction() {

            @Override
            public void updateValues() {
                Enzyme.decrement();
                Substrate.decrement();
                ESComplex.increment();
            }

            @Override
            public double calculateQuantities() {
                return K1 * Enzyme.getValue() * Substrate.getValue();
            }
        };

        //For ES --> E + S
        reactions[1] = new Reaction() {

            @Override
            public void updateValues() {
                Enzyme.increment();
                Substrate.increment();
                ESComplex.decrement();
            }

            @Override
            public double calculateQuantities() {
                return K2 * ESComplex.getValue();
            }
        };

        //For ES --> E + P
        reactions[2] = new Reaction() {

            @Override
            public void updateValues() {
                Enzyme.increment();
                Product.increment();
                ESComplex.decrement();
            }

            @Override
            public double calculateQuantities() {
                return K3 * ESComplex.getValue();
            }
        };

        //Install the reactions into the chain, then run it
        chain.setReactions(reactions);
        chain.start();

        /**
         * Get the data out for all the Species in this cell
         * The data is a 2D array of Time, Species1, Species2, etc
         * with the first row as a header row
         * then, print out all the values
         */
        Object[][] allspeciesdata = chain.getBinnedData(40);
        for(int i=0; i<allspeciesdata[0].length; i++) {
            for(int j=0; j<allspeciesdata.length; j++) {
                System.out.print(allspeciesdata[j][i] + "  ");
            }
            System.out.println();
        }

        //Passing the data array to AllSpeciesOverTime's constructor makes a Window
        new AllSpeciesOverTime(allspeciesdata);
    }

/*-----------------
     variables
 -----------------*/

    private double K1 = 0.0004;
    private double K2 = 0.00004;
    private double K3 = 0.000004;

}
