package view.tm;

public class AttendanceTm {
    private String attendanceId;
    private String readerId;
    private String date;
    private String inTime;
    private String outTime;

    public AttendanceTm() {
    }

    public AttendanceTm(String attendanceId, String readerId, String date, String inTime, String outTime) {
        this.setAttendanceId(attendanceId);
        this.setReaderId(readerId);
        this.setDate(date);
        this.setInTime(inTime);
        this.setOutTime(outTime);
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
}
