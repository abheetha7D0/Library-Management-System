package controller;

import db.DBConnection;
import model.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BookController {
    public String getBookId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT BookId FROM `Book` ORDER BY BookId DESC LIMIT 1"
        ).executeQuery();

        if (rst.next()){
            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "B-00"+tempId;
            }else if(tempId.get() <=99){
                return "B-0"+tempId;
            }else{
                return "B-"+tempId;
            }

        }else{
            return "B-001";
        }
    }
    public List<String> getAllBookIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Book").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    public boolean saveBook(Book book) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Book VALUES(?,?,?,?,?,?)");
        stm.setObject(1, book.getBookId());
        stm.setObject(2, book.getName());
        stm.setObject(3, book.getAuthorId());
        stm.setObject(4, book.getPublisherId());
        stm.setObject(5, book.getUnitPrice());
        stm.setObject(6, book.getCopies());
        return stm.executeUpdate() > 0;
    }
    public ArrayList<Book> getAllBook() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Book");
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
    public boolean deleteBook(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Book WHERE BookId = ?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }
    public boolean updateBook(Book book) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Book SET Name=? ,AuthorId=?,PublisherId=?,UnitPrice=?,Copies=? WHERE  BookId=?");

        stm.setObject(1, book.getName());
        stm.setObject(2, book.getAuthorId());
        stm.setObject(3, book.getPublisherId());
        stm.setObject(4, book.getUnitPrice());
        stm.setObject(5, book.getCopies());
        stm.setObject(6, book.getBookId());
        return stm.executeUpdate() > 0;
    }
    public static List<Book> searchBook(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm =DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Book WHERE name LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<Book> books = new ArrayList<>();
        while (resultSet.next()){
            books.add(new Book(
                    resultSet.getString("BookId"),
                    resultSet.getString("Name"),
                    resultSet.getString("AuthorId"),
                    resultSet.getString("PublisherId"),
                    resultSet.getDouble("UnitPrice"),
                    resultSet.getInt("Copies")
            ));

        }
        return books;
    }
    public Book getBook(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Book WHERE BookId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Book(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getInt(6)
            );

        } else {
            return null;
        }
    }
}
