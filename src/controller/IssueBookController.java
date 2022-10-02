package controller;

import db.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IssueBookController {

    public String getBookIssueId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT IssueId FROM `Book Issue` ORDER BY IssueId DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()){

            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "BI-00"+tempId;
            }else if(tempId.get() <=99){
                return "BI-0"+tempId;
            }else{
                return "BI-"+tempId;
            }

        }else{
            return "BI-001";
        }

    }
    public List<String> getAllIssueBookIds(String readerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select  bid.BookId From `book issue` As bi  INNER JOIN `book issue detail` bid  ON bi.IssueId = bid.IssueId where bi.ReaderId='"+readerId+"';").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }

        return ids;
    }
    public List<String> getAllIssueReadersIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `book issue` group by ReaderId").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(2)
            );
        }
        return ids;
    }

    public ArrayList<IssueReaderAndDetail> getAllIssueReaderAndDetail() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement
                ("select bi.ReaderId, bid.BookId, b.Name,bi.DueDate From " +
                        "`book issue` As bi INNER JOIN `book issue detail` bid INNER JOIN `Book` " +
                        "b ON bi.IssueId = bid.IssueId and bid.BookId = b.BookId  Order by bi.ReaderId");
        ResultSet rst = stm.executeQuery();
        ArrayList<IssueReaderAndDetail> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new IssueReaderAndDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return items;
    }
    public ArrayList<ReaderIssueStatus> getAllReturnAndDetail() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement
                ("select bi.ReaderId, r.name,bi.DueDate , b.Name From " +
                        "`book issue` As bi INNER JOIN `book issue detail` bid INNER JOIN `Book` " +
                        "b ON bi.IssueId = bid.IssueId and bid.BookId = b.BookId  Order by bi.ReaderId");
        ResultSet rst = stm.executeQuery();
        ArrayList<ReaderIssueStatus> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new ReaderIssueStatus(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return items;
    }
    public static List<IssueReaderAndDetail> searchBook(String name) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("select bi.ReaderId, bid.BookId, b.Name,bi.DueDate From  + " +
                "`book issue` As bi INNER JOIN `book issue detail` bid INNER JOIN `Book`  +" +
                " b ON bi.IssueId = bid.IssueId and bid.BookId = b.BookId  like b.Name='%"+name+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<IssueReaderAndDetail> readers = new ArrayList<>();
        while (resultSet.next()){
            readers.add(new IssueReaderAndDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return readers;
    }
    public boolean IssueBook(BookIssue bookIssue) {
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.prepareStatement("INSERT INTO `Book Issue` VALUES(?,?,?,?,?)");


            stm.setObject(1, bookIssue.getIssueId());
            stm.setObject(2, bookIssue.getReaderId());
            stm.setObject(3, bookIssue.getIssueDate());
            stm.setObject(4, bookIssue.getDueDate());
            stm.setObject(5, bookIssue.getBookCount());

            if (stm.executeUpdate() > 0){
                if (saveBookIssueDetail(bookIssue.getIssueId(), bookIssue.getBooks())){
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

    private boolean saveBookIssueDetail(String issueId, ArrayList<BookIssueDetail> items) throws SQLException, ClassNotFoundException {
        for (BookIssueDetail temp : items
        ) {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Book Issue detail` VALUES(?,?,?)");
            stm.setObject(1, issueId);
            stm.setObject(2, temp.getBookId());
            stm.setObject(3, temp.getCopies());
            if (stm.executeUpdate() > 0) {

                if (updateCopies(temp.getBookId())){

                }else{
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }
    private boolean updateCopies(String BookId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Book SET Copies=(Copies-" + 1
                + ") WHERE BookId='" + BookId + "'");
        return stm.executeUpdate()>0;
    }

    public ArrayList<Book> searchBooks(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Book where name='"+value+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Book> books = new ArrayList<>();
        while (rst.next()) {
            books.add(new Book(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getInt(6)
            ));
        }
        return books;
    }
}
