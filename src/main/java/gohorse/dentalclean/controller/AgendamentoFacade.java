package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Agendamento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AgendamentoFacade extends AbstractFacade<Agendamento> {

    @PersistenceContext(unitName = "goHorse_DentalClean_war_2.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendamentoFacade() {
        super(Agendamento.class);
    }
    
}
