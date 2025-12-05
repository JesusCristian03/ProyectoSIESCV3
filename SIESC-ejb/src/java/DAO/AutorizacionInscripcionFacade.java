/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.AutorizacionInscripcion;

/**
 *
 * @author cris_
 */
@Stateless
public class AutorizacionInscripcionFacade extends AbstractFacade<AutorizacionInscripcion> implements AutorizacionInscripcionFacadeLocal {

    @PersistenceContext(unitName = "SIESC-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutorizacionInscripcionFacade() {
        super(AutorizacionInscripcion.class);
    }

    @Override
    public List<AutorizacionInscripcion> buscarAutorizaciones(String periodo, String noControl) {
        List<AutorizacionInscripcion> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT a FROM  AutorizacionInscripcion a WHERE a.periodo.periodo=:periodo and a.noDeControl.noDeControl=:noControl order by a.noDeControl.noDeControl";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("noControl", noControl);

        lista = (List<AutorizacionInscripcion>) (queryPersonal.getResultList());

        return lista;

    }

}
