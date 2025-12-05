/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;
import javax.ejb.Local;
import modelo.AutorizacionInscripcion;

/**
 *
 * @author cris_
 */
@Local
public interface AutorizacionInscripcionFacadeLocal {

    void create(AutorizacionInscripcion autorizacionInscripcion);

    void edit(AutorizacionInscripcion autorizacionInscripcion);

    void remove(AutorizacionInscripcion autorizacionInscripcion);

    AutorizacionInscripcion find(Object id);

    List<AutorizacionInscripcion> findAll();

    List<AutorizacionInscripcion> findRange(int[] range);

    int count();
    
    List<AutorizacionInscripcion> buscarAutorizaciones(String periodo, String noControl);
    
}
