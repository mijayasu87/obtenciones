/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author michael
 */
public class SFTPUtil {

    public boolean guardarFileEnServidorRemoto(FileInputStream uploadedFile, int idRenewal, String nombreArchivo) {
        if (uploadedFile == null) {
            System.err.println("No se ha seleccionado ningún archivo para subir.");
            return false;
        }

        Session session = null;
        ChannelSftp channelSftp = null;

        try (InputStream input = uploadedFile) {
            // Configuración del servidor SFTP

            String SFTP_DIR = Parameter.RUTA_SERVER + idRenewal + "/";

            JSch jsch = new JSch();
            session = jsch.getSession(Parameter.SFTP_USER, Parameter.SFTP_HOST, Parameter.SFTP_PORT);
            session.setPassword(Parameter.SFTP_PASS);

            // Evita errores de validación del host key
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // Conexión
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Subir archivo
//            String nombreArchivo = uploadedFile.getFileName();
            channelSftp.cd(SFTP_DIR);
            channelSftp.put(input, nombreArchivo);

//            System.out.println("✅ Archivo subido correctamente a " + SFTP_DIR + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error al subir el archivo: " + e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public boolean guardarArchivoEnServidorRemoto(InputStream inputs, int idCarpeta, String nombreArchivo) {
        if (inputs == null) {
            System.err.println("No se ha seleccionado ningún archivo para subir.");
            return false;
        }
        Session session = null;
        ChannelSftp channelSftp = null;
        try (InputStream input = inputs) {
            // Configuración del servidor SFTP

            String SFTP_DIR = Parameter.RUTA_SERVER + idCarpeta + "/";
            JSch jsch = new JSch();
            session = jsch.getSession(Parameter.SFTP_USER, Parameter.SFTP_HOST, Parameter.SFTP_PORT);
            session.setPassword(Parameter.SFTP_PASS);

            // Evita errores de validación del host key
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // Conexión
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            //Crear carpeta en caso de que no exista
            createRemoteDirectoriy(channelSftp, SFTP_DIR);
            // Subir archivo
//            String nombreArchivo = uploadedFile.getFileName();
//            channelSftp.cd(SFTP_DIR);
            channelSftp.put(input, nombreArchivo);

//            System.out.println("✅ Archivo subido correctamente a " + SFTP_DIR + nombreArchivo);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error al subir el archivo: " + e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public boolean copiarArchivoEnServidorRemoto(
            String carpetaOrigen,
            String carpetaDestino,
            String nombreArchivo) {

        Session session = null;
        ChannelSftp sftpRead = null;
        ChannelSftp sftpWrite = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(
                    Parameter.SFTP_USER,
                    Parameter.SFTP_HOST,
                    Parameter.SFTP_PORT
            );
            session.setPassword(Parameter.SFTP_PASS);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            sftpRead = (ChannelSftp) session.openChannel("sftp");
            sftpRead.connect();

            sftpWrite = (ChannelSftp) session.openChannel("sftp");
            sftpWrite.connect();

            // 🔴 RUTAS ABSOLUTAS
            String origen = carpetaOrigen + nombreArchivo;
//            String destino = carpetaDestino + "/" + nombreArchivo;
            System.out.println("origen: " + origen);
//            System.out.println("destino: "+destino);

            createRemoteDirectory(sftpRead, carpetaDestino);

            try (InputStream input = sftpRead.get(origen)) {
                sftpWrite.cd(carpetaDestino);
                sftpWrite.put(input, nombreArchivo);
            }
            System.out.println("✅ Archivo copiado correctamente");
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error al copiar archivo: " + e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            if (sftpRead != null) {
                sftpRead.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    /**
     * No usa cd
     */
    private void createRemoteDirectory(ChannelSftp sftp, String remoteDir) throws SftpException {

        String[] folders = remoteDir.split("/");
        String path = "";

        for (String folder : folders) {
            if (folder.isEmpty()) {
                continue;
            }

            path += "/" + folder;
            try {
                sftp.stat(path);
            } catch (SftpException e) {
                sftp.mkdir(path);
            }
        }
    }

    /**
     * Usa cd
     */
    private void createRemoteDirectoriy(ChannelSftp sftpChannel, String remotePath) throws SftpException {
        // Asegurar que termina en "/"
        if (!remotePath.endsWith("/")) {
            remotePath = remotePath + "/";
        }

        // Extraer ruta padre y nombre de la última carpeta
        int lastSlash = remotePath.lastIndexOf("/", remotePath.length() - 2);
        String parentPath = remotePath.substring(0, lastSlash + 1);
        String lastFolder = remotePath.substring(lastSlash + 1, remotePath.length() - 1);

        // Ir a la carpeta padre
        sftpChannel.cd(parentPath);

        try {
            // Intentar entrar a la última carpeta
            sftpChannel.cd(lastFolder);
        } catch (SftpException e) {
            // Si no existe → crear solo esa
            sftpChannel.mkdir(lastFolder);
            sftpChannel.cd(lastFolder);
        }
    }

    public boolean renombrarArchivoEnServidorRemoto(String nombreActual, String nuevoNombre, int idRenewal) {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Configuración del servidor SFTP
            String SFTP_DIR = Parameter.RUTA_SERVER + idRenewal + "/";

            // Establecer conexión
            JSch jsch = new JSch();
            session = jsch.getSession(Parameter.SFTP_USER, Parameter.SFTP_HOST, Parameter.SFTP_PORT);
            session.setPassword(Parameter.SFTP_PASS);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Cambiar directorio
            channelSftp.cd(SFTP_DIR);

            // Renombrar archivo
            channelSftp.rename(nombreActual, nuevoNombre);

//            System.out.println("✅ Archivo renombrado correctamente: " + nombreActual + " → " + nuevoNombre);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error al renombrar el archivo: " + e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public boolean eliminarArchivoEnServidorRemoto(String nombreArchivo, int id) {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Configuración del servidor SFTP
            String SFTP_DIR = Parameter.RUTA_SERVER + id + "/";

            // Establecer conexión
            JSch jsch = new JSch();
            session = jsch.getSession(Parameter.SFTP_USER, Parameter.SFTP_HOST, Parameter.SFTP_PORT);
            session.setPassword(Parameter.SFTP_PASS);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Cambiar directorio
            channelSftp.cd(SFTP_DIR);

            // Eliminar archivo
            channelSftp.rm(nombreArchivo);

//            System.out.println("🗑️ Archivo eliminado correctamente: " + nombreArchivo);
            return true;

        } catch (com.jcraft.jsch.SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                System.err.println("⚠️ El archivo no existe en el servidor: " + nombreArchivo);
            } else {
                System.err.println("❌ Error al eliminar el archivo: " + e.getMessage());
            }
            return false;

        } catch (Exception e) {
            System.err.println("❌ Error general al eliminar archivo: " + e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
