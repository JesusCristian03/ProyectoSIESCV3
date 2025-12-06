/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.List;
import javax.ejb.Local;
import modelo.RequisitosMateria;

/**
 *
 * @author cris_
 */
@Local
public interface RequisitosMateriaFacadeLocal {

    void create(RequisitosMateria requisitosMateria);

    void edit(RequisitosMateria requisitosMateria);

    void remove(RequisitosMateria requisitosMateria);

    RequisitosMateria find(Object id);

    List<RequisitosMateria> findAll();

    List<RequisitosMateria> findRange(int[] range);

    int count();
    
    RequisitosMateria buscarRequisitoMateria(String codigoM);
    
}
