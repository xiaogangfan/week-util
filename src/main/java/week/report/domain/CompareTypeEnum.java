package week.report.domain;

public enum CompareTypeEnum {
    Rate("Rate"),
    Num("Num");


    private String name;


    CompareTypeEnum(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
//        System.out.println(IndicatorEnum.人工对账周期.code);
    }


}
