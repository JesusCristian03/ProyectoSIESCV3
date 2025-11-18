/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import modelo.Carrera;
import modelo.Estudiante;

/**
 *
 * @author cris_
 */
@Named(value = "altaEstudiantesBean")
@SessionScoped
public class altaEstudiantesBean implements Serializable {
    Estudiante estudiante=new Estudiante();
    
    List<Carrera> listaCarreras;
    /**
     * Creates a new instance of altaEstudiantesBean
     */
    public altaEstudiantesBean() {
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }
    
    
}
