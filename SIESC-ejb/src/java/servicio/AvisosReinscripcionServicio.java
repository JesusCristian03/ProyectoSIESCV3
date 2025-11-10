/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.AvisosReinscripcionFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.AvisosReinscripcion;
import modelo.Estudiante;
import modelo.PeriodoEscolar;

/**
 *
 * @author cris_
 */
@Stateless
public class AvisosReinscripcionServicio implements AvisosReinscripcionServicioLocal {

    @EJB
    private AvisosReinscripcionFacadeLocal avisosReinscripcionFacade;


    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public AvisosReinscripcion buscarAvisoReinscripcion(Estudiante estudiante, PeriodoEscolar periodo){
        return avisosReinscripcionFacade.buscarAvisoReinscripcion(estudiante, periodo);  
    }
}
