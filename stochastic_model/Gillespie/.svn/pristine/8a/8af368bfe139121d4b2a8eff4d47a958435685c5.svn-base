package org.devicescourse.charts;




import org.jCharts.chartData.ChartDataException;
import org.jCharts.properties.*;

import javax.swing.*;
import java.awt.*;
import org.devicescourse.jchart.AxisCharts;
import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.DataSeries;
import org.jCharts.test.TestDataGenerator;
import org.jCharts.types.ChartType;


public class SimpleScatter extends JFrame
{
	private JPanel panel;


	/*******************************************************************************
	 *
	 ********************************************************************************/
	public SimpleScatter(final double[][] data)  {
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

                String[] xAxisLabels= new String[_data[0].length];
                for(int i=0; i<_data[0].length; i++) {
                    xAxisLabels[i] = Double.toString(_data[0][i]);
                }

		String xAxisTitle= "X Axis";
		String yAxisTitle= "Y Axis";
		String title= "Simple Scatter";
                
		DataSeries dataSeries = new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle, title );

                double[][] data = new double[1][_data[0].length];
                for(int i=0; i<_data[0].length; i++) {
                    data[0][i] = _data[1][i];
                }

		String[] legendLabels= { "values"};
		Paint[] paints= TestDataGenerator.getRandomPaints( 1 );

		Shape[] shapes= { PointChartProperties.SHAPE_DIAMOND };
		boolean[] fillPointFlags= { true};
		Paint[] outlinePaints= { Color.black};
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
       double[][] _data;

}
