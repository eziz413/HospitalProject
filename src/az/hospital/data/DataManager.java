/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.hospital.data;

import az.hospital.config.DBHospital;
import az.hospital.model.Connect;
import az.hospital.model.Doctors;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class DataManager {

    Connection c;
    PreparedStatement ps;
    ResultSet rs;

    public List<Doctors> getDoctorsList() throws SQLException {
        List<Doctors> doctors = new ArrayList<>();
        String query = "  \n"
                + "  select d.*,MD.department_name from doctors d inner join\n"
                + "  medicaldepartment md on D.DEPARTMENT_ID=MD.ID where D.ACTIVE=1";

        try {
            c = DBHospital.connect();
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {

     
                Doctors doctor = new Doctors();
                doctor.setId(rs.getInt("id"));
                doctor.setAd(rs.getString("ad"));
                doctor.setSoyad(rs.getString("soyad"));
                doctor.setAtaAdi(rs.getString("ata_adi"));
                doctor.setDob(rs.getDate("dob"));
                doctor.setElmiDerece(rs.getString("elmi_derece"));
                doctor.setIxtisas(rs.getString("ixtisas"));
                doctor.setDepartmentId(rs.getInt("department_id"));
                doctor.setDepartmentName(rs.getString("department_name"));
                doctors.add(doctor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }
        return doctors;
    }

    public List<Connect> getDepartmentList() throws SQLException {

        List<Connect> connect = new ArrayList<>();
        String sql = "select * from medicaldepartment ";

        try {
            c = DBHospital.connect();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {

                    connect.add(new Connect(rs.getInt("id"), rs.getString("department_name")));

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }

        return connect;
    }

    public boolean addDoctors(Doctors doctor) throws SQLException {

        String sql = "insert into doctors values (  seq_doctors.nextval,?,?,?,?,?,1,?,? )";
        String userSql ="insert into users values(seq_users.nextval,?,?,1,?,?,1) ";

        try {
            c = DBHospital.connect();

            if (c != null) {
                ps = c.prepareStatement(sql, new String []{"id"});
                ps.setString(1, doctor.getAd());
                ps.setString(2, doctor.getSoyad());
                ps.setString(3, doctor.getSoyad());
                ps.setDate(4, new Date(doctor.getDob().getTime()));
                ps.setString(5, doctor.getElmiDerece());
                ps.setInt(6, doctor.getDepartmentId());
                ps.setString(7, doctor.getIxtisas());
                ps.execute();
                rs=ps.getGeneratedKeys();
                int doctorId=0;
                if(rs.next()){
                    BigDecimal lastId= (BigDecimal) rs.getObject(1);
                doctorId= lastId.intValue();
                }
                
                ps= c.prepareStatement(userSql);
                ps.setString(1, doctor.getUsername());
                ps.setString(2, doctor.getPasword());
                ps.setString(3, doctor.getEmail());
                ps.setInt(4, doctorId);
                
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }

        return false;
    }

    public boolean updateDoctors(Doctors doctor) throws SQLException {

        String sql = "update doctors set ad=?,soyad=?,ATA_ADI=?,dob=?,ELMI_DERECE=?,DEPARTMENT_ID=?,ixtisas=? where id =?";

        try {
            c = DBHospital.connect();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setString(1, doctor.getAd());
                ps.setString(2, doctor.getSoyad());
                ps.setString(3, doctor.getAtaAdi());
                ps.setDate(4, new Date(doctor.getDob().getTime()));
                ps.setString(5, doctor.getElmiDerece());
                ps.setInt(6, doctor.getDepartmentId());
                ps.setString(7, doctor.getIxtisas());
                ps.setInt(8, doctor.getId());
                ps.execute();
                return true;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }
        return false;
    }

    public boolean deleteDoctors(Doctors doctor) throws SQLException {

        String sql = "update doctors set active = 0 where id =?";

        try {
            c = DBHospital.connect();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setInt(1, doctor.getId());
                ps.execute();
                return true;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }
        return false;
    }

    public List<Doctors> simpleSearch(String searchValue) throws SQLException {
 List<Doctors> doctors = new ArrayList<>();
        String query = "  \n"
                + "  select d.*,MD.department_name from doctors d inner join\n"
                + "  medicaldepartment md on D.DEPARTMENT_ID=MD.ID where D.ACTIVE=1"
                + "and lower (ad) like lower ('%"+searchValue+"%')"
                + "or lower (soyad) like lower ('%"+searchValue+"%')";

        try {
            c = DBHospital.connect();
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {

     
                Doctors doctor = new Doctors();
                doctor.setId(rs.getInt("id"));
                doctor.setAd(rs.getString("ad"));
                doctor.setSoyad(rs.getString("soyad"));
                doctor.setAtaAdi(rs.getString("ata_adi"));
                doctor.setDob(rs.getDate("dob"));
                doctor.setElmiDerece(rs.getString("elmi_derece"));
                doctor.setIxtisas(rs.getString("ixtisas"));
                doctor.setDepartmentId(rs.getInt("department_id"));
                doctor.setDepartmentName(rs.getString("department_name"));
                doctors.add(doctor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }
        return doctors;    }

    public List<Doctors> loadDoctorsBySearch(int id) throws SQLException {

        List<Doctors> doctors = new ArrayList<>();
        String query = "  \n"
                + "  select d.*,MD.department_name from doctors d inner join\n"
                + "  medicaldepartment md on D.DEPARTMENT_ID=MD.ID where D.ACTIVE=1"
                + "and d.DEPARTMENT_ID=?";

        try {
            c = DBHospital.connect();
            ps = c.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

     
                Doctors doctor = new Doctors();
                doctor.setId(rs.getInt("id"));
                doctor.setAd(rs.getString("ad"));
                doctor.setSoyad(rs.getString("soyad"));
                doctor.setAtaAdi(rs.getString("ata_adi"));
                doctor.setDob(rs.getDate("dob"));
                doctor.setElmiDerece(rs.getString("elmi_derece"));
                doctor.setIxtisas(rs.getString("ixtisas"));
                doctor.setDepartmentId(rs.getInt("department_id"));
                doctor.setDepartmentName(rs.getString("department_name"));
                doctors.add(doctor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }
        return doctors;

    }

    public List<Doctors> advancedsearch(int departmentId, Integer doctorId) throws SQLException {
List<Doctors> doctors = new ArrayList<>();
        String query = "  \n"
                + "  select d.*,MD.department_name from doctors d inner join\n"
                + "  medicaldepartment md on D.DEPARTMENT_ID=MD.ID where D.ACTIVE=1";
        
        if(departmentId !=0){
            query +=" and D.DEPARTMENT_ID= "+departmentId;
        }
        if (doctorId!=0){
            query +="and d.id ="+doctorId;
        }

        try {
            c = DBHospital.connect();
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {

     
                Doctors doctor = new Doctors();
                doctor.setId(rs.getInt("id"));
                doctor.setAd(rs.getString("ad"));
                doctor.setSoyad(rs.getString("soyad"));
                doctor.setAtaAdi(rs.getString("ata_adi"));
                doctor.setDob(rs.getDate("dob"));
                doctor.setElmiDerece(rs.getString("elmi_derece"));
                doctor.setIxtisas(rs.getString("ixtisas"));
                doctor.setDepartmentId(rs.getInt("department_id"));
                doctor.setDepartmentName(rs.getString("department_name"));
                doctors.add(doctor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHospital.disconnect(c, ps, rs);
        }
        return doctors;
    }

    

     
    
}
