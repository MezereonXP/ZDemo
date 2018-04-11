import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */
public class Fund {
    private String id;
    private String name;
    private ArrayList<Double> X;

    public Fund(String id, String name) {
        this.id = id;
        this.name = name;
        X = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getX() {
        return X;
    }

    public void setX(ArrayList<Double> x) {
        X = x;
    }
}
