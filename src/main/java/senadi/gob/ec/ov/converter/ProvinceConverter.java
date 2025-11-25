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
import senadi.gob.ec.ov.bean.solicitudes.Province;


/**
 *
 * @author michael
 */
@FacesConverter(value = "provinceConverter")
public class ProvinceConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String idProvince) {
        ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(),"#{breederFormBean}", BreederFormBean.class);
        BreederFormBean breederBean = (BreederFormBean)vex.getValue(ctx.getELContext());
        return breederBean.getProvinceById(Integer.valueOf(idProvince));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object province) {
        return ((Province)province).getId().toString();
    }
}
