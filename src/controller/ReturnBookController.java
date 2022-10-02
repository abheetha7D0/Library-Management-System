package controller;

import db.DBConnection;
import model.BookReturn;
import model.BookReturnDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReturnBookController {
    public String getBookReturnId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT ReturnId FROM `Book return` ORDER BY ReturnId DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()){

            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "BR-00"+tempId;
            }else if(tempId.get() <=99){
                return "BR-0"+tempId;
            }else{
                return "BR-"+tempId;
            }

        }else{
            return "BR-001";
        }
    }

    public boolean returnBook(BookReturn bookReturn) {
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO `Book Return` VALUES(?,?,?,?)");


            stm.setObject(1, bookReturn.getReturnId());
            stm.setObject(2, bookReturn.getReaderId());
            stm.setObject(3, bookReturn.getReturnDate());
            stm.setObject(4, bookReturn.getBookCount());

            if (stm.executeUpdate() > 0){
                if (saveBookReturnDetail(bookReturn.getReturnId(), bookReturn.getBooks())){
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
    private boolean saveBookReturnDetail(String returnId, ArrayList<BookReturnDetail> items) throws SQLException, ClassNotFoundException {

        for (BookReturnDetail temp : items
        ) {
            PreparedStatement stm = DBConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO `Book Return detail` VALUES(?,?,?,?,?)");
            stm.setObject(1, returnId);
            stm.setObject(2, temp.getName());
            stm.setObject(3, temp.getCopies());
            stm.setObject(4,temp.getReport());
            stm.setObject(5,temp.getFinePrice());
            if (stm.executeUpdate() > 0) {

                if (updateCopies(temp.getName(), 1)){
                    return true;
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

}
