/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.util;

/**
 *
 * @author micharesp
 */
public class Parameter {

    //producción formularios
//    public static String iepi_casilleros = "jdbc:mysql://10.0.20.130:3306/iepi_casilleros";
//    public static String iepi_formularios = "jdbc:mysql://10.0.20.130:3306/iepi_formularios";
    
    //prueba formularios
    public static String iepi_casilleros = "jdbc:mysql://10.0.26.130:3306/iepi_casilleros";
    public static String iepi_formularios = "jdbc:mysql://10.0.26.130:3306/iepi_formularios";
    public static String USER = "iepi-solicitudes";
    public static String PASSWORD = "5ad0d5c3fced39d5048f";
    
    //Producción
//    public static String SFTP_HOST = "10.0.20.130";
//    public static String SFTP_USER = "mjyanangomez";
//    public static String SFTP_PASS = "S31tNW4$";
//    public static int SFTP_PORT = 22;
    
    //Localhost
    public static String SFTP_HOST = "10.0.26.130";
    public static String SFTP_USER = "root";
    public static String SFTP_PASS = "temporal123";
    public static int SFTP_PORT = 22;
    
    public static String RUTA_SERVER = "/var/www/html/solicitudes/media/files/breeder_forms/";
    public static String RUTA_URL = "https://pruebas.propiedadintelectual.gob.ec/solicitudes/media/files/breeder_forms/";
        
    //Data Base local
    public static String USER_DB = "root";
    public static String PASSWORD_DB = "MichaRoot6*";
    public static String DATABASE = "senadi_vegetable";
    public static String HOST_DB = "localhost";    
    
}
