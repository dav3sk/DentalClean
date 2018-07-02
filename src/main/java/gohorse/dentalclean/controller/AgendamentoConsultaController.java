package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Agendamento;
import gohorse.dentalclean.controller.util.JsfUtil;
import gohorse.dentalclean.controller.util.JsfUtil.PersistAction;
import gohorse.dentalclean.model.entity.Cliente;
import gohorse.dentalclean.model.entity.Dentista;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;


@Named("agendamentoConsulta")
@SessionScoped
public class AgendamentoConsultaController implements Serializable {
    
    @EJB private gohorse.dentalclean.controller.AgendamentoFacade ejbAgendamentoFacade;
    @EJB private gohorse.dentalclean.controller.ClienteFacade ejbClienteFacade;
    @EJB private gohorse.dentalclean.controller.DentistaFacade ejbDentistaFacade;
    
    private ScheduleModel eventModel;

    
    private List<Agendamento> agendamentoItems = null;
    private List<Dentista> dentistaItems = null;
    private List<Cliente> clienteItems = null;
    
    private Dentista dentista = new Dentista();
    private Cliente cliente = new Cliente();
    private Agendamento selected = new Agendamento();
    
    @PostConstruct
    public void init() {
        agendamentoItems = getAgendamentoItems();
        eventModel = new DefaultScheduleModel();
        for(Agendamento agendamento : agendamentoItems){
            Date dataFim = new Date(agendamento.getData().getTime() + agendamento.getHorario().getTime());
            eventModel.addEvent(new DefaultScheduleEvent(agendamento.getCliente().getNome(), agendamento.getData(), dataFim, agendamento));
        }
    }

    public Agendamento getSelected() {
        return selected;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
    
    public void setSelected(Agendamento selected) {
        this.selected = selected;
    }

    private AgendamentoFacade getAgendamentoFacade() {
        return ejbAgendamentoFacade;
    }

    private ClienteFacade getClienteFacade() {
        return ejbClienteFacade;
    }

    private DentistaFacade getDentistaFacade() {
        return ejbDentistaFacade;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        System.out.println("set-agendamentodentista_" + dentista);
        this.dentista = dentista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        System.out.println("set-agendamentocliente_" + cliente);
        this.cliente = cliente;
    }
    
    public Agendamento prepareCreate() {
        selected = new Agendamento();
        return selected;
    }

    public void create() {
        selected.setCliente(cliente);
        selected.setDentista(dentista);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/agendamento").getString("AgendamentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            agendamentoItems = null;    // Invalidate list of items to trigger re-query.
        }
        
        selected = new Agendamento();
        cliente = new Cliente();
        dentista = new Dentista();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/agendamento").getString("AgendamentoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/agendamento").getString("AgendamentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            agendamentoItems = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Agendamento> getAgendamentoItems() {
        if (agendamentoItems == null) {
            agendamentoItems = getAgendamentoFacade().findAll();
        }
        return agendamentoItems;
    }
    
    public List<Dentista> getDentistaItems() {
        if (dentistaItems == null) {
            dentistaItems = getDentistaFacade().findAll();
        }
        return dentistaItems;
    }
        
    public List<Cliente> getClienteItems() {
        if (clienteItems == null) {
            clienteItems = getClienteFacade().findAll();
        }
        return clienteItems;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getAgendamentoFacade().edit(selected);
                } else {
                    getAgendamentoFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/agendamento").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/agendamento").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Agendamento getAgendamento(java.lang.Long id) {
        return getAgendamentoFacade().find(id);
    }

    public List<Agendamento> getItemsAvailableSelectMany() {
        return getAgendamentoFacade().findAll();
    }

    public List<Agendamento> getItemsAvailableSelectOne() {
        return getAgendamentoFacade().findAll();
    }

    @FacesConverter(forClass=Agendamento.class)
    public static class AgendamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgendamentoConsultaController controller = (AgendamentoConsultaController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agendamentoCadastro");
            return controller.getAgendamento(getKey(value));
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
            if (object instanceof Agendamento) {
                Agendamento o = (Agendamento) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Agendamento.class.getName()});
                return null;
            }
        }

    }

}
