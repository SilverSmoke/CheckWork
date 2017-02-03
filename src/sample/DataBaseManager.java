package sample;
import java.sql.*;

/**
 * Created by belikov.a on 17.01.2017.
 */
public class DataBaseManager {

    private static String urlDB = "jdbc:mysql://localhost:3360/checkdatabase1?useSSL=false";
    private static String user = "root";
    private static String pass = "SupRoot";

    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet result;

    DataBaseManager(String urlDB, String user, String pass){
        this.urlDB = urlDB;
        this.user = user;
        this.pass = pass;
    }
    DataBaseManager(){

    }

    public static ResultSet getResult(String query){
        try{

            connect = DriverManager.getConnection(urlDB, user, pass);

            statement = connect.createStatement();

            result = statement.executeQuery(query);

        }catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }finally {
            return result;
        }
    }

    public void updateDB(String query){
        try{

            connect = DriverManager.getConnection(urlDB, user, pass);

            statement = connect.createStatement();

            statement.executeUpdate(query);

        }catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        }finally {
            try{
                connect.close();
                statement.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
