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
        Chain chain = repressillatorRNA.generateChain(100,1,1,1);

        chain.start();

        Specie[] RNAandProtein = new  Specie[4];
        RNAandProtein[0] = chain.getSpecie("aRNA");
        RNAandProtein[1] = chain.getSpecie("bRNA");
        RNAandProtein[2] = chain.getSpecie("cRNA");
        RNAandProtein[3] = chain.getSpecie("G");


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

        final Specie Ggene = new Specie(chain, 1, "G gene");
        final Specie aGene = new Specie(chain, 1, "a gene");
        final Specie bGene = new Specie(chain, 1, "b gene");
        final Specie cGene = new Specie(chain, 1, "c gene");


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
        * Degradation of aRNA
        */
        reactions[5] = new Reaction() {
            @Override
            public void updateValues() {
                aRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                //Amount of cRNA
                double conccRNA = cRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = aRNA.getValue() *  1 / (1 + Ka_CtoA * conccRNA);
                if(probabiliy < Math.random()) {
                    return DegaRNA * aRNA.getValue();
                }
                return DegaRNA * aRNA.getValue() + Deg_cleaving_rate * aRNA.getValue() * cRNA.getValue();
            }
        };

       /*
        * Degradation of bRNA
        */
        reactions[6] = new Reaction() {
            @Override
            public void updateValues() {
                bRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                //Amount of aRNA
                double concaRNA = aRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = bRNA.getValue() *  1 / (1 + Ka_AtoB* concaRNA);
                if(probabiliy < Math.random()) {
                    return DegbRNA * bRNA.getValue();
                }
                return DegbRNA * bRNA.getValue() + Deg_cleaving_rate * bRNA.getValue() * aRNA.getValue();
            }
        };


        /*
        * Degradation of gfpRNA
        */
        reactions[7] = new Reaction() {
            @Override
            public void updateValues() {
                gfpRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                //Amount of aRNA
                double concaRNA = aRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy =  gfpRNA.getValue() * 1 / (1 + Ka_AtoGFP * concaRNA);
                if(probabiliy < Math.random()) {
                    return DeggfpRNA * gfpRNA.getValue();
                }
                return DeggfpRNA * gfpRNA.getValue() + Deg_cleaving_rate * gfpRNA.getValue() * aRNA.getValue();
            }
        };


       /*
        * Degradation of cRNA
        */
        reactions[8] = new Reaction() {
            @Override
            public void updateValues() {
                cRNA.decrement();
            }

            @Override
            public double calculateQuantities() {
                //Amount of bRNA
                double concbRNA = bRNA.getValue()/avagadroNumber/volumeOfEcoli;

                double probabiliy = cRNA.getValue() *  1 / (1 + Ka_BtoC * concbRNA);
                System.out.println("Probability cleave cRNA: " + probabiliy);
                if(probabiliy < Math.random()) {
                    return DegcRNA * cRNA.getValue();
                }
                return DegcRNA * cRNA.getValue() + Deg_cleaving_rate * cRNA.getValue() * bRNA.getValue();
            }
        };


        /*
        * Degradation of GFP (constitutive)
        */
        reactions[9]  = new Reaction() {
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

    double Ka_AtoB = 1E6;
    double Ka_BtoC = 1E6;
    double Ka_CtoA = 1E6;

    double BetaForaRNA   = 50;   //Formation of aRNA rate
    double BetaForbRNA   = 50;   //Formation of bRNA rate
    double BetaForcRNA  = 50;   //Formation of cRNA
    double BetaForgfpRNA =  20;   //Formation of gfpRNA rate
    double BetaForGFP    =  0;   //Formation of GFP rate

    double DegaRNA   =  0.5;         //Degradation of aRNA
    double DegbRNA   = 0.5;         //Degradation of bRNA
    double DeggfpRNA = 4.5;         //Degradation of gfpRNA
    double DegGFP    = 0.5;         //Degradation of GFP
    double DegcRNA  = 0.5;          //Degradation of abRNA
    double Deg_cleaving_rate = 0.5;  //cleaving rate
}