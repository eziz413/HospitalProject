/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.hospital.model;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class Doctors {

    private Integer id;
    private String ad;
    private String soyad;
    private String ataAdi;
    private Date dob;
    private String elmiDerece;
    private Integer departmentId;
    private String ixtisas;
    private String departmentName;
    private  String username;
    private String email;
    private String pasword;
    

    public Doctors() {
    }

    public Doctors(Integer id, String ad) {
        this.id = id;
        this.ad = ad;
    }

   
    
    

    public Doctors(Integer id, String ad, String soyad, String ataAdi, Date dob, String elmiDerece, Integer departmentId, String ixtisas, String departmentName) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.ataAdi = ataAdi;
        this.dob = dob;
        this.elmiDerece = elmiDerece;
        this.departmentId = departmentId;
        this.ixtisas = ixtisas;
        this.departmentName = departmentName;
        
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getAtaAdi() {
        return ataAdi;
    }

    public void setAtaAdi(String ataAdi) {
        this.ataAdi = ataAdi;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getElmiDerece() {
        return elmiDerece;
    }

    public void setElmiDerece(String elmiDerece) {
        this.elmiDerece = elmiDerece;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getIxtisas() {
        return ixtisas;
    }

    public void setIxtisas(String ixtisas) {
        this.ixtisas = ixtisas;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
    
    
   

    @Override
    public String toString() {
        return ad + " " +soyad;
    }

    
}
