package gohorse.dentalclean.controller;

import gohorse.dentalclean.controller.util.AgendamentoEvent;
import gohorse.dentalclean.model.entity.Agendamento;
import gohorse.dentalclean.controller.util.JsfUtil;
import gohorse.dentalclean.controller.util.JsfUtil.PersistAction;
import gohorse.dentalclean.model.entity.Cliente;
import gohorse.dentalclean.model.entity.Dentista;

import java.io.Serializable;
import java.time.LocalTime;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named("principalController")
@SessionScoped
public class PrincipalController implements Serializable {

    @EJB
    private gohorse.dentalclean.controller.AgendamentoFacade ejbAgendamentoFacade;
    @EJB
    private gohorse.dentalclean.controller.ClienteFacade ejbClienteFacade;
    @EJB
    private gohorse.dentalclean.controller.DentistaFacade ejbDentistaFacade;
    @EJB
    private gohorse.dentalclean.controller.SecretariaFacade ejbSecretariaFacade;

    private ScheduleModel eventModel;
    private ScheduleEvent consulta = new AgendamentoEvent();

    private List<Agendamento> agendamentoItems = null;
    private List<Dentista> dentistaItems = null;
    private List<Cliente> clienteItems = null;

    private Dentista dentista = new Dentista();
    private Cliente cliente = new Cliente();
    private Agendamento selected = new Agendamento();

    private boolean viewChange;

    @PostConstruct
    public void init() {
        System.out.println("gohorse.dentalclean.controller.AgendamentoConsultaController.init()");
        agendamentoItems = getAgendamentoItems();
        eventModel = new DefaultScheduleModel();
        for (Agendamento agendamento : agendamentoItems) {
            if (agendamento.getStatus().equals("PENDENTE")) {
                eventModel.addEvent(new AgendamentoEvent(agendamento));
            }
        }
        viewChange = false;
    }

    public Agendamento getSelected() {
        return selected;
    }

    public ScheduleModel getEventModel() {
        System.out.println(LocalTime.now() + " - gohorse.dentalclean.controller.AgendamentoConsultaController.getEventModel()");
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        consulta = (AgendamentoEvent) selectEvent.getObject();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        selected = (Agendamento) event.getScheduleEvent().getData();
        consulta = (AgendamentoEvent) event.getScheduleEvent();
        System.out.println("gohorse.dentalclean.controller.AgendamentoConsultaController.onEventMove() " + selected);
        persist(PersistAction.UPDATE, "Agendamento movido para:" + selected.getDataHorario());
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

    public ScheduleEvent getConsulta() {
        return consulta;
    }

    public void setConsulta(ScheduleEvent consulta) {
        this.consulta = (AgendamentoEvent) consulta;
    }

    public boolean isViewChange() {
        return viewChange;
    }

    public void setViewChange(boolean viewChange) {
        this.viewChange = viewChange;
    }

    public void update() {
        selected = (Agendamento) consulta.getData();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/agendamento").getString("AgendamentoUpdated"));
    }

    public void confirm() {
        selected = (Agendamento) consulta.getData();
        selected.setStatus("CONFIRMADO");
        System.out.println("gohorse.dentalclean.controller.AgendamentoConsultaController.confirm() " + selected);
        persist(PersistAction.UPDATE, "Agendamento confirmado.");
    }

    public void destroy() {
        System.out.println("gohorse.dentalclean.controller.AgendamentoConsultaController.destroy()");
        selected = (Agendamento) consulta.getData();
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
                    System.out.println("gohorse.dentalclean.controller.AgendamentoConsultaController.persist() " + selected);
                    getAgendamentoFacade().edit(selected);
                } else {
                    getAgendamentoFacade().remove(selected);
                    eventModel.deleteEvent(consulta);
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

    public SecretariaFacade getSecretariaFacade() {
        return ejbSecretariaFacade;
    }

    public int getAgendamentoCount() {
        return getAgendamentoFacade().findAll().size();
    }

    public int getDentistaCount() {
        return getDentistaFacade().findAll().size();
    }

    public int getSecretariaCount() {
        return getSecretariaFacade().findAll().size();
    }

    public int getClienteCount() {
        return getClienteFacade().findAll().size();
    }

    @FacesConverter(forClass = Agendamento.class)
    public static class AgendamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgendamentoConsultaController controller = (AgendamentoConsultaController) facesContext.getApplication().getELResolver().
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
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ""; //To change body of generated methods, choose Tools | Templates.
        }
    }
}
