package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Cliente;
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

@Named("clienteConsulta")
@SessionScoped
public class ClienteConsultaController implements Serializable {

    @EJB
    private gohorse.dentalclean.controller.ClienteFacade ejbFacade;
    private List<Cliente> items = null;
    private Cliente selected = new Cliente();

    public ClienteConsultaController() {
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }
    
    public boolean isSelectedNull() {
        return selected == null;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ClienteFacade getFacade() {
        return ejbFacade;
    }

    public Cliente prepareCreate() {
        
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/cliente").getString("ClienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        selected = new Cliente();
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/cliente").getString("ClienteUpdated"));
        
        selected = new Cliente();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/cliente").getString("ClienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cliente> getItems() {
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
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/cliente").getString("PersistenceErrorOccured"));
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/cliente").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Cliente getCliente(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Cliente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Cliente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Cliente.class)
    public static class ClienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClienteConsultaController controller = (ClienteConsultaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clienteConsulta");
            return controller.getCliente(getKey(value));
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
            if (object instanceof Cliente) {
                Cliente o = (Cliente) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cliente.class.getName()});
                return null;
            }
        }

    }

}
