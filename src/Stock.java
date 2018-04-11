import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */
public class Stock {
    private String id;
    private String name;
    private ArrayList<Double> M;

    public Stock(String id, String name) {
        this.id = id;
        this.name = name;
        M = new ArrayList<>();
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

    public ArrayList<Double> getM() {
        return M;
    }

    public void setM(ArrayList<Double> m) {
        M = m;
    }
}
