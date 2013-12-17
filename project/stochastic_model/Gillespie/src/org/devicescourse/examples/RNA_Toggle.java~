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
 * In this example, we model the Toggle switch by explicity writing
 * out each species.  As you might expect, this one takes forever to run.
 *
 * (I don't recommend that you actually run it)
 *
 * @author jcanderson
 */
public class Toggle1 {

    public static void main(String[] args) {
        Chain chain = generateChain();
        chain.start();

        Specie[] proteinsonly = new Specie[3];
        proteinsonly[0] = chain.getSpecie("G");
        proteinsonly[1] = chain.getSpecie("R");
        proteinsonly[2] = chain.getSpecie("S");

        new PlotSpecies(proteinsonly, 50);

    }

    public static Chain generateChain() {

        //Create a cell (one chain) with its end time as 1200 milliseconds
        Chain chain = new Chain(1200);

        //Create 9 species with their initial values and put them into the cell
        final Specie Sgene  = new Specie(chain, 5, "P_R driving S");
        final Specie GFPgene = new Specie(chain, 5, "P_S driving GFP");
        final Specie Rgene = new Specie(chain, 5, "P_S driving R");

        final Specie Rprot = new Specie(chain, 320, "R");
        final Specie Sprot = new Specie(chain, 9, "S");
        final Specie GFPprot = new Specie(chain, 0, "G");

        final Specie boundGFPgene = new Specie(chain, 0, "bound GFP gene");
        final Specie boundRgene = new Specie(chain, 0, "bound R gene");
        final Specie boundSgene = new Specie(chain, 0, "bound S gene");


        //Create the reactions that define each type of molecular interaction
        Reaction[] reactions = new Reaction[12];

        //For P_S driving GFP + S  -->  boudn GFP gene
        reactions[0] = new Reaction() {

            @Override
            public void updateValues() {
                GFPgene.decrement();
                Sprot.decrement(2);
                boundGFPgene.increment();
            }

            @Override
            public double calculateQuantities() {
                return K1 * GFPgene.getValue() * Sprot.getValue()* Sprot.getValue();
            }
        };

        //For bound GFP gene --> P_S driving GFP + S
        reactions[1] = new Reaction() {

            @Override
            public void updateValues() {
                GFPgene.increment();
                Sprot.increment(2);
                boundGFPgene.decrement();
            }

            @Override
            public double calculateQuantities() {
                return Kminus1 * boundGFPgene.getValue();
            }
        };

        //For P_S driving GFP --> P_S driving GFP + G
        reactions[2] = new Reaction() {

            @Override
            public void updateValues() {
                GFPprot.increment();
            }

            @Override
            public double calculateQuantities() {
                return K2 * GFPgene.getValue();
            }
        };

        //For G --> null
        reactions[3] = new Reaction() {

            @Override
            public void updateValues() {
                GFPprot.decrement();
            }

            @Override
            public double calculateQuantities() {
                return K3 * GFPprot.getValue();
            }
        };

        //////////////////////////////////////

        //For P_S driving R + S  -->  bound R gene
        reactions[4] = new Reaction() {

            @Override
            public void updateValues() {
                Rgene.decrement();
                Sprot.decrement(2);
                boundRgene.increment();
            }

            @Override
            public double calculateQuantities() {
                return K4 * Rgene.getValue() * Sprot.getValue()* Sprot.getValue();
            }
        };

        //For bound R gene --> P_S driving R + S
        reactions[5] = new Reaction() {

            @Override
            public void updateValues() {
                Rgene.increment();
                Sprot.increment(2);
                boundRgene.decrement();
            }

            @Override
            public double calculateQuantities() {
                return Kminus4 * boundRgene.getValue();
            }
        };

        //For P_S driving R --> P_S driving R + R
        reactions[6] = new Reaction() {

            @Override
            public void updateValues() {
                Rprot.increment();
            }

            @Override
            public double calculateQuantities() {
                return K5 * Rgene.getValue();
            }
        };

        //For R --> null
        reactions[7] = new Reaction() {

            @Override
            public void updateValues() {
                Rprot.decrement();
            }

            @Override
            public double calculateQuantities() {
                return K6 * Rprot.getValue();
            }
        };


        //////////////////////////////////////

        //For P_R driving S + R  -->  R.P_R driving S
        reactions[8] = new Reaction() {

            @Override
            public void updateValues() {
                Sgene.decrement();
                Rprot.decrement(2);
                boundSgene.increment();
            }

            @Override
            public double calculateQuantities() {
                return K7 * Sgene.getValue() * Rprot.getValue()* Rprot.getValue();
            }
        };

        //For bound S gene --> P_R driving S + R
        reactions[9] = new Reaction() {

            @Override
            public void updateValues() {
                Sgene.increment();
                Rprot.increment(2);
                boundSgene.decrement();
            }

            @Override
            public double calculateQuantities() {
                return Kminus7 * boundSgene.getValue();
            }
        };

        //For P_R driving S --> P_R driving S + S
        reactions[10] = new Reaction() {

            @Override
            public void updateValues() {
                Sprot.increment();
            }

            @Override
            public double calculateQuantities() {
                return K8 * Sgene.getValue();
            }
        };

        //For S --> null
        reactions[11] = new Reaction() {

            @Override
            public void updateValues() {
                Sprot.decrement();
            }

            @Override
            public double calculateQuantities() {
                return K9 * Sprot.getValue();
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

    private static double K1 = 100;       //S binding to promoter
    private static double Kminus1 = 0.1;
    private static double K2 = 1;     //GFP formation
    private static double K3 = 0.1;   //GFP degradation

    private static double K4 = 100;       //S binding to promoter
    private static double Kminus4 = 0.1;
    private static double K5 = 1;     //R formation
    private static double K6 = 0.1;   //R degradation

    private static double K7 = 100;
    private static double Kminus7 = 0.1;
    private static double K8 = 1;     //S formation
    private static double K9 = 0.1;   //S degradation
}
