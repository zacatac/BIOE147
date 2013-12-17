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

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.devicescourse.charts.CSVWriter;
import org.devicescourse.charts.DotPlotter;
import org.devicescourse.charts.HistogramPlot;
import org.devicescourse.charts.SimpleScatter;
import org.devicescourse.gillespie.Chain;
import org.devicescourse.gillespie.Specie;

/**
 * In this example, we'll run Toggle2 simulations, and we'll run histograms,
 * but we'll scan through different parameters.  So, we're going to have to
 * run this a lot more times (it's going to take a while!)
 *
 * The parameter we'll vary is the initial amount of R and S proteins
 *
 * @author J. Christopher Anderson
 */
public class Toggle6 {

    public static void main(String[] args) {
        /**There is an issue here of the thing drawing the GUIs on the same thread as
         * running the simulation.  Don't worry about it.  It's always going to be
         * wise to initiate your simulations with this relay.
        */
        new Thread() {
            @Override
            public void run() {
                runSimulation();
            }
        }.start();
    }

    private static void runSimulation() {
        String[] labels = new String[6];

                                  //   # series  # runs per series
        double[][] dataset = new double[  6  ][      50   ];
        for(int i=0; i<6; i++) {
            int differential = 20*i;
            int initialR = differential;
            int initialS = 110-differential;

            //The label array is used for the chart for each parameter run
            labels[i] = "R= " + initialR;

            String title = "Starting a simulation with R = " + initialR + " and S = " + initialS;
            System.out.println(title);

            //Do the simulation                #runs
            dataset[i] = runEndpointSimulations(50, initialR,  initialS);
        }

        //Save the data to a CSV file (change the file path to somewhere on your computer)
        CSVWriter.write(dataset, new File("C:\\Users\\jcanderson\\Desktop\\data.csv"));

        //Plot it
        new DotPlotter(dataset, labels, "Toggle switch varying initial R and S", true);
    }

    private static double[] runEndpointSimulations(int numRuns,  int initialR, int initialS) {
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


            //                                    #S        #R      End
            Chain chain = toggler.generateChain(initialS, initialR, 500);
            chain.start();

            System.out.print(" "+ i);

            Specie GFP = chain.getSpecie("G");
            endGFP[i] = GFP.getValue();
        }
        
        return endGFP;
    }

/*-----------------
     variables
 -----------------*/
}
