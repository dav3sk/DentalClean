package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Dentista;
import gohorse.dentalclean.controller.util.JsfUtil;
import gohorse.dentalclean.controller.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("dentistaConsulta")
@SessionScoped
public class DentistaConsultaController implements Serializable {

    @EJB
    private gohorse.dentalclean.controller.DentistaFacade ejbFacade;
    private List<Dentista> items = null;
    private Dentista selected = new Dentista();

    public DentistaConsultaController() {
    }

    public Dentista getSelected() {
        return selected;
    }

    public void setSelected(Dentista selected) {
        this.selected = selected;
    }
    
    public boolean isSelectedNull() {
        return selected == null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DentistaFacade getFacade() {
        return ejbFacade;
    }

    public Dentista prepareCreate() {
        
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/dentista").getString("DentistaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        selected = new Dentista();
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/dentista").getString("DentistaUpdated"));
        
        selected = new Dentista();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/dentista").getString("DentistaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Dentista> getItems() {
        items = getFacade().findAll();
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/dentista").getString("PersistenceErrorOccured"));
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/dentista").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Dentista getDentista(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Dentista> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Dentista> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Dentista.class)
    public static class DentistaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DentistaConsultaController controller = (DentistaConsultaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dentistaConsulta");
            return controller.getDentista(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Dentista) {
                Dentista o = (Dentista) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Dentista.class.getName()});
                return null;
            }
        }

    }

}
