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
import modelo.AvisosReinscripcion;
import modelo.Estudiante;
import modelo.PeriodoEscolar;

/**
 *
 * @author cris_
 */
@Stateless
public class AvisosReinscripcionFacade extends AbstractFacade<AvisosReinscripcion> implements AvisosReinscripcionFacadeLocal {

    @PersistenceContext(unitName = "SIESC-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AvisosReinscripcionFacade() {
        super(AvisosReinscripcion.class);
    }

    @Override
    public AvisosReinscripcion buscarAvisoReinscripcion(Estudiante estudiante, PeriodoEscolar periodo) {
        AvisosReinscripcion av = null;
        String sqlHorario = "SELECT a FROM AvisosReinscripcion a WHERE a.semeste = :Semestre "
                + "AND a.noDeControl = :NoDeControl AND a.periodo = :Periodo";

        Query queryPersonal = em.createQuery(sqlHorario);
        queryPersonal.setParameter("Periodo", periodo);
        queryPersonal.setParameter("NoDeControl", estudiante);
        queryPersonal.setParameter("Semestre", estudiante.getSemestre());

        List<AvisosReinscripcion> resultados = queryPersonal.getResultList();
        if (!resultados.isEmpty()) {
            av = resultados.get(0); // Devuelve solo el primero
        }

        return av;
    }

}
