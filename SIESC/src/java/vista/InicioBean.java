/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package vista;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author valeria
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
                "itc.png",
            "inicio1.jpg",
            "inicio2.webp",
            "inicio3.webp",
            "inicio4.enc",
            "inicio5.webp"
            
        );
    }
    
    public List<String> getImagenes() {
        return imagenes;
    }
}
