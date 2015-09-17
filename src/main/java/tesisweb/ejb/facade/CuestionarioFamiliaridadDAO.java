/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tesisweb.ejb.entity.CuestionarioFamiliaridad;

/**
 *
 * @author root
 */
@Stateless
public class CuestionarioFamiliaridadDAO extends AbstractFacade<CuestionarioFamiliaridad> {
    @PersistenceContext(unitName = "tesisweb_tesisweb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuestionarioFamiliaridadDAO() {
        super(CuestionarioFamiliaridad.class);
    }
    
}
