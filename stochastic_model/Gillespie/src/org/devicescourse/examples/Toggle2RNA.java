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
/**
 * Created with IntelliJ IDEA.
 * User: zacatac
 * Date: 11/26/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */


package org.devicescourse.examples;

import org.devicescourse.gillespie.Chain;
import org.devicescourse.gillespie.Reaction;
import org.devicescourse.gillespie.Specie;
import org.devicescourse.charts.PlotSpecies;

/**
 * Toggle switch with RNA
 *
 * @author zrfield
 */
public class Toggle2RNA {

    public static void main(String[] args){
        Toggle2RNA toggle = new Toggle2RNA();
        Chain chain = toggle.generateChain(100,100,100);

        chain.start();

        Specie[] RNAandProtein = new  Specie[3];
        RNAandProtein[0] = chain.getSpecie("aRNA");
        RNAandProtein[1] = chain.getSpecie("bRNA");
        RNAandProtein[2] = chain.getSpecie("G");

        new PlotSpecies(RNAandProtein, 50);
    }

    public Chain generateChain(int numaRNA, int numbRNA, double endtime) {
        Chain chain = new Chain(endtime);

        // Creating all species
        final Specie GFP = new Specie(chain, 0, "G");
        final Specie gfpRNA = new Specie(chain, 0, "gfpRNA");
        final Specie aRNA = new Specie(chain, numaRNA, "aRNA");
        final  Specie bRNA = new Specie(chain, numbRNA, "bRNA");

        final Specie Ggene = new Specie(chain, 1, "G gene");
        final Specie aGene = new Specie(chain, 1, "a gene");
        final Specie bGene = new Specie(chain, 1, "b gene");


        // Create all reactions
        Reaction[] reactions = new Reaction[10];    // Not sure how many yet

        /*
        * Generation of GFP from
        * translation of aRNA
        * determined by the presence of bRNA
        * and subsequent cleavage of aRNA
        */
        reactions[0] = new Reaction() {
            @Override
            public void updateValues() {
                GFP.increment();
            }

            @Override
            public double calculateQuantities() {
                /*Amount of aRNA*/
                double concaRNA = aRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = gfpRNA.getValue() * 1 / (1 + concaRNA);
                if(probabiliy < Math.random()) {
                    return 0;
                }
                return BetaForGFP * gfpRNA.getValue();
            }
        };

        /*
        * Production of aRNA (constitutive)
        */
        reactions[1] = new Reaction() {
            @Override
            public void updateValues() {
                aRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForaRNA * aRNA.getValue();
            }
        };

        /*
        * Production of bRNA (constitutive)
        */
        reactions[2] = new Reaction() {
            @Override
            public void updateValues() {
                bRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForbRNA * bRNA.getValue();
            }
        };

        /*
        * Production of gfpRNA (constitutive)
        */
        reactions[3] = new Reaction() {
            @Override
            public void updateValues() {
                gfpRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForgfpRNA * gfpRNA.getValue();
            }
        };

        /*
        * Degradation of aRNA
        */
        reactions[4] = new Reaction() {
            @Override
            public void updateValues() {
                aRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegaRNA * aRNA.getValue();
            }
        };

        /*
        * Degradation of bRNA
        */
        reactions[5] = new Reaction() {
            @Override
            public void updateValues() {
                bRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegbRNA * bRNA.getValue();
            }
        };


        /*
        * Degradation of gfpRNA
        */
        reactions[6] = new Reaction() {
            @Override
            public void updateValues() {
                gfpRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DeggfpRNA * gfpRNA.getValue();
            }
        };


        /*
        * Degradation of GFP
        */
        reactions[7]  = new Reaction() {
            @Override
            public void updateValues() {
                GFP.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegGFP * GFP.getValue();
            }
        };

        /*
        * Cleaving of aRNA by bRNA
        */
        reactions[8] = new Reaction() {
            @Override
            public void updateValues() {
                aRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
               /*Amount of aRNA*/
                double concbRNA = bRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = aRNA.getValue() * 1 / (1 + concbRNA);
                if(probabiliy < Math.random()) {
                    return 0;
                }
                return cleaveRate_aRNA * aRNA.getValue();
            }
        };

        /*
        * Cleaving of bRNA by aRNA
        */
        reactions[9] = new Reaction() {
            @Override
            public void updateValues() {
                bRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                /*Amount of aRNA*/
                double concaRNA = aRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = bRNA.getValue() * 1 / (1 + concaRNA);
                if(probabiliy < Math.random()) {
                    return 0;
                }
                return cleaveRate_bRNA * bRNA.getValue();
            }
        };

        chain.setReactions(reactions);

        return chain;

    }

    private static final double avagadroNumber = 6.022E23;

    double volumeOfEcoli = 4.76E-18;    //Volume of an E. coli cell

    double KeqForaRNA = 1E9;     //Binding Keq of aRNA to bRNA ribozyme
    double KeqForbRNA = 1E9;     //Binding Keq of bRNA to aRNA ribozyme

    double BetaForaRNA = 2;    //Formation of aRNA rate
    double BetaForbRNA = 2;    //Formation of bRNA rate
    double BetaForgfpRNA = 2;  //Formation of gfpRNA rate
    double BetaForGFP = 120;     //Formation of GFP rate

    double cleaveRate_aRNA = 25;     // The rate at which aRNA is cleaved when bound by bRNA
    double cleaveRate_bRNA = 25;  // The rate at which bRNA is cleaved when bound by aRNA

    double DegaRNA = 5;         //Degradation of aRNA
    double DegbRNA = 5;         //Degradation of bRNA
    double DeggfpRNA = 5;       //Degradation of gfpRNA
    double DegGFP = 1;       //Degradation of GFP
}
