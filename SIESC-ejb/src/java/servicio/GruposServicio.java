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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
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
@PermitAll
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
    public Grupos buscarPorIdInt(int x) {
        return gruposFacade.find(x);
    }

    @Override
    public void actualizar(Grupos g) {
        gruposFacade.edit(g);

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

        System.out.println("--------------SERVICIO MATERIA----------------");
        System.out.println("Carrera:" + r);
        System.out.println("Semestre:" + semestre);
        System.out.println("PeriodoEscolar:" + p);
        System.out.println("Materia:" + materia);

        List<Grupos> gruposDisponibles = gruposFacade.buscarGruposPorCampoMateria(r, semestre, p, materia);
        List<Horarios> horariosDisponibles = horariosFacade.buscarHorariosPorMateria(r, semestre, p, materia);
        //HorarioAsignatura horario = new HorarioAsignatura();
        System.out.println("Se han encontrado gruposDisponibles.size:" + gruposDisponibles.size());
        System.out.println("Se han encontrado horariosDisponibles:" + horariosDisponibles.size());
        return acomodarListaHorarios(gruposDisponibles, horariosDisponibles);

    }

    @Override
    public List<HorarioAsignatura> buscarGruposPorDepartamento(int reticula, Integer semestre, String periodo, String claveArea) {
        PeriodoEscolar p = periodoEscolarFacade.find(periodo);
        Carrera r = carreraFacade.find(reticula);

        System.out.println("--------------SERVICIO MATERIA----------------");
        System.out.println("Carrera:" + r);
        System.out.println("Semestre:" + semestre);
        System.out.println("PeriodoEscolar:" + p);

        List<Grupos> gruposDisponibles = gruposFacade.buscarGruposPorCampoDepartamento(r, semestre, p, claveArea);
        List<Horarios> horariosDisponibles = horariosFacade.buscarHorarioPorDepartamento(periodo, reticula, semestre, claveArea);
        //HorarioAsignatura horario = new HorarioAsignatura();
        System.out.println("Se han encontrado gruposDisponibles.size:" + gruposDisponibles.size());
        System.out.println("Se han encontrado horariosDisponibles:" + horariosDisponibles.size());
        return acomodarListaHorarios(gruposDisponibles, horariosDisponibles);

    }

    @Override
    public List<HorarioAsignatura> buscarGruposPorCampoGrupoSeleccionada(int reticula, Integer semestre, String periodo, String grupo) {
        PeriodoEscolar p = periodoEscolarFacade.find(periodo);
        Carrera r = carreraFacade.find(reticula);

        System.out.println("--------------SERVICIO GRUPO----------------");
        System.out.println("Carrera:" + r);
        System.out.println("Semestre:" + semestre);
        System.out.println("PeriodoEscolar:" + p);
        System.out.println("Grupo:" + grupo);

        List<Grupos> gruposDisponibles = gruposFacade.buscarGruposPorCampoGrupo(r, semestre, p, grupo);
        List<Horarios> horariosDisponibles = horariosFacade.buscarHorariosPorGrupos(r, semestre, p, grupo);
        //HorarioAsignatura horario = new HorarioAsignatura();
        System.out.println("Se han encontrado gruposDisponibles.size:" + gruposDisponibles.size());
        System.out.println("Se han encontrado horariosDisponibles:" + horariosDisponibles.size());
        return acomodarListaHorarios(gruposDisponibles, horariosDisponibles);

    }

    public List<HorarioAsignatura> acomodarListaHorarios(List<Grupos> gruposDisponibles, List<Horarios> horariosDisponibles) {

        List<HorarioAsignatura> listaHorarioAsignaturas = new ArrayList<>();

        for (Grupos grupo : gruposDisponibles) {
            HorarioAsignatura horarioAsignatura = new HorarioAsignatura();
            horarioAsignatura.setGrupo(grupo.getGrupo());
            horarioAsignatura.setMateria(grupo.getMateria());
            horarioAsignatura.setAsignatura(grupo.getIdMateriaCarrera().getMateria().getNombreCompletoMateria());
            horarioAsignatura.setId(grupo.getIdGrupo());
            // ===== VALIDACIÓN NUEVA =====
            if (grupo.getRfc() != null) {
                // el docente sí existe
                horarioAsignatura.setDocente(
                        grupo.getRfc().getNombreEmpleado() + " "
                        + grupo.getRfc().getApellidoPaterno() + " "
                        + grupo.getRfc().getApellidoMaterno());
            } else {
                // no existe docente asignado
                horarioAsignatura.setDocente("Sin docente");
            }
            // ============================
            // Recorremos los horarios y asignamos según el día
            for (Horarios horario : horariosDisponibles) {
                if (horario.getIdGrupo().equals(grupo)) { // Coincide grupo
                    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
                    switch (horario.getDiaSemana()) {
                        case 2:
                            horarioAsignatura.setLunes(
                                    (horarioAsignatura.getLunes() != null ? horarioAsignatura.getLunes() + "   " : "")
                                    + formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 3:
                            horarioAsignatura.setMartes(
                                    (horarioAsignatura.getMartes() != null ? horarioAsignatura.getMartes() + "   " : "")
                                    + formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 4:
                            horarioAsignatura.setMiercoles(
                                    (horarioAsignatura.getMiercoles() != null ? horarioAsignatura.getMiercoles() + "   " : "")
                                    + formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 5:
                            horarioAsignatura.setJueves(
                                    (horarioAsignatura.getJueves() != null ? horarioAsignatura.getJueves() + "   " : "")
                                    + formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 6:
                            horarioAsignatura.setViernes(
                                    (horarioAsignatura.getViernes() != null ? horarioAsignatura.getViernes() + "   " : "")
                                    + formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                        case 7:
                            horarioAsignatura.setSabado(
                                    (horarioAsignatura.getSabado() != null ? horarioAsignatura.getSabado() + "   " : "")
                                    + formatoHora.format(horario.getHoraInicial()) + " - " + formatoHora.format(horario.getHoraFinal())
                            );
                            break;
                    }
                }
            }

            listaHorarioAsignaturas.add(horarioAsignatura);
        }
        /**
         * --------------------------------------------- ORDENAMIENTO POR: 1.
         * Materia (alfabético) 2. Grupo (alfabético)
         * ---------------------------------------------
         */
        listaHorarioAsignaturas.sort(
                Comparator.comparing(HorarioAsignatura::getMateria)
                        .thenComparing(HorarioAsignatura::getGrupo)
        );

        for (HorarioAsignatura h : listaHorarioAsignaturas) {
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

        return listaHorarioAsignaturas;
    }

}
