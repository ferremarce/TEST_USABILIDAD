/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.experimento.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;

/**
 *
 * @author root
 */
@Stateless
public class GrupoMatrizExperimentalFacade extends AbstractFacade<GrupoMatrizExperimental> {

    @PersistenceContext(unitName = "tesisweb_tesisweb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoMatrizExperimentalFacade() {
        super(GrupoMatrizExperimental.class);
    }

    public List<GrupoMatrizExperimental> findAllbyDisenho(Integer idDisenho) {
        Query q = em.createQuery("SELECT a FROM GrupoMatrizExperimental a WHERE a.idDisenho.idDisenho=:xIdDisenho ORDER BY a.idGrupo ");
        q.setParameter("xIdDisenho", idDisenho);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<GrupoMatrizExperimental> tr = q.getResultList();
        return tr;
    }
}
