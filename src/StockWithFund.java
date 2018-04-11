/**
 * Created by Administrator on 2018/4/11.
 */
public class StockWithFund {

    private String pair;
    private int index;
    private double w;

    public StockWithFund(String pair, int index, double w) {
        this.pair = pair;
        this.index = index;
        this.w = w;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }
}
