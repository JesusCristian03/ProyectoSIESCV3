/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.CarreraFacadeLocal;
import DAO.GruposFacadeLocal;
import DAO.HorariosFacadeLoca;
import DAO.PeriodoEscolarFacadeLocal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Carrera;
import modelo.Grupos;
import modelo.HorarioAsignatura;
import modelo.Horarios;
import modelo.PeriodoEscolar;

/**
 *
 * @author gacev
 */
@Stateless
public class GruposServicio implements GruposServicioLocal {

    @EJB
    private HorariosFacadeLoca horariosFacade;

    @EJB
    private PeriodoEscolarFacadeLocal periodoEscolarFacade;

    @EJB
    private CarreraFacadeLocal carreraFacade;

    @EJB
    private GruposFacadeLocal gruposFacade;

    private List<HorarioAsignatura> listaHorarioAsignatura;

    public List<HorarioAsignatura> getListaHorarioAsignatura() {
        return listaHorarioAsignatura;
    }

    public void setListaHorarioAsignatura(List<HorarioAsignatura> listaHorarioAsignatura) {
        this.listaHorarioAsignatura = listaHorarioAsignatura;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Grupos> gruposActivos() {
        return gruposFacade.gruposActivos();
    }

    @Override
    public void insertarNuevoGrupo(Grupos grupo) {
        gruposFacade.create(grupo);
    }

    @Override
    public void eliminar(Grupos grupo) {
        gruposFacade.remove(grupo);
    }

    @Override
    public Grupos buscarPorId(String x) {
        return gruposFacade.find(x);
    }

    @Override
    public List<Grupos> buscarGrupoSii(Integer reticula, Integer idmateriacarrera, String periodo, String grupo) {
        return gruposFacade.buscarGrupoSii(reticula, idmateriacarrera, periodo, grupo);
    }

    @Override
    public List<Grupos> buscarGruposPorCampoNombre(Carrera reticula, Integer semestre, PeriodoEscolar periodo, String grupo) {
        return gruposFacade.buscarGruposPorCampoNombre(reticula, semestre, periodo, grupo);
    }

    @Override
    public List<String> buscarGruposCompletos(Carrera reticula, Integer semestre, PeriodoEscolar periodo) {
        List<Grupos> gruposPorMateria = gruposFacade.buscarGruposCompletos(reticula, semestre, periodo);

        // Obtener solo los nombres de grupo únicos
        List<String> gruposUnicos = gruposPorMateria.stream()
                .map(Grupos::getGrupo) // obtiene el valor del campo "grupo"
                .distinct() // elimina los repetidos
                .collect(Collectors.toList());

        // Si quieres verlos por consola:
        gruposUnicos.forEach(System.out::println);
        return gruposUnicos;
    }

    @Override
    public List<HorarioAsignatura> buscarGruposPorCampoMateriaSeleccionada(int reticula, Integer semestre, String periodo, String materia) {
        PeriodoEscolar p = periodoEscolarFacade.find(periodo);
        Carrera r = carreraFacade.find(reticula);
        System.out.println("--------------SERVICIO----------------");
        System.out.println("Carrera:" + r);
        System.out.println("Semestre:" + semestre);
        System.out.println("PeriodoEscolar:" + p);
        System.out.println("Materia:" + materia);

        List<Grupos> gruposDisponibles = gruposFacade.buscarGruposPorCampoMateria(r, semestre, p, materia);
        List<Horarios> horariosDisponibles = horariosFacade.buscarHorariosPorMateria(r, semestre, p, materia);

        //HorarioAsignatura horario = new HorarioAsignatura();
        System.out.println("Se han encontrado gruposDisponibles.size:" + gruposDisponibles.size());
        System.out.println("Se han encontrado horariosDisponibles:" + horariosDisponibles.size());

        List<HorarioAsignatura> listaHorarioAsignatura = new ArrayList<>();

        for (Grupos grupo : gruposDisponibles) {
            HorarioAsignatura horarioAsignatura = new HorarioAsignatura();
            horarioAsignatura.setGrupo(grupo.getGrupo());
            horarioAsignatura.setMateria(grupo.getMateria());
            horarioAsignatura.setAsignatura(grupo.getIdMateriaCarrera().getMateria().getNombreCompletoMateria());
            horarioAsignatura.setId(grupo.getIdGrupo());
            horarioAsignatura.setDocente(grupo.getRfc().getNombreEmpleado());
            // Recorremos los horarios y asignamos según el día
            for (Horarios horario : horariosDisponibles) {
                if (horario.getIdGrupo().equals(grupo)) { // Coincide grupo
                    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
                    switch (horario.getDiaSemana()) {
                        case 2:
                            horarioAsignatura.setLunes(
                                    formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 3:
                            horarioAsignatura.setMartes(
                                    formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 4:
                            horarioAsignatura.setMiercoles(
                                    formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 5:
                            horarioAsignatura.setJueves(
                                    formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 6:
                            horarioAsignatura.setViernes(
                                    formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 7:
                            horarioAsignatura.setSabado(
                                    formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                    }
                }
            }

            listaHorarioAsignatura.add(horarioAsignatura);
        }

        for (HorarioAsignatura h : listaHorarioAsignatura) {
            System.out.println("=================================");
            System.out.println("Grupo: " + h.getGrupo());
            System.out.println("IdGrupo: " + h.getId());
            System.out.println("Materia: " + h.getMateria());
            System.out.println("Asignatura: " + h.getAsignatura());
            System.out.println("Docente: " + h.getDocente());
            System.out.println("Lunes: " + h.getLunes());
            System.out.println("Martes: " + h.getMartes());
            System.out.println("Miércoles: " + h.getMiercoles());
            System.out.println("Jueves: " + h.getJueves());
            System.out.println("Viernes: " + h.getViernes());
            System.out.println("Sábado: " + h.getSabado());

            System.out.println("=================================");
        }

        return listaHorarioAsignatura;

    }

}
