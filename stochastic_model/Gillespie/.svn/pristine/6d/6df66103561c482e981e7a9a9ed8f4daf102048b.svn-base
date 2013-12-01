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

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author J. Christopher Anderson
 */
public class DotPlotter extends JFrame {

    /**
     * You pass a n x m data array to this guy
     * the first row is the bin labels (m of them)
     *
     * The next n-1 rows each correspond to one data
     * point to plot.
     *
     * @param data
     */
    public DotPlotter(double[][] data, String[] labs, String title, boolean doXAsScatter) {
        super("Dot Plotter");
        _title = title;

        //Get the number of bars and calculate their width in pixels
        numBars = data.length;

        System.out.println(numBars + " numBars");

        double d1 = 450/(numBars-1);
        barWidth = (int) Math.floor(d1);

        //Put the labels into a String array
        labels = labs;

        //Determine the highest value
        double highest = 0;
        for(int i=0; i<numBars; i++) {
            for(int j=0; j<data[0].length; j++) {
                if(data[i][j]>highest) {
                    highest = data[i][j];
                }
            }
        }

        double scaleFactor = 400/(1.2*highest);

        //Translate the data into ints, stuff into value array
        values = new int[numBars][data[0].length];
        for(int i=0; i<numBars; i++) {
            for(int j=0; j<data[0].length; j++) {
                double newval = data[i][j] * scaleFactor;
                values[i][j] = (int) Math.floor(newval);
            }
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
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                paintGraphics(g2d);
            }
        };
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);

        JButton saveButton = new JButton("Save to File");
        getLayeredPane().setLayout(null);
        saveButton.setBounds(450, 10, 100, 22);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });
        getLayeredPane().add(saveButton);

        pack();
        setVisible(true);
    }

    private void paintGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setColor(Color.WHITE);
        Rectangle rect = new Rectangle(0, 0, 600, 500);
        g2d.fill(rect);
        g2d.setColor(Color.black);

        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.20f);
        Composite composite = g2d.getComposite();


        Ellipse2D.Double circle;

        //Put in titles
        FontRenderContext context = g2d.getFontRenderContext();
        TextLayout layout = new TextLayout(_title, arial18BOLD, context);
        layout.draw(g2d, 20 , 40);

        //Put in bars and data
        for(int i=0; i<numBars; i++) {
            g2d.setComposite(composite);
            g2d.setColor(Color.BLACK);
            context = g2d.getFontRenderContext();
            layout = new TextLayout(labels[i], arial12BOLD, context);
            layout.draw(g2d, 60 + barWidth*i, 425);

            g2d.setComposite(alpha);
            g2d.setColor(Color.BLUE);
            for(int j=0; j<values[0].length; j++) {
                int offset = (int) (Math.random() * barWidth * 0.07);
                if(Math.random()>0.5) {
                    offset = -1 * offset;
                }
                circle = new Ellipse2D.Double(60 + barWidth*i + offset, 400-values[i][j], 6, 6);
                g2d.fill(circle);
            }
        }
    }

    /**
     * On Clicking the save button, saves the image to a file as a PNG
     */
    private void saveImage() {
        System.out.println("Saving image to file");
        try {
            int width = 600, height = 500;
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();
            paintGraphics(ig2);

            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(this);
            File selFile = fc.getSelectedFile();
            String filepath = selFile.getAbsolutePath()+".png";
            ImageIO.write(bi, "PNG", new File(filepath));
        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
               double[][] datas = new double[][]{ {20, 30, 40, 50, 60},
                                                  {30, 35, 45, 75, 85},
                                                  {40, 45, 85, 95, 85},
                                                  {100, 120, 220, 250, 280},
                                                  {100, 120, 220, 250, 280},
                                                  {100, 120, 220, 250, 280},
                                                  {30, 35, 45, 75, 85},
                                                  {40, 45, 85, 95, 85},
                                                  {100, 120, 220, 250, 280},
                                                  };
               String[] labels = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        new DotPlotter(datas, labels, "a plot of some stuff", false);
    }
/*-----------------
     variables
 -----------------*/
    private int numBars;
    private int barWidth;
    private int[][] values;
    private String[] labels;
    private String _title;


    protected final Font arial12BOLD =new Font("Arial", Font.BOLD, 12);
    protected final Font arial18BOLD =new Font("Arial", Font.BOLD, 18);
}
