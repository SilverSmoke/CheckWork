package sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by belikov.a on 12.01.2017.
 */
public class CheckNode {

    private String market;
    private String section;
    private String product;
    private double price;
    private int number;
    private LocalDateTime purchaseDate;
    private int transaction;

    @Override
    public String toString(){
        String string;
        string =  market+"::"+section+"::"+product+"::"+price+"::"+purchaseDate+"::"+transaction;
        return string;
    }

    public CheckNode(int transaction){
        setTransaction(transaction);
        if(transaction == 0) {
            System.out.println("Новый элемент");
        }else if(transaction == -1){
            System.out.println("Прибыль");
        }else{
            System.out.println("Получить из базы");
            extractOfBase(transaction);
        }
    }
    
    public void setMarket(String market){
        this.market = market;
    }

    public void setSection(String section){
        this.section = section;
    }

    public void setProduct(String product){
        this.product = product;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public void setNumber(int number){ this.number = number; }

    public void setPurchaseDate(LocalDateTime purchaseDate){
        this.purchaseDate = purchaseDate;
    }

    public void setTransaction(int transaction){
        this.transaction = transaction;
    }

    public String getMarket(){
        return this.market;
    }

    public String getSection(){
        return this.section;
    }

    public String getProduct(){
        return this.product;
    }

    public Double getPrice(){
        return this.price;
    }

    public LocalDateTime getPurchaseDate(){
        return this.purchaseDate;
    }

    public int getTransaction(){
        return this.transaction;
    }

    public void addInBase(){
        //добавление позиции в базу
        DataBaseManager managerDB = new DataBaseManager();

        if(this.transaction == 0) {
            System.out.println("INSERT");
            for (int i = 0; i < this.number; i++) {
                managerDB.updateDB("INSERT INTO `transaction` (`id`, `market`, `section`, `product`, `price`, `time`) VALUES (NULL, '" + this.market + "', '" + this.section + "', '" + this.product + "', '" + this.price + "', UNIX_TIMESTAMP());");
            }
        }else if(this.transaction > 0){
            managerDB.updateDB("UPDATE `transaction` SET  `market` = '" + this.market + "', `section`='" + this.section + "'," +
                    " `product`='" + this.product + "', `price`='" + this.price + "'," +
                    " `time`= '" + this.purchaseDate.toEpochSecond(ZoneOffset.ofHours(-6)) + "' WHERE `id` = '" + this.transaction + "';");

        }
        System.out.println(this.purchaseDate.toInstant(ZoneOffset.ofHours(-6)).getEpochSecond());
    }

    private void extractOfBase(int transaction){
        String query = "SELECT * FROM `transaction` WHERE `id` = " + transaction;

        ResultSet resultSet = DataBaseManager.getResult(query);

        System.out.println(resultSet);

        try {
            while(resultSet.next()) {
                market = resultSet.getString(2);
                section = resultSet.getString(3);
                product = resultSet.getString(4);
                price = Double.parseDouble(resultSet.getString(5));
                purchaseDate = LocalDateTime.ofEpochSecond(Long.parseLong(resultSet.getString(6)), 0, ZoneOffset.ofHours(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
