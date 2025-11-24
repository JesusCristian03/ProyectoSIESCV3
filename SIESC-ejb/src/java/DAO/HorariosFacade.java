/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Aulas;
import modelo.Carrera;
import modelo.Horarios;
import modelo.PeriodoEscolar;

/**
 *
 * @author gacev
 */
@Stateless
public class HorariosFacade extends AbstractFacade<Horarios> implements HorariosFacadeLoca {

    @PersistenceContext(unitName = "SIESC-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorariosFacade() {
        super(Horarios.class);
    }

    @Override
    public List<Horarios> horarioDocente(String periodo, String rfc, Character tipo) {
        List<Horarios> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.rfc.rfc=:rfc and h.tipoHorario=:tipo and h.materia.tipoMateria.tipoMateria<>:tipoMateria order by h.materia.materia,h.grupo";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("rfc", rfc);
        queryPersonal.setParameter("tipo", tipo);
        queryPersonal.setParameter("tipoMateria", tipoMateria);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    public List<Horarios> entradaDocente(short dia, int hora, String periodo, Character tipoHorario) {
        List<Horarios> lista = null;
        String sqlHorario = "SELECT h FROM Horarios h WHERE h.periodo.periodo=:periodo and h.tipoHorario=:tipoHorario and h.diaSemana=:dia and EXTRACT(hour(h.horaInicial)) <= :hora and EXTRACT(hour(h.horaFinal)) > :hora order by h.aula.aula";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("tipoHorario", tipoHorario);
        queryPersonal.setParameter("dia", dia);
        queryPersonal.setParameter("hora", hora);
        lista = queryPersonal.getResultList();
        return lista;
    }

    public List<Horarios> listaDocentes(String periodo) {
        List<Horarios> lista = null;
        String sqlHorario = "SELECT h.rfc.rfc,h.rfc.nombreEmpleado,h.rfc.apellidoPaterno,h.rfc.apellidoMaterno FROM  Horarios h WHERE h.periodo.periodo=:periodo group by h.rfc.rfc,h.rfc.nombreEmpleado,h.rfc.apellidoPaterno,h.rfc.apellidoMaterno order by h.rfc.rfc";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        //System.out.println("Periodo " + periodo);
        /* List<ListaA> listaTodos = new ArrayaList<ListaA>();
        Map<String,List<ListaA>> agrupado = listaTodos.stream().collect(Collectors.groupBy(ListaA::getNombre));
        agrupado.forEach((nombre,lista)->{System.out.println("Nombre:" +  nombre + ": " + lista)})
         */
        lista = (List<Horarios>) (queryPersonal.getResultList());

        lista = queryPersonal.getResultList();

        //Map<String, List<Horarios>> listaProfesores = lista.stream().collect(Collectors.groupingBy(w -> w.getRfc().getRfc()));
        //lista = listaProfesores.put(periodo, lista);
        //Map<String,List<Horarios>> agrupado = lista.stream().collect(Collectors.groupingBy(x -> x.getRfc().getRfc());
        //var map = lista
        return lista;
    }

    //Horario Asignatura Periodo Materia y Grupo
    public List<Horarios> horarioAsignatura(String periodo, String materia, String grupo) {
        List<Horarios> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.idGrupo.materia=:materia and h.idGrupo.grupo=:grupo and h.materia.tipoMateria.tipoMateria<>:tipoMateria order by h.materia.materia,h.grupo";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("materia", materia);
        queryPersonal.setParameter("grupo", grupo);
        queryPersonal.setParameter("tipoMateria", tipoMateria);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    //Horario Asignatura Por Semestre de la Carrera
    public List<Horarios> horarioAsignatura(String periodo, Integer reticula, Integer semestre) {
        List<Horarios> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.idGrupo.idMateriaCarrera.semestreReticula=:semestre and h.idGrupo.idMateriaCarrera.reticula.reticula=:reticula and h.materia.tipoMateria.tipoMateria<>:tipoMateria order by h.materia.materia,h.grupo";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("semestre", semestre);
        queryPersonal.setParameter("reticula", reticula);
        queryPersonal.setParameter("tipoMateria", tipoMateria);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    //Horario Asignatura Por Semestre de la Carrera
    @Override
    public List<Horarios> buscarHorarioPorDepartamento(String periodo, Integer reticula, Integer semestre, String clavearea) {
        List<Horarios> lista = null;

        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo "
                + "and h.idGrupo.idMateriaCarrera.semestreReticula=:semestre "
                + "and h.idGrupo.idMateriaCarrera.reticula.reticula=:reticula "
                + "and h.idGrupo.idMateriaCarrera.materia.claveArea.claveArea=:claveArea";

        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("semestre", semestre);
        queryPersonal.setParameter("reticula", reticula);
        queryPersonal.setParameter("claveArea", clavearea);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    //Horario Todas las Asignaturas de la Carrera
    public List<Horarios> horarioAsignatura(String periodo, Integer reticula) {
        List<Horarios> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.idGrupo.idMateriaCarrera.reticula.reticula=:reticula and h.materia.tipoMateria.tipoMateria<>:tipoMateria order by h.materia.materia,h.grupo";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("reticula", reticula);
        queryPersonal.setParameter("tipoMateria", tipoMateria);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    //Horario todos los grupos de una asignatura
    public List<Horarios> horarioAsignatura(String periodo, String materia) {
        List<Horarios> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.idGrupo.grupo=:materia and h.materia.tipoMateria.tipoMateria<>:tipoMateria order by h.materia.materia,h.grupo";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("materia", materia);
        queryPersonal.setParameter("tipoMateria", tipoMateria);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    //
    // Verificar consultas
    //
    public List<Horarios> horariosSinChecar(String periodo, int hora) {
        List<Horarios> lista = null;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.idGrupo.grupo=:materia and h.grupo=:grupo and h.materia.tipoMateria.tipoMateria<>:tipoMateria order by h.materia.materia,h.grupo";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        //queryPersonal.setParameter("materia", materia);
        //queryPersonal.setParameter("grupo", grupo);
        //queryPersonal.setParameter("tipoMateria", tipoMateria);

        lista = (List<Horarios>) (queryPersonal.getResultList());
        return lista;
    }

    public List<Horarios> horarioDia(String periodo, short dia, Character tipo) {
        List<Horarios> lista = null;
        Integer tipoMateria = 5;
        String sqlHorario = "SELECT h FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.diaSemana=:dia and h.tipoHorario=:tipo order by h.aula.aula";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("dia", dia);
        queryPersonal.setParameter("tipo", tipo);

        lista = (List<Horarios>) (queryPersonal.getResultList());

        return lista;
    }

    public List<Horarios> buscarDocentesArea(String periodo, String areaAcademica) {
        List<Horarios> lista = null;
        String sqlHorario = "SELECT h.rfc.rfc,h.rfc.nombreEmpleado,h.rfc.apellidoPaterno,h.rfc.apellidoMaterno FROM  Horarios h WHERE h.periodo.periodo=:periodo and h.rfc.areaAcademica.claveArea=:areaAcademica group by h.rfc.rfc,h.rfc.nombreEmpleado,h.rfc.apellidoPaterno,h.rfc.apellidoMaterno order by h.rfc.rfc";
        Query queryPersonal = em.createQuery(sqlHorario);

        queryPersonal.setParameter("periodo", periodo);
        queryPersonal.setParameter("areaAcademica", areaAcademica);
        //System.out.println("Periodo " + periodo);
        lista = (List<Horarios>) (queryPersonal.getResultList());
        lista = queryPersonal.getResultList();
        return lista;
    }

    @Override
    public List<Horarios> buscarHorariosPorGrupos(Carrera reticula, int semestre, PeriodoEscolar periodo, String nombregrupo) {
        List<Horarios> lista = null;
        System.out.println("BUSCANDO HORARIOS ");

        String sql = "SELECT h FROM Horarios h "
                + "JOIN h.idGrupo g "
                + "JOIN g.idMateriaCarrera mc "
                + "WHERE g.reticula = :reticula "
                + "AND mc.semestreReticula = :semestre "
                + "AND g.periodo = :periodo "
                + "AND g.grupo = :nombreGrupo";

        Query query = em.createQuery(sql);

        query.setParameter("reticula", reticula);
        query.setParameter("semestre", semestre);
        query.setParameter("periodo", periodo);
        query.setParameter("nombreGrupo", nombregrupo);

        lista = (List<Horarios>) query.getResultList();

        return lista;
    }

    @Override
    public List<Horarios> buscarHorariosPorMateria(Carrera reticula, int semestre, PeriodoEscolar periodo, String materia) {
        List<Horarios> lista = null;
        System.out.println("BUSCANDO HORARIOS POR MATERIA:");

        String sql = "SELECT h FROM Horarios h "
                + "JOIN h.idGrupo g "
                + "JOIN g.idMateriaCarrera mc "
                + "WHERE g.reticula = :reticula "
                + "AND mc.semestreReticula = :semestre "
                + "AND g.periodo = :periodo "
                + "AND g.materia = :Materia";

        Query query = em.createQuery(sql);

        query.setParameter("reticula", reticula);
        query.setParameter("semestre", semestre);
        query.setParameter("periodo", periodo);
        query.setParameter("Materia", materia);

        lista = (List<Horarios>) query.getResultList();

        return lista;
    }

    @Override
    public List<Horarios> buscarHorariosPorMateriayGrupo(Carrera reticula, int semestre, PeriodoEscolar periodo, String materia, String grupo) {
        List<Horarios> lista = null;
        System.out.println("BUSCANDO HORARIOS POR MATERIA:");

        String sql = "SELECT h FROM Horarios h "
                + "JOIN h.idGrupo g "
                + "JOIN g.idMateriaCarrera mc "
                + "WHERE g.reticula = :reticula "
                + "AND mc.semestreReticula = :semestre "
                + "AND g.periodo = :periodo "
                + "AND g.materia = :Materia "
                + "AND g.grupo = :Grupo";

        Query query = em.createQuery(sql);

        query.setParameter("reticula", reticula);
        query.setParameter("semestre", semestre);
        query.setParameter("periodo", periodo);
        query.setParameter("Materia", materia);
        query.setParameter("Grupo", grupo);

        lista = (List<Horarios>) query.getResultList();

        return lista;
    }

    @Override
    public List<Horarios> buscarHorariosPorAulas(Carrera reticula, PeriodoEscolar periodo, Aulas aula) {
        List<Horarios> lista = null;
        System.out.println("BUSCANDO HORARIOS: -> ");

        String sql = "SELECT h FROM Horarios h "
                + "JOIN h.idGrupo g "
                + "WHERE g.reticula = :reticula "
                + "AND g.periodo = :periodo "
                + "AND h.aula = :Aula";

        Query query = em.createQuery(sql);
        query.setParameter("reticula", reticula);
        query.setParameter("periodo", periodo);
        query.setParameter("Aula", aula);
        lista = (List<Horarios>) query.getResultList();

        return lista;
    }

    @Override
    public Horarios buscarHorarioPorEmpalme(short diaSemana, String horaInicial, String horaFinal, Aulas aula) {

        try {
            return em.createQuery(
                    "SELECT h FROM Horarios h "
                    + "WHERE h.diaSemana = :diaSemana "
                    + "AND FUNCTION('TO_CHAR', h.horaInicial, 'HH24:MI:SS') = :horaInicial "
                    + "AND FUNCTION('TO_CHAR', h.horaFinal, 'HH24:MI:SS') = :horaFinal "
                    + "AND h.aula = :aula", Horarios.class)
                    .setParameter("diaSemana", diaSemana)
                    .setParameter("horaInicial", horaInicial)
                    .setParameter("horaFinal", horaFinal)
                    .setParameter("aula", aula)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("NO SE ENCONTRO NINGUN EMPALME ENTONCES PUEDE AVANZAR");
            return null;
        }

    }

}
