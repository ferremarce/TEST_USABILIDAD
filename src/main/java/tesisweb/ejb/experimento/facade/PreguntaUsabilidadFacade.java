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
import tesisweb.ejb.experimento.entity.PreguntaUsabilidad;

/**
 *
 * @author root
 */
@Stateless
public class PreguntaUsabilidadFacade extends AbstractFacade<PreguntaUsabilidad> {
    @PersistenceContext(unitName = "tesisweb_tesisweb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreguntaUsabilidadFacade() {
        super(PreguntaUsabilidad.class);
    }
    public List<PreguntaUsabilidad> findAllbyCriterio(String criterio) {
        Query q = em.createQuery("SELECT a FROM PreguntaUsabilidad a WHERE UPPER(a.idMecanismoUsabilidad.nombreMecanismo) LIKE :xCriterio ORDER BY a.idMecanismoUsabilidad, a.idPreguntaUsabilidad ");
        q.setParameter("xCriterio", "%" + criterio.toUpperCase() + "%");
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<PreguntaUsabilidad> tr = q.getResultList();
        return tr;
    }
    
}
