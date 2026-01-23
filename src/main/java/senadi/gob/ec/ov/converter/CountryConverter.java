/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.converter;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import senadi.gob.ec.ov.bean.BreederFormBean;
import senadi.gob.ec.ov.solicitudes.Country;



/**
 *
 * @author michael
 */
@FacesConverter(value = "countryConverter")
public class CountryConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String idCountry) {
        ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(),"#{breederFormBean}", BreederFormBean.class);
        BreederFormBean breederBean = (BreederFormBean)vex.getValue(ctx.getELContext());
        return breederBean.getCountryById(Integer.valueOf(idCountry));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object country) {
        return ((Country)country).getId().toString();
    }
}
