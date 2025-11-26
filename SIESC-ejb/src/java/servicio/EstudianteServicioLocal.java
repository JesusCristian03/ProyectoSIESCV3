/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package servicio;

import java.util.List;
import javax.ejb.Local;
import modelo.Estudiante;
import modelo.informacionEstudiante;

/**
 *
 * @author gacev
 */
@Local
public interface EstudianteServicioLocal {

    Estudiante buscarPorID(String no_de_control);

    Estudiante loginEstudiante(Estudiante estudiante);
    
    void insertarEstudiante(Estudiante estudiante);
    
    List<informacionEstudiante> traerEstudiantesPorApellidoPaterno(String apPaterno);
    
    void eliminarEstudiante(Estudiante e);
    
}
