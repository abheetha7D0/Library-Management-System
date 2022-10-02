package controller;

import db.DBConnection;
import model.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthorController {
    public String getAuthorId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT AuthorId FROM `Author` ORDER BY AuthorId DESC LIMIT 1"
        ).executeQuery();

        if (rst.next()){
            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "A-00"+tempId;
            }else if(tempId.get() <=99){
                return "A-0"+tempId;
            }else{
                return "A-"+tempId;
            }

        }else{
            return "A-001";
        }
    }
    public List<String> getAllAuthorIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Author").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    public boolean saveAuthor(Author author) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Author VALUES(?,?,?,?)");
        stm.setObject(1, author.getAuthorId());
        stm.setObject(2, author.getName());
        stm.setObject(3, author.getTpNo());
        stm.setObject(4, author.getEmail());
        return stm.executeUpdate() > 0;
    }
    public ArrayList<Author> getAllAuthor() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Author");
        ResultSet rst = stm.executeQuery();
        ArrayList<Author> authors = new ArrayList<>();
        while (rst.next()) {
            authors.add(new Author(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return authors;
    }
    public boolean deleteAuthor(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Author WHERE AuthorId = ?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }
    public boolean updateAuthor(Author author) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Author SET Name=? ,TpNo=?,Email=? WHERE  AuthorId=?");

        stm.setObject(1, author.getName());
        stm.setObject(2, author.getTpNo());
        stm.setObject(3, author.getEmail());
        stm.setObject(4, author.getAuthorId());
        return stm.executeUpdate() > 0;
    }

    public static List<Author> searchAuthor(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm =DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Author WHERE name LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<Author> authors = new ArrayList<>();
        while (resultSet.next()){
            authors.add(new Author(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));

        }
        return authors;
    }
}
