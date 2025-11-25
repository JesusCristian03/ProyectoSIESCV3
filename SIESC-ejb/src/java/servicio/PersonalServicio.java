/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.PersonalFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Personal;
import modelo.informacionDocente;

/**
 *
 * @author gacev
 */
@Stateless
public class PersonalServicio implements PersonalServicioLocal {


    @EJB
    private PersonalFacadeLocal personalFacade;
    
    
    
    
    

    // Add business logic below. (Right-clpersonalFacadeick in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Personal buscarPorId(String rfc) {
        return personalFacade.find(rfc);
    }

    @Override
    public List<Personal> buscarTodos() {
        return personalFacade.findAll();
    }

    @Override
    public List<Personal> personalActivos() {
        return personalFacade.personalesActivos();
    }
    @Override
    public List<Personal> personalPorArea(String clavearea){
    return personalFacade.personalPorDepartamento(clavearea);
    
    }
    
    @Override
    public  void eliminarDocentes (Personal docente){
      personalFacade.edit(docente);
    }
    
    @Override
    public List<informacionDocente> traerDocentesPorApellidoPaterno(String apPaterno){
        List<informacionDocente> lie=new ArrayList<>();
        List<Personal> listaDocente= personalFacade.traerDocenteApellidoPaterno(apPaterno);
        if(listaDocente !=null && !listaDocente.isEmpty()){
        System.out.println("encontre" + listaDocente.size() + "docentes");
        for (int i = 0; i < listaDocente.size(); i++){
            informacionDocente nuevoDocente = new informacionDocente();
            Personal p=listaDocente.get(i);
        nuevoDocente.setId(p.getRfc());
        nuevoDocente.setNombre(p.getNombreEmpleado());
        nuevoDocente.setCurp(p.getCurpEmpleado());
        nuevoDocente.setApellidoPaterno(p.getApellidoPaterno());
        nuevoDocente.setApellidoMaterno(p.getApellidoMaterno());
        nuevoDocente.setCorreo(p.getCorreoElectronico());
        
        lie.add(nuevoDocente);
        
        }
        }
        return lie;
    //return personalFacade.traerDocenteApellidoPaterno(apPaterno);
    }

}
