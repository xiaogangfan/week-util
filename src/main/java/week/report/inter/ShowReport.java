package week.report.inter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import week.report.domain.CompareTypeEnum;
import week.report.domain.IndicatorEnum;
import week.report.domain.ReportData;
import week.report.domain.WeekData;
import week.report.infras.CreatLineChart;
import week.report.infras.Serie;
import week.report.test.DoubleY;

import java.math.BigDecimal;
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
        List<String> xList = getList(originData);


        System.out.println("- 系统可用性");
        System.out.println(getItem(originData, IndicatorEnum.余额调节表对账TPS, CompareTypeEnum.Rate));
        System.out.println(getItem(originData, IndicatorEnum.资金帐写入最高TPS, CompareTypeEnum.Rate));
        System.out.println(getItem(originData, IndicatorEnum.余额调节表对账TP99, CompareTypeEnum.Rate));
        System.out.println(getItem(originData, IndicatorEnum.资金帐写入最高TP99, CompareTypeEnum.Rate));
        leftYData.add(new Serie(IndicatorEnum.余额调节表对账TPS.getName(), originData.get(IndicatorEnum.余额调节表对账TPS.getName())));
        leftYData.add(new Serie(IndicatorEnum.资金帐写入最高TPS.getName(), originData.get(IndicatorEnum.资金帐写入最高TPS.getName())));
        rightYData.add(new Serie(IndicatorEnum.余额调节表对账TP99.getName(), originData.get(IndicatorEnum.余额调节表对账TP99.getName())));
        rightYData.add(new Serie(IndicatorEnum.资金帐写入最高TP99.getName(), originData.get(IndicatorEnum.资金帐写入最高TP99.getName())));

        int weekNums = getWeeks(originData);
        reportData.setX(xList);
        reportData.setxTitle("最近" + weekNums + "周");
        reportData.setLeftY(leftYData);
        reportData.setRightY(rightYData);
        reportData.setyLeftTitle("TPS");
        reportData.setyRightTitle("TP99(ms)");
        reportData.setTitle("系统可用性");
        DoubleY.create(reportData);
    }

//    private static void getAvaiableChartDataTP99(List<String> categories, List<Serie> series, Map<String, BigDecimal[]> originData, CreatLineChart creatLineChart) {
//        categories.clear();
//        series.clear();
//        categories.add("last week");
//        categories.add("this week");
//        System.out.println("- 系统可用性");
//        System.out.println(getItem(originData, IndicatorEnum.余额调节表对账TP99));
//        System.out.println(getItem(originData, IndicatorEnum.资金帐写入最高TP99));
//        series.add(new Serie(IndicatorEnum.余额调节表对账TP99.getName(), originData.get(IndicatorEnum.余额调节表对账TP99.getName())));
//        series.add(new Serie(IndicatorEnum.资金帐写入最高TP99.getName(), originData.get(IndicatorEnum.资金帐写入最高TP99.getName())));
//
//        ReportData reportData = new ReportData();
//        reportData.setxTitle("时间（最近x周）");
//        reportData.setyTitle("TP99（ms）");
//        reportData.setTitle("系统可用性TP99");
//        reportData.setY(series);
//        reportData.setX(categories);
//        creatLineChart.createReport(reportData);
//    }

    private static void getRateChartData(Map<String, BigDecimal[]> originData) {
        CreatLineChart creatLineChart = new CreatLineChart();
        List<Serie> yList = Lists.newArrayList();
        int weekNums = getWeeks(originData);
        List<String> xList = getList(originData);


        System.out.println("- 业务效率(百分比)");
        System.out.println(getItem(originData, IndicatorEnum.自动对账率));
        System.out.println(getItem(originData, IndicatorEnum.资金调拨自动签收率));
        System.out.println(getItem(originData, IndicatorEnum.境内付款成功率));
        System.out.println(getItem(originData, IndicatorEnum.境外付款成功率));
        yList.add(new Serie(IndicatorEnum.自动对账率.getName(), originData.get(IndicatorEnum.自动对账率.getName())));
        yList.add(new Serie(IndicatorEnum.资金调拨自动签收率.getName(), originData.get(IndicatorEnum.资金调拨自动签收率.getName())));
        yList.add(new Serie(IndicatorEnum.境内付款成功率.getName(), originData.get(IndicatorEnum.境内付款成功率.getName())));
        yList.add(new Serie(IndicatorEnum.境外付款成功率.getName(), originData.get(IndicatorEnum.境外付款成功率.getName())));
        ReportData reportData = new ReportData();
        reportData.setxTitle("时间（最近" + weekNums + "周）");
        reportData.setyTitle("百分比(%)");
        reportData.setTitle("业务效率");
        reportData.setY(yList);
        reportData.setX(xList);
        creatLineChart.createReport(reportData);
    }

    private static int getWeeks(Map<String, BigDecimal[]> originData) {

        return originData.get(IndicatorEnum.境内付款成功率.getName()).length;
    }

    private static void getNumChartData(Map<String, BigDecimal[]> originData) {
        List<Serie> leftYData = Lists.newArrayList();
        List<Serie> rightYData = Lists.newArrayList();
        List<String> xList = getList(originData);

        int lastIndex = getLastIndex(originData);

        System.out.println("- 业务量");
        System.out.println(getItem(originData, IndicatorEnum.余额调节表对账总量, CompareTypeEnum.Rate));
        System.out.println(getItem(originData, IndicatorEnum.资金帐写入总量, CompareTypeEnum.Rate));
        System.out.println(getItem(originData, IndicatorEnum.境内付款总量, CompareTypeEnum.Rate));
        System.out.println(getItem(originData, IndicatorEnum.境外付款总量, CompareTypeEnum.Rate));
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

    private static List<String> getList(Map<String, BigDecimal[]> originData) {
        List<String> result = Lists.newArrayList();
        int length = originData.get(IndicatorEnum.余额调节表对账总量.getName()).length;
        for (int i = length -1; i >-1; i--) {
            result.add("过去第" + (i + 1) + "周");
        }
        return result;
    }

    private static String getItem(Map<String, BigDecimal[]> originData, IndicatorEnum indicatorEnum) {
        return getItem(originData, indicatorEnum, CompareTypeEnum.Num);
    }

    private static String getItem(Map<String, BigDecimal[]> originData, IndicatorEnum indicatorEnum, CompareTypeEnum compareTypeEnum) {
        int lastIndex = getLastIndex(originData);
        int last2Index = getLast2Index(originData);
        BigDecimal lastData = originData.get(indicatorEnum.getName())[lastIndex];
        BigDecimal last2Data = originData.get(indicatorEnum.getName())[last2Index];
        String mark = getDiff(last2Data, lastData, compareTypeEnum);
        return indicatorEnum.getName() + ":" + originData.get(indicatorEnum.getName())[lastIndex]
                + mark;
    }

    private static String getDiff(BigDecimal last2Data, BigDecimal lastData, CompareTypeEnum compareTypeEnum) {
        String diff = "";
        if (CompareTypeEnum.Num.equals(compareTypeEnum)) {
            diff = "" + lastData.subtract(last2Data).setScale(4, BigDecimal.ROUND_HALF_UP
            ).abs().doubleValue();
        } else if (CompareTypeEnum.Rate.equals(compareTypeEnum)) {
            diff = "" + new BigDecimal(100).multiply(lastData.subtract(last2Data).divide(lastData, 4, BigDecimal.ROUND_HALF_UP).setScale(4, BigDecimal.ROUND_HALF_UP
            )).abs().doubleValue() + "%";
        }
        String mark = "(环比上周" + getTrend(lastData, last2Data) + diff + ")";
        return mark;
    }

    private static String getTrend(BigDecimal lastData, BigDecimal last2Data) {
        if (lastData.doubleValue() == last2Data.doubleValue()) {
            return "持平";
        } else {
            return (lastData.doubleValue() > last2Data.doubleValue() ? "上升" : "下降");
        }
    }

    private static int getLastIndex(Map<String, BigDecimal[]> originData) {
        return originData.get(IndicatorEnum.余额调节表对账总量.getName()).length - 1;
    }

    private static int getLast2Index(Map<String, BigDecimal[]> originData) {
        return originData.get(IndicatorEnum.余额调节表对账总量.getName()).length - 2;
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
        BigDecimal[] arr = new BigDecimal[3];
        String reg = indicatorEnum.getRegExp();
        //String reg = "(settleResultForShowMapper\\.)(.*?)(\\))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(data);
        int i = 0;
        while (matcher.find()) {
//            System.out.println(indicatorEnum.getName() + "=" + matcher.group(indicatorEnum.getRegIndex()));
            arr[i] = new BigDecimal(matcher.group(indicatorEnum.getRegIndex()));
            i++;
        }
        return arr;
    }
}
