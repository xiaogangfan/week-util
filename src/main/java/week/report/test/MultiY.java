package week.report.test;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

public class MultiY {

    XYSeriesCollection dataset1;
    XYSeriesCollection dataset2;
    XYSeriesCollection dataset3;
    JFreeChart chart;
    XYPlot plot;

    public MultiY() {
        // 生成数据
        XYSeries series1 = new XYSeries("系列1");
        series1.add(1, 2);
        series1.add(2, 4);
        series1.add(7, 6);

        XYSeries series2 = new XYSeries("系列2");
        series2.add(5, 2);
        series2.add(3, 4);
        series2.add(7, 3);

        XYSeries series3 = new XYSeries("系列3");
        series3.add(3, 2);
        series3.add(5, 4);
        series3.add(4, 6);

        dataset1 = new XYSeriesCollection();
        dataset2 = new XYSeriesCollection();
        dataset3 = new XYSeriesCollection();

        dataset1.addSeries(series1);
        dataset2.addSeries(series2);
        dataset3.addSeries(series3);

        chart = ChartFactory.createXYLineChart("MultiAxis", "X axis",
                "First Y Axis", dataset1, PlotOrientation.VERTICAL, true, true,
                false);

        plot = chart.getXYPlot();


        // 添加第2个Y轴

        NumberAxis axis2 = new NumberAxis("Second Axis");
        // -- 修改第2个Y轴的显示效果
        axis2.setAxisLinePaint(Color.BLUE);
        axis2.setLabelPaint(Color.BLUE);
        axis2.setTickLabelPaint(Color.BLUE);

        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);
        // -- 修改第2条曲线显示效果
        XYLineAndShapeRenderer render2 =  new XYLineAndShapeRenderer();
        render2.setSeriesPaint(0, Color.BLUE);
        plot.setRenderer(1, render2);


        // 添加第3个Y轴
        NumberAxis axis3 = new NumberAxis("Third Axis");

        axis3.setAxisLinePaint(Color.GREEN);
        axis3.setLabelPaint(Color.GREEN);
        axis3.setTickLabelPaint(Color.GREEN);

        plot.setRangeAxis(2, axis3);
        plot.setDataset(2, dataset3);
        plot.mapDatasetToRangeAxis(2, 2);

        XYLineAndShapeRenderer render3 =  new XYLineAndShapeRenderer();
        render3.setSeriesPaint(0, Color.GREEN);
        plot.setRenderer(2, render3);


        TextTitle copyright = new TextTitle(" 小龙飞翔@Java Eye    ");
        copyright.setPosition(RectangleEdge.BOTTOM);
        copyright.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        copyright.setFont(new Font("黑体", 12, 12));
        chart.addSubtitle(copyright);
        chart.getLegend().setItemFont(new Font("黑体", 12, 12));
    }

    public static void main(String[] agrs) {
        MultiY obj = new MultiY();
        ChartFrame frame = new ChartFrame("多坐标轴", obj.chart);
        frame.pack();
        frame.setVisible(true);
    }

}