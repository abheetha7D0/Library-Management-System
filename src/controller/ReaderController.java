package controller;

import db.DBConnection;
import model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReaderController {
    public String getReaderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT ReaderId FROM `Reader` ORDER BY ReaderId DESC LIMIT 1"
        ).executeQuery();

        if (rst.next()){
            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "R-00"+tempId;
            }else if(tempId.get() <=99){
                return "R-0"+tempId;
            }else{
                return "R-"+tempId;
            }

        }else{
            return "R-001";
        }
    }
    public boolean saveReader(Reader reader) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Reader VALUES(?,?,?,?,?)");
        stm.setObject(1, reader.getReaderId());
        stm.setObject(2, reader.getName());
        stm.setObject(3, reader.getAge());
        stm.setObject(4, reader.getTp_No());
        stm.setObject(5, reader.getAddress());
        return stm.executeUpdate() > 0;
    }
    public ArrayList<Reader> getAllReader() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Reader");
        ResultSet rst = stm.executeQuery();
        ArrayList<Reader> readers = new ArrayList<>();
        while (rst.next()) {
            readers.add(new Reader(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return readers;
    }
    public boolean deleteReader(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM Reader WHERE ReaderId = ?";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }
    public boolean updateReader(Reader reader) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Reader SET Name=? ,Age=?,TpNo=?,Address=? WHERE  ReaderId=?");

        stm.setObject(1, reader.getName());
        stm.setObject(2, reader.getAge());
        stm.setObject(3, reader.getTp_No());
        stm.setObject(4, reader.getAddress());
        stm.setObject(5, reader.getReaderId());
        return stm.executeUpdate() > 0;
    }

    public static List<Reader> searchReader(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Reader WHERE name LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<Reader> readers = new ArrayList<>();
        while (resultSet.next()){
            readers.add(new Reader(
                    resultSet.getString("ReaderId"),
                    resultSet.getString("Name"),
                    resultSet.getInt("Age"),
                    resultSet.getString("TpNo"),
                    resultSet.getString("Address")

            ));
        }
        return readers;
    }

    public List<String> getAllReaderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Reader").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Reader getReader(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Reader WHERE ReaderId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Reader(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }
}
