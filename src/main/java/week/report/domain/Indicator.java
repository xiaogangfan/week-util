package week.report.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode
public class Indicator {
    /**
     * 业务-体量
     */
    public static final String REPORT_CATE1_BUSI_NUM="REPORT_CATE1_BUSI_NUM";
    /**
     * 业务-效率
     */
    public static final String REPORT_CATE1_BUSI_RATE="REPORT_CATE1_BUSI_RATE";
    /**
     * 系统-可用性
     */
    public static final String REPORT_CATE1_SYST_AVAILABLE="REPORT_CATE1_SYST_AVAILABLE";

    public static Map<String, List<String>> cate2Indi = Maps.newHashMap();

    static {
        List<String> busiNumList = Lists.newArrayList();
        busiNumList.add("余额调节表对账总量(5天)");
        busiNumList.add("资金帐写入总量(5天)");
        busiNumList.add("自动对账率（5天）");
        busiNumList.add("人工对账周期(5天）");
        cate2Indi.put(REPORT_CATE1_BUSI_NUM,busiNumList);

        List<String> busiRateList = Lists.newArrayList();
        busiRateList.add("余额调节表对账TPS");
        busiRateList.add("资金帐写入最高TPS");
        cate2Indi.put(REPORT_CATE1_BUSI_RATE,busiNumList);
        List<String> sysAvaiableList = Lists.newArrayList();
        sysAvaiableList.add("余额调节表对账TPS");
        sysAvaiableList.add("资金帐写入最高TPS");
        cate2Indi.put(REPORT_CATE1_BUSI_NUM,busiNumList);

    }
}
