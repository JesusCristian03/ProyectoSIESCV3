/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.AutorizacionInscripcionFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.AutorizacionInscripcion;

/**
 *
 * @author cris_
 */
@Stateless
public class AutorizacionInscripcionServicio implements AutorizacionInscripcionServicioLocal {

    @EJB
    private AutorizacionInscripcionFacadeLocal autorizacionInscripcionFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void eliminar(AutorizacionInscripcion ai){
    autorizacionInscripcionFacade.remove(ai);
    }
    @Override
    public void insertar (AutorizacionInscripcion x){
    autorizacionInscripcionFacade.create(x);
    
    }
    @Override
    public List<AutorizacionInscripcion> buscarAutorizacionesAlumno(String periodo, String noControl){
    
    return autorizacionInscripcionFacade.buscarAutorizaciones(periodo, noControl);
    }
}
