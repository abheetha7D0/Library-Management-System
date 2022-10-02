package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Attendance;
import model.Reader;
import view.tm.AttendanceTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReaderAttendanceFormController implements Initializable {
    public JFXTextField txtName;
    public JFXTextField txtAge;
    public JFXTextField txtTp_No;
    public JFXTextField txtAddress;
    public TableColumn colReaderId;
    public TableView<AttendanceTm> tblAttendance;
    public TableColumn colAttendanceId;
    public TableColumn colDate;
    public TableColumn colInTime;
    public TableColumn colOutTime;
    public JFXComboBox<String> cmbReaderId;
    public Label lblAttendanceId;
    public JFXButton btnIn;
    public JFXButton btnOut;
    public JFXTextField txtSearch;
    String date = null;
    String time = null;
    String attendanceId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("AttendanceId"));
        colReaderId.setCellValueFactory(new PropertyValueFactory<>("ReaderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("InTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("OutTime"));

        try {
            loadReaderIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            setAttendanceValueToTable(new AttendanceController().getAllAttendance());

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        setAttendanceId();

        cmbReaderId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setReaderData(newValue);
                btnIn.setDisable(false);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        tblAttendance.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbReaderId.setValue(newValue.getReaderId());
                attendanceId = newValue.getAttendanceId();
                try {
                    setReaderData(newValue.getAttendanceId());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                btnIn.setDisable(true);

                if (newValue.getOutTime().equals("Pending")) {
                    btnOut.setDisable(false);
                } else {
                    btnOut.setDisable(true);
                }
            }
        });
        btnIn.setDisable(true);
        btnOut.setDisable(true);
    }

    private void setAttendanceId() {
        try {
            lblAttendanceId.setText(new AttendanceController().getAttendanceId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inTimeOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Attendance attendance = new Attendance(lblAttendanceId.getText(), cmbReaderId.getValue(), date, time, "Pending");
        ArrayList<Attendance> attendances = new AttendanceController().getAllAttendance();
        for (Attendance s : attendances) {
            if (s.getReaderId().equals(cmbReaderId.getValue()) && s.getOutTime().equals("Pending")) {
                new Alert(Alert.AlertType.WARNING, "Already In..").show();

                return;
            }
        }
        if (new AttendanceController().saveAttendance(attendance)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setAttendanceValueToTable(new AttendanceController().getAllAttendance());
            setAttendanceId();
            cmbReaderId.setValue(null);
            txtTp_No.clear();
            txtAge.clear();
            txtAddress.clear();
            txtName.clear();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    private void loadReaderIds() throws SQLException, ClassNotFoundException {
        List<String> suppliers = new ReaderController().getAllReaderIds();
        cmbReaderId.getItems().addAll(suppliers);
    }

    private void setReaderData(String ReaderId) throws SQLException, ClassNotFoundException {
        Reader reader = new ReaderController().getReader(ReaderId);
        if (reader == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtName.setText(reader.getName());
            txtAge.setText(String.valueOf(reader.getAge()));
            txtAddress.setText(reader.getAddress());
            txtTp_No.setText(reader.getTp_No());
        }
    }

    public void outTimeOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        if (new AttendanceController().updateOutTime(attendanceId, time)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setAttendanceValueToTable(new AttendanceController().getAllAttendance());
            btnIn.setDisable(false);
            btnOut.setDisable(true);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }


    private void setAttendanceValueToTable(ArrayList<Attendance> attendances) {
        ObservableList<AttendanceTm> obList = FXCollections.observableArrayList();
        attendances.forEach(e -> {
            obList.add(
                    new AttendanceTm(e.getAttendanceId(), e.getReaderId(), e.getDate(), e.getInTime(), e.getOutTime()));
        });
        tblAttendance.setItems(obList);
    }


    public void searchOnTextFields_Key_Released(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Reader> readers = ReaderController.searchReader(txtSearch.getText());

        for (Reader i : readers
        ) {
            cmbReaderId.setValue(i.getReaderId());
            txtName.setText(i.getName());
            txtAge.setText(String.valueOf(i.getAge()));
            txtAddress.setText(i.getAddress());
            System.out.println(i.getName());
        }
    }
}
