/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package servicio;

import java.util.List;
import javax.ejb.Local;
import modelo.Personal;
import modelo.informacionDocente;

/**
 *
 * @author gacev
 */
@Local
public interface PersonalServicioLocal {

    Personal buscarPorId(String rfc);

    List<Personal> buscarTodos();
    
    List<Personal> personalActivos();
    
    List<Personal> personalPorArea(String clavearea);
    public List<informacionDocente> traerDocentesPorApellidoPaterno(String apPaterno);
    void eliminarDocentes (Personal docente);
    
}
