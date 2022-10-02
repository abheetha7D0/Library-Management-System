package controller;

import db.DBConnection;
import model.Book;
import model.Publisher;
import model.Reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PublisherController {
    public String getPublisherId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT PublisherId FROM `Publisher` ORDER BY PublisherId DESC LIMIT 1"
        ).executeQuery();

        if (rst.next()){
            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "P-00"+tempId;
            }else if(tempId.get() <=99){
                return "P-0"+tempId;
            }else{
                return "P-"+tempId;
            }

        }else{
            return "P-001";
        }
    }
    public List<String> getAllPublisherIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Publisher").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
    public boolean savePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Publisher VALUES(?,?,?,?,?)");
        stm.setObject(1, publisher.getPublisherId());
        stm.setObject(2, publisher.getName());
        stm.setObject(3, publisher.getTpNo());
        stm.setObject(4, publisher.getEmail());
        stm.setObject(5, publisher.getAddress());
        return stm.executeUpdate() > 0;
    }
    public ArrayList<Publisher> getAllPublisher() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Publisher");
        ResultSet rst = stm.executeQuery();
        ArrayList<Publisher> publishers = new ArrayList<>();
        while (rst.next()) {
            publishers.add(new Publisher(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return publishers;
    }
    public boolean deletePublisher(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Publisher WHERE PublisherId = ?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }
    public boolean updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Publisher SET Name=? ,TpNo=?,Email=? WHERE  PublisherId=?");

        stm.setObject(1, publisher.getName());
        stm.setObject(2, publisher.getTpNo());
        stm.setObject(3, publisher.getEmail());
        stm.setObject(4, publisher.getPublisherId());
        return stm.executeUpdate() > 0;
    }
    public static List<Publisher> searchPublisher(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm =DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Publisher WHERE name LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<Publisher> publishers = new ArrayList<>();
        while (resultSet.next()){
            publishers.add(new Publisher(
                    resultSet.getString("PublisherId"),
                    resultSet.getString("Name"),
                    resultSet.getString("TpNo"),
                    resultSet.getString("Email"),
                    resultSet.getString("Address")
            ));

        }
        return publishers;
    }
}
