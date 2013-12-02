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

package org.devicescourse.charts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author J. Christopher Anderson
 */
public class CSVWriter {

    /**
     * Write an double array to disk as a CSV file
     * @param data the 2D array
     * @param afile the file you want to write to
     */
    public static void write(double[][] data, File afile) {
        Object[][] recasted = new Object[data.length][data[0].length];
        for(int i=0; i<data.length; i++) {
            for(int j=0; j<data[0].length; j++) {
                recasted[i][j] = data[i][j];
            }
        }
        write(recasted, afile);
    }

    /**
     * Write an Object array to disk as a CSV file
     * @param data the 2D array of data (can include Strings, ints, or Doubles)
     * @param afile the file you want to write to
     */
    public static void write(Object[][] data, File afile) {
        StringBuffer sb = new StringBuffer();
        String currvalue;

        System.out.println(data[0].length + " i length");
        System.out.println(data.length + " j length");

        for(int i=0; i<data.length; i++) {
            for(int j=0; j<data[0].length; j++) {
                try {
                    currvalue = Integer.toString((Integer) data[i][j]);
                    sb.append(currvalue);
                    sb.append(",");
                    continue;
                } catch(Exception e) {
                }

                try {
                    currvalue = Double.toString((Double) data[i][j]);
                    sb.append(currvalue);
                    sb.append(",");
                    continue;
                } catch(Exception e) {
                }

                try {
                    currvalue = (String) data[i][j];
                    sb.append(currvalue);
                    sb.append(",");
                    continue;
                } catch(Exception e) {
                }

            }
            sb.append("\n");
        }

        String datastring = sb.toString();
        String out = datastring.substring(0, datastring.length()-2);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(afile.getAbsolutePath()));
            bw.write(out);
            bw.close();
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        Object[][] datas = new Object[][]{ {20, 30, 40, 50, 60},
                                          {30, 35, 45, 75, 85},
                                          {40, 45, 85, 95, 85},
                                          {100, 120, 220, 250, 280},
                                          {100, 120, 220, 250, 280},
                                          {100, 120, 220, 250, 280},
                                          {30, 35, 45, 75, 85},
                                          {40, 45, 85, 95, 85},
                                          {100, 120, 220, 250, 280},
                                          };
        write(datas, new File("C:\\Users\\jcanderson\\Desktop\\data.csv"));
    }

/*-----------------
     variables
 -----------------*/
}
