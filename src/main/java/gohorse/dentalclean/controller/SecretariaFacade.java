package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Secretaria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SecretariaFacade extends AbstractFacade<Secretaria> {

    @PersistenceContext(unitName = "goHorse_DentalClean_war_2.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecretariaFacade() {
        super(Secretaria.class);
    }
    
}
