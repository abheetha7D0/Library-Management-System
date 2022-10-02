package controller;

import db.DBConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingController {
    public boolean updatePassword(String userId, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE User SET Password=md5(?) WHERE Id=(?)");
        stm.setObject(1, password);
        stm.setObject(2, userId);
        return stm.executeUpdate()>0;
    }

    public User getUser(String userId, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM User WHERE id=? and Password=md5(?)");
        stm.setObject(1, userId);
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
}
