/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.RequisitosMateriaFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.RequisitosMateria;

/**
 *
 * @author cris_
 */
@Stateless
public class RequisitosMateriaServicio implements RequisitosMateriaServicioLocal {

    @EJB
    private RequisitosMateriaFacadeLocal requisitosMateriaFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public RequisitosMateria encontrarAntecedenteMateria(String codigoM){
    return requisitosMateriaFacade.buscarRequisitoMateria(codigoM);
    
    }
    
}
