/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.hospital.controller;

import az.hospital.data.DataManager;
import az.hospital.model.Connect;
import az.hospital.model.Doctors;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.management.Notification;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class FXMLEditDoctorsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage stage;
    @FXML
    private TextField adInput;
    @FXML
    private TextField soyadInput;
    @FXML
    private TextField ataAdiInput;
    @FXML
    private ComboBox departmentCombo;
    @FXML
    private DatePicker dobInput;
    @FXML
    private TextField elmiDereceInput;
    @FXML
    private TextField ixtisasInput;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button updateDoctorsbtn;

    private ObservableList<Connect> departmentComboData;
    private DataManager manager = new DataManager();
    @FXML
    private Button deleteDoctorsBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Doctors doctor;

    public void setDoctors(Doctors doctor) {
        this.doctor = doctor;
        adInput.setText(doctor.getAd());
        soyadInput.setText(doctor.getSoyad());
        ataAdiInput.setText(doctor.getAtaAdi());
        departmentCombo.setValue(new Connect(doctor.getDepartmentId(), doctor.getDepartmentName()));
        dobInput.setValue(convertDate(doctor.getDob()));
        elmiDereceInput.setText(doctor.getElmiDerece());
        ixtisasInput.setText(doctor.getIxtisas());

    }

    public LocalDate convertDate(Date date) {
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //updateDoctors();

        try {

            loadDepartmentCombolist();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateDoctors(ActionEvent event) throws SQLException {

        String ad = adInput.getText();
        String soyad = soyadInput.getText();
        String ataadi = ataAdiInput.getText();
        Date dob = Date.from(dobInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String elmiderece = elmiDereceInput.getText();
        String ixtisas = ixtisasInput.getText();
        Connect connect = (Connect) departmentCombo.getSelectionModel().getSelectedItem();

        Doctors updatedoctor = new Doctors();
        updatedoctor.setId(doctor.getId());
        updatedoctor.setAd(ad);
        updatedoctor.setSoyad(soyad);
        updatedoctor.setAtaAdi(ataadi);
        updatedoctor.setDob(dob);
        updatedoctor.setElmiDerece(elmiderece);
        updatedoctor.setIxtisas(ixtisas);
        updatedoctor.setDepartmentId(connect.getId());
        updatedoctor.setDepartmentName(connect.getName());

        boolean result = manager.updateDoctors(updatedoctor);

        stage.close();
        if (result) {
            Notifications notification = Notifications.create()
                    .title("Bildirish")
                    .text("Doctor update")
                    .darkStyle()
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(3));
            notification.showInformation();
        }

    }

    @FXML
    private void closeEditView(ActionEvent event) {

        stage.close();
    }

    public void loadDepartmentCombolist() throws SQLException {
        departmentComboData = FXCollections.observableArrayList();
        departmentComboData.addAll(manager.getDepartmentList());

        departmentCombo.setValue(new Connect(0, "secin"));
        departmentCombo.setItems(departmentComboData);

    }

    @FXML
    private void deleteDoctors(ActionEvent event) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Doctors");
        alert.setHeaderText("Delete doctors");
        alert.setContentText("Are you sure ?");

        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get().equals(ButtonType.OK)) {
            boolean result = manager.deleteDoctors(doctor);
        }

    }

}
