import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException {
        //初始化股票数据
        ArrayList<Stock> stocks = readXlsx();
        //初始化N的数据
        double[] N = readN();
        //初始化基金的数据
        ArrayList<Fund> funds = readFund();
        //初始化w的数据
        ArrayList<StockWithFund>[] pairs = readPairs(stocks);


        //遍历股票
        for (Stock stock:stocks){

            if (stock.getIsMentioned()==1) {
                System.out.print(stock.getId() + "," + stock.getName() + ",");
                //对于每一只股票计算36个季度的Z值
                for (int t = 0; t < 36; t++) {
                    double sum = 0;// 求和部分初始化
                    for (int i = 0; i < funds.size(); i++) {
                        String key = stock.getId() + "," + funds.get(i).getId();

                        int pos = isExit(pairs[t], key);
                        if (pos != -1)
                            sum += funds.get(i).getX().get(t) * pairs[t].get(pos).getW();
                    }
                    double result = 0;

                    if (stock.getM().get(t) != 0) {
                        result = (sum * N[t]) / stock.getM().get(t);
                    }
                    if (t != 35)
                        System.out.print(result + ",");
                    else
                        System.out.println(result);
                }
            }

        }

    }

    //查询某季度股票是否投资了相应的基金
    private static int isExit(ArrayList<StockWithFund> pair, String key) {
        int pos = 0;
        for (StockWithFund stockWithFund:pair){
            if (stockWithFund.getPair().equals(key)){
               return pos;
            }
            pos++;
        }
        return -1;
    }

    private static ArrayList<StockWithFund>[] readPairs(ArrayList<Stock> stocks) throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook("./Z/W.xlsx");
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        String cell;
        ArrayList<StockWithFund>[] data = new ArrayList[36];
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (i == 1608){
                break;
            }
            if (row == null) {
                break;
            }
            String pair = "," + row.getCell(0).toString();
            for (int index = 0; index < 36; index++) {
                if (i == (sheet.getFirstRowNum() + 1))
                    data[index] = new ArrayList<>();
                for (int j = 3 + 20*index; j < 3+20*(index+1); j+=2) {
                    cell = row.getCell(j).toString();
                    if (!cell.isEmpty() && !cell.equals("--")) {
                        String stockName = find(stocks, cell);
//                        System.out.println(i+", "+index+", "+j);
                        StockWithFund t = new StockWithFund(stockName + pair, index, Double.parseDouble(row.getCell(j+1).toString()));
                        data[index].add(t);
                    }
                }
            }
        }
        return data;
    }

    private static String find(ArrayList<Stock> stocks, String cell) {
        for (Stock stock:stocks){
            if (stock.getName().equals(cell)){
                if (stock.getIsMentioned()==0){
                    stock.setIsMentioned(1);
                }
                return stock.getId();
            }
        }
        return "NULL";
    }

    private static ArrayList<Fund> readFund() throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook("./Z/X.xlsx");
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        String cell;
        ArrayList<Fund> funds = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            Fund fund = new Fund(row.getCell(0).toString(), row.getCell(1).toString());
            for (int j = 2; j < row.getPhysicalNumberOfCells(); j++) {
                cell = row.getCell(j).toString();
                if (!cell.isEmpty() && !cell.equals("--")) {
                    fund.getX().add(Double.parseDouble(cell));
                } else {
                    fund.getX().add(0.0);
                }
            }
            funds.add(fund);
        }
        return funds;
    }

    /**
     * 读取xlsx文件
     *
     * @throws IOException
     */
    public static ArrayList<Stock> readXlsx() throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook("./Z/M.xlsx");
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        String cell;
        ArrayList<Stock> stocks = new ArrayList<>();
        for (int i = sheet.getFirstRowNum() + 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            Stock stock = new Stock(row.getCell(0).toString(), row.getCell(1).toString());
            for (int j = 2; j < row.getPhysicalNumberOfCells(); j++) {
                cell = row.getCell(j).toString();
                if (!cell.equals("--")) {
                    stock.getM().add(Double.parseDouble(cell));
                } else {
                    stock.getM().add(0.0);
                }
            }
            stocks.add(stock);
        }
        return stocks;
    }

    private static double[] readN() throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook("./Z/N.xlsx");
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        String cell;
        double[] N = new double[36];
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            Stock stock = new Stock(row.getCell(0).toString(), row.getCell(1).toString());
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                cell = row.getCell(j).toString();
                N[j] = (Double.parseDouble(cell));
            }
        }
        return N;
    }
}
