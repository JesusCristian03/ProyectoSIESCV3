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
import modelo.RequisitosMateria;

/**
 *
 * @author cris_
 */
@Stateless
public class RequisitosMateriaFacade extends AbstractFacade<RequisitosMateria> implements RequisitosMateriaFacadeLocal {

    @PersistenceContext(unitName = "SIESC-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RequisitosMateriaFacade() {
        super(RequisitosMateria.class);
    }

    @Override
    public RequisitosMateria buscarRequisitoMateria(String codigoM) {

        String sqlHorario = "SELECT r FROM RequisitosMateria r "
                + "WHERE r.materiaRelacion.materia = :Materia "
                + "ORDER BY r.materia.materia";

        Query query = em.createQuery(sqlHorario);
       //query.setParameter("Reticula", reticula);
        query.setParameter("Materia", codigoM);

        return (RequisitosMateria) query.getSingleResult();
    }

}
