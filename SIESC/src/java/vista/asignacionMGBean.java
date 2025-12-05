/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import modelo.AvisosReinscripcion;
import modelo.Carrera;
import modelo.Grupos;
import modelo.HorarioAsignatura;
import modelo.HorarioCrear;
import modelo.Horarios;
import modelo.MateriasCarreras;
import modelo.Organigrama;
import modelo.PeriodoEscolar;
import modelo.Permisos;
import modelo.Personal;
import modelo.ReticulaDatos;
import modelo.Usuario;
import servicio.CarreraServicioLocal;
import servicio.GruposServicioLocal;
import servicio.HorarioServicioLocal;
import servicio.OrganigramaServicioLocal;
import servicio.PeriodoEscolarServicioLocal;
import servicio.PermisoServicioLocal;
import servicio.PersonalServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "asignacionMGBean")
@ViewScoped
public class asignacionMGBean implements Serializable {

    @EJB
    private HorarioServicioLocal horarioServicio;
    @EJB
    private GruposServicioLocal gruposServicio;
    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private PermisoServicioLocal permisoServicio;
    @EJB
    private CarreraServicioLocal carreraServicio;
    @EJB
    private OrganigramaServicioLocal organigramaServicio;
    @EJB
    private PersonalServicioLocal personalServicio;

    private List<Organigrama> listaDepartamentos;
    private List<Carrera> listaCarreras;
    private List<Permisos> listaPermisos;
    private List<PeriodoEscolar> listaPeriodos;
    private List<Integer> semestres;
    private List<Personal> listaDocentes;
    private List<HorarioAsignatura> listaHorarioCrear;
    private List<HorarioAsignatura> listaHorarioCrearMaestros;

    private Usuario usuario;
    private String periodoSeleccionado;
    private Integer semestreSeleccionado;
    private Integer carreraSeleccionada;
    private String departamentoSeleccionado;
    private String docenteRFC;
    private HorarioAsignatura horarioCrearSeleccionado;

    /**
     * Creates a new instance of asignacionMGBean
     */
    public asignacionMGBean() {
    }

    private void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }

    public void validacionCampos() {
        if ((departamentoSeleccionado != null && !"".equals(departamentoSeleccionado))
                && (carreraSeleccionada != null && carreraSeleccionada != 0)
                && (periodoSeleccionado != null && !"".equals(periodoSeleccionado))
                && (semestreSeleccionado != null && !"".equals(semestreSeleccionado))) {

            addMessage(FacesMessage.SEVERITY_INFO, "CAMPOS COMPLETOS",
                    "Area: " + departamentoSeleccionado
                    + " Carrera: " + carreraServicio.buscarPorId(carreraSeleccionada).getNombreCarrera()
                    + " Periodo: " + periodoSeleccionado
                    + " Semestre: " + semestreSeleccionado);

        }

    }
    public void resetearSiEsRecarga() {
    FacesContext fc = FacesContext.getCurrentInstance();

    if (!fc.isPostback()) {

        // Listas
        listaDepartamentos = new ArrayList<>();
        listaCarreras = new ArrayList<>();
        listaPermisos = new ArrayList<>();
        listaPeriodos = new ArrayList<>();
        semestres = new ArrayList<>();
        listaDocentes = new ArrayList<>();
        listaHorarioCrear = new ArrayList<>();
        listaHorarioCrearMaestros = new ArrayList<>();

        // Objetos
        usuario = new Usuario();
        horarioCrearSeleccionado = new HorarioAsignatura();

        // Valores simples
        periodoSeleccionado = "";
        semestreSeleccionado = null;
        carreraSeleccionada = null;
        departamentoSeleccionado = "";
        docenteRFC = "";
        inicializar();
 
    }
}

    public void inicializar() {
        // 4. Obtiene el contexto actual de JSF (para trabajar con sesión, request, etc.)
        FacesContext contexto = FacesContext.getCurrentInstance();
        // 5. Recupera el objeto "usuario" guardado en la sesión
        usuario = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        System.out.println("Usuario "+usuario);
        listaDepartamentos = organigramaServicio.traerListaOrganigrama();
        //departamentoSeleccionado = new Organigrama();
        listaCarreras = carreraServicio.traerListaCarrera();
        listaPermisos = permisoServicio.buscarCarreras(usuario.getUsuario());
        listaPeriodos = periodoEscolarServicio.periodosEscolaresActivos();
        periodoSeleccionado = listaPeriodos.get(0).getPeriodo();
        semestres = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            semestres.add(i);
        }
        listaDocentes = personalServicio.personalActivos();

    }

    public void prueba() {
        System.out.println("-------------------------------------MENSAJE----------------------------------");
        System.out.println("DepartamentoSeleccionado ClaveArea(String):" + departamentoSeleccionado);
        System.out.println("CarreraSeleccionado Reticula(Integer):" + carreraSeleccionada);
        System.out.println("SemestreSeleccionado S(Integer)" + semestreSeleccionado);
        System.out.println("DocenteSeleciconado docenteRFC(String)" + docenteRFC);
        System.out.println("PeriodoSeleccionado p.periodo(String)" + periodoSeleccionado);
        System.out.println("-------------------------------------MENSAJE----------------------------------");

    }

    public void cambioPeriodo() {

        prueba();
        validacionCampos();
    }

    public void cambioDepartamento() {

        listaDocentes = personalServicio.personalPorArea(departamentoSeleccionado);
        System.out.println("Tamaño listadocentes" + listaDocentes.size());
        prueba();
        listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        System.out.println("listaHorarioCrear->" + listaHorarioCrear.size());
        validacionCampos();
    }

    public void cambioCarrera() {
        prueba();
        listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        System.out.println("listaHorarioCrear->" + listaHorarioCrear.size());
        validacionCampos();

    }

    public void cambioSemestre() {
        prueba();
        listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        System.out.println("listaHorarioCrear->" + listaHorarioCrear.size());
        validacionCampos();

    }

    public void filaSeleccionada() {
        listaHorarioCrearMaestros = new ArrayList();

        listaDocentes = personalServicio.personalPorArea(departamentoSeleccionado);
        System.out.println("HorarioSeleccionado" + horarioCrearSeleccionado);

        System.out.println("horarioCrearSeleccionado.getDocente()" + horarioCrearSeleccionado.getDocente());
        if (!"Sin docente".equals(horarioCrearSeleccionado.getDocente())) {
            Personal p = null;
            for (int i = 0; i < listaDocentes.size(); i++) {
                p = listaDocentes.get(i);

                String nombreCompleto = p.getNombreEmpleado() + " " + p.getApellidoPaterno() + " " + p.getApellidoMaterno();
                System.out.println("Comparando: horarioCrearSeleccionado.getDocente() = "
                        + horarioCrearSeleccionado.getDocente()
                        + " con " + nombreCompleto);

                if (horarioCrearSeleccionado.getDocente().equals(nombreCompleto)) {
                    System.out.println("¡Coincidencia encontrada!");
                    System.out.println("Docente: " + nombreCompleto);
                    break;
                }
            }

            docenteRFC = p.getRfc();
            verHorarioMaestro();

        } else {
            docenteRFC = "";

        }

        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA",
                "MATERIA: " + horarioCrearSeleccionado.getAsignatura()
                + " CLAVE: " + horarioCrearSeleccionado.getMateria()
                + " GRUPO: " + horarioCrearSeleccionado.getGrupo());

    }

    public void accionEliminar(HorarioAsignatura ha) {
        if (ha != null) {
            Grupos g = gruposServicio.buscarPorIdInt(ha.getId());
            g.setRfc(null);
            gruposServicio.actualizar(g);
            List<Horarios> lh = horarioServicio.buscarHorarioPorMateriayGrupo(
                    carreraSeleccionada,
                    semestreSeleccionado,
                    periodoSeleccionado,
                    ha.getMateria(),
                    ha.getGrupo());
            System.out.println("HorarioCrearSeleccionado" + ha);
            for (Horarios h : lh) {
                h.setRfc(null);
                horarioServicio.actualizar(h);
                System.out.println("Actualize");
            }
            addMessage(FacesMessage.SEVERITY_INFO, "ACTUALIZANDO",
                    "SE HA ELIMINADO DOCENTE");
            listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        }
    }

    public void limpiarEspaciosInicioHorarios(List<HorarioAsignatura> lista) {

        for (HorarioAsignatura h : lista) {

            // Limpiar cada día quitando espacios del inicio (trim left)
            h.setLunes((h.getLunes() != null) ? h.getLunes().replaceAll("^\\s+", "") : "");
            h.setMartes((h.getMartes() != null) ? h.getMartes().replaceAll("^\\s+", "") : "");
            h.setMiercoles((h.getMiercoles() != null) ? h.getMiercoles().replaceAll("^\\s+", "") : "");
            h.setJueves((h.getJueves() != null) ? h.getJueves().replaceAll("^\\s+", "") : "");
            h.setViernes((h.getViernes() != null) ? h.getViernes().replaceAll("^\\s+", "") : "");
            h.setSabado((h.getSabado() != null) ? h.getSabado().replaceAll("^\\s+", "") : "");
        }
    }

    public void guardarMaestro() {
        HorarioAsignatura ha = null;

        Personal maestro = personalServicio.buscarPorId(docenteRFC);

        if (!listaHorarioCrearMaestros.isEmpty()) {
            boolean empalme = false;
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            limpiarEspaciosInicioHorarios(listaHorarioCrearMaestros);
            limpiarEspaciosInicioHorarios(Arrays.asList(horarioCrearSeleccionado));
            System.out.println("=========== INICIANDO VERIFICACION DE EMPALMES ===========");
            System.out.println("Total de horarios existentes del maestro: " + listaHorarioCrearMaestros.size());
            System.out.println("Horario NUEVO seleccionado:");
            System.out.println(" - Lunes:" + horarioCrearSeleccionado.getLunes());
            System.out.println(" - Martes:" + horarioCrearSeleccionado.getMartes());
            System.out.println(" - Miércoles:" + horarioCrearSeleccionado.getMiercoles());
            System.out.println(" - Jueves:" + horarioCrearSeleccionado.getJueves());
            System.out.println(" - Viernes:" + horarioCrearSeleccionado.getViernes());
            System.out.println(" - Sábado:" + horarioCrearSeleccionado.getSabado());
            System.out.println("===========================================================");

            for (HorarioAsignatura hc : listaHorarioCrearMaestros) {
                // Recorremos cada día 
                System.out.println("\n--- NUEVA MATERIA EXISTENTE A COMPARAR ---");
                System.out.println("Asignatura existente: " + hc.getAsignatura());
                System.out.println("Docente:" + hc.getDocente());
                System.out.println("Grupo:" + hc.getGrupo());
                System.out.println("Lunes:" + hc.getLunes());
                System.out.println("Martes:" + hc.getMartes());
                System.out.println("Miércoles:" + hc.getMiercoles());
                System.out.println("Jueves:" + hc.getJueves());
                System.out.println("Viernes:" + hc.getViernes());
                System.out.println("Sábado:" + hc.getSabado());
                System.out.println("------------------------------------------------------------");

                Map<String, String> dias = new LinkedHashMap<>();

                dias.put("lunes", hc.getLunes());
                dias.put("martes", hc.getMartes());
                dias.put("miercoles", hc.getMiercoles());
                dias.put("jueves", hc.getJueves());
                dias.put("viernes", hc.getViernes());
                dias.put("sabado", hc.getSabado());

                for (Map.Entry<String, String> entry : dias.entrySet()) {
                    String dia = entry.getKey();
                    String horariosExistentes = entry.getValue();
                    String horariosNuevo = null;

                    switch (dia) {
                        case "lunes":
                            System.out.println("Entro a lunes");
                            horariosNuevo = horarioCrearSeleccionado.getLunes();
                            break;
                        case "martes":
                            horariosNuevo = horarioCrearSeleccionado.getMartes();
                            break;
                        case "miercoles":
                            horariosNuevo = horarioCrearSeleccionado.getMiercoles();
                            break;
                        case "jueves":
                            horariosNuevo = horarioCrearSeleccionado.getJueves();
                            break;
                        case "viernes":
                            horariosNuevo = horarioCrearSeleccionado.getViernes();
                            break;
                        case "sabado":
                            horariosNuevo = horarioCrearSeleccionado.getSabado();
                            break;
                    }
                    // ====== IMPRIMIENDO VALORES ANTES DE SPLIT ======
                    System.out.println("\nDía revisado: " + dia);
                    System.out.println("Horarios EXISTENTES raw: [" + horariosExistentes + "]");
                    System.out.println("Horarios NUEVOS raw:      [" + horariosNuevo + "]");
                    System.out.println("------------------------------------------------");

                    if (horariosExistentes != null && !"".equals(horariosExistentes)
                            && horariosNuevo != null && !"".equals(horariosNuevo)) {
                        // Separar rangos por el separador usado
                        String[] rangosExistentes = horariosExistentes.split("   ");
                        String[] rangosNuevo = horariosNuevo.split("   ");

                        for (String rangoE : rangosExistentes) {
                            for (String rangoN : rangosNuevo) {
                                // Parsear horas
                                String[] hE = rangoE.split(" - ");
                                String[] hN = rangoN.split(" - ");

                                System.out.println("Rangos existentes encontrados: " + Arrays.toString(rangosExistentes));
                                System.out.println("Rangos nuevos encontrados:     " + Arrays.toString(rangosNuevo));

                                System.out.println("---- DEPURANDO HORARIOS ----");
                                System.out.println("Dia: " + dia);
                                System.out.println("Horarios existentes (raw): [" + horariosExistentes + "]");
                                System.out.println("Horarios nuevo (raw): [" + horariosNuevo + "]");
                                System.out.println("Rango existente: [" + rangoE + "]");
                                System.out.println("Rango nuevo: [" + rangoN + "]");
                                System.out.println("Partes hE: " + Arrays.toString(hE));
                                System.out.println("Partes hN: " + Arrays.toString(hN));

                                try {
                                    System.out.println("Parseando existente inicio: " + hE[0] + "  fin: " + hE[1]);
                                    System.out.println("Parseando nuevo inicio: " + hN[0] + "  fin: " + hN[1]);
                                    Date inicioE = formatoHora.parse(hE[0].trim());
                                    Date finE = formatoHora.parse(hE[1].trim());
                                    Date inicioN = formatoHora.parse(hN[0].trim());
                                    Date finN = formatoHora.parse(hN[1].trim());

                                    // Verificar si se empalman
                                    if (inicioN.before(finE) && finN.after(inicioE)) {
                                        empalme = true;
                                        addMessage(FacesMessage.SEVERITY_WARN, "EMPALME DETECTADO",
                                                "La materia " + horarioCrearSeleccionado.getAsignatura()
                                                + " tiene horario que se empalma en " + dia);
                                        break;
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (empalme) {
                                break;
                            }
                        }
                    }
                    if (empalme) {
                        break;
                    }
                }
                if (empalme) {
                    break;
                }
            }

            if (empalme) {
                System.out.println("Encontre");
                return;
            }
        }

        for (HorarioAsignatura h : listaHorarioCrear) {
            if (h.getId() == horarioCrearSeleccionado.getId()) {
                // Aquí actualizas el objeto real
                h.setDocente(maestro.getNombreEmpleado()
                        + " " + maestro.getApellidoPaterno()
                        + " " + maestro.getApellidoMaterno());
                ha = h;
                break;
            }
        }

        if (ha != null) {
            System.out.println("=================================");
            System.out.println("Grupo: " + ha.getGrupo());
            System.out.println("IdGrupo: " + ha.getId());
            System.out.println("Materia: " + ha.getMateria());
            System.out.println("Asignatura: " + ha.getAsignatura());
            System.out.println("Docente: " + ha.getDocente());
            System.out.println("Lunes: " + ha.getLunes());
            System.out.println("Martes: " + ha.getMartes());
            System.out.println("Miércoles: " + ha.getMiercoles());
            System.out.println("Jueves: " + ha.getJueves());
            System.out.println("Viernes: " + ha.getViernes());
            System.out.println("Sábado: " + ha.getSabado());
            System.out.println("=================================");

            Grupos g = gruposServicio.buscarPorIdInt(ha.getId());
            Personal objetorfc = personalServicio.buscarPorId(docenteRFC);
            g.setRfc(objetorfc);
            gruposServicio.actualizar(g);

            List<Horarios> lh = horarioServicio.buscarHorarioPorMateriayGrupo(
                    carreraSeleccionada,
                    semestreSeleccionado,
                    periodoSeleccionado,
                    horarioCrearSeleccionado.getMateria(),
                    ha.getGrupo());
            System.out.println("===== PARÁMETROS PARA buscarHorariosPorGrupos =====");
            System.out.println("Carrera: " + carreraServicio.buscarPorId(carreraSeleccionada));
            System.out.println("Carrera (ID): " + carreraSeleccionada);
            System.out.println("Semestre: " + semestreSeleccionado);
            System.out.println("Periodo: " + periodoEscolarServicio.buscarPorId(periodoSeleccionado));
            System.out.println("Periodo (ID): " + periodoSeleccionado);
            System.out.println("Materia(NNN): " + horarioCrearSeleccionado.getMateria());
            System.out.println("====================================================");
            for (Horarios h : lh) {
                h.setRfc(objetorfc);
                horarioServicio.actualizar(h);
            }
            addMessage(FacesMessage.SEVERITY_INFO, "ACTUALIZANDO",
                    "DOCENTE:" + objetorfc.getNombreEmpleado() + " " + objetorfc.getApellidoPaterno() + " " + objetorfc.getApellidoMaterno() + " SE HA INSERTADO.");

        }

        docenteRFC = "";
    }

    public void verHorarioMaestro() {
        System.out.println("RFC:" + docenteRFC);
        listaHorarioCrearMaestros = gruposServicio.buscarGruposPorMaestro(carreraSeleccionada,
                semestreSeleccionado,
                periodoSeleccionado,
                docenteRFC);

    }

    public List<HorarioAsignatura> getListaHorarioCrearMaestros() {
        return listaHorarioCrearMaestros;
    }

    public void setListaHorarioCrearMaestros(List<HorarioAsignatura> listaHorarioCrearMaestros) {
        this.listaHorarioCrearMaestros = listaHorarioCrearMaestros;
    }

    public List<HorarioAsignatura> getListaHorarioSeleccionado() {
        if (horarioCrearSeleccionado != null) {
            return Collections.singletonList(horarioCrearSeleccionado); // Lista de un solo elemento
        }
        return Collections.emptyList(); // Lista vacía si es null
    }

    public HorarioAsignatura getHorarioCrearSeleccionado() {
        return horarioCrearSeleccionado;
    }

    public void setHorarioCrearSeleccionado(HorarioAsignatura horarioCrearSeleccionado) {
        this.horarioCrearSeleccionado = horarioCrearSeleccionado;
    }

    public List<HorarioAsignatura> getListaHorarioCrear() {
        return listaHorarioCrear;
    }

    public void setListaHorarioCrear(List<HorarioAsignatura> listaHorarioCrear) {
        this.listaHorarioCrear = listaHorarioCrear;
    }

    public Integer getCarreraSeleccionada() {
        return carreraSeleccionada;
    }

    public void setCarreraSeleccionada(Integer carreraSeleccionada) {
        this.carreraSeleccionada = carreraSeleccionada;
    }

    public Integer getSemestreSeleccionado() {
        return semestreSeleccionado;
    }

    public void setSemestreSeleccionado(Integer semestreSeleccionado) {
        this.semestreSeleccionado = semestreSeleccionado;
    }

    public List<Integer> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<Integer> semestres) {
        this.semestres = semestres;
    }

    public List<PeriodoEscolar> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(List<PeriodoEscolar> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public String getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(String periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Permisos> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permisos> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public String getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public List<Organigrama> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Organigrama> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Personal> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Personal> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public String getDocenteRFC() {
        return docenteRFC;
    }

    public void setDocenteRFC(String docenteRFC) {
        this.docenteRFC = docenteRFC;
    }

}
