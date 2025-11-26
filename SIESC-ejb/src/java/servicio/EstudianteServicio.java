/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.EstudianteFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Estudiante;
import modelo.informacionEstudiante;

/**
 *
 * @author gacev
 */
@Stateless
public class EstudianteServicio implements EstudianteServicioLocal {

    @EJB
    private EstudianteFacadeLocal estudianteFacade;

    @Override
    public Estudiante buscarPorID(String no_de_control) {
        return estudianteFacade.find(no_de_control);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Estudiante loginEstudiante(Estudiante estudiante) {
        return estudianteFacade.loginEstudiante(estudiante);
    }

    @Override
    public void insertarEstudiante(Estudiante estudiante) {
        estudianteFacade.create(estudiante);
    }

    @Override
    public void eliminarEstudiante(Estudiante e) {
        estudianteFacade.edit(e);
    }

    @Override
    public List<informacionEstudiante> traerEstudiantesPorApellidoPaterno(String apPaterno) {
        List<informacionEstudiante> lie = new ArrayList<>();
        List<Estudiante> listaEstudiante = estudianteFacade.traerEstudiantesPorApellidoPaterno(apPaterno);

        if (listaEstudiante != null && !listaEstudiante.isEmpty()) {
            System.out.println("Encontre " + listaEstudiante.size() + " estudiantes");
            for (int i = 0; i < listaEstudiante.size(); i++) {
                informacionEstudiante nuevoEstudiante = new informacionEstudiante();
                Estudiante e=listaEstudiante.get(i);
                
                nuevoEstudiante.setId(e.getNoDeControl());
                nuevoEstudiante.setNombre(e.getNombreAlumno());
                nuevoEstudiante.setCurp(e.getCurpAlumno());
                nuevoEstudiante.setCarrera(e.getCarrera());
                nuevoEstudiante.setApellidoPaterno(e.getApellidoPaterno());
                nuevoEstudiante.setApellidoMaterno(e.getApellidoPaterno());
                
                lie.add(nuevoEstudiante);
                
            }

        }

        return lie;
    }

}
