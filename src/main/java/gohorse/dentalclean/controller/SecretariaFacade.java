/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gohorse.dentalclean.controller;

import gohorse.dentalclean.model.entity.Secretaria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dav3s
 */
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
