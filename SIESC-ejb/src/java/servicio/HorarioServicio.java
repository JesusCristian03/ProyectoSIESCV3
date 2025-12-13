/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.HorariosFacadeLoca;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Aulas;
import modelo.Carrera;
import modelo.Horarios;
import modelo.PeriodoEscolar;

/**
 *
 * @author gacev
 */
@Stateless
@PermitAll
public class HorarioServicio implements HorarioServicioLocal {

    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private CarreraServicioLocal carreraServicio;
    @EJB
    private HorariosFacadeLoca horariosFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Horarios> horarioDocente(String periodo, String rfc, Character tipo) {
        return horariosFacade.horarioDocente(periodo, rfc, tipo);
    }

    public List<Horarios> horarioAsignatura(String periodo, String materia, String grupo) {
        return horariosFacade.horarioAsignatura(periodo, materia, grupo);
    }

    public List<Horarios> horarioAsignatura(String periodo, String materia) {
        return horariosFacade.horarioAsignatura(periodo, materia);
    }

    public List<Horarios> horarioAsignatura(String periodo, Integer reticula, Integer semestre) {
        return horariosFacade.horarioAsignatura(periodo, reticula, semestre);
    }

    public List<Horarios> horarioAsignatura(String periodo, Integer reticula) {
        return horariosFacade.horarioAsignatura(periodo, reticula);
    }

    @Override
    public void insertarHorario(Horarios horarios) {
        horariosFacade.create(horarios);
    }

    @Override
    public List<Horarios> buscarHorariosPorGrupos(Carrera reticula, int semestre, PeriodoEscolar periodo, String nombregrupo) {
        return horariosFacade.buscarHorariosPorGrupos(reticula, semestre, periodo, nombregrupo);
    }

    @Override
    public List<Horarios> buscarHorariosPorAulas(PeriodoEscolar periodo, Aulas aula) {
        return horariosFacade.buscarHorariosPorAulas(periodo, aula);
    }

    @Override
    public void eliminar(Horarios horarios) {
        horariosFacade.remove(horarios);
    }

    @Override
    public void actualizar(Horarios horarios) {
        horariosFacade.edit(horarios);
        System.out.println("Horario a actualizar->" + horarios);
    }

    @Override
    public Horarios buscarHorarioPorEmpalme(short diaSemana, String horaInicial, String horaFinal, Aulas aula, String periodo) {
        return horariosFacade.buscarHorarioPorEmpalme(diaSemana, horaInicial, horaFinal, aula, periodo);
    }

    @Override
    public Horarios buscarHorarioPorEmpalmePorAula(short diaSemana, String horaInicial, String horaFinal,
            String grupo, Integer reticula, int semestre,
            String periodo) {
        return horariosFacade.buscarHorarioPorEmpalmePorAula(diaSemana, horaInicial, horaFinal, grupo, reticula, semestre, periodo);
    }

    @Override
    public List<Horarios> buscarHorarioPorMateria(int reticula, int semestre, String periodo, String materia) {
        Carrera reticulaObjeto = carreraServicio.buscarPorId(reticula);
        PeriodoEscolar periodoObjeto = periodoEscolarServicio.buscarPorId(periodo);

        return horariosFacade.buscarHorariosPorMateria(reticulaObjeto, semestre, periodoObjeto, materia);
    }

    @Override
    public List<Horarios> buscarHorarioPorMateriayGrupo(int reticula, int semestre, String periodo, String materia, String grupo) {
        Carrera reticulaObjeto = carreraServicio.buscarPorId(reticula);
        PeriodoEscolar periodoObjeto = periodoEscolarServicio.buscarPorId(periodo);

        return horariosFacade.buscarHorariosPorMateriayGrupo(reticulaObjeto, semestre, periodoObjeto, materia, grupo);
    }

}
