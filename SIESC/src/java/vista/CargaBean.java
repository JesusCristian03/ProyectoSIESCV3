/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author valeria
 */

@Named
@ViewScoped
public class CargaBean implements Serializable {

    private List<MateriaCarga> materias;

    @PostConstruct
    public void init() {
        materias = new ArrayList<>();

        materias.add(new MateriaCarga("MAT101", "Cálculo Diferencial", "A",
                "07:00-09:00", "—", "07:00-09:00", "—", "—"));

        materias.add(new MateriaCarga("FIS102", "Física I", "B",
                "—", "09:00-11:00", "—", "09:00-11:00", "—"));

        materias.add(new MateriaCarga("PRO203", "Programación Web", "A",
                "11:00-13:00", "—", "11:00-13:00", "—", "—"));

        materias.add(new MateriaCarga("BD204", "Bases de Datos", "C",
                "—", "13:00-15:00", "—", "13:00-15:00", "—"));

        materias.add(new MateriaCarga("RED301", "Redes", "A",
                "15:00-17:00", "—", "15:00-17:00", "—", "—"));
    }

    public List<MateriaCarga> getMaterias() {
        return materias;
    }

    // Acciones del menú
    public void modificar(MateriaCarga m) {
        System.out.println("Modificar " + m.getAsignatura());
    }

    public void eliminar(MateriaCarga m) {
        materias.remove(m);
    }

    public void verDetalle(MateriaCarga m) {
        System.out.println("Detalle " + m.getAsignatura());
    }
    


}

