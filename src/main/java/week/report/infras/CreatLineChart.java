package week.report.infras;

import com.google.common.collect.Lists;
import org.jfree.chart.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import week.report.domain.ReportData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * @author
 * @Title:创建折线图
 * @history
 * @since JDK1.6
 */
public class CreatLineChart {

    /**
     * Jfreechart工具类
     * <p>
     * 解决中午乱码问题<br>
     * 用来创建类别图表数据集、创建饼图数据集、时间序列图数据集<br>
     * 用来对柱状图、折线图、饼图、堆积柱状图、时间序列图的样式进行渲染<br>
     * 设置X-Y坐标轴样式
     * <p>
     */
    private static String OUT_PUT_PATH = "/Users/xiaogangfan/Downloads/weekreport.png";
    private static String title = "title";
    private static String xTitle = "时间";
    private static String yTitle = "指标";
    private static Font FONT = new Font("宋体", Font.PLAIN, 12);

    public static Color[] CHART_COLORS = {
            new Color(31, 129, 188), new Color(92, 92, 97), new Color(144, 237, 125), new Color(255, 188, 117),
            new Color(153, 158, 255), new Color(255, 117, 153), new Color(253, 236, 109), new Color(128, 133, 232),
            new Color(158, 90, 102), new Color(255, 204, 102)};//颜色

    /**
     * 静态代码块
     */
    static {
        setChartTheme();
    }

    /**
     * 无参构造器
     */
    public CreatLineChart() {

    }

    /**
     * TODO  可以通过调用这个方法, 提供对应格式的参数即可生成图片,并存在指定位置
     * 生成一个这先出并保存为png格式,
     *
     * @param title     图片标题
     * @param xtitle    x轴标题
     * @param ytitle    y轴标题
     * @param filepath  文件路径+文件名
     * @param categorie 横坐标类型
     * @param series    数据内容
     * @param width     图片宽度
     * @param height    图片高度
     * @throws Exception
     */
    public static void CreateNewLineChartForPng(String title, String xtitle, String ytitle, String filepath, List<String> categorie, List<Serie> series, int width, int height) throws Exception {
        ChartPanel chartPanel = new CreatLineChart().createChart(title, xtitle, ytitle, categorie, series);
        //将图片保存为png格式
        saveAsFile(chartPanel.getChart(), filepath, width, height);
    }


    /**
     * 中文主题样式 解决乱码
     */
    public static void setChartTheme() {
        // 设置中文主题样式 解决乱码
        StandardChartTheme chartTheme = new StandardChartTheme("CN");
        // 设置标题字体
        chartTheme.setExtraLargeFont(FONT);
        // 设置图例的字体
        chartTheme.setRegularFont(FONT);
        // 设置轴向的字体
        chartTheme.setLargeFont(FONT);
        chartTheme.setSmallFont(FONT);
        chartTheme.setTitlePaint(new Color(51, 51, 51));
        chartTheme.setSubtitlePaint(new Color(85, 85, 85));

        chartTheme.setLegendBackgroundPaint(Color.WHITE);// 设置标注
        chartTheme.setLegendItemPaint(Color.BLACK);//
        chartTheme.setChartBackgroundPaint(Color.WHITE);
        // 绘制颜色绘制颜色.轮廓供应商
        // paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence

        Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[]{Color.WHITE};
        // 绘制器颜色源
        DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS, OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
        chartTheme.setDrawingSupplier(drawingSupplier);

        chartTheme.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
        chartTheme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
        chartTheme.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
        chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

        chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
        chartTheme.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
        chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

        chartTheme.setBaselinePaint(Color.WHITE);
        chartTheme.setCrosshairPaint(Color.BLUE);// 不确定含义
        chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
        chartTheme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
        chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
        chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染

        chartTheme.setItemLabelPaint(Color.black);
        chartTheme.setThermometerPaint(Color.white);// 温度计

        ChartFactory.setChartTheme(chartTheme);
    }


    /**
     * 创建类别数据集合
     */
    public static DefaultCategoryDataset createDefaultCategoryDataset(List<Serie> series, String[] categories) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Serie serie : series) {
            String name = serie.getName();
            Vector<Object> data = serie.getData();
            if (data != null && categories != null && data.size() == categories.length) {
                for (int index = 0; index < data.size(); index++) {
                    String value = data.get(index) == null ? "" : data.get(index).toString();
                    if (isPercent(value)) {
                        value = value.substring(0, value.length() - 1);
                    }
                    if (isNumber(value)) {
                        dataset.setValue(Double.parseDouble(value), name, categories[index]);
                    }
                }
            }

        }
        return dataset;

    }

    /**
     * 设置图例无边框，默认黑色边框
     */
    public static void setLegendEmptyBorder(JFreeChart chart) {
        chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

    }

    /**
     * 是不是一个%形式的百分比
     *
     * @param str
     * @return
     */
    public static boolean isPercent(String str) {
        return str != null ? str.endsWith("%") && isNumber(str.substring(0, str.length() - 1)) : false;
    }

    /**
     * 是不是一个数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return str != null ? str.matches("^[-+]?(([0-9]+)((([.]{0})([0-9]*))|(([.]{1})([0-9]+))))$") : false;
    }

    /**
     * 设置 折线图样式
     *
     * @param plot
     * @param isShowDataLabels 是否显示数据标签 默认不显示节点形状
     */
    public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels) {
        setLineRender(plot, isShowDataLabels, false);
    }

    /**
     * 设置折线图样式
     *
     * @param plot
     * @param isShowDataLabels 是否显示数据标签
     */
    @SuppressWarnings("deprecation")
    public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels, boolean isShapesVisible) {
        plot.setNoDataMessage(OUT_PUT_PATH);
        plot.setInsets(new RectangleInsets(10, 10, 0, 10), false);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        renderer.setStroke(new BasicStroke(1.5F));
        if (isShowDataLabels) {
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
                    NumberFormat.getInstance()));
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi
        }
        renderer.setBaseShapesVisible(isShapesVisible);// 数据点绘制形状
        setXAixs(plot);
        setYAixs(plot);

    }


    /**
     * 设置类别图表(CategoryPlot) X坐标轴线条颜色和样式
     */
    public static void setXAixs(CategoryPlot plot) {
        Color lineColor = new Color(31, 121, 170);
        plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
        plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

    }

    /**
     * 设置类别图表(CategoryPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
     */
    public static void setYAixs(CategoryPlot plot) {
        Color lineColor = new Color(192, 208, 224);
        ValueAxis axis = plot.getRangeAxis();
        axis.setAxisLinePaint(lineColor);// Y坐标轴颜色
        axis.setTickMarkPaint(lineColor);// Y坐标轴标记|竖线颜色
        // 隐藏Y刻度
        axis.setAxisLineVisible(false);
        axis.setTickMarksVisible(false);
        // Y轴网格线条
        plot.setRangeGridlinePaint(new Color(192, 192, 192));
        plot.setRangeGridlineStroke(new BasicStroke(1));

        plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
        plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距

    }

    /**
     * 必须设置文本抗锯齿
     */
    public static void setAntiAlias(JFreeChart chart) {
        chart.setTextAntiAlias(false);

    }

    //-----------------------------------------------------------------------------------------------------------------

    /**
     * 折线图
     * <p>
     * 创建图表步骤：<br/>
     * 1：创建数据集合<br/>
     * 2：创建Chart：<br/>
     * 3:设置抗锯齿，防止字体显示不清楚<br/>
     * 4:对柱子进行渲染，<br/>
     * 5:对其他部分进行渲染<br/>
     * 6:使用chartPanel接收<br/>
     *
     * </p>
     */
    //创建折线图图表
    public DefaultCategoryDataset createDataset(List<String> categorie, List<Serie> series) {
        // 标注类别
        String[] categories = categorie.toArray(new String[categorie.size()]);
        //横坐标
//           String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
//           series = new Vector<Serie>();
//           // 柱子名称：柱子所有的值集合
//           //纵坐标
//           series.add(new Serie("Tokyo", new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4 }));
//           series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
//           series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//           series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
        // 1：创建数据集合
        DefaultCategoryDataset dataset = CreatLineChart.createDefaultCategoryDataset(series, categories);
        return dataset;
    }

    /**
     * 创建折线图
     *
     * @param title     折线图标题
     * @param xtitle    x轴标题
     * @param ytitle    y轴标题
     * @param categorie 横坐标类别
     * @param series    数据集
     * @return
     * @throws Exception
     */
    public ChartPanel createChart(String title, String xtitle, String ytitle, List<String> categorie, List<Serie> series) throws Exception {

        // 2：创建Chart[创建不同图形]
        JFreeChart chart = ChartFactory.createLineChart(title, xtitle, ytitle, createDataset(categorie, series));
        // 3:设置抗锯齿，防止字体显示不清楚
        CreatLineChart.setAntiAlias(chart);// 抗锯齿
        // 4:对柱子进行渲染[[采用不同渲染]]
        CreatLineChart.setLineRender(chart.getCategoryPlot(), false, true);//
        // 5:对其他部分进行渲染
        CreatLineChart.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
        CreatLineChart.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
        // 设置标注无边框
        chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
        // 6:使用chartPanel接收
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }


    /**
     * 主方法 用来测试  `
     *
     * @param args
     */
    public static void main(String[] args) {
        CreatLineChart creatLineChart = new CreatLineChart();
        List<String> categories = new ArrayList<>();
        categories.add("Jan");
        categories.add("Feb");
        List<Serie> series = Lists.newArrayList();
        //横坐标
        // 柱子名称：柱子所有的值集合
        //纵坐标
        series.add(new Serie("Tokyo", new Double[]{49.9, 71.5}));
        series.add(new Serie("New York", new Double[]{83.6, 78.8}));
//        series.add(new Serie("London", new Double[]{48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2}));
//        series.add(new Serie("Berlin", new Double[]{42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1}));
        creatLineChart.createReport(categories, series,"chart");
    }


    /**
     * 将图表保存为PNG、JPEG图片
     *
     * @param chart      折线图对象
     * @param outputPath 文件保存路径, 包含文件名
     * @param weight     宽
     * @param height     高
     * @throws Exception
     */
    public static void saveAsFile(JFreeChart chart, String outputPath, int weight, int height) throws Exception {
        FileOutputStream out = null;
        File outFile = new File(outputPath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        out = new FileOutputStream(outputPath);
        // 保存为PNG
        ChartUtilities.writeChartAsPNG(out, chart, weight, height);
        // 保存为JPEG
        // ChartUtilities.writeChartAsJPEG(out, chart, weight, height);
        out.flush();
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // do nothing
                e.printStackTrace();
            }

        }
    }


    public void createReport(List<String> categories, List<Serie> series,String title) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 420);
        frame.setLocationRelativeTo(null);
        try {

            ChartPanel chartPanel = new CreatLineChart().createChart(title, xTitle, yTitle, categories, series);
            frame.getContentPane().add(chartPanel);
            frame.setVisible(true);
//            saveAsFile(chartPanel.getChart(),"/Users/xiaogangfan/Downloads/weekreport.png",900,500);
//            CreateNewLineChartForPng("lol2.png", NO_DATA, NO_DATA, OUT_PUT_PATH, new ArrayList<>(categorie), series, 900, 500);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public void createReport(ReportData reportData) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 420);
        frame.setLocationRelativeTo(null);
        try {

            ChartPanel chartPanel = new CreatLineChart().createChart(reportData.getTitle(), reportData.getxTitle(), reportData.getyTitle(), reportData.getX(), reportData.getY());
            frame.getContentPane().add(chartPanel);
            frame.setVisible(true);
//            saveAsFile(chartPanel.getChart(),"/Users/xiaogangfan/Downloads/weekreport.png",900,500);
//            CreateNewLineChartForPng("lol2.png", NO_DATA, NO_DATA, OUT_PUT_PATH, new ArrayList<>(categorie), series, 900, 500);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}