/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.experimento.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tesisweb.ejb.experimento.entity.DisenhoExperimental;

/**
 *
 * @author root
 */
@Stateless
public class DisenhoExperimentalFacade extends AbstractFacade<DisenhoExperimental> {

    @PersistenceContext(unitName = "tesisweb_tesisweb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisenhoExperimentalFacade() {
        super(DisenhoExperimental.class);
    }

}
