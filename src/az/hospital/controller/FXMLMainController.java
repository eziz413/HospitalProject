/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.hospital.controller;

import az.hospital.data.DataManager;
import az.hospital.model.Connect;
import az.hospital.model.Doctors;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class FXMLMainController implements Initializable {
    
    private Label label;
    @FXML
    private TableView<Doctors> DoctorTable;
    @FXML
    private TableColumn<Doctors,String> columnAd;
    @FXML
    private TableColumn<Doctors,String> columnSoyad;
    @FXML
    private TableColumn<Doctors,String> columnAtaadi;
    @FXML
    private TableColumn<Doctors,Date> columnDob;
    @FXML
    private TableColumn<Doctors,String> columnElmiDerece;
    @FXML
    private TableColumn<Doctors,String> columnİxtisas;
     @FXML
    private TableColumn<Doctors,String> columnDepartment;
    @FXML
    private Button LoadDoctorsBtn;
    
    private ObservableList<Doctors> doctorsData;
    private ObservableList<Connect> departmentComboData;
    private ObservableList<Doctors> doctorsComboData;
    
   private DataManager manager = new DataManager();
    @FXML
    private Button addDoctorsBtn;
    @FXML
    private TextField searchInput;
    @FXML
    private Button searchBtn;
    @FXML
    private ComboBox departmentCombo;
    @FXML
    private ComboBox doctorCombo;
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO


            loadDepartmentCombo();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadDoctorsCombo(manager.getDoctorsList());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void actionLoadDoctors(ActionEvent event) throws SQLException {
        System.out.println("daxil oldu");
        loadDoctors(manager.getDoctorsList());
    }
    
     private void loadDoctors(List <Doctors> doctor) throws SQLException{
        doctorsData = FXCollections.observableArrayList();
        doctorsData.addAll(doctor);
         
        
        columnAd.setCellValueFactory(new PropertyValueFactory<>("ad"));
        columnSoyad.setCellValueFactory(new PropertyValueFactory<>("soyad"));
        columnAtaadi.setCellValueFactory(new PropertyValueFactory<>("ataAdi"));
        columnDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        columnElmiDerece.setCellValueFactory(new PropertyValueFactory<>("elmiDerece"));
        columnİxtisas.setCellValueFactory(new PropertyValueFactory<>("ixtisas"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        
       DoctorTable.getItems().clear();
       DoctorTable.setItems(doctorsData);
        
    }

    @FXML
    private void addDoctorsView(ActionEvent event) throws IOException {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLAddDoctors.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Add doctors page");
        stage.setScene(new Scene(root));
        
        FXMLAddDoctorsController addDoctorsController = loader.getController();
        addDoctorsController.setStage(stage);
        
        stage.show();
    }

    @FXML
    private void editDoctors(MouseEvent event) throws IOException {
        
        if (event.getClickCount()==2){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditDoctors.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Doctors");
            stage.setScene(new Scene(root));
            
            FXMLEditDoctorsController editDoctorsController= loader.getController();
            editDoctorsController.setStage (stage);
            
            Doctors doctor=DoctorTable.getSelectionModel().getSelectedItem();
            editDoctorsController.setDoctors(doctor);
            stage.show();
            
        }
        
        
             
    }

    @FXML
    private void simpleSearch(KeyEvent event) throws SQLException {
    
        String searchValue=searchInput.getText();
        List<Doctors> doctor = manager.simpleSearch(searchValue);
        loadDoctors(doctor);
    }

    @FXML
    private void advancedsearch(ActionEvent event) throws SQLException {
        
        Connect connect = (Connect) departmentCombo.getSelectionModel().getSelectedItem();
        Doctors  doctors = (Doctors) doctorCombo.getSelectionModel().getSelectedItem();
        
        List <Doctors> doctor = manager.advancedsearch(connect.getId(),doctors.getId());
        loadDoctors(doctor);
    }

    private void loadDepartmentCombo() throws SQLException {
        departmentComboData=FXCollections.observableArrayList();
        List<Connect> connects=manager.getDepartmentList();
        connects.add(new Connect(0, "secin"));
        departmentComboData.addAll(connects);
        
        
        departmentCombo.setValue(new Connect(0, "secin"));
        departmentCombo.setItems(departmentComboData);  
    }

    private void loadDoctorsCombo(List <Doctors> doctor) throws SQLException {

        doctorsComboData=FXCollections.observableArrayList();
        doctorsComboData.addAll(doctor);
        doctorCombo.setValue(new Connect(0, "secin"));
        doctorCombo.setItems(doctorsComboData);
        
    }

    @FXML
    private void loadDoctorsBySearch(ActionEvent event) throws SQLException {

         Connect connect = (Connect) departmentCombo.getSelectionModel().getSelectedItem();
         List<Doctors> doctor =null;
         if(connect.getId()==0){
             doctor=manager.getDoctorsList();
         }else{
             doctor = manager.loadDoctorsBySearch(connect.getId());
         }
         
         
        loadDoctorsCombo(doctor);
    }
    
    
    }
    

