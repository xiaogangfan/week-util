package week.report.domain;

import lombok.Data;
import week.report.infras.Serie;

import java.util.List;

@Data
public class ReportData {

    private String title;
    private String xTitle;
    private String yTitle;
    private String yLeftTitle;
    private String yRightTitle;
    private List<String> x;
    private List<Serie> leftY;
    private List<Serie> y;
    private List<Serie> rightY;

    public List<String> getX() {
        return x;
    }

    public void setX(List<String> x) {
        this.x = x;
    }

    public List<Serie> getLeftY() {
        return leftY;
    }

    public void setLeftY(List<Serie> leftY) {
        this.leftY = leftY;
    }

    public List<Serie> getRightY() {
        return rightY;
    }

    public void setRightY(List<Serie> rightY) {
        this.rightY = rightY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Serie> getY() {
        return y;
    }

    public void setY(List<Serie> y) {
        this.y = y;
    }

    public String getxTitle() {
        return xTitle;
    }

    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
    }

    public String getyTitle() {
        return yTitle;
    }

    public void setyTitle(String yTitle) {
        this.yTitle = yTitle;
    }

    public String getyLeftTitle() {
        return yLeftTitle;
    }

    public void setyLeftTitle(String yLeftTitle) {
        this.yLeftTitle = yLeftTitle;
    }

    public String getyRightTitle() {
        return yRightTitle;
    }

    public void setyRightTitle(String yRightTitle) {
        this.yRightTitle = yRightTitle;
    }
}
