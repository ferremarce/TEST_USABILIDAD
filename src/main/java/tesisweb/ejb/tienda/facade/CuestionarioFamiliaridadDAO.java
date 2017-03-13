/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.tienda.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tesisweb.ejb.tienda.entity.CuestionarioFamiliaridad;

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

    public List<CuestionarioFamiliaridad> getAllCuestionarioFamiliaridadExperimento(Integer idDise) {
        Query q = em.createQuery("SELECT a FROM CuestionarioFamiliaridad a WHERE a.idUsuario.idGrupoExperimental.idDisenho.idDisenho=:xIdDise ORDER BY a.idUsuario.fechaHoraConexion");
        q.setParameter("xIdDise", idDise);
        List<CuestionarioFamiliaridad> tr = q.getResultList();
        return tr;

    }

}
