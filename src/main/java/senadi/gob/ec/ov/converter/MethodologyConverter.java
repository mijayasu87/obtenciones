/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import senadi.gob.ec.ov.bean.BreederFormBean;
import senadi.gob.ec.ov.model.Methodology;

/**
 *
 * @author michael
 */
@FacesConverter(value = "methodologyConverter", forClass = Methodology.class)
public class MethodologyConverter implements Converter<Methodology> {

    @Override
    public Methodology getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty())
            return null;

        BreederFormBean bean = context.getApplication()
            .evaluateExpressionGet(context, "#{breederFormBean}", BreederFormBean.class);

        // Buscar por ID
        for (Methodology m : bean.getMethodologies()) {
            if (m.getId().toString().equals(value)) {
                return m;
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Methodology value) {
        return value == null ? "" : String.valueOf(value.getId());
    }
}
