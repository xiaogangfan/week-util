package week.report.domain;

public enum IndicatorEnum {
    余额调节表对账总量("余额调节表对账总量", "余额调节表对账总量\\(5天\\):(.*?)\\n", 1),
    资金帐写入总量("资金帐写入总量", "资金帐写入总量\\(5天\\):(.*?)\\n", 1),
    境内付款总量("境内付款总量", "境内付款总量:(.*?)\\n", 1),
    境外付款总量("境外付款总量", "境外付款总量:(.*?)\\n", 1),
    自动对账率("自动对账率", "自动对账率（5天）：(.*?)%", 1),
    资金调拨自动签收率("资金调拨自动签收率", "资金集中管理，自动签收率：(.*?)%", 1),
    境内付款成功率("境内付款成功率", "境内银企付款成功率：(.*?)%", 1),
    境外付款成功率("境外付款成功率", "境外银企付款成功率：(.*?)%", 1),
    余额调节表对账TPS("余额调节表对账TPS", "余额调节表对账TPS:(.*?),TP99:(.*?)ms", 1),
    余额调节表对账TP99("余额调节表对账TP99", "余额调节表对账TPS:(.*?),TP99:(.*?)ms", 2),
    资金帐写入最高TPS("资金帐写入最高TPS", "资金帐写入最高TPS:(.*?),TP99:(.*?)ms", 1),
    资金帐写入最高TP99("资金帐写入最高TP99", "资金帐写入最高TPS:(.*?),TP99:(.*?)ms", 2);

    private String name;
    private String regExp;
    private Integer regIndex;

    IndicatorEnum(String name, String regExp, Integer regIndex) {
        this.name = name;
        this.regExp = regExp;
        this.regIndex = regIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegExp() {
        return regExp;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    public Integer getRegIndex() {
        return regIndex;
    }

    public void setRegIndex(Integer regIndex) {
        this.regIndex = regIndex;
    }

    public static void main(String[] args) {
//        System.out.println(IndicatorEnum.人工对账周期.code);
    }


}
