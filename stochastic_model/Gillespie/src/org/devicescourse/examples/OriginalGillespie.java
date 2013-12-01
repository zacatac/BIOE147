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

/**
 * This is a java translation of the The “First-reaction” Method example Fortran code from:
 *
 * A General Method for Numerically Simulating the Stochastic Time Evolution of Coupled Chemical Reactions
 * JOURNAL OF COMPUTATIONAL PHYSICS 2, 403-434 (1976)
 *
 * The reaction it encapsulates is:
 *
 *      X <==> Y
 *      2X <==> Z
 *      W + X <==> 2X
 *
 * What is that reaction?  Beats me, but that's the original example.
 *
 * @author jcanderson
 */
public class OriginalGillespie {

    public static void main(String[] args) {

        //Create a cell (one chain)
        Chain chain = new Chain(500);

        //Create 4 species and put them into the cell
        final Specie w = new Specie(chain, 25, "dubya");
        final Specie x = new Specie(chain, 45, "ex");
        final Specie y = new Specie(chain, 15, "why");
        final Specie z = new Specie(chain, 2, "zee");

        //Create the reactions that define each type of molecular interaction
        Reaction[] reactions = new Reaction[6];

        reactions[0] = new Reaction() {

            @Override
            public void updateValues() {
                x.decrement();
                y.increment();
            }

            @Override
            public double calculateQuantities() {
                return 0.03*x.getValue();
            }
        };

        reactions[1] = new Reaction() {

            @Override
            public void updateValues() {
                x.increment();
                y.decrement();
            }

            @Override
            public double calculateQuantities() {
                return 0.3*y.getValue();
            }
        };

        reactions[2] = new Reaction() {

            @Override
            public void updateValues() {
                x.decrement(2);
                z.increment();
            }

            @Override
            public double calculateQuantities() {
                return 0.006*x.getValue()*(x.getValue()-1)/2;
            }
        };

        reactions[3] = new Reaction() {

            @Override
            public void updateValues() {
                x.increment(2);
                z.decrement();
            }

            @Override
            public double calculateQuantities() {
                return 0.9*z.getValue();
            }
        };

        reactions[4] = new Reaction() {

            @Override
            public void updateValues() {
                x.increment();
                w.decrement();
            }

            @Override
            public double calculateQuantities() {
                return  0.003*w.getValue()*x.getValue();
            }
        };

        reactions[5] = new Reaction() {

            @Override
            public void updateValues() {
                x.decrement();
                w.increment();
            }

            @Override
            public double calculateQuantities() {
                return  0.003*x.getValue()*(x.getValue()-1)/2;
            }
        };

        //Install the reactions into the chain, then run it
        chain.setReactions(reactions);
        chain.start();

        /**
         * Get the data out for one Specie, in this case 'w'
         * The data returned is a 2D array of Time, Value
         * then, print out all those values
         */
        double[][] data = w.getBinnedData(20);

        System.out.println("Time" + "  " + "Value");
        for(int i=0; i<data[0].length; i++) {
            System.out.println(data[0][i] + "  " + data[1][i]);
        }

        /**
         * Get the data out for all the Species in this cell
         * The data is a 2D array of Time, Species1, Species2, etc
         * with the first row as a header row
         * then, print out all the values
         */
        Object[][] allspeciesdata = chain.getBinnedData(50);
        for(int i=0; i<allspeciesdata[0].length; i++) {
            for(int j=0; j<allspeciesdata.length; j++) {
                System.out.print(allspeciesdata[j][i] + "  ");
            }
            System.out.println();
        }
    }
}
