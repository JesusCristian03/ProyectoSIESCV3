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
import modelo.informacionDocente;
import servicio.PersonalServicioLocal;

/**
 *
 * @author valer
 */
@Named(value = "docenteBusquedaBean")
@SessionScoped
public class docenteBusquedaBean implements Serializable {

    @EJB
    private PersonalServicioLocal personalServicio;

    /**
     * Creates a new instance of docenteBusquedaBean
     */
    private List<informacionDocente>listaPersonal;
    private String filtro;

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
    
    
    
    
    public docenteBusquedaBean() {
    }

    public List<informacionDocente> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<informacionDocente> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }
    
    
    public void inicializacion(){
        System.out.println("ejecuto correctamente");
  }
    public void cambioBusqueda(){
        System.out.println("Filtro"+filtro);
        listaPersonal = personalServicio.traerDocentesPorApellidoPaterno(filtro);
        System.out.println("son listeDocetes.size" + listaPersonal.size());
    
    }
    
}
