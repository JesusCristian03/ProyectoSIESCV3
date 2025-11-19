/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.AlumnosGeneralesFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.AlumnosGenerales;

/**
 *
 * @author cris_
 */
@Stateless
public class AlumnosGeneralesServicio implements AlumnosGeneralesServicioLocal {

    @EJB
    private AlumnosGeneralesFacadeLocal alumnosGeneralesFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public void insertarAlumnoGeneral(AlumnosGenerales alumnosGenerales){
        alumnosGeneralesFacade.create(alumnosGenerales);
    
    }
}
