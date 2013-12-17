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

import com.sun.tools.internal.xjc.api.SpecVersion;
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
public class RepressillatorRNA{

    public static void main(String[] args){
        RepressillatorRNA repressillatorRNA = new RepressillatorRNA();
        Chain chain = repressillatorRNA.generateChain(20,1,1,100);

        chain.start();

        Specie[] RNAandProtein = new  Specie[3];
        RNAandProtein[0] = chain.getSpecie("aRNA");
        RNAandProtein[1] = chain.getSpecie("bRNA");
        RNAandProtein[2] = chain.getSpecie("cRNA");
        //RNAandProtein[3] = chain.getSpecie("G");
        //RNAandProtein[4] = chain.getSpecie("abRNA");
        //RNAandProtein[5] = chain.getSpecie("bcRNA");
        //RNAandProtein[6] = chain.getSpecie("caRNA");


        new PlotSpecies(RNAandProtein, 50);
    }

    public Chain generateChain(int numaRNA, int numbRNA, int numcRNA, double endtime) {
        Chain chain = new Chain(endtime);

        // Creating all species
        final Specie GFP = new Specie(chain, 1, "G");
        final Specie gfpRNA = new Specie(chain, 1, "gfpRNA");
        final Specie aRNA = new Specie(chain, numaRNA, "aRNA");
        final  Specie bRNA = new Specie(chain, numbRNA, "bRNA");
        final Specie cRNA = new Specie(chain, numcRNA, "cRNA");
        final Specie abRNA = new Specie(chain, 1, "abRNA");
        final Specie bcRNA = new Specie(chain, 1, "bcRNA");
        final Specie caRNA = new Specie(chain, 1, "caRNA");

        final Specie Ggene = new Specie(chain, 1, "G gene");
        final Specie aGene = new Specie(chain, 1, "a gene");
        final Specie bGene = new Specie(chain, 1, "b gene");
        final Specie cGene = new Specie(chain, 1, "c gene");


        // Create all reactions
        Reaction[] reactions = new Reaction[19];

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
        * Production of cRNA constitutive
        */

        reactions[4] = new Reaction() {
            @Override
            public void updateValues() {
                cRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return BetaForcRNA * cGene.getValue();
            }
        };

        /*
        * Production of abRNA
        */

        reactions[5] = new Reaction() {
            @Override
            public void updateValues() {
                abRNA.increment();
                aRNA.decrement();
                bRNA.decrement();
            }

            @Override
            public double calculateQuantities() {

                return complexes* aRNA.getValue() * bRNA.getValue();
            }
        };

        /*
        * Production of bcRNA
        */

        reactions[6] = new Reaction() {
            @Override
            public void updateValues() {
                bcRNA.increment();
                bRNA.decrement();
                cRNA.decrement();
            }

            @Override
            public double calculateQuantities() {

                return complexes * bRNA.getValue() * cRNA.getValue();
            }
        };

        /*
        * Production of caRNA
        */

        reactions[7] = new Reaction() {
            @Override
            public void updateValues() {
                caRNA.increment();
                cRNA.decrement();
                aRNA.decrement();
            }

            @Override
            public double calculateQuantities() {

                return complexes * cRNA.getValue() * aRNA.getValue();
            }
        };

        /*
        * Cleaving of bRNA
        */

        reactions[8] = new Reaction() {
            @Override
            public void updateValues() {
                abRNA.decrement();
                aRNA.increment();
                bRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return Deg_cleaving_rate * abRNA.getValue();
            }
        };

        /*Decomplex abRNA*/
        reactions[9] = new Reaction() {
            @Override
            public void updateValues() {
                abRNA.decrement();
                aRNA.increment();
                bRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return decomplex * abRNA.getValue();
            }
        };

        /*Decomplex bcRNA*/
        reactions[10] = new Reaction() {
            @Override
            public void updateValues() {
                bcRNA.decrement();
                bRNA.increment();
                cRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return decomplex * bcRNA.getValue();
            }
        };

        /*Decomplex caRNA*/
        reactions[11] = new Reaction() {
            @Override
            public void updateValues() {
                caRNA.decrement();
                cRNA.increment();
                aRNA.increment();
            }

            @Override
            public double calculateQuantities() {
                return decomplex * caRNA.getValue();
            }
        };

        /*
        * Cleaving of cRNA
        */

        reactions[12] = new Reaction() {
            @Override
            public void updateValues() {
                bcRNA.decrement();
                bRNA.increment();
                cRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return Deg_cleaving_rate * bcRNA.getValue();
            }
        };

        /*
        * Cleaving of aRNA
        */
        reactions[13] = new Reaction() {
            @Override
            public void updateValues() {
                caRNA.decrement();
                cRNA.increment();
                aRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return Deg_cleaving_rate * caRNA.getValue();
            }
        };

        /*
        * Degradation of aRNA
        */
        reactions[14] = new Reaction() {
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
        reactions[15] = new Reaction() {
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
        reactions[16] = new Reaction() {
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
        * Degradation of cRNA
        */
        reactions[17] = new Reaction() {
            @Override
            public void updateValues() {
                cRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegcRNA * cRNA.getValue();
            }
        };


        /*
        * Degradation of GFP (constitutive)
        */
        reactions[18]  = new Reaction() {
            @Override
            public void updateValues() {
                GFP.decrement();
            }

            @Override
            public double calculateQuantities() {
                return DegGFP * GFP.getValue();
            }
        };


        chain.setReactions(reactions);

        return chain;

    }

    private static final double avagadroNumber = 6.022E23;

    double volumeOfEcoli = 4.76E-18;    //Volume of an E. coli cell

    double Ka_AtoGFP  = 1E9;      //Binding Keq of aRNA to GFPrna

    double Ka_AtoB = 1E16;
    double Ka_BtoC = 1E16;
    double Ka_CtoA = 1E16;
    double Keq_cleave = 1E9;

    double complexes     =   0.9;    //Formation of complex
    double decomplex     =   0.1;    //Deforming of complex
    double BetaForaRNA   = 0.6897;   //Formation of aRNA rate
        double BetaForbRNA   = 0.6897;   //Formation of bRNA rate
    double BetaForcRNA   = 0.6897;   //Formation of cRNA
    double BetaForgfpRNA = 0;        //Formation of gfpRNA rate
    double BetaForGFP    = 0;        //Formation of GFP rate

    double DegaRNA   =  1/220;         //Degradation of aRNA
    double DegbRNA   =  1/220;         //Degradation of bRNA
    double DegcRNA  =   1/220;         //Degradation of abRNA
    double DeggfpRNA = 5;              //Degradation of gfpRNA
    double DegGFP    = 5;              //Degradation of GFP
    double Deg_cleaving_rate = 1/60;   //cleaving rate
}