package gohorse.dentalclean.controller.util;

import gohorse.dentalclean.model.entity.*;
import java.util.Date;

public class AgendamentoEvent extends org.primefaces.model.DefaultScheduleEvent {

    private Agendamento agendamento;

    public AgendamentoEvent(Agendamento agendamento) {
        super(agendamento.getCliente().getNome(), agendamento.getDataHorario(), new Date(agendamento.getDataHorario().getTime() + (1L * (60L * 60L * 1000L))));
        this.agendamento = agendamento;
    }
    
    public AgendamentoEvent(){
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
    
    @Override
    public Object getData() {
        updateEvent();
        return agendamento;
    }
    
    public void setData(Agendamento agendamento) {
        super.setStartDate(agendamento.getDataHorario());
        super.setEndDate(new Date(agendamento.getDataHorario().getTime() + (1L * (60L * 60L * 1000L))));
        this.agendamento = agendamento;
    }
    
    public void updateEvent() {
        if(agendamento != null){
            super.setStartDate(agendamento.getDataHorario());
            super.setEndDate(new Date(agendamento.getDataHorario().getTime() + (1L * (60L * 60L * 1000L))));
        }
    }

    @Override
    public String toString() {
        return agendamento.toString();
    }
    
}
