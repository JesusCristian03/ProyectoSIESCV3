/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import modelo.informacionEstudiante;
import servicio.EstudianteServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "alumnosBusquedaBean")
@SessionScoped
public class alumnosBusquedaBean implements Serializable {

    @EJB
    private EstudianteServicioLocal estudianteServicio;

    /**
     * Creates a new instance of alumnosBusquedaBean
     */
    private List<informacionEstudiante> listaAlumnos;
    private String filtro;

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public alumnosBusquedaBean() {
    }

    public List<informacionEstudiante> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<informacionEstudiante> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public void inicializacion() {
        System.out.println("Si ejecuto correctamente inicializacion");
    }

    public void cambioBusqueda() {
        System.out.println("Filtro "+filtro);
        listaAlumnos = estudianteServicio.traerEstudiantesPorApellidoPaterno(filtro);
        System.out.println("Son en ListaAlumnos.size() "+listaAlumnos.size());

    }

}
