/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.hospital.controller;

import az.hospital.data.DataManager;
import az.hospital.model.Connect;
import az.hospital.model.Doctors;
import az.hospital.util.HashAlgorithms;
import az.hospital.util.SendMail;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
public class FXMLAddDoctorsController implements Initializable {

    @FXML
    private TextField adInput;
    @FXML
    private TextField soyadInput;
    @FXML
    private TextField ataadiInput;
    @FXML
    private DatePicker dobInput;
    @FXML
    private TextField ixtisasInput;
    @FXML
    private TextField elmidereceInput;
    @FXML
    private ComboBox departmentCombo;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveDoctorsBtn;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Connect> departmentComboData;
    private DataManager manager = new DataManager();
    @FXML
    private TextField userNameInput;
    @FXML
    private TextField emailInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            loadDepartmentComboList();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
    }

    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadDepartmentComboList() throws SQLException {
        departmentComboData = FXCollections.observableArrayList();
        departmentComboData.addAll(manager.getDepartmentList());
        departmentCombo.setValue(new Connect(0, "secin"));
        departmentCombo.setItems(departmentComboData);

    }

    @FXML
    private void saveAddDoctors(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        System.out.println("giris");
        String ad = adInput.getText();
        String soyad = soyadInput.getText();
        String ataadi = ataadiInput.getText();
        Date dob = Date.from(dobInput.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String elmiderece = elmidereceInput.getText();
        String ixtisas = ixtisasInput.getText();
        Connect connect = (Connect) departmentCombo.getSelectionModel().getSelectedItem();
        String username = userNameInput.getText();
        String email = emailInput.getText();

        UUID uuid = UUID.randomUUID();
        String pasword = uuid.toString();
        pasword = pasword.substring(pasword.length() - 10);
        System.out.println(pasword);

        Doctors doctor = new Doctors();
        doctor.setAd(ad);
        doctor.setSoyad(soyad);
        doctor.setAtaAdi(ataadi);
        doctor.setDob(dob);
        doctor.setElmiDerece(elmiderece);
        doctor.setIxtisas(ixtisas);
        doctor.setDepartmentId(connect.getId());
        doctor.setUsername(username);
        doctor.setEmail(email);
        doctor.setPasword(HashAlgorithms.encodeSha256(pasword));

        boolean result = manager.addDoctors(doctor);
        if(result){
            
            SendMail.SendMail(email, "you are pasword", "your username"+username+"your pasword"+pasword);
            stage.close();
              Notifications notification = Notifications.create()
                .title("Bildiris")
                .text("Doctor added succesful")
                .position(Pos.TOP_CENTER)
                .darkStyle()
                .graphic(null)
                .hideAfter(Duration.seconds(2));
        notification.showInformation();

            
        }

      
    }

    @FXML
    private void closeAddDoctors(ActionEvent event) {
        stage.close();
    }
}
