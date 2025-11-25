/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.util;

/**
 *
 * @author michael
 */
public class TipoDocumento {
    private static final Integer TIPO_CEDULA = 1;
    private static final Integer TIPO_RUC_NATURAL = 2;
    private static final Integer RUC_PRIVADA = 3;
    private static final Integer RUC_PUBLICA = 4;

    public static Integer getTipoCedula() {
        return TIPO_CEDULA;
    }

    public static Integer getTipoRucNatural() {
        return TIPO_RUC_NATURAL;
    }

    public static Integer getRucPrivada() {
        return RUC_PRIVADA;
    }

    public static Integer getRucPublica() {
        return RUC_PUBLICA;
    }
}
