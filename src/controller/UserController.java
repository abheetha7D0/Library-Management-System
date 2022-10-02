package controller;

import db.DBConnection;
import model.Reader;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    public String getUserId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT Id FROM User ORDER BY Id DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "U-00"+tempId;
            }else if(tempId<=99){
                return "U-0"+tempId;
            }else{
                return "U-"+tempId;
            }

        }else{
            return "U-001";
        }
    }

    public User getUser(String userName,String password) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM User WHERE UserName=? and Password=md5(?)");
        stm.setObject(1, userName);
        stm.setObject(2, password);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }

    public ArrayList<User> getAllUser() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM User");
        ResultSet rst = stm.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return users;
    }

    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO User VALUES(?,?,md5(?),?,?)");
        stm.setObject(1, user.getUserId());
        stm.setObject(2, user.getUserName());
        stm.setObject(3, user.getPassword());
        stm.setObject(4, user.getRole());
        stm.setObject(5, user.getTpNo());
        stm.setObject(6, user.getEmail());
        stm.setObject(7, user.getAddress());
        return stm.executeUpdate() > 0;
    }
    public static ArrayList<User> searchUser(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM User where UserName like'%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return users;
    }
}
