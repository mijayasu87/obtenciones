/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.gob.ec.ov.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Michael Yanangómez
 */
public class Operations {

    public static String INFORMACION = "INFORMACIÓN";
    public static String ERROR = "ERROR";
    public static String AVISO = "AVISO";

    public static Connection doConnectionToCasilleros() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(Parameter.iepi_casilleros, Parameter.USER, Parameter.PASSWORD);
        return con;
    }

    public static Connection doConnectionToFormularios() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(Parameter.iepi_formularios, Parameter.USER, Parameter.PASSWORD);
        return con;
    }

    public static String getCurrentTimeStamp() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    public static void mensaje(String tipo, String mensaje) {
        FacesMessage msg = null;
        switch (tipo) {
            case "ERROR":
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", mensaje);
                break;
            case "INFORMACIÓN":
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", mensaje);
                break;
            case "AVISO":
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", mensaje);
                break;
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Pasa un String Ej: '2020-05-15' a java.util.Date *
     */
    public static Date convertStringToDate(String fec) {
        String an = fec.substring(0, fec.indexOf("-"));
        String aux = fec.substring(fec.indexOf("-") + 1);

        int a = Integer.parseInt(an);
        int m = Integer.parseInt(aux.substring(0, aux.indexOf("-")));
        int d = Integer.parseInt(aux.substring(aux.indexOf("-") + 1));

        return new Date(a - 1900, m - 1, d);
    }

    /**
     * Da formato a la fecha recibida en el siguiente orden 'yyyy-mm-dd'
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
//        System.out.println("--> "+date.toString());
        int dia = date.getDate();
        int mes = date.getMonth() + 1;
        int año = date.getYear() + 1900;
        String d = dia + "";
        String m = mes + "";
        if (dia < 10) {
            d = "0" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        }

        String fecha = año + "-" + m + "-" + d;
//        System.out.println("<-- "+fecha+"\n");
        return fecha;
    }

    /**
     * Da formato a una fecha recibida, en el siguiente orden 'Dddddd dd de
     * Mmmmm de yyyy'
     */
    public static String formatDateToLarge(Date fecha) {

        Date aux = fecha;

//        aux.setDate(aux.getDate()+1);
        int dia = aux.getDate();
        int mes = aux.getMonth();
        int año = aux.getYear() + 1900;
//        int diasem = fecha.getDay();
        String fec = dia + " de " + getMes(mes) + " de " + año;  //getDia(diasem) + " " + 
        return fec.toLowerCase();
    }

    public static String getDia(int dia) {
        if (dia == 1) {
            return "Lunes";
        } else if (dia == 2) {
            return "Martes";
        } else if (dia == 3) {
            return "Miércoles";
        } else if (dia == 4) {
            return "Jueves";
        } else if (dia == 5) {
            return "Viernes";
        } else if (dia == 6) {
            return "Sábado";
        } else if (dia == 0) {
            return "Domingo";
        } else {
            return "Error";
        }
    }

    public static String getMes(int mes) {
        if (mes == 0) {
            return "Enero";
        } else if (mes == 1) {
            return "Febrero";
        } else if (mes == 2) {
            return "Marzo";
        } else if (mes == 3) {
            return "Abril";
        } else if (mes == 4) {
            return "Mayo";
        } else if (mes == 5) {
            return "Junio";
        } else if (mes == 6) {
            return "Julio";
        } else if (mes == 7) {
            return "Agosto";
        } else if (mes == 8) {
            return "Septiembre";
        } else if (mes == 9) {
            return "Octubre";
        } else if (mes == 10) {
            return "Noviembre";
        } else if (mes == 11) {
            return "Diciembre";
        } else {
            return "Error";
        }
    }

    /* Retorna un hash MD5 a partir de un texto */
    public static String md5(String txt) {
        return getHash(txt, "MD5");
    }

    /* Retorna un hash a partir de un tipo y un texto */
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getTramiteFromPdfName(String pdfName) {
        String tramite = "";
        for (int i = 0; i < pdfName.length(); i++) {
            if (pdfName.charAt(i) == '_') {
                tramite = pdfName.substring(0, i);
                break;
            }
        }
        return tramite;
    }

    public static boolean copyFile(String fileName, InputStream stream, String rutaCarpeta) {
        try {
            // write the inputStream to a FileOutputStream
            String nombreDoc = fileName;
            String rutaCompleta = rutaCarpeta + nombreDoc;
            removeSimilarFiles(nombreDoc, rutaCarpeta);
//            System.out.println("rutacompleta: "+rutaCarpeta);
            FileOutputStream fichero = new FileOutputStream(rutaCompleta);
            // Lectura de la url de la web y escritura en fichero local
            byte[] buffer = new byte[1024]; // buffer temporal de lectura.
            int readed = stream.read(buffer);
            while (readed > 0) {
                fichero.write(buffer, 0, readed);
                readed = stream.read(buffer);
            }
            // cierre de conexion y fichero.
            stream.close();
            fichero.close();
            return true;
//            System.out.println("New file uploaded: " + (rutaCarpeta + fileName));
        } catch (IOException e) {
            System.out.println("Error al guardar documento: " + e.getMessage());
            return false;
        }
    }

    public static boolean removeSimilarFiles(String logo, String rutaCarpeta) {
        String[] extensiones = {".pdf"};

        boolean aviso = false;
        for (String extension : extensiones) {
            File file = new File(rutaCarpeta + logo + extension);
            if (file.exists()) {
                file.delete();
                aviso = true;
            }
        }

        return aviso;
    }

    public static boolean validarFecha(Date fecha) {
        try {
            fecha.toString();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /*diasemana: se refiere al día de la semana al cual se va a cambiar la fecha*/
    public static Date cambiarFechaADiaDado(Date fecha, int diasemana) {
        int diasem = fecha.getDay();
        if (diasem == diasemana) {
//            System.out.println("entra 1");
            fecha.setDate(fecha.getDate() + 7);
        } else if (diasemana < diasem) {
//            System.out.println("entra 2");
            fecha.setDate(fecha.getDate() + (7 - (diasem - diasemana)));
        } else {
//            System.out.println("entra 3");
            fecha.setDate(fecha.getDate() + (diasemana - diasem));
        }

        return fecha;
    }

    public static String getStringMes(int mes) {
        if (mes < 10) {
            return "0" + mes;
        } else {
            return mes + "";
        }
    }

    public static String getRandomWord() {
        byte[] bytearray;
        // bind the length 
        bytearray = new byte[256];

        String mystring;
        StringBuffer thebuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray);

        mystring = new String(bytearray, Charset.forName("UTF-8"));

        thebuffer = new StringBuffer();

        //remove all spacial char 
        theAlphaNumericS = mystring.replaceAll("[^A-Z0-9a-z]", "");

        int n = getValorAleatorio(7, 15);

        //random selection
        for (int i = 0; i < theAlphaNumericS.length(); i++) {

            if (Character.isLetter(theAlphaNumericS.charAt(i)) && (n > 0) || Character.isDigit(theAlphaNumericS.charAt(i)) && (n > 0)) {
                thebuffer.append(theAlphaNumericS.charAt(i));
                n--;
            }
        }

        // the resulting string 
        return thebuffer.toString();
    }

    public static int getValorAleatorio(int start, int limit) {
        int valor = (int) ((Math.random() * limit) + start);
        return valor;
    }

    public static String calcularHash(String input) {
        try {
            // Crear una instancia de SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

            // Convertir los bytes en un string hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular el hash", e);
        }
    }

    public static String getModificationType(int type) {
        switch (type) {
            case 21:
                return "RENOVACIÓN";
            case 23:
            case 24:
                return "TRANSFERENCIA";
            case 25:
                return "CAMBIO DE NOMBRE";
            case 26:
                return "CAMBIO DE DOMICILIO";
            case 27:
                return "LICENCIA DE USO";
            case 28:
                return "PRENDA COMERCIAL";
            case 179:
                return "TASA DE MODIFICACIÓN";
            default:
                return "";
        }
    }

}
