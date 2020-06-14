import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndicatorEnumTest {


    public void testGetValue(){
        String target = "余额调节表对账总量(5天):173862832\n" +
                "资金帐写入总量(5天):232783541\n" +
                "自动对账率（5天）：99.99675230172464%\n" +
                "人工对账周期(5天）：1.2680521310320536分钟 公式：人工对账时间合计（5天分钟数）/5天人工对账笔数\n" +
                "\n" +
                "余额调节表对账TPS:6907,TP99:100ms\n" +
                "资金帐写入最高TPS:3928,TP99:18ms\n" +
                "\n" +
                "境内付款总量:152170\n" +
                "境外付款总量:1768\n" +
                "境内银企付款成功率：98.50%\n" +
                "境外银企付款成功率：99.13%\n" +
                "\n" +
                "资金集中管理，自动签收率：92.31%\n";
        String reg = "余额调节表对账总量\\(5天\\):(.*?)\\n";
        //String reg = "(settleResultForShowMapper\\.)(.*?)(\\))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(target);
        while (matcher.find()) {
            System.out.println("----");
            System.out.println(matcher.group(1));
        }

    }

    public static void main(String[] args) {
        new IndicatorEnumTest().testGetValue();
    }


}
