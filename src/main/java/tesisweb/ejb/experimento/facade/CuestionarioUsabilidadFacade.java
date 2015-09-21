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
import tesisweb.ejb.experimento.entity.CuestionarioUsabilidad;
import tesisweb.ejb.experimento.entity.PreguntaUsabilidad;

/**
 *
 * @author root
 */
@Stateless
public class CuestionarioUsabilidadFacade extends AbstractFacade<CuestionarioUsabilidad> {

    @PersistenceContext(unitName = "tesisweb_tesisweb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuestionarioUsabilidadFacade() {
        super(CuestionarioUsabilidad.class);
    }

    public List<PreguntaUsabilidad> findAllPreguntasUsabilidad(Integer idMU) {
        Query q;
        if (idMU.compareTo(0) == 0) {
            q = em.createQuery("SELECT a FROM PreguntaUsabilidad a WHERE a.idMecanismoUsabilidad IS NULL ORDER BY a.idPreguntaUsabilidad ");
        } else {
            q = em.createQuery("SELECT a FROM PreguntaUsabilidad a WHERE a.idMecanismoUsabilidad.idMu=:xIdMU ORDER BY a.idPreguntaUsabilidad ");
            q.setParameter("xIdMU", idMU);
        }
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<PreguntaUsabilidad> tr = q.getResultList();
        return tr;
    }
}
