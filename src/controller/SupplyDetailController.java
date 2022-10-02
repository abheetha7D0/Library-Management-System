package controller;

import db.DBConnection;
import model.BookSupply;
import model.BookSupplyDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplyDetailController {
    public int getMonthlyCost(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select SupplyCost from `book supply` WHERE SupplyDate LIKE '%"+date+"%'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getAnnuallyCost(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select SupplyCost from `book supply` WHERE SupplyDate LIKE '%"+date+"%'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getDayCost(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select SupplyCost from `book supply` WHERE SupplyDate ='"+date+"' ").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public ArrayList<BookSupplyDetail> getSupplyDetail(String id ) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(" Select b.name, bsd.SupplyQty, bsd.price from `Book Supply` bs Join `book supply Detail` bsd Join Book b ON bs.SupplyId = bsd.SupplyId  and bsd.BookId=b.BookId where bs.SupplyId='"+id+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookSupplyDetail> Books = new ArrayList<>();
        while (rst.next()) {
            Books.add(new BookSupplyDetail(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getDouble(3)
            ));
        }
        return Books;
    }
    public int getDonatedBooks() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("Select bsd.SupplyQty from `Book Supply` bs Join `book supply Detail` bsd JOIN supplier s Join Book b ON bs.SupplyId = bsd.SupplyId and bs.SupplierId=s.SupplierId and bsd.BookId=b.BookId where s.supType='Donator'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }

    public int getSupplierSupplyBooks() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("Select bsd.SupplyQty from `Book Supply` bs Join `book supply Detail` bsd JOIN supplier s Join Book b ON bs.SupplyId = bsd.SupplyId and bs.SupplierId=s.SupplierId and bsd.BookId=b.BookId where s.supType='Supplier'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public static List<BookSupply> searchSupplierId(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `book supply` WHERE SupplierId LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<BookSupply> readers = new ArrayList<>();
        while (resultSet.next()){
            readers.add(new BookSupply(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return readers;
    }

}
