/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Secretária;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dav3s
 */
@Stateless
public class SecretáriaFacade extends AbstractFacade<Secretária> {

    @PersistenceContext(unitName = "goHorse_DentalClean_war_2.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecretáriaFacade() {
        super(Secretária.class);
    }
    
}
