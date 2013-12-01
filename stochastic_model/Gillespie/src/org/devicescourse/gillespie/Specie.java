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


import java.util.HashMap;
import javax.swing.JOptionPane;

    /**
     * A Specie corresponds to a unique chemical population within the cell, such
     * as a free enzyme which would be distinct from a substrate-bound enzyme specie
     *
     * @author jcanderson
     */
    public class Specie {
        /**
         * Public constructor for a Specie object.
         *
         * @param initialvalue The initial number of molecules in the cell
         */
        public Specie(Chain chain, int initialvalue, String name) {
            _chain = chain;
            Tint = chain.getEndTime()/5000;
            if(initialvalue<0) {
                _value = 0;
            } else {
                _value = initialvalue;
            }
            _initialValue = _value;
            _name = name;
            _chain.addSpecie(this);
        }

        /**
         * Get the current value of the Specie
         * @return
         */
        public double getValue() {
            return (double) _value;
        }

        /**Decrease the concentration by 1
         *
         */
        public void decrement() {
            decrement(1);
        }

        /**
         * Increase the concentration by 1
         */
        public void increment() {
            increment(1);
        }

        /**
         * Decrease the concentration by some number n
         * @param n
         */
        public void decrement(int n) {
            if(_value>n) {
                _value = _value -n;
            } else {
                _value = 0;
            }
            storeTimepoint();
        }

        /**
         * Increase the concentration by some number n
         * @param n
         */
        public void increment(int n) {
            _value += n;
            storeTimepoint();
        }

        /**
         * Get the name of the Specie supplied in the Constructor
         * @return a String such as "Enzyme"
         */
        public String getName() {
            return _name;
        }

        /**
         * Returns a data array with the times in the first column
         * and the values in the second
         *
         * @param timepoints
         * @return  a two-column array of Doubles
         */
        public double[][] getBinnedData(int timepoints) {
            double endpoint = _chain.getEndTime();
            double[][] out = new double[2][timepoints];

            //Put in a dummy value to the time column to replace later
            for(int i=0; i<timepoints; i++) {
                out[0][i] = -5;
            }

            //Bin the data from the history hash
            for(Double timetaken : history.keySet()) {
                int value = history.get(timetaken);
                double binD = timepoints*timetaken/endpoint;
                int rounded = (int) Math.floor(binD);

                //Abort that data point if it's out of the time range
                if(rounded >= timepoints) {
                    continue;
                }

                //Put in the time
                out[0][rounded] = endpoint * rounded/timepoints;
                out[1][rounded] = value;
            }

            //Put in previous timepoint data for unfilled timepoints
            for(int i=0; i<timepoints; i++) {
                if(out[0][i] < 0) {
                    out[0][i] = endpoint * i/timepoints;

                    if(i==0) {
                        out[1][i] = _initialValue;
                    } else {
                        out[1][i] = out[1][i-1];
                    }
                }
            }

            return out;
        }

        /**
         * Whenever increment or decrement methods are called,
         * a time point gets saved to the history of this chain.
         */
        private void storeTimepoint() {
            double currtime = _chain.getCurrentTime();
            if(currtime > lastTime + Tint) {
                history.put(currtime, _value);
                lastTime = currtime;
            }
        }

        private void changeInitialValue(int value) {
            if(history.isEmpty()) {
                _value = value;
                _initialValue = value;
            } else {
                JOptionPane.showMessageDialog( null, "You need to call changeInitialValue before running the simulation", "Already ran it", JOptionPane.ERROR_MESSAGE );
            }
        }



/*-----------------
     variables
 -----------------*/
        /**
         * Value is the current value at this point in time
         */
        private int _value;

        /**
         * The initial value for the Species
         */
        private int _initialValue;

        /**
         * The name of the species, like "GFP"
         */
        private String _name;

        /**
         * Which markov chain (in effect, which cell) this belongs to
         */
        private Chain _chain;

        /**
         * The history array is [time, value]
         */
        private HashMap<Double, Integer> history = new HashMap<Double, Integer>();

        /**
         * The time interval for storing a datapoint so this doesn't store more than 5000 time points
         */
        private double Tint;
        private double lastTime = 0;
    }