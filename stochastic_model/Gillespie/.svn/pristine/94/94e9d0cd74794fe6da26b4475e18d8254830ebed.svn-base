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

import org.devicescourse.charts.SimpleScatter;
import org.devicescourse.gillespie.Chain;
import org.devicescourse.gillespie.Specie;

/**
 * In this example, we'll run Toggle2 simulations, but we'll take out
 * of them a histogram of different endpoint values of GFP
 *
 * The SimpleScatter just plots an array of 2 x n doubles.
 *
 * @author J. Christopher Anderson
 */
public class Toggle4 {

    public static void main(String[] args) {

        //Do the simulation                   #runs  #bins
        double[][] out = runEndpointSimulations(100,   10);

        //Plot it
        new SimpleScatter(out);
    }

    private static double[][] runEndpointSimulations(int numRuns, int numbins) {
        //Create an array to store all the final values
        double[] endGFP = new double[numRuns];

        //Do multiple runs and store each final value in a list
        for(int i = 0; i<numRuns; i++) {
            Toggle2 toggler = new Toggle2();

            //Put in the parameters we want to test
            toggler.BetaForG = 100;
            toggler.BetaForR = 300;
            toggler.BetaForS = 300;
            toggler.DegR = 20;
            toggler.DegS = 20;


            //                                   #S  #R  End
            Chain chain = toggler.generateChain(10, 100, 500);
            chain.start();

            System.out.println("Finished run #" + i);

            Specie GFP = chain.getSpecie("G");
            endGFP[i] = GFP.getValue();
        }

        //Find the maximal value observed in the runs
        double highest = 0;
        for(int i=0; i<numRuns; i++) {
            System.out.println(endGFP[i]);
            if(endGFP[i]>highest) {
                highest = endGFP[i];
            }
        }

        System.out.println("highest " + highest);


        //Create some number of bins
        int[] bins = new int[numbins];
        for(int i=0; i<numbins; i++) {
            bins[i] = 0;
        }

        //Calculate the width of each bin
        double binwidth = highest/(numbins-1);
        System.out.println("binwidth " + binwidth);

        for(int i=0; i<numRuns; i++) {
            double value = endGFP[i];
            double divbindwidth = value/binwidth;
            int binchosen = (int) Math.floor(divbindwidth);
            System.out.println("I chose bin " + binchosen);
            bins[binchosen]++;
        }

        double[][] out = new double[2][numbins];
        for(int i=0; i<numbins; i++) {
            System.out.println("bind #" + i + " has " + bins[i] + " occurances");
            out[0][i] = i*binwidth;
            out[1][i] = 1.0*bins[i];
        }
        
        return out;
    }

/*-----------------
     variables
 -----------------*/
}
