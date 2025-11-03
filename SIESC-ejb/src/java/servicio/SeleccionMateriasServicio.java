/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.EstudianteFacadeLocal;
import DAO.GruposFacadeLocal;
import DAO.MateriaFacadeLocal;
import DAO.PeriodoEscolarFacadeLocal;
import DAO.SeleccionMateriasFacadeLocal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Estudiante;
import modelo.Grupos;
import modelo.HorarioAsignatura;
import modelo.Materia;
import modelo.PeriodoEscolar;
import modelo.SeleccionMaterias;

/**
 *
 * @author cris_
 */
@Stateless
public class SeleccionMateriasServicio implements SeleccionMateriasServicioLocal {

    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;

    @EJB
    private GruposFacadeLocal gruposFacade;

    @EJB
    private MateriaFacadeLocal materiaFacade;

    @EJB
    private EstudianteFacadeLocal estudianteFacade;

    @EJB
    private PeriodoEscolarFacadeLocal periodoEscolarFacade;

    @EJB
    private SeleccionMateriasFacadeLocal seleccionMateriasFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public SeleccionMaterias insertarNuevoSeleccionMaterias(String noDeControl, HorarioAsignatura grupoSeleccionado) {//Insertar Nueva Seleccion Matieras

        SeleccionMaterias sm = new SeleccionMaterias();
        PeriodoEscolar p = periodoEscolarFacade.find(periodoEscolarServicio.periodosEscolaresActivos().get(0).getPeriodo());//20252      
        Estudiante e = estudianteFacade.find(noDeControl);//21680052
        Materia m = materiaFacade.find(grupoSeleccionado.getMateria());//1A1
        Grupos g = gruposFacade.find(grupoSeleccionado.getId());
        // Crear fecha y hora actual
        Date fechaHoraSeleccion = new Date(); // <-- aquí se crea

        //Global
        sm.setPeriodo(p);
        sm.setNoDeControl(e);
        sm.setMateria(m);
        sm.setGrupo(grupoSeleccionado.getGrupo());
        sm.setStatusSeleccion('S');
        sm.setFechaHoraSeleccion(fechaHoraSeleccion);
        sm.setIdGrupo(g);

        seleccionMateriasFacade.create(sm);

        return sm;
    }
}
