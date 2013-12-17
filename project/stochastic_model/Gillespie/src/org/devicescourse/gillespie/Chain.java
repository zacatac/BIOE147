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

package org.devicescourse.gillespie;

import java.net.StandardSocketOptions;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 * An Chain is a simulation of the events that occur in one cell.  There are multiple species
 * (say, 500 GFPs, 45 polymerases, etc) associated with one Chain.  When run, it will simulate
 * the molecular history of events occurring in that one cell.  To simulate what would happen in many
 * cells or the probabilities of different types of results, you'd simulate multiple Chains.
 *
 * @author J. Christopher Anderson
 */
public class Chain {

    /**
     * Constructor for an Chain.  It needs an endpoint, and that's it.  The constructor
     * alone will not execute the simulation.  You need to subsequently set the Reactions
     * and then call start().
     * @param endtime
     */
    public Chain(double endtime) {
        _endTime = endtime;
    }

    /**
     * Install the reactions, the Chain can't run without having its reactions in place
     * @param rxns
     */
    public void setReactions(Reaction[] rxns) {
        _reactions = rxns;
    }

    /**
     * Make this Chain run!
     */
    public void start() {
        if(_reactions==null) {
            JOptionPane.showMessageDialog( null, "You didn't put in the Reactions", "No Reactions", JOptionPane.ERROR_MESSAGE );
            return;
        }
        while(_currTime < _endTime) {
            runSteps();

        }
        isDone = true;
    }

    /**
     * If you've run start() and then want all the data for each species in bins in a nice matrix
     * call this method.  The first row is String headers.
     *
     * The first column will be time, then each successive one is the data such as:
     *
     * time     Enz1   substrate   product
     * 0        50       299         0
     * 5        40       298         1
     * 10       30       285         14
     *
     * @param timeinterval
     * @return
     */
    public Object[][] getBinnedData(int timepoints) {
        if(!isDone) {
            JOptionPane.showMessageDialog( null, "You need to call start before requesting binned data", "Not run yet", JOptionPane.ERROR_MESSAGE );
            return null;
        }

        //Make the output array
        Object[][] out = new Object[_species.size() + 1][timepoints+1];
        out[0][0] = "Time";

        //Put names in the rest of the header row
        int i = 1;
        for(Specie sp : _species) {
            out[i][0] = sp.getName();
            i++;
        }

        //Put the time and value into the remaining rows
        int column = 1;
        for(Specie sp : _species) {
            double[][] twocolumns = sp.getBinnedData(timepoints);
            for(int j=0; j<twocolumns[0].length; j++) {
                out[0][j+1] = twocolumns[0][j];
                out[column][j+1] = twocolumns[1][j];
            }
            column++;
        }

        return out;
    }

    /**
     * Get all the Species in this Chain as a Collection
     * @return
     */
    public HashSet<Specie> getSpecies() {
        return _species;
    }

    /**
     * Get a Specie from this Chain by its name
     * @param name
     * @return
     */
    public Specie getSpecie(String name) {
        return NameToSpecie.get(name);
    }

    /***********************************************************/
    /***************PRIVATE METHODS BELOW HERE******************/
    /***********************************************************/

    /**
     * This method executes one atomic reaction in the trajectory it:
     *  1)  Picks two random numbers
     *  2)  One random number is used to pick the next time interval
     *  3)  The other number is used to pick which Reaction will occur
     *  4)  Finally it executes the Reaction
     *  5)  The Reaction updates the concentrations of the Species
     */
    private void runSteps() {
        //Calculate all the current populations
        double A0=0;
        double[] A = new double[_reactions.length];
        for(int i = 0; i < _reactions.length; i++) {
            A[i] = _reactions[i].calculateQuantities();
            A0+=A[i];
        }

        //Choose the two random numbers
        double R1 = Math.random();
        double R2 = Math.random();

        //Increment the time with the random time interval
        _currTime = _currTime + Math.log(1/R1)/A0;

        double R2A0 = R2*A0;
        double SUM = 0;

        //Choose which reaction is next
        for(int NU =0; NU<_reactions.length; NU++) {

            int MU = NU;
            SUM = SUM + A[NU];
            if(SUM >= R2A0) {
                updateValues(MU);
                break;
            }
        }
    }

    private void updateValues(int MU) {
        _reactions[MU].updateValues();
        if(verbose) {
            System.out.println("chose reaction " +  MU);
        }
    }

    Double getCurrentTime() {
        return _currTime;
    }

    Double getEndTime() {
        return _endTime;
    }

    void addSpecie(Specie spec) {
        _species.add(spec);
        NameToSpecie.put(spec.getName(), spec);
    }


/*-----------------
     variables
 -----------------*/
    private double _currTime = 0;
    private double _endTime;
    private Reaction[] _reactions;
    private boolean isDone = false;
    private HashSet<Specie> _species = new HashSet<Specie>();

    private HashMap<String, Specie> NameToSpecie = new HashMap<String, Specie>();

    public static final boolean verbose = true;

}
