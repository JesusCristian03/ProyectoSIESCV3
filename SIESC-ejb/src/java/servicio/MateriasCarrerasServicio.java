/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package servicio;

import DAO.MateriasCarrerasFacadeLocal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Estudiante;
import modelo.HistoriaAlumno;
import modelo.MateriasCarreras;
import modelo.Reticula;
import modelo.ReticulaDatos;

/**
 *
 * @author gacev
 */
@Stateless
public class MateriasCarrerasServicio implements MateriasCarrerasServicioLocal {

    @EJB
    private MateriasCarrerasFacadeLocal materiasCarrerasFacade1;

    @EJB
    private HistoriaAlumnoServicioLocal historiaAlumnoServicio;

    @EJB
    private MateriasCarrerasFacadeLocal materiasCarrerasFacade;

    private Reticula reticula;

    @Override
    public void prueba() {
        System.out.println("Prueba");
    }

    @Override
    public MateriasCarreras buscarPorId(Integer x) {
        return materiasCarrerasFacade1.find(x);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public ArrayList<Reticula> buscarMaterias(Estudiante estudiante) {

        ArrayList<Reticula> listaR = new ArrayList();//Lista final de objetos Reticula que se va a devolver.
        //Lista de todas las materias disponibles para la retícula del estudiante. Es decir creo saca todas las materias que corresponde con la reticula del estudiante
        //Caracteristicas de las materias por carrera tomando la reticula 9 es decir como los creditos totales, horas teoricas practicas, etc. 
        List<MateriasCarreras> listaM = materiasCarrerasFacade.buscarMaterias(estudiante.getReticula().getReticula());
        //estudiante.getReticula().getReticula() -> 9
        //Tabla estudiante tiene esto -> Word

        //Tabla MateriasCarrera tiene esto -> Word
        //Como lo dice su nombre es un historial de sus calificaciones de la materia, con el grupo, si esta en doble 
        List<HistoriaAlumno> listaHA = historiaAlumnoServicio.buscarAsignaturas(estudiante.getNoDeControl());
        //Tabla MateriasCarrera tiene esto -> Word

        Integer renglonAnt = 1;//Controla el "renglón" o fila de materias que se van a agrupar en un objeto Reticula.
        //Esto significa que cada objeto Reticula representa un renglón de materias en la tabla de retícula
        reticula = new Reticula();//Objeto temporal de tipo Reticula que se va llenando mientras recorremos listaM.
        for (MateriasCarreras materia : listaM) {//Recorres todas las materias de la carrera (listaM).
            System.out.println(materia.getRenglon() + "/" + materia.getSemestreReticula() + " Materia Semestre" + materia.getMateria().getNombreCompletoMateria() + " clave " + materia.getMateria().getMateria());
            System.out.println("Renglon: " + renglonAnt);
            System.out.println("---------------------------");

            if (renglonAnt != materia.getRenglon()) {//Se verifica si el renglon cambió respecto al anterior:
                //Si sí, se guarda la reticula actual en listaR y se crea una nueva para el siguiente renglón.
                listaR.add(reticula);
                reticula = new Reticula();
                agregarDatos(materia, estudiante.getSemestre());// 8 
                renglonAnt = materia.getRenglon();
            } else {
                //Si no, se sigue llenando la reticula actual.//Objeto Materia ,  8 
                agregarDatos(materia, estudiante.getSemestre());
                renglonAnt = materia.getRenglon();
            }

        }
        String color;
        boolean disponible;
        //Se recorren todas las materias que el estudiante ya cursó (listaHA).
        for (HistoriaAlumno historiaA : listaHA) {

            if (historiaA.getCalificacion() >= 70) {
                //Si la calificación es ≥70 → materia acreditada, no disponible para inscripción (disponible=false).
                color = "acreditado";
                disponible = false;
            } else {
                //Si no está acreditada → se determina color según tipo de evaluación (ordinario, repetición, especial).
                disponible = true;
                switch (historiaA.getTipoEvaluacion().getPrioridad()) {
                    case 1:
                        color = "ordinario";
                        break;
                    case 2:
                        color = "repeticion";
                        break;
                    case 3:
                        color = "especial";
                        break;
                    default:
                        color = "nodisponible";
                        break;
                }
            }
            for (int ren = 0; ren < listaR.size(); ren++) {
                Reticula aux = listaR.get(ren);
                //Por cada materia en la historia académica, se busca en cada semestre de cada renglón si coincide la materia.

                //Busca el codigo 1R1 = 1R1 
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre1().getMateria())) {
                    //Se asigna la calificación con el tipo de evaluación (70 / ordinario).
                    aux.getSemestre1().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    //Se pinta el color correspondiente (acreditado, repeticion, etc.).
                    aux.getSemestre1().setColor(color);
                    //Se establece si está disponible o no para inscripción.
                    aux.getSemestre1().setDisponible(disponible);

                    /*Color de celda según situación de la materia.
                    Texto de calificación + tipo de examen.
                    Si el botón o acción para inscribirse está habilitada.*/
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre2().getMateria())) {
                    aux.getSemestre2().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre2().setColor(color);
                    aux.getSemestre2().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre3().getMateria())) {
                    aux.getSemestre3().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre3().setColor(color);
                    aux.getSemestre3().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre4().getMateria())) {
                    aux.getSemestre4().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre4().setColor(color);
                    aux.getSemestre4().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre5().getMateria())) {
                    aux.getSemestre5().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre5().setColor(color);
                    aux.getSemestre5().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre6().getMateria())) {
                    aux.getSemestre6().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre6().setColor(color);
                    aux.getSemestre6().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre7().getMateria())) {
                    aux.getSemestre7().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre7().setColor(color);
                    aux.getSemestre7().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre8().getMateria())) {
                    aux.getSemestre8().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre8().setColor(color);
                    aux.getSemestre8().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre9().getMateria())) {
                    aux.getSemestre9().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre9().setColor(color);
                    aux.getSemestre9().setDisponible(disponible);
                }
                if (historiaA.getMateria().getMateria().equals(aux.getSemestre10().getMateria())) {
                    aux.getSemestre10().setCalificacion(String.valueOf(historiaA.getCalificacion()) + " / " + historiaA.getTipoEvaluacion().getTipoEvaluacion());
                    aux.getSemestre10().setColor(color);
                    aux.getSemestre10().setDisponible(disponible);
                }
            }
        }
        //Devuelve la lista completa de Reticula, cada una con sus 9 semestres (ReticulaDatos) llenos con materia, calificación, color y disponibilidad.


        return listaR;
    }

    @Override
    public MateriasCarreras buscarMateriasCarreraPorMateria(String idmateria) {
        return materiasCarrerasFacade.buscarMateriaCarreraPorMateria(idmateria);
    }

    private void agregarDatos(MateriasCarreras materia, Integer semestre) {

        //Aqui asigna acomoda la materia en la reticula de acuerdo al semestre 
        ReticulaDatos reticulaDatos = new ReticulaDatos();//Se crea un objeto ReticulaDatos que contendrá la información de una materia específica.
        //Se llenan los campos:
        reticulaDatos.setMateria(materia.getMateria().getMateria());//el código de la materia.
        reticulaDatos.setNombreMateria(materia.getMateria().getNombreAbreviadoMateria());//el nombre abreviado de la materia.
        reticulaDatos.setClave(materia.getClaveOficialMateria());//la clave oficial de la materia en la carrera.
        reticulaDatos.setCreditos(""+materia.getCreditosMateria());
        //Se compara el semestre de la materia (materia.getSemestreReticula()) con el semestre actual del estudiante (semestre).
        if (materia.getSemestreReticula() <= semestre) {
            //Se marca como disponible (disponible = true) y el color se pone en "disponible".
            reticulaDatos.setColor("disponible");
            reticulaDatos.setDisponible(true);
        } else {
            //Se marca como no disponible (disponible = false) y el color se pone en "nodisponible".
            reticulaDatos.setColor("nodisponible");
            reticulaDatos.setDisponible(false);
        }
        switch (materia.getSemestreReticula()) {
            case 1:
                reticula.setSemestre1(reticulaDatos);
                break;
            case 2:
                reticula.setSemestre2(reticulaDatos);
                break;
            case 3:
                reticula.setSemestre3(reticulaDatos);
                break;
            case 4:
                reticula.setSemestre4(reticulaDatos);
                break;
            case 5:
                reticula.setSemestre5(reticulaDatos);
                break;
            case 6:
                reticula.setSemestre6(reticulaDatos);
                break;
            case 7:
                reticula.setSemestre7(reticulaDatos);
                break;
            case 8:
                reticula.setSemestre8(reticulaDatos);
                break;
            case 9:
                reticula.setSemestre9(reticulaDatos);
                break;
            case 10:
                reticula.setSemestre10(reticulaDatos);
                break;
        }
    }

    @Override
    public List<MateriasCarreras> buscarPorCarrera(Integer reticula) {
        return materiasCarrerasFacade1.buscarMateriasCarrera(reticula);
    }

    @Override
    public List<MateriasCarreras> buscarPorCarreraDepto(Integer reticula, String claveArea) {
        return materiasCarrerasFacade1.buscarMateriasDepto(reticula, claveArea);
    }

    @Override
    public List<MateriasCarreras> buscarAsinaturaSemestre(Integer reticula, Integer semestre) {
        return materiasCarrerasFacade1.buscarAsignaturaSemestre(reticula, semestre);
    }

}
