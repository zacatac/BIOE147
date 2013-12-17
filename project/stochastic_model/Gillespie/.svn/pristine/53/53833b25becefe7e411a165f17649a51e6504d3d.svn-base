package org.devicescourse.charts;

import org.devicescourse.gillespie.Chain;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.properties.*;

import javax.swing.*;
import java.awt.*;
import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.DataSeries;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;


/**
 * Pass this one the Object[][] coming from a MChain.getBinnedData(int) call
 * and it launches a GUI plotting that data
 *
 * @author jcanderson
 */
public class AllSpeciesOverTime extends JFrame {

	/*******************************************************************************
	 *
	 ********************************************************************************/
	public AllSpeciesOverTime(Object[][] data) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _data = data;
            try {
                initComponents();
            } catch(Exception e) {
                e.printStackTrace();
            }
	}


	/*******************************************************************************
	 *
	 ********************************************************************************/
	private void initComponents() throws ChartDataException, PropertyException {
		setSize( 700, 550 );
                setResizable(false);
                
		panel=new JPanel( true );
                panel.setBackground(Color.WHITE);
		panel.setSize( 700, 550 );
		getContentPane().add( panel );
		setVisible( true );

                String[] xAxisLabels = new String[_data[0].length -1];
                for(int i=0; i< _data[0].length-1; i++) {
                    xAxisLabels[i] = Double.toString((Double) _data[0][i+1]);
                    if(Chain.verbose) {
                        System.out.println("xAxisLabels " + xAxisLabels[i]);
                    }
                }
                
		String xAxisTitle= "Time";
		String yAxisTitle= "Number";
		String title= "Timecourse plot";
		DataSeries dataSeries = new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle, title );

		double[][] data= new double[_data.length -1][_data[0].length -1];
                for(int i=0; i<_data.length -1; i++) {
                    for(int j=0; j<_data[0].length -1; j++ ) {
                        data[i][j] = (Double) _data[i+1][j+1];
                        if(Chain.verbose) {
                            System.out.println("i " + i + " j " + j + " " + data[i][j]);
                        }
                    }
                }


                String[] legendLabels = new String[_data.length -1];
                for(int i=0; i< _data.length-1; i++) {
                    legendLabels[i] = (String) _data[i+1][0];
                    if(Chain.verbose) {
                        System.out.println("i " + i + "  " + legendLabels[i]);
                    }
                }

		Paint[] paints= TestDataGenerator.getRandomPaints( _data.length -1 );

                Shape[] shapes = new Shape[_data.length -1];
                Paint[] outlinePaints = new Paint[_data.length -1];
                boolean[] fillPointFlags = new boolean[_data.length -1];
                        
                for(int i=0; i< _data.length-1; i++) {
                    shapes[i] = PointChartProperties.SHAPE_CIRCLE;
                    outlinePaints[i] = Color.black;
                    fillPointFlags[i] = true;

                }
               
		PointChartProperties pointChartProperties= new PointChartProperties( shapes, fillPointFlags, outlinePaints );

		AxisChartDataSet axisChartDataSet= new AxisChartDataSet( data, legendLabels, paints, ChartType.POINT, pointChartProperties );

		dataSeries.addIAxisPlotDataSet( axisChartDataSet );

		ChartProperties chartProperties= new ChartProperties();
		AxisProperties axisProperties= new AxisProperties();
		LegendProperties legendProperties= new LegendProperties();

		AxisChart axisChart= new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 670, 500 );


		axisChart.setGraphics2D( (Graphics2D) this.panel.getGraphics() );
		axisChart.render();
	}

/*-----------------
     variables
 -----------------*/
    private JPanel panel;
    private Object[][] _data;
}
