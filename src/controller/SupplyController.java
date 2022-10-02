package controller;

import db.DBConnection;
import model.BookSupply;
import model.BookSupplyDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplyController {
    public String getSupplyId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT SupplyId FROM `Book Supply` ORDER BY SupplyId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "S-00"+tempId;
            }else if(tempId<=99){
                return "S-0"+tempId;
            }else{
                return "S-"+tempId;
            }

        }else{
            return "S-001";
        }
    }

    public boolean addToLibrary(BookSupply bookSupply) {
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO `Book Supply` VALUES(?,?,?,?)");


            stm.setObject(1, bookSupply.getSupplyId());
            stm.setObject(2, bookSupply.getSupplyDate());
            stm.setObject(3, bookSupply.getSupplierId());
            stm.setObject(4, bookSupply.getSupplyCost());

            if (stm.executeUpdate() > 0){
                if (saveBookSupplyDetail(bookSupply.getSupplyId(), bookSupply.getBooks())){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }
    private boolean saveBookSupplyDetail(String supplyId, ArrayList<BookSupplyDetail> items) throws SQLException, ClassNotFoundException {
        for (BookSupplyDetail temp : items
        ) {
            PreparedStatement stm = DBConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO `Book Supply detail` VALUES(?,?,?,?)");
            stm.setObject(1, supplyId);
            stm.setObject(2, temp.getBook());
            stm.setObject(3, temp.getSupplyQty());
            stm.setObject(4, temp.getPrice());
            if (stm.executeUpdate() > 0) {

                if (updateCopies(temp.getBook(), temp.getSupplyQty())){

                }else{
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }
    private boolean updateCopies(String BookId, int copies) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Book SET Copies=(Copies+" + copies
                                + ") WHERE BookId='" + BookId + "'");
        return stm.executeUpdate()>0;
    }

    public ArrayList<BookSupply> getAllSupplies() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Book Supply`");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookSupply> Books = new ArrayList<>();
        while (rst.next()) {
            Books.add(new BookSupply(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return Books;
    }



}
