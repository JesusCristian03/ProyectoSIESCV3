/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;
import javax.ejb.Local;
import modelo.AlumnosGenerales;

/**
 *
 * @author cris_
 */
@Local
public interface AlumnosGeneralesFacadeLocal {

    void create(AlumnosGenerales alumnosGenerales);

    void edit(AlumnosGenerales alumnosGenerales);

    void remove(AlumnosGenerales alumnosGenerales);

    AlumnosGenerales find(Object id);

    List<AlumnosGenerales> findAll();

    List<AlumnosGenerales> findRange(int[] range);

    int count();
    
}
