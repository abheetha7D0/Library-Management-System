package controller;

import db.DBConnection;
import model.Attendance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class AttendanceController {
    public String getAttendanceId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT AttendanceId FROM `Attendance` ORDER BY AttendanceId DESC LIMIT 1"
        ).executeQuery();

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("MM:dd");
        String Date =f.format(date);

        if (rst.next()){

            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(rst.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "A"+Date+"-00"+tempId;
            }else if(tempId.get() <=99){
                return "A"+Date+"-0"+tempId;
            }else{
                return "A"+Date+"-"+tempId;
            }

        }else{
            return "A"+Date+"-001";
        }
    }
    public boolean saveAttendance(Attendance attendance) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Attendance VALUES(?,?,?,?,?)");
        stm.setObject(1, attendance.getAttendanceId());
        stm.setObject(2, attendance.getReaderId());
        stm.setObject(3, attendance.getDate());
        stm.setObject(4, attendance.getInTime());
        stm.setObject(5, attendance.getOutTime());
        return stm.executeUpdate() > 0;
    }
    public boolean updateOutTime(String attendanceId, String outTime) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Attendance SET OutTime=('" + outTime
                + "') WHERE AttendanceId='" + attendanceId + "'");
        return stm.executeUpdate()>0;
    }
    public ArrayList<Attendance> getAllAttendance() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Attendance");
        ResultSet rst = stm.executeQuery();
        ArrayList<Attendance> attendances = new ArrayList<>();
        while (rst.next()) {
            attendances.add(new Attendance(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return attendances;
    }
}
