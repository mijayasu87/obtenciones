/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.solicitudes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import senadi.gob.ec.ov.util.Operations;

/**
 *
 * @author michael
 */
public class BreederDAO {

    public Integer saveBreederForm(BreederForm breed) {
        String sql = "INSERT INTO breeder_forms ("
                + "proposed_name, commercial_name, status, application_number, owner_id, `group`, discount_file"
                + ") VALUES (?,?,?,?,?,?,?)";

        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, breed.getProposedName());
            ps.setString(2, breed.getCommercialName());
            ps.setString(3, breed.getStatus());
            ps.setString(4, breed.getApplicationNumber());
            ps.setInt(5, breed.getOwnerId());
            ps.setString(6, breed.getGroup());
            ps.setString(7, breed.getDiscountFile());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // devuelve el id generado
                    }
                }
            }
            con.close();
            return 0; // no se insertó nada o no hubo id generado

        } catch (SQLException e) {
            //System.err.println("No se creó la persona " + breed.getName() + " con identificación " + breed.getIdentificationNumber() + ": " + e);
            System.err.println("No se creó el breeder " + e);
            return 0;
        }
    }
    
    public boolean savePersonBreeder(PersonBreeder pb){
        String sql = "INSERT INTO person_breeder ("
                + "breeder_form_id, person_id, `type`"
                +") VALUES (?,?,?)";
        try{
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pb.getBreederFormId());
            ps.setInt(2, pb.getPersonId());
            ps.setString(3, pb.getTypePerson());
            
            int filas = ps.executeUpdate();
            con.close();
            return filas > 0;
            
        }catch (SQLException e) {
            //System.err.println("No se creó la persona " + breed.getName() + " con identificación " + breed.getIdentificationNumber() + ": " + e);
            System.err.println("No se creó el person_breeder " +pb.toString()+": "+ e);
            return false;
        }
    }

    public int getNextApplicationNumber() {
        int year = LocalDate.now().getYear();
        String query = "Select * from sequence where cur_year = " + year;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            int actual = 0;
            while (rs.next()) {
                actual = rs.getInt("id");
                break;
            }
            con.close();

            int next;
            if (actual > 0) {
                next = actual + 1;
                if (!updateSequence(next, 0)) {
                    next = -1;
                }
            } else {
                next = 1;
                if (!updateSequence(next, year)) {
                    next = -1;
                }
            }
            return next;
        } catch (SQLException ex) {
            System.out.println("error en obtener el siguiente application_number: " + ex);
            return -1;
        }
    }        

    public boolean updateBreederForm(BreederForm breeder) {
        String sql = "UPDATE breeder_forms set application_date = ?, status = ? "
                + "WHERE application_number = ?";
        try {

            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setTimestamp(1, breeder.getApplicationDate());
            ps.setString(2, breeder.getStatus());
            ps.setString(3, breeder.getApplicationNumber());

            int rows = ps.executeUpdate();
            con.close();
            return rows > 0;

        } catch (SQLException ex) {
            System.err.println("No se pudo actualizar el breeder_form de solicitudes " + breeder.getApplicationNumber() + ": " + ex);
            return false;
        }
    }

    public boolean updateSequence(int nextnumber, int year) {
        String sql = "";
        if (year > 0) {
            sql = "UPDATE sequence SET "
                    + "id = ?, cur_year = ?";
        } else {
            sql = "UPDATE sequence SET "
                    + "id = ?";
        }

        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, nextnumber);
            if (year > 0) {
                ps.setInt(2, year);
            }

            int filas = ps.executeUpdate();
            con.close();
            return filas > 0;

        } catch (Exception ex) {
            System.err.println("No se pudo actualizar sequence con el número " + nextnumber + ": " + ex);
            return false;
        }
    }

    public boolean createApplications(String applicationNumber, String tableName, Integer ownerId,
            String status, Integer year, Integer number, String serviceWindow) {

        String sql = "INSERT INTO applications ("
                + "application_number, table_name, owner_id, status, year, number, servicewindow"
                + ") VALUES (?,?,?,?,?,?,?)";

        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, applicationNumber);
            ps.setString(2, tableName);
            ps.setInt(3, ownerId);
            ps.setString(4, status);
            ps.setInt(5, year);
            ps.setInt(6, number);
            ps.setString(7, serviceWindow);

            int filas = ps.executeUpdate();
            con.close();
            return filas > 0;

        } catch (SQLException e) {
            //System.err.println("No se creó la persona " + breed.getName() + " con identificación " + breed.getIdentificationNumber() + ": " + e);
            System.err.println("No se creó el applications de " + applicationNumber + ": " + e);
            return false;
        }
    }

    public int saveFormPaymentRates(FormPaymentRates formpay) {
        String sql = "INSERT INTO form_payment_rates ("
                + "payment_rate_id, serial_form, date"
                + ") VALUES (?,?,?)";

        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, formpay.getPaymentRateId());
            ps.setInt(2, formpay.getSerialForm());
            ps.setTimestamp(3, formpay.getDate());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // devuelve el id generado
                    }
                }
            }
            con.close();
            return 0; // no se insertó nada o no hubo id generado

        } catch (SQLException e) {
            //System.err.println("No se creó la persona " + breed.getName() + " con identificación " + breed.getIdentificationNumber() + ": " + e);
            System.err.println("No se creó el form_payment_rates: " + e);
            return 0;
        }
    }

    public PaymentRates getPaymentRatesById(Integer id) {
        String query = "Select * from payment_rates where id = " + id;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            PaymentRates paymentRates = new PaymentRates();
            while (rs.next()) {
                paymentRates.setId(rs.getInt("id"));
                paymentRates.setDescription(rs.getString("description"));
                paymentRates.setDiscount(rs.getDouble("discount"));
                paymentRates.setFormId(rs.getInt("form_id"));
                paymentRates.setFormTypeId(rs.getInt("form_type_id"));
                paymentRates.setLawDescription(rs.getString("law_description"));
                paymentRates.setPaymentCode(rs.getString("payment_code"));
                paymentRates.setShortPaymentCode(rs.getString("short_payment_code"));
                paymentRates.setSubFormType(rs.getInt("sub_form_type"));
                paymentRates.setValue(rs.getDouble("value"));
            }
            con.close();
            return paymentRates;
        } catch (SQLException ex) {
            System.out.println("error en obtener datos payment_rates " + id + ": " + ex);
            return new PaymentRates();
        }
    }
    
    

}
