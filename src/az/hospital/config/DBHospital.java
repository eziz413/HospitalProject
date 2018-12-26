/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.hospital.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author DELL
 */
public class DBHospital {
    public static Connection connect () throws SQLException, IOException {
        Locale.setDefault(Locale.GERMAN);
        DriverManager.registerDriver(new OracleDriver());
        String FilePath ="az/hospital/config/Hospital.properties";
        ClassLoader loader= Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream(FilePath);
        Properties properties = new Properties();
        
        properties.load(in);
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String pasword = properties.getProperty("db.pasword");
        
        Connection c = DriverManager.getConnection(url, user, pasword);
        
        
        return c;
        
        
    }
    public static void disconnect (Connection c , PreparedStatement ps ,ResultSet rs) throws SQLException {
        if(c!=null){
            c.close();
        }
        if (ps!=null){
            ps.close();
        }
        if(rs!=null){
            rs.close();
        }
    }
    
}
