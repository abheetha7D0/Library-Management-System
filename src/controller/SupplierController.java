package controller;

import db.DBConnection;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController {
    public String getSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT SupplierId FROM `Supplier` ORDER BY SupplierId DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "Sp-00"+tempId;
            }else if(tempId<=99){
                return "Sp-0"+tempId;
            }else{
                return "Sp-"+tempId;
            }

        }else{
            return "Sp-001";
        }
    }
    public List<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Supplier").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public boolean saveSuppler(Supplier supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Supplier VALUES(?,?,?,?,?,?)");
        stm.setObject(1, supplier.getSupplierId());
        stm.setObject(2, supplier.getSupName());
        stm.setObject(3, supplier.getSupType());
        stm.setObject(4, supplier.getTpNo());
        stm.setObject(5, supplier.getEmail());
        stm.setObject(6, supplier.getAddress());
        return stm.executeUpdate() > 0;
    }

    public ArrayList<Supplier> getAllSupplier() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier");
        ResultSet rst = stm.executeQuery();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (rst.next()) {
            suppliers.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return suppliers;
    }

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM Supplier WHERE SupplierId = ?";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }

    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Supplier SET SupName=? ,supType=?,TpNo=?,Address=? WHERE  SupplierId=?");

        stm.setObject(1, supplier.getSupName());
        stm.setObject(2, supplier.getSupType());
        stm.setObject(3, supplier.getTpNo());
        stm.setObject(4, supplier.getAddress());
        stm.setObject(5, supplier.getSupplierId());
        return stm.executeUpdate() > 0;
    }

    public static List<Supplier> searchSupplier(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE supName LIKE '%" + value + "%'");
        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> suppliers = new ArrayList<>();
        while (resultSet.next()) {
            suppliers.add(new Supplier(
                    resultSet.getString("SupplierId"),
                    resultSet.getString("SupName"),
                    resultSet.getString("supType"),
                    resultSet.getString("TpNo"),
                    resultSet.getString("Email"),
                    resultSet.getString("Address")
            ));

        }
        return suppliers;
    }

    public Supplier getSupplier(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE SupplierId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }


}
