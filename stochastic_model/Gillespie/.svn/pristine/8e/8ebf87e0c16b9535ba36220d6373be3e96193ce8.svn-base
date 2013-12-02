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

import java.util.ArrayList;
import org.devicescourse.charts.AllSpeciesOverTime;
import org.devicescourse.gillespie.Chain;
import org.devicescourse.gillespie.Specie;

/**
 * In this example, we'll run Toggle2 simulations, but we'll run it
 * multiple times, average all the time point values, then display a plot
 * of all the average values.
 * 
 * By averaging multiple "cells" you are looking at the data that would
 * result from looking at a population of cells.  So, let's say we took a bunch
 * of cells and then lysed them and ran western blots.  These numbers would
 * correlate with the protein concentrations we'd observe on the ensemble.
 *
 * Note that this will not expose the population heterogeneity in the system.
 *
 * @author J. Christopher Anderson
 */
public class Toggle3 {

    public static void main(String[] args) {

        //                                          #runs
        new AllSpeciesOverTime(runAverageSimulations(20));

    }

    private static Object[][] runAverageSimulations(int numRuns) {
        //Do 5 runs and get the average R, S, and G values for each timepoint
        ArrayList<double[][]> Gdata = new ArrayList<double[][]>();
        ArrayList<double[][]> Rdata = new ArrayList<double[][]>();
        ArrayList<double[][]> Sdata = new ArrayList<double[][]>();

        //Run 5 values and store just the binned data
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

            Specie GFP = chain.getSpecie("G");
            Specie R = chain.getSpecie("R");
            Specie S = chain.getSpecie("S");

            Gdata.add(GFP.getBinnedData(50));
            Rdata.add(R.getBinnedData(50));
            Sdata.add(S.getBinnedData(50));
        }

        //Create a final array to store all the averaged data
        Object[][] averagedData = new Object[4][51];
        averagedData[0][0] = "time";
        averagedData[1][0] = "G";
        averagedData[2][0] = "R";
        averagedData[3][0] = "S";

        //For each run, average the data and put it into the array
        for(int timepoint=0; timepoint<50; timepoint++) {
            //Use the timepoints from just one of the plots
            averagedData[0][timepoint+1] = Gdata.get(0)[0][timepoint];

            double totG = 0;
            double totR = 0;
            double totS = 0;

            //For each timepoint of each run, add upp the number of molecules
            for(int run=0; run<numRuns; run++) {
                totG += Gdata.get(run)[1][timepoint];
                totR += Rdata.get(run)[1][timepoint];
                totS += Sdata.get(run)[1][timepoint];
            }

            //Put in the average of all the runs at each timepoint
            averagedData[1][timepoint+1] = totG/numRuns;
            averagedData[2][timepoint+1] = totR/numRuns;
            averagedData[3][timepoint+1] = totS/numRuns;
        }

        return averagedData;
    }

/*-----------------
     variables
 -----------------*/
}
