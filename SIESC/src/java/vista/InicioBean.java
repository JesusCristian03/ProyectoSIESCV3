/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package vista;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author valer
 */


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("inicioBean")
@ViewScoped
public class InicioBean implements Serializable {

    private List<String> imagenes;


    public InicioBean() {
        imagenes = Arrays.asList(
            "itcuautla.png",
            "logo2.png",
            "itc.png"
        );
    }
    
    public List<String> getImagenes() {
        return imagenes;
    }
}
