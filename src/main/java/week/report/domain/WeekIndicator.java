package week.report.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class WeekIndicator {
    /**
     * 指标名称
     */
    private String indiName;
    /**
     * 指标值
     */
    private Double[] indiValueArr;

}
