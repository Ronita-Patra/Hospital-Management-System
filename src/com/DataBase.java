package src.com;
import java.sql.*;

public class DataBase {
    private Connection con=null;
    private String message;
    private Statement statement=null;
    // constructor
    DataBase(String url,String user, String pass    ){
        try{
            this.con = DriverManager.getConnection(url,user,pass);
            this.statement = con.createStatement();
            this.message = "Success connection"; 
        }
        catch(Exception e){
            this.message = "Failure Connection";
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        // String url = "jdbc:mysql://localhost:3306/GoodHospital";
        // String user = "GoodUser";
        // String pass = "GoodPass@123";
        DataBase d = new DataBase("jdbc:mysql://localhost:3306/GoodHospital", "GoodUser", "GoodPass@123");
        System.out.println(d.getMessage());
        d.init();
        try{
            Patient p = new Patient("222312312345");
            p.setAge(20);
            p.setName("Satyapraksh");
            p.setNumber("+917781834675");
            p.setSex('M');
            d.addData(p);
        }
        catch(Exception e   ){
            System.out.println(e);
        }
        return;
     }
     // methods
    String getMessage(){
        return this.message;
    }

    //creating required Table and all
    void init(){
        // create patient table
        String queryPatientTable = "CREATE TABLE patient(aadhaar VARCHAR(12) PRIMARY KEY,name VARCHAR(60) NOT NULL, number VARCHAR(13) NOT NULL,age INT NOT NULL, sex CHAR NOT NULL);";
        try{
            statement.executeUpdate(queryPatientTable);
            System.out.println("patient table INITIATED!!");
        }
        catch(Exception e   ){
            if(e.toString().contains("already exists")){
                System.out.println("patient table INITIATED!!");
            }
            else{
                System.out.println(e    );
            }
        }
    }
    // simply adds patient data, without checking wether the name or other variables are set or not. this will be managed by Manager.
    // handle duplicates
    void addData(Patient p){
        String queryAddData = "INSERT INTO patient (aadhaar, name, number, age, sex) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement pStatement = con.prepareStatement(queryAddData);
            pStatement.setString(1,p.getAdhaarNum());
            pStatement.setString(2, p.getName());
            pStatement.setString(3, p.getPhoneNum());   
            pStatement.setInt(4, p.getAge());
            pStatement.setString(5, String.valueOf(p.getSex()));

            pStatement.executeUpdate();

            System.out.println("yes");
        }
        catch(Exception e   ){
            if(e.toString().contains("Duplicate entry")){
                this.message  = "Duplicate entry";
            }
            System.out.println(e    );
        }
    }
}
