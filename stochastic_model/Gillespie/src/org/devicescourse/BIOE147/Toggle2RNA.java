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


package org.devicescourse.BIOE147;

import org.devicescourse.charts.PlotSpecies;
import org.devicescourse.gillespie.Chain;
import org.devicescourse.gillespie.Reaction;
import org.devicescourse.gillespie.Specie;
import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * Toggle switch with RNA regulation
 *
 * @author zrfield
 */
public class Toggle2RNA {

    public static void main(String[] args){
        Toggle2RNA toggle = new Toggle2RNA();
        Chain chain = toggle.generateChain(1,100,1);

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
        final Specie GFP = new Specie(chain, 1, "G");
        final Specie gfpRNA = new Specie(chain, 1, "gfpRNA");
        final Specie aRNA = new Specie(chain, numaRNA, "aRNA");
        final  Specie bRNA = new Specie(chain, numbRNA, "bRNA");

        final Specie Ggene = new Specie(chain, 1, "G gene");
        final Specie aGene = new Specie(chain, 1, "a gene");
        final Specie bGene = new Specie(chain, 1, "b gene");


        // Create all reactions
        Reaction[] reactions = new Reaction[10];

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
                //Amount of aRNA
                double concaRNA = aRNA.getValue()/avagadroNumber/volumeOfEcoli;
                // YE OLDE VERSION // double probabiliy = gfpRNA.getValue() * 1 / (1 + KeqArna_Gfp * concaRNA);
                double probabiliy =  1 / (1 + KeqArna_Gfp * concaRNA);
                if(probabiliy < Math.random()) {
                    return 0;
                }
                return BetaForGFP * gfpRNA.getValue();
            }
        };

        /*
        * Production of gfpRNA (constitutive)
        */
        reactions[1] = new Reaction() {
            @Override
            public void updateValues() {
                gfpRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForgfpRNA * Ggene.getValue();

            }
        };

        /*
        * Production of aRNA constitutive
        */
        reactions[2] = new Reaction() {
            @Override
            public void updateValues() {
                aRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForaRNA * aGene.getValue();
            }
        };

        /*
        * Production of bRNA constitutive
        */

        reactions[3] = new Reaction() {
            @Override
            public void updateValues() {
                bRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForbRNA * bGene.getValue();
            }
        };

        /*
        * Degradation of aRNA (constitutive)
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
        * Degradation of bRNA (constitutive)
        *
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
        * Degradation of GFP (constitutive)
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
        * aRNA degradation which depends on bRNA.
        */
        reactions[8] = new Reaction() {
            @Override
            public void updateValues() {
                aRNA.decrement();
            }

            @Override
            public double calculateQuantities() {

                double concbRNA = bRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = aRNA.getValue() * 1 / (1 + KeqBrna_Arna * concbRNA);
                //System.out.println("aRNA -/-> bRNA: " + probabiliy);
                if(0.5 < Math.random()) {
                    return 0;
                }
                //return DegBybRNA * aRNA.getValue();

                return DegBybRNA * aRNA.getValue();
            }
        };

        /*
        * bRNA degradation which depends on aRNA
        */
        reactions[9] = new Reaction() {
            @Override
            public void updateValues() {
                bRNA.decrement();
            }

            @Override
            public double calculateQuantities() {

                double concaRNA = aRNA.getValue()/avagadroNumber/volumeOfEcoli;
                double probabiliy = bRNA.getValue() * 1 / (1 + KeqArna_Brna* concaRNA);
                //System.out.println("bRNA -/-> aRNA: " + probabiliy);
                if(0.5 < Math.random()) {
                    return 0;
                }
                //return DegByaRNA * bRNA.getValue();
                return DegByaRNA * bRNA.getValue();
            }
        };

        chain.setReactions(reactions);

        return chain;

    }

    private static final double avagadroNumber = 6.022E23;

    double volumeOfEcoli = 4.76E-18;    //Volume of an E. coli cell
    double KeqFold       = 1E5;         //Fraction RNA folded   (almost all folded)

    double KeqArna_Gfp  = 1E5;      //Binding Keq of aRNA to GFPrna
    double KeqArna_Brna = 1E5;      //Binding Keq of aRNA to bRNA
    double KeqBrna_Arna = 1E5;      //Binding Keq of bRNA to aRNA

    double BetaForaRNA   = 1000;   //Formation of aRNA rate
    double BetaForbRNA   = 1000;   //Formation of bRNA rate
    double BetaForgfpRNA =  70;   //Formation of gfpRNA rate
    double BetaForGFP    =  70;   //Formation of GFP rate

    double DegaRNA   = 5;         //Degradation of aRNA
    double DegbRNA   = 5;         //Degradation of bRNA
    double DeggfpRNA = 5;         //Degradation of gfpRNA
    double DegGFP    = 15;         //Degradation of GFP
    double DegBybRNA = 15;         //Degradation of aRNA by bRNA
    double DegByaRNA = 15;         //Degradation of bRNA by aRNA
}