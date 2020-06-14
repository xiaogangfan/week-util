package week.report.inter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import week.report.domain.IndicatorEnum;
import week.report.domain.ReportData;
import week.report.domain.WeekData;
import week.report.infras.CreatLineChart;
import week.report.infras.Serie;
import week.report.test.DoubleY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowReport {
    public static void main(String[] args) {
        Map<String, BigDecimal[]> originData = getData();

//         业务量
        CompletableFuture.supplyAsync(() -> {
            try {
                getNumChartData(originData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "supplyAsyncWithSleep Thread Id : " + Thread.currentThread();
        });

        // 业务效率
//        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
                getAvaiableChartData(originData);

                Thread.sleep(500);
                getRateChartData(originData);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            return "supplyAsyncWithSleep Thread Id : " + Thread.currentThread();
//        });
    }

    private static void getAvaiableChartData(Map<String, BigDecimal[]> originData) {
        ReportData reportData = new ReportData();
        List<Serie> leftYData = Lists.newArrayList();
        List<Serie> rightYData = Lists.newArrayList();
        List<String> xList = new ArrayList<>();

        xList.add("last week");
        xList.add("this week");
        leftYData.add(new Serie(IndicatorEnum.余额调节表对账TPS.getName(), originData.get(IndicatorEnum.余额调节表对账TPS.getName())));
        leftYData.add(new Serie(IndicatorEnum.资金帐写入最高TPS.getName(), originData.get(IndicatorEnum.资金帐写入最高TPS.getName())));
        rightYData.add(new Serie(IndicatorEnum.余额调节表对账TP99.getName(), originData.get(IndicatorEnum.余额调节表对账TP99.getName())));
        rightYData.add(new Serie(IndicatorEnum.资金帐写入最高TP99.getName(), originData.get(IndicatorEnum.资金帐写入最高TP99.getName())));

        reportData.setX(xList);
        reportData.setxTitle("最近x周");
        reportData.setLeftY(leftYData);
        reportData.setRightY(rightYData);
        reportData.setyLeftTitle("TPS");
        reportData.setyRightTitle("TP99(ms)");
        reportData.setTitle("系统可用性");
        DoubleY.create(reportData);
    }

    private static void getAvaiableChartDataTP99(List<String> categories, List<Serie> series, Map<String, BigDecimal[]> originData, CreatLineChart creatLineChart) {
        categories.clear();
        series.clear();
        categories.add("last week");
        categories.add("this week");
        series.add(new Serie(IndicatorEnum.余额调节表对账TP99.getName(), originData.get(IndicatorEnum.余额调节表对账TP99.getName())));
        series.add(new Serie(IndicatorEnum.资金帐写入最高TP99.getName(), originData.get(IndicatorEnum.资金帐写入最高TP99.getName())));

        ReportData reportData = new ReportData();
        reportData.setxTitle("时间（最近x周）");
        reportData.setyTitle("TP99（ms）");
        reportData.setTitle("系统可用性TP99");
        reportData.setY(series);
        reportData.setX(categories);
        creatLineChart.createReport(reportData);
    }

    private static void getRateChartData(Map<String, BigDecimal[]> originData) {
        CreatLineChart creatLineChart = new CreatLineChart();
        List<Serie> yList = Lists.newArrayList();
        List<String> xList = new ArrayList<>();

        xList.add("last week");
        xList.add("this week");
        yList.add(new Serie(IndicatorEnum.自动对账率.getName(), originData.get(IndicatorEnum.自动对账率.getName())));
        yList.add(new Serie(IndicatorEnum.资金调拨自动签收率.getName(), originData.get(IndicatorEnum.资金调拨自动签收率.getName())));
        yList.add(new Serie(IndicatorEnum.境内付款成功率.getName(), originData.get(IndicatorEnum.境内付款成功率.getName())));
        yList.add(new Serie(IndicatorEnum.境外付款成功率.getName(), originData.get(IndicatorEnum.境外付款成功率.getName())));
        ReportData reportData = new ReportData();
        reportData.setxTitle("时间（最近x周）");
        reportData.setyTitle("百分比(%)");
        reportData.setTitle("业务效率");
        reportData.setY(yList);
        reportData.setX(xList);
        creatLineChart.createReport(reportData);
    }

    private static void getNumChartData(Map<String, BigDecimal[]> originData) {
        List<Serie> leftYData = Lists.newArrayList();
        List<Serie> rightYData = Lists.newArrayList();
        List<String> xList = new ArrayList<>();

        xList.add("last week");
        xList.add("this week");
        leftYData.add(new Serie(IndicatorEnum.余额调节表对账总量.getName(), originData.get(IndicatorEnum.余额调节表对账总量.getName())));
        leftYData.add(new Serie(IndicatorEnum.资金帐写入总量.getName(), originData.get(IndicatorEnum.资金帐写入总量.getName())));
        rightYData.add(new Serie(IndicatorEnum.境内付款总量.getName(), originData.get(IndicatorEnum.境内付款总量.getName())));
        rightYData.add(new Serie(IndicatorEnum.境外付款总量.getName(), originData.get(IndicatorEnum.境外付款总量.getName())));

        ReportData reportData = new ReportData();
        reportData.setX(xList);
        reportData.setLeftY(leftYData);
        reportData.setRightY(rightYData);
        reportData.setTitle("业务量");
        DoubleY.create(reportData);
    }

    private static Map<String, BigDecimal[]> getData() {
        Map<String, BigDecimal[]> map = Maps.newHashMap();
        String data = WeekData.data;
        for (IndicatorEnum indicatorEnum : IndicatorEnum.values()) {
            BigDecimal[] values = getIndiValueArray(data, indicatorEnum);
            map.put(indicatorEnum.getName(), values);
        }
        return map;
    }

    private static BigDecimal[] getIndiValueArray(String data, IndicatorEnum indicatorEnum) {
        BigDecimal[] arr = new BigDecimal[2];
        String reg = indicatorEnum.getRegExp();
        //String reg = "(settleResultForShowMapper\\.)(.*?)(\\))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(data);
        int i = 0;
        while (matcher.find()) {
            System.out.println(indicatorEnum.getName() + "=" + matcher.group(indicatorEnum.getRegIndex()));
            arr[i] = new BigDecimal(matcher.group(indicatorEnum.getRegIndex()));
            i++;
        }
        return arr;
    }
}
