/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package servicio;

import java.util.List;
import javax.ejb.Local;
import modelo.Carrera;
import modelo.Grupos;
import modelo.HorarioAsignatura;
import modelo.PeriodoEscolar;

/**
 *
 * @author gacev
 */
@Local
public interface GruposServicioLocal {
    List<Grupos> gruposActivos();

    void insertarNuevoGrupo(Grupos grupo);
    Grupos buscarPorId(String x);
    public List<Grupos> buscarGrupoSii(Integer reticula, Integer idmateriacarrera, String periodo, String grupo);
    List<Grupos> buscarGruposPorCampoNombre(Carrera reticula, Integer semestre, PeriodoEscolar periodo, String grupo);
    void eliminar(Grupos grupo);
    List<HorarioAsignatura> buscarGruposPorCampoMateriaSeleccionada(int reticula, Integer semestre, String periodo, String materia);
    List<String> buscarGruposCompletos(Carrera reticula, Integer semestre, PeriodoEscolar periodo);
    
    List<HorarioAsignatura> buscarGruposPorCampoGrupoSeleccionada(int reticula, Integer semestre, String periodo, String grupo);
}
