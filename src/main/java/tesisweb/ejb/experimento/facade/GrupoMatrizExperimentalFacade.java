/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.experimento.facade;

import java.util.List;
import java.util.Random;
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

    public GrupoMatrizExperimental findSiguienteGrupoExperimental() {
        Query q = em.createQuery("SELECT a FROM GrupoMatrizExperimental a WHERE a.idDisenho.estado=true AND a.cantidadParticipante NOT IN (SELECT MAX(b.cantidadParticipante) FROM GrupoMatrizExperimental b WHERE b.idDisenho.estado=true)");
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<GrupoMatrizExperimental> tr = q.getResultList();
        GrupoMatrizExperimental grupoForUpdate;
        Random r = new Random();
        int aleatorio;
        if (!tr.isEmpty()) {
            //Se trae solo los ordenes con cantidades iguales a la que tiene cantidad mayor
            aleatorio = r.nextInt(tr.size());
            grupoForUpdate = tr.get(aleatorio);
        } else {
            //Se trae todos los ordenes
            q = em.createQuery("SELECT a FROM GrupoMatrizExperimental a WHERE a.idDisenho.estado=true");
            tr = q.getResultList();
            aleatorio = r.nextInt(tr.size());
            grupoForUpdate = tr.get(aleatorio);
        }
        //Incrementar la cantidad de participantes
        grupoForUpdate.setCantidadParticipante(grupoForUpdate.getCantidadParticipante() + 1);
        em.merge(grupoForUpdate);
        //retornar el grupo para el usuario
        return grupoForUpdate;
    }
}
