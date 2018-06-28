package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Dentista;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DentistaFacade extends AbstractFacade<Dentista> {

    @PersistenceContext(unitName = "goHorse_DentalClean_war_2.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public DentistaFacade() {
        super(Dentista.class);
    }    
}
