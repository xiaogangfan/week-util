package week.report.test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.EmptyBlock;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import week.report.domain.ReportData;
import week.report.infras.Serie;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class DoubleY extends ApplicationFrame {

    public DoubleY(ReportData reportData) {
        super(reportData.getTitle());
        setContentPane(createDemoPanel(reportData));
    }

    private static XYDataset createDatasetLeft(ReportData reportData) {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        for (int i = 0; i < reportData.getLeftY().size(); i++) {
            Serie serie = reportData.getLeftY().get(i);
            String x = reportData.getX().get(i);
            XYSeries xyseries = new XYSeries(serie.getName());
            int index = 1;
            for (Object data : serie.getData()) {
                xyseries.add(index,((BigDecimal)data).doubleValue());
                index++;
            }
            xyseriescollection.addSeries((xyseries));
        }
        return xyseriescollection;
    }

    private static XYDataset createDatasetRight(ReportData reportData) {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        for (int i = 0; i < reportData.getRightY().size(); i++) {
            Serie serie = reportData.getRightY().get(i);
            String x = reportData.getX().get(i);
            XYSeries xyseries = new XYSeries(serie.getName());
            int index = 1;
            for (Object data : serie.getData()) {
                xyseries.add(index,((BigDecimal)data).doubleValue());
                index++;
            }
            xyseriescollection.addSeries((xyseries));
        }
        return xyseriescollection;
    }

    private static JFreeChart createChart(ReportData reportData) {
        XYDataset xydataset = createDatasetLeft(reportData);
        JFreeChart jfreechart = ChartFactory.createXYLineChart("", "最近两周 ", reportData.getyLeftTitle(), xydataset, PlotOrientation.VERTICAL, false, true, false);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        NumberAxis numberaxis1 = new NumberAxis(reportData.getyRightTitle());
        numberaxis1.setAutoRangeIncludesZero(false);
        xyplot.setRangeAxis(1, numberaxis1);  //关键代码
        xyplot.setDataset(1, createDatasetRight(reportData)); //关键代码
        xyplot.mapDatasetToRangeAxis(1, 1);
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
//        xylineandshaperenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
//        xylineandshaperenderer.setShapesVisible(true);
//        xylineandshaperenderer.setShapesFilled(true);
        XYPointerAnnotation xypointerannotation = new XYPointerAnnotation("Annotation   1   (2.0,   167.3) ", 2D, 167.30000000000001D, -0.78539816339744828D);
        xypointerannotation.setTextAnchor(TextAnchor.BOTTOM_LEFT);
        xypointerannotation.setPaint(Color.red);
        xypointerannotation.setArrowPaint(Color.red);
        xylineandshaperenderer.addAnnotation(xypointerannotation);
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, true);
        xylineandshaperenderer1.setSeriesPaint(0, Color.black);
//        xylineandshaperenderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        XYPointerAnnotation xypointerannotation1 = new XYPointerAnnotation("Annotation   2   (15.0,   613.2) ", 15D, 613.20000000000005D, 1.5707963267948966D);
        xypointerannotation1.setTextAnchor(TextAnchor.TOP_CENTER);
        xylineandshaperenderer1.addAnnotation(xypointerannotation1);
        xyplot.setRenderer(1, xylineandshaperenderer1);
        LegendTitle legendtitle = new LegendTitle(xylineandshaperenderer);
        LegendTitle legendtitle1 = new LegendTitle(xylineandshaperenderer1);
        BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
        blockcontainer.add(legendtitle, RectangleEdge.LEFT);
        blockcontainer.add(legendtitle1, RectangleEdge.RIGHT);
        blockcontainer.add(new EmptyBlock(2000D, 0.0D));
        CompositeTitle compositetitle = new CompositeTitle(blockcontainer);
        compositetitle.setPosition(RectangleEdge.BOTTOM);
        jfreechart.addSubtitle(compositetitle);
        return jfreechart;
    }

    public static JPanel createDemoPanel(ReportData reportData) {
        JFreeChart jfreechart = createChart(reportData);
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[]) {
//        create("业务量");
    }


    public static void create(ReportData reportData) {
        DoubleY annotationdemo2 = new DoubleY(reportData);
        annotationdemo2.pack();
        RefineryUtilities.centerFrameOnScreen(annotationdemo2);
        annotationdemo2.setVisible(true);
    }
}

