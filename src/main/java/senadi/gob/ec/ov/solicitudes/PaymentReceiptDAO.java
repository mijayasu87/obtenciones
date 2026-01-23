/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.solicitudes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import senadi.gob.ec.ov.util.Operations;

/**
 *
 * @author michael
 */
public class PaymentReceiptDAO {

    public List<BreederForm> getBreederFormsWithPayByOwnerId(Integer ownerId) {
        String query = "SELECT * FROM form_payment_rates AS fpr "
                + "INNER JOIN breeder_forms AS bf ON bf.id = fpr.serial_form "
                + "INNER JOIN payment_receipt AS pr ON pr.id = bf.payment_receipt_id "
                + "WHERE bf.owner_id = ? "
                + "AND fpr.payment_rate_id IN (10, 528) "
                + "AND bf.`status` = ?";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, ownerId);
            pst.setString(2, "FINISHED");
            ResultSet rs = pst.executeQuery();
            List<BreederForm> breeders = new ArrayList<>();
            while (rs.next()) {
                BreederForm breeder = new BreederForm();
                breeder.setId(rs.getInt("id"));
                breeder.setApplicationNumber(rs.getString("application_number"));
                breeder.setDiscountFile(rs.getString("discount_file"));
                breeder.setGroup(rs.getString("group"));
                breeder.setOwnerId(rs.getInt("owner_id"));
                breeder.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                breeders.add(breeder);
            }
            con.close();
            return breeders;
        } catch (SQLException ex) {
            System.out.println("error en obtener datos breeders owner_id " + ownerId + ": " + ex);
            return new ArrayList<>();
        }
    }

    public BreederForm getBreederFormByApplicationNumber(String applicationNumber) {
        String query = "Select * from breeder_forms where application_number = ?";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, applicationNumber);

            ResultSet rs = pst.executeQuery();
            BreederForm breeder = new BreederForm();
            while (rs.next()) {
                breeder.setId(rs.getInt("id"));
                breeder.setApplicationNumber(rs.getString("application_number"));
                breeder.setDiscountFile(rs.getString("discount_file"));
                breeder.setGroup(rs.getString("group"));
                breeder.setOwnerId(rs.getInt("owner_id"));
                breeder.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
            }
            con.close();
            return breeder;
        } catch (SQLException ex) {
            System.out.println("error en obtener datos breeder application_number " + applicationNumber + ": " + ex);
            return new BreederForm();
        }
    }

}
