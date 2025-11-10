/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;
import javax.ejb.Local;
import modelo.AvisosReinscripcion;
import modelo.Estudiante;
import modelo.PeriodoEscolar;

/**
 *
 * @author cris_
 */
@Local
public interface AvisosReinscripcionFacadeLocal {

    void create(AvisosReinscripcion avisosReinscripcion);

    void edit(AvisosReinscripcion avisosReinscripcion);

    void remove(AvisosReinscripcion avisosReinscripcion);

    AvisosReinscripcion find(Object id);

    List<AvisosReinscripcion> findAll();

    List<AvisosReinscripcion> findRange(int[] range);

    int count();
    
    AvisosReinscripcion buscarAvisoReinscripcion(Estudiante estudiante, PeriodoEscolar periodo);
    
}
