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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class HistogramPlot extends JFrame {

    /**
     * You pass a 2 x n data array to this guy
     * the first column is the bin values, the second
     * column is the # per bin
     * @param data
     */
    public HistogramPlot(double[][] data, String title) {
        _title = title;
        //Get the number of bars and calculate their width in pixels
        numBars = data[0].length;
        double d1 = 450/(numBars-1);
        barWidth = (int) Math.floor(d1);

        //Put the labels into a String array
        labels = new String[numBars];
        for(int i=0; i<numBars; i++) {
            int rounded = (int) Math.floor(data[0][i]);
            labels[i] = Integer.toString(rounded);
        }

        //Move the data into a simpler array
        double[] raw = new double[numBars];
        for(int i=0; i<numBars; i++) {
            raw[i] = data[1][i];
        }

        double highest = 0;
        for(int i=0; i<numBars; i++) {
            if(raw[i]>highest) {
                highest = raw[i];
            }
        }

        double scaleFactor = 400/(1.2*highest);

        //Translate the data into ints, stuff into value array
        values = new int[numBars];
        for(int i=0; i<numBars; i++) {
            double newval = raw[i] * scaleFactor;
            values[i] = (int) Math.floor(newval);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initcomponents();
            }
        });
    }

    private void initcomponents() {
        this.setPreferredSize(new Dimension(600, 500));
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                Rectangle rect = new Rectangle(0, 0, 600, 500);
                g2d.fill(rect);
                g2d.setColor(Color.black);

                //Put in titles
                FontRenderContext context = g2d.getFontRenderContext();
                TextLayout layout = new TextLayout(_title, arial18BOLD, context);
                layout.draw(g2d, 20 , 40);

                //Put in bars and data
                for(int i=0; i<numBars; i++) {

                    rect = new Rectangle(30 + barWidth*i, 400-values[i], barWidth, values[i]);
                    g2d.fill(rect);

                    context = g2d.getFontRenderContext();
                    layout = new TextLayout(labels[i], arial12BOLD, context);
                    layout.draw(g2d, 30 + barWidth*i + barWidth/2 - 10, 425);
                }
            }
        };
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
		double[][] data= new double[][]{ { 250, 45, 36, 66, 145, 80, 55  },
                                                 { 150, 15,  6,  62, 54, 10, 84  } };
        new HistogramPlot(data, "a plot of some stuff");
    }
/*-----------------
     variables
 -----------------*/
    private int numBars;
    private int barWidth;
    private int[] values;
    private String[] labels;
    private String _title;

    protected final Font arial12BOLD =new Font("Arial", Font.BOLD, 12);
    protected final Font arial18BOLD =new Font("Arial", Font.BOLD, 18);
}
