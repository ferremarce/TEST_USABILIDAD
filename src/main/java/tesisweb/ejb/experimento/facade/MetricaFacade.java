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
import tesisweb.ejb.experimento.entity.Metrica;

/**
 *
 * @author root
 */
@Stateless
public class MetricaFacade extends AbstractFacade<Metrica> {

    @PersistenceContext(unitName = "tesisweb_tesisweb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetricaFacade() {
        super(Metrica.class);
    }

    public List<Metrica> findAllbyUsuario(Integer idUsuario) {
        Query q = em.createQuery("SELECT a FROM Metrica a WHERE a.idUsuario.idUsuario=:xIdUsuario");
        q.setParameter("xIdUsuario", idUsuario);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<Metrica> tr = q.getResultList();
        return tr;
    }

    public List<Metrica> findAllbyDisenho(Integer idDisenho) {
        Query q = em.createQuery("SELECT a FROM Metrica a WHERE a.idUsuario.idGrupoExperimental.idDisenho.idDisenho=:xIdDis");
        q.setParameter("xIdDis", idDisenho);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<Metrica> tr = q.getResultList();
        return tr;
    }

    public List<Metrica> findAllbyDisenhoMecanismo(Integer idDisenho, Integer idMU) {
        Query q = em.createQuery("SELECT a FROM Metrica a WHERE a.idUsuario.idGrupoExperimental.idDisenho.idDisenho=:xIdDis AND a.idMecanismoUsabilidad.idMu=:xIdMU");
        q.setParameter("xIdDis", idDisenho);
        q.setParameter("xIdMU", idMU);
        //LOG.log(Level.INFO, "findAllbyTipo: {0}", sql);
        List<Metrica> tr = q.getResultList();
        return tr;
    }

    public Integer getValorRespuestaCuestionario(Integer usuario, Integer idPreg) {
        Query q = em.createQuery("SELECT a FROM CuestionarioUsabilidad a WHERE a.idUsuario.idUsuario=:xIdUsu AND a.idPreguntaUsabilidad.idPreguntaUsabilidad=:xIdPreg");
        q.setParameter("xIdUsu", usuario);
        q.setParameter("xIdPreg", idPreg);
        List<CuestionarioUsabilidad> tr = q.getResultList();
        if (!tr.isEmpty()) {
            return tr.get(0).getValorPregunta();
        } else {
            return -1;
        }
    }

}
