/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.EntidadFederativaFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.EntidadFederativa;

/**
 *
 * @author cris_
 */
@Stateless
public class EntidadFederativaServicio implements EntidadFederativaServicioLocal {

    @EJB
    private EntidadFederativaFacadeLocal entidadFederativaFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public EntidadFederativa buscarEntidadFederativa(Integer x){
    return entidadFederativaFacade.find(x);
    
    }
    
    @Override
    public List<EntidadFederativa> traerListaEF(){
    return entidadFederativaFacade.findAll();
    
    }
}
