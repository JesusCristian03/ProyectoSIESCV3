/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import modelo.AutorizacionInscripcion;
import modelo.AvisosReinscripcion;
import modelo.Estudiante;
import modelo.HistoriaAlumno;
import modelo.HorarioAsignatura;
import modelo.Horarios;
import modelo.MateriasCarreras;
import modelo.PeriodoEscolar;
import modelo.RequisitosMateria;
import modelo.Reticula;
import modelo.ReticulaDatos;
import modelo.SeleccionMaterias;
import servicio.AutorizacionInscripcionServicioLocal;
import servicio.AvisosReinscripcionServicioLocal;
import servicio.GruposServicioLocal;
import servicio.HistoriaAlumnoServicioLocal;
import servicio.HorarioAsignaturaServicioLocal;
import servicio.HorarioServicioLocal;
import servicio.MateriasCarrerasServicioLocal;
import servicio.PeriodoEscolarServicioLocal;
import servicio.RequisitosMateriaServicioLocal;
import servicio.SeleccionMateriasServicioLocal;

/**
 *
 * @author gacev
 */
@Named(value = "inscripcionesBean")
@ViewScoped
public class InscripcionesBean implements Serializable {

    @EJB
    private RequisitosMateriaServicioLocal requisitosMateriaServicio;
    @EJB
    private AutorizacionInscripcionServicioLocal autorizacionInscripcionServicio;
    @EJB
    private AvisosReinscripcionServicioLocal avisosReinscripcionServicio;
    @EJB
    private HistoriaAlumnoServicioLocal historiaAlumnoServicio;
    @EJB
    private SeleccionMateriasServicioLocal seleccionMateriasServicio;
    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private GruposServicioLocal gruposServicio;
    @EJB
    private HorarioAsignaturaServicioLocal horarioAsignaturaServicio;
    @EJB
    private HorarioServicioLocal horarioServicio;
    @EJB
    private MateriasCarrerasServicioLocal materiasCarrerasServicio;

    private List<HorarioAsignatura> listaHorarioAsignatura = new ArrayList<>();//Para llenar mi tabla de seleccion materias.
    private List<HorarioAsignatura> listaHorarioASeleccionadas = new ArrayList<>();//Para llenar mi tabla de seleccion materias.
    private List<Horarios> listaHorariosGenerados;
    private List<PeriodoEscolar> listaPeriodoEscolar;//para sacar los periodos escolares. 
    private List<SeleccionMaterias> listaSM = new ArrayList();
    private List<String> listaGC = new ArrayList();
    private ArrayList<Reticula> listaM = new ArrayList();
    private List<AutorizacionInscripcion> listaAE = new ArrayList();

    private HorarioAsignatura grupoSeleccionado;
    private AvisosReinscripcion vr;
    private Estudiante estudiante;
    private MateriasCarreras materiaSeleccionada;
    private PeriodoEscolar periodoActual;
    private ReticulaDatos materiaDeTabla;

    private String periodo;
    private String materia;
    private double promedio;
    private Boolean grupoBloqueado = false;
    private Boolean modoGrupo = false;
    private String grupo = "";
    private int creditosSeleccionados = 0;
    private String mensaje;
    private String colorMensaje; // clase CSS
    private Boolean modoImprimir = false, modoInsertar = false, modoEliminar = false;
    private String[] autorizacionesDisponibles = new String[6];
    private int Mrojas = 0, Mamarillas = 0, Mnaranjas = 0, Mazules = 0;
    List<int[]> listaRojas = new ArrayList<>();
    List<int[]> listaNaranjas = new ArrayList<>();
    List<int[]> listaAmarillas = new ArrayList<>();
    List<int[]> listaAzules = new ArrayList<>();

    public String[] getAutorizacionesDisponibles() {
        return autorizacionesDisponibles;
    }

    public void setAutorizacionesDisponibles(String[] autorizacionesDisponibles) {
        this.autorizacionesDisponibles = autorizacionesDisponibles;
    }

    public List<AutorizacionInscripcion> getListaAE() {
        return listaAE;
    }

    public void setListaAE(List<AutorizacionInscripcion> listaAE) {
        this.listaAE = listaAE;
    }

    public int getCreditosSeleccionados() {
        return creditosSeleccionados;
    }

    public void setCreditosSeleccionados(int creditosSeleccionados) {
        this.creditosSeleccionados = creditosSeleccionados;
    }

    public Boolean getModoImprimir() {
        return modoImprimir;
    }

    public Boolean getModoInsertar() {
        return modoInsertar;
    }

    public void setModoInsertar(Boolean modoInsertar) {
        this.modoInsertar = modoInsertar;
    }

    public Boolean getModoEliminar() {
        return modoEliminar;
    }

    public void setModoEliminar(Boolean modoEliminar) {
        this.modoEliminar = modoEliminar;
    }

    public void setModoImprimir(Boolean modoImprimir) {
        this.modoImprimir = modoImprimir;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public void mostrarMensajeExito() {
        mensaje = "Estudiante inscrito correctamente";
        colorMensaje = "mensaje-exito";
    }

    public void mostrarMensajeError() {
        mensaje = "Ocurrió un error al guardar";
        colorMensaje = "mensaje-error";
    }

    public void mostrarMensajeInfo() {
        mensaje = "Seleccione una opción";
        colorMensaje = "mensaje-info";
    }

    public void mostrarPanel(String mensaje, String color) {
        this.mensaje = mensaje;
        this.colorMensaje = color;
    }

    public AvisosReinscripcion getVr() {
        return vr;
    }

    public Boolean getModoGrupo() {
        return modoGrupo;
    }

    public void setModoGrupo(Boolean modoGrupo) {
        this.modoGrupo = modoGrupo;
    }

    public void setVr(AvisosReinscripcion vr) {
        this.vr = vr;
    }

    public List<int[]> getListaRojas() {
        return listaRojas;
    }

    public void setListaRojas(List<int[]> listaRojas) {
        this.listaRojas = listaRojas;
    }

    public List<int[]> getListaNaranjas() {
        return listaNaranjas;
    }

    public void setListaNaranjas(List<int[]> listaNaranjas) {
        this.listaNaranjas = listaNaranjas;
    }

    public List<int[]> getListaAmarillas() {
        return listaAmarillas;
    }

    public void setListaAmarillas(List<int[]> listaAmarillas) {
        this.listaAmarillas = listaAmarillas;
    }

    public List<int[]> getListaAzules() {
        return listaAzules;
    }

    public void setListaAzules(List<int[]> listaAzules) {
        this.listaAzules = listaAzules;
    }

    public int getMrojas() {
        return Mrojas;
    }

    public void setMrojas(int Mrojas) {
        this.Mrojas = Mrojas;
    }

    public int getMamarillas() {
        return Mamarillas;
    }

    public void setMamarillas(int Mamarillas) {
        this.Mamarillas = Mamarillas;
    }

    public int getMnaranjas() {
        return Mnaranjas;
    }

    public void setMnaranjas(int Mnaranjas) {
        this.Mnaranjas = Mnaranjas;
    }

    public int getMazules() {
        return Mazules;
    }

    public void setMazules(int Mazules) {
        this.Mazules = Mazules;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Boolean getGrupoBloqueado() {
        return grupoBloqueado;
    }

    public void setGrupoBloqueado(Boolean grupoBloqueado) {
        this.grupoBloqueado = grupoBloqueado;
    }

    public List<String> getListaGC() {
        return listaGC;
    }

    public void setListaGC(List<String> listaGC) {
        this.listaGC = listaGC;
    }

    public PeriodoEscolar getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(PeriodoEscolar periodoActual) {
        this.periodoActual = periodoActual;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public ReticulaDatos getMateriaDeTabla() {
        return materiaDeTabla;
    }

    public List<HorarioAsignatura> getListaHorarioASeleccionadas() {
        return listaHorarioASeleccionadas;
    }

    public void setListaHorarioASeleccionadas(List<HorarioAsignatura> listaHorarioASeleccionadas) {
        this.listaHorarioASeleccionadas = listaHorarioASeleccionadas;
    }

    public HorarioAsignatura getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public List<SeleccionMaterias> getListaSM() {
        return listaSM;
    }

    public void setListaSM(List<SeleccionMaterias> listaSM) {
        this.listaSM = listaSM;
    }

    /**
     * Creates a new instance of InscripcionesBean
     */
    public void setGrupoSeleccionado(HorarioAsignatura grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public void setMateriaDeTabla(ReticulaDatos materiaDeTabla) {
        this.materiaDeTabla = materiaDeTabla;
    }

    public InscripcionesBean() {
        try {
            FacesContext contexto = FacesContext.getCurrentInstance();
            estudiante = (Estudiante) contexto.getExternalContext().getSessionMap().get("estudiante");

            if (estudiante != null) {
                //System.out.println("Buscarrrrrrrr Materias" + estudiante.getReticula().getReticula());
                //

            } else {

            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace() + "\nError" + e.getCause().toString());
        }
    }

    public List<PeriodoEscolar> getListaPeriodoEscolar() {
        return listaPeriodoEscolar;
    }

    public void setListaPeriodoEscolar(List<PeriodoEscolar> listaPeriodoEscolar) {
        this.listaPeriodoEscolar = listaPeriodoEscolar;
    }

    public MateriasCarreras getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(MateriasCarreras materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public ArrayList<Reticula> getListaM() {
        return listaM;
    }

    public void setListaM(ArrayList<Reticula> listaM) {
        this.listaM = listaM;
    }

    public List<HorarioAsignatura> getListaHorarioAsignatura() {
        return listaHorarioAsignatura;
    }

    public void setListaHorarioAsignatura(List<HorarioAsignatura> listaHorarioAsignatura) {
        this.listaHorarioAsignatura = listaHorarioAsignatura;
    }

    public void resetearSiEsRecarga() {
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("Ejecuto resetear");
        if (!fc.isPostback()) {
            System.out.println("Entro al reseteo");
            // Es recarga o entrada nueva → limpiar todo
            // Reinicia TODAS las variables que deben empezar limpias
            creditosSeleccionados = 0;
            listaHorarioASeleccionadas = new ArrayList<>();
            grupoSeleccionado = null;
            materiaDeTabla = null;

            listaHorarioAsignatura = new ArrayList<>();
            listaHorarioASeleccionadas = new ArrayList<>();
            listaHorariosGenerados = new ArrayList<>();
            listaPeriodoEscolar = new ArrayList<>();
            listaSM = new ArrayList();
            listaGC = new ArrayList();
            listaM = new ArrayList();
            listaAE = new ArrayList();

            grupoSeleccionado = new HorarioAsignatura();
            vr = new AvisosReinscripcion();
            materiaSeleccionada = new MateriasCarreras();
            periodoActual = new PeriodoEscolar();
            materiaDeTabla = new ReticulaDatos();

            periodo = "";
            materia = "";
            promedio = 0.0;
            grupoBloqueado = false;
            modoGrupo = false;
            grupo = "";
            creditosSeleccionados = 0;
            mensaje = "";
            colorMensaje = ""; // clase CSS
            modoImprimir = false;
            modoInsertar = false;
            modoEliminar = false;

            Mrojas = 0;
            Mamarillas = 0;
            Mnaranjas = 0;
            Mazules = 0;
            listaRojas = new ArrayList<>();
            listaNaranjas = new ArrayList<>();
            listaAmarillas = new ArrayList<>();
            listaAzules = new ArrayList<>();
            autorizacionesDisponibles = new String[6];
            // Reinicia cualquier otra lista o valor
            System.out.println("entro a iniciar");
            iniciarInscripcion();
        }
    }

    public void iniciarInscripcion() {
        //Esa lista se asigna a tu bean (listaM) y luego se usa en la página JSF para mostrar la retícula de materias.
        listaM = materiasCarrerasServicio.buscarMaterias(estudiante);
        periodoActual = periodoEscolarServicio.buscarPorId(periodoEscolarServicio.periodoActual());
        ///-----------------------------------------------------------
        PeriodoEscolar prueba = periodoEscolarServicio.buscarPorId("20251");//Componer porque no hay como tal 
        //.............................................................

        vr = avisosReinscripcionServicio.buscarAvisoReinscripcion(estudiante, prueba);//Para conseguir el limite de creditos
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        //Reinicializar valores
        Mrojas = 0;
        Mamarillas = 0;
        Mnaranjas = 0;
        Mazules = 0;
        listaRojas = new ArrayList<>();
        listaNaranjas = new ArrayList<>();
        listaAmarillas = new ArrayList<>();
        listaAzules = new ArrayList<>();

        guardandoPosicionesDeCadaTipoDeColor();//Contar cuantas materias hay de cada color.
        listaAE = autorizacionInscripcionServicio.buscarAutorizacionesAlumno("20252", estudiante.getNoDeControl());
        System.out.println("ListAE.size:" + listaAE.size());
        for (int i = 0; i < listaAE.size(); i++) {
            AutorizacionInscripcion ai = listaAE.get(i);

            System.out.println("===== REGISTRO " + i + " =====");
            System.out.println("Tipo: " + ai.getTipoAutorizacion());
            System.out.println("Motivo: " + ai.getMotivoAutorizacion());
            System.out.println("Autoriza: " + ai.getQuienAutoriza());
            System.out.println("Fecha: " + ai.getFechaHoraAutoriza());
            System.out.println("Cantidad: " + ai.getCantidad());
            System.out.println("ID: " + ai.getIdAutorizacion());
        }

        if (!listaAE.isEmpty()) {
            for (int i = 0; i < listaAE.size(); i++) {
                AutorizacionInscripcion x = listaAE.get(i);
                if (x.getTipoAutorizacion().equals("CM")) {
                    // Código para CARGA MÍNIMA
                    autorizacionesDisponibles[0] = "CM";
                } else if (x.getTipoAutorizacion().equals("CR")) {
                    // Código para CRUCE DE MATERIAS
                    autorizacionesDisponibles[1] = "CR";
                } else if (x.getTipoAutorizacion().equals("RQ")) {
                    // Código para CURSAR MATERIA REQUISITADA
                    autorizacionesDisponibles[2] = "RQ";
                } else if (x.getTipoAutorizacion().equals("SC")) {
                    // Código para SOBRECARGA
                    autorizacionesDisponibles[3] = "SC";
                } else if (x.getTipoAutorizacion().equals("12")) {
                    // Código para MÁS DE 12 SEMESTRES
                    autorizacionesDisponibles[4] = "12";
                } else if (x.getTipoAutorizacion().equals("OE")) {
                    // Código para OMITIR CURSOS DE REPETICIÓN/ESPECIALES
                    autorizacionesDisponibles[5] = "OE";
                } else {
                    // Código para cualquier otro valor no contemplado
                }
            }
        }

        validaciones();

        // List<HistoriaAlumno> listaHA = historiaAlumnoServicio.buscarAsignaturas(estudiante.getNoDeControl());
        promedio = estudiante.getPromedioAritmeticoAcumulado();
        listaGC = gruposServicio.buscarGruposCompletos(estudiante.getReticula(), estudiante.getSemestre(), periodoActual);

        //Validaciones de mensajes 
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("mensaje", mensaje);
        System.out.println("Color Mensaje" + colorMensaje);
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .put("colorMensaje", colorMensaje);
    }

    public void generarPDF() {
        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseHeader("Content-Disposition",
                    "attachment; filename=Horario_" + estudiante.getNoDeControl() + ".pdf");

            OutputStream out = ec.getResponseOutputStream();

            // ======= CREACIÓN DEL PDF =======
            // Ajustamos márgenes para no tapar header/footer
            Document pdf = new Document(PageSize.LETTER, 40, 40, 120, 80); // left, right, top, bottom

            // Cargar imágenes
            Image headerImg = Image.getInstance(ec.getRealPath("/resources/imagenes/pdfDiseños/header.png"));
            Image footerImg = Image.getInstance(ec.getRealPath("/resources/imagenes/pdfDiseños/footer.png"));

            // Registrar evento de header/footer
            PdfWriter writer = PdfWriter.getInstance(pdf, out);
            writer.setPageEvent(new HeaderFooterPDF(headerImg, footerImg));

            pdf.open();

            // ======= FUENTES =======
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font subTituloFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            Font infoLabelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font tableContentFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            // ======= ESPACIO EXTRA PARA HEADER =======
            pdf.add(Chunk.NEWLINE);
            pdf.add(Chunk.NEWLINE);

            // ======= TÍTULO CENTRADO =======
            Paragraph titulo = new Paragraph("INSTITUTO TECNOLÓGICO DE CUAUTLA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            pdf.add(titulo);

            Paragraph subtitulo = new Paragraph("HORARIO DE REINSCRIPCIÓN\n\n", subTituloFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            pdf.add(subtitulo);

            // ======= DATOS DEL ESTUDIANTE =======
            pdf.add(new Paragraph("NO. DE CONTROL: " + estudiante.getNoDeControl(), infoLabelFont));
            pdf.add(new Paragraph(
                    "NOMBRE: " + (estudiante.getApellidoPaterno() + " "
                            + estudiante.getApellidoMaterno() + " "
                            + estudiante.getNombreAlumno()).toUpperCase(),
                    infoFont));
            pdf.add(new Paragraph("CARRERA: " + estudiante.getReticula().getNombreCarrera().toUpperCase(), infoFont));
            pdf.add(new Paragraph("SEMESTRE: " + estudiante.getSemestre().toString().toUpperCase(), infoFont));
            pdf.add(new Paragraph("PERIODO: " + periodoActual.getIdentificacionLarga().toUpperCase(), infoFont));
            pdf.add(Chunk.NEWLINE);

            // ======= TABLA DE HORARIOS =======
            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);

            // Encabezados
            String[] columnas = {"Grupo", "Materia", "Asignatura", "Docente",
                "Lunes", "Martes", "Miércoles", "Jueves",
                "Viernes", "Sábado"};
            for (String col : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(col, tableHeaderFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Contenido de la tabla
            for (HorarioAsignatura h : listaHorarioASeleccionadas) {
                PdfPCell[] celdas = new PdfPCell[]{
                    new PdfPCell(new Phrase(h.getGrupo(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getMateria(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getAsignatura(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getDocente(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getLunes(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getMartes(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getMiercoles(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getJueves(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getViernes(), tableContentFont)),
                    new PdfPCell(new Phrase(h.getSabado(), tableContentFont))
                };
                for (PdfPCell celda : celdas) {
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(celda);
                }
            }

            pdf.add(table);

            pdf.close();
            fc.responseComplete();
            addMessage(FacesMessage.SEVERITY_INFO, "IMPRIMIENDO...", "Tu horario se ha impreso");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// ======= FUNCIÓN AUXILIAR PARA CENTRAR CELDAS =======
    private PdfPCell celdaCentro(String texto, Font f) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, f));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    public void recuperarPanelFlash() {
        Map<String, Object> flash = FacesContext.getCurrentInstance()
                .getExternalContext().getFlash();

        mensaje = (String) flash.get("mensaje");
        colorMensaje = (String) flash.get("colorMensaje");

        System.out.println("Mensaje " + mensaje);
        System.out.println("colorMensaje " + colorMensaje);
    }

    public void verificarAcceso() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .setKeepMessages(true);
        System.out.println("=== VERIFICACIÓN DE INSCRIPCIÓN ===");

        // Obtiene periodo de prueba
        PeriodoEscolar prueba = periodoEscolarServicio.buscarPorId("20251");
        vr = avisosReinscripcionServicio.buscarAvisoReinscripcion(estudiante, prueba);
        System.out.println("PruebaP" + prueba);
        System.out.println("Estudiante" + estudiante);
        System.out.println("VR" + vr);

        if (vr == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "NO REINSCRIPCION", "NO PUEDES REINSCRIBIRTE");
            return;
        }
        /*
        Date fechaSeleccionDate = vr.getFechaHoraSeleccion();

        LocalDateTime fechaSeleccion = fechaSeleccionDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime finVentana = fechaSeleccion.plusMinutes(50);

        // --- Impresiones en consola ---
        System.out.println("Fecha/Hora asignada al alumno: " + fechaSeleccion);
        System.out.println("Hora actual del sistema:        " + ahora);
        System.out.println("Fin de ventana (30 min):        " + finVentana);
        System.out.println("-----------------------------------------");

        // --- Validaciones ---
         if (ahora.isBefore(fechaSeleccion)) {
            System.out.println("[INFO] Aún no es tu hora, acceso bloqueado.");
            addMessage(FacesMessage.SEVERITY_WARN, "Aún no puedes inscribirte", "Tu horario empieza a las: " + fechaSeleccion);
            return;
        }

        if (ahora.isAfter(finVentana)) {
            System.out.println("[INFO] Se acabaron tus 30 minutos, acceso bloqueado.");
            addMessage(FacesMessage.SEVERITY_ERROR, "Tiempo expirado", "Tu ventana de inscripción terminó");
            return;
        }
        // 5. Si todo está correcto, continúa aquí
        System.out.println("[OK] Estás dentro del tiempo permitido, redirigiendo...");
        System.out.println("-----------------------------------------");

        
        // 1. Validación de nulls (si algo es null, salimos)
        if (vr.getAutorizaEscolar() == null
                || vr.getAdeudaBiblioteca() == null
                || vr.getAdeudaEscolar() == null
                || vr.getAdeudaFinancieros() == null
                || vr.getAdeudoEspecial() == null
                || vr.getBaja() == null
                || vr.getEgresa() == null
                || vr.getEncuesto() == null) {

            addMessage(FacesMessage.SEVERITY_ERROR, "DATOS INCOMPLETOS", "CONTACTA A ESCOLAR");
            return; // ← salir inmediatamente
        } 

        // 2. Imprimir valores
        System.out.println("AutorizaEscolar: " + vr.getAutorizaEscolar());
        System.out.println("AdeudaBiblioteca: " + vr.getAdeudaBiblioteca());
        System.out.println("AdeudaEscolar: " + vr.getAdeudaEscolar());
        System.out.println("AdeudaFinancieros: " + vr.getAdeudaFinancieros());
        System.out.println("AdeudoEspecial: " + vr.getAdeudoEspecial());
        System.out.println("Baja: " + vr.getBaja());
        System.out.println("Egresa: " + vr.getEgresa());
        System.out.println("Encuesto: " + vr.getEncuesto());
        // 3. Validar adeudos
        if (!(vr.getAutorizaEscolar() == 'S'
                && vr.getAdeudaBiblioteca() == 'N'
                && vr.getAdeudaEscolar() == 'N'
                && vr.getAdeudaFinancieros() == 'N'
                && vr.getAdeudoEspecial() == 'N')) {

            addMessage(FacesMessage.SEVERITY_ERROR, "ADEUDO PENDIENTE", "NO TE PUEDES REINSCRIBIR");
            return;
        }

        // 4. Validar situación escolar
        if (!(vr.getBaja() == 'N'
                && vr.getEgresa() == 'N'
                && vr.getEncuesto() == 'S')) {

            addMessage(FacesMessage.SEVERITY_ERROR, "REINSCRIPCIÓN NO DISPONIBLE POR SITUACION ESCOLAR", "NO TE PUEDES REINSCRIBIR");
            return;
        }*/

        // Aquí ya puedes continuar con la lógica
        try {
            // iniciarInscripcion();

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);

            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("inscripciones.xhtml");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    int s = 0;

    public void validaciones() {
        if (autorizacionesDisponibles[4] != null) {
            if (autorizacionesDisponibles[4].equals("12")) {
                mostrarPanel("Semestre rebasado autorizado", "mensaje-info");

            }
        } else {
            if (vr.getSemeste() > 12) {
                mostrarPanel("No te puedes inscribir en semestre inválido", "mensaje-error");
                bloquearAzules();
                return;

            }
        }

        if (validacionRojas()) {
            bloquearAzules();
            return;
        }

        for (int i = 0; i < autorizacionesDisponibles.length; i++) {
            System.out.println("Posición " + i + ": " + autorizacionesDisponibles[i]);
        }
        if (autorizacionesDisponibles[5] != null) {
            if (autorizacionesDisponibles[5].equals("OE")) {
                mostrarPanel("Puedes seleccionar materias no prioritarias", "mensaje-info");
                System.out.println("Se hizo mostrarPanel(\"Salto puedes seleccionar materias no prioritarias\", \"mensaje-naranja\");");
            }
        } else {
            if (validacionNaranjas()) {//Naranja. 
                return;
            }
            if (validacionAmarillas()) {
                return;
            }

        }
        validacionAzules();

    }

    public Boolean validacionRojas() {
        if (Mrojas != 0) {
            grupoBloqueado = true;
            //addMessage(FacesMessage.SEVERITY_ERROR, "CARRERA REPROBADA", "NO PUEDES SELECCIONAR NINGUNA MATERIA");
            mostrarPanel("No puedes seleccionar  [ Materia(s) reprobada(s) ]", "mensaje-error");
            return true;
        }
        return false;

    }

    public Boolean validacionNaranjas() {
        s = 0;
        if (Mnaranjas != 0) {
            grupoBloqueado = true;
            //addMessage(FacesMessage.SEVERITY_WARN, "PRIORIDAD  MATERIA(S) EN REPETICION", "SELECCIONA LAS MATERIAS EN REPETICION MAX. 2");

            for (int i = 0; i < listaNaranjas.size(); i++) {
                Reticula r = listaM.get(listaNaranjas.get(i)[0]);
                try {
                    s = listaNaranjas.get(i)[1];
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);
                    if (rd != null) {
                        rd.setDisponible(true);

                    }
                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }

            }

            /*  if (listaNaranjas.size() >= 2) {
                mostrarPanel("Selecciona materias prioritarias [ Materias en repetición Max. 2]" + " Tienes:" + listaNaranjas.size(), "mensaje-naranja");
                return true;
            }*/
            mostrarPanel("Selecciona materias prioritarias [ Materias en repetición Max. 2]" + " Tienes:" + listaNaranjas.size(), "mensaje-naranja");
            return true;
        }
        return false;
    }

    public Boolean validacionAmarillas() {
        if (Mamarillas != 0) {
            grupoBloqueado = true;
            //addMessage(FacesMessage.SEVERITY_WARN, "PRIORIDAD MATERIA(S) EN ORDINARIO", "SELECCIONA TODAS LAS MATERIAS EN ORDINARIO ");
            /* if (listaNaranjas.size() == 1) {
                mostrarPanel("Selecciona materias prioritarias [Materias en repetición Min. 1]->[ Materias en ordinario" + listaAmarillas.size() + "]", "mensaje-amarillas");
            }*/
            mostrarPanel("Selecciona materias prioritarias [ Materias en ordinario " + listaAmarillas.size() + "]", "mensaje-amarillo");
            for (int i = 0; i < listaAmarillas.size(); i++) {
                Reticula r = listaM.get(listaAmarillas.get(i)[0]);
                try {
                    s = listaAmarillas.get(i)[1];
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);
                    if (rd != null) {
                        rd.setDisponible(true);
                    }
                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }

            }
            return true;
        }
        return false;
    }

    public void bloquearAzules() {
        int materiasDisponibles = 0;
        if (Mazules != 0) {
            int semestreAlumno = estudiante.getSemestre();
            //  addMessage(FacesMessage.SEVERITY_INFO, "MATERIAS DISPONIBLES ", "SELECCIONA TUS MATERIAS");

            // Caso 1: el semestre del alumno es PAR y s es IMPAR
            for (int i = 0; i < listaAzules.size(); i++) {
                Reticula r = listaM.get(listaAzules.get(i)[0]);
                try {
                    s = listaAzules.get(i)[1];

                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);
                    // IMPRIME LOS VALORES
                    if (rd != null) {
                        System.out.println("-----------------------------------");
                        System.out.println("Analizando materia:");
                        System.out.println("  s = " + s);
                        System.out.println("  Disponible = " + rd.getDisponible());
                        System.out.println("  Color = " + rd.getColor());

                    }
                    if (rd != null) {
                        rd.setDisponible(false);
                        rd.setColor("nodisponible");
                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }
            }

        }

    }

    public void validacionAzules() {
        int materiasDisponibles = 0;
        Boolean autorizo=false;
        if (Mazules != 0) {
            int semestreAlumno = estudiante.getSemestre();
            //  addMessage(FacesMessage.SEVERITY_INFO, "MATERIAS DISPONIBLES ", "SELECCIONA TUS MATERIAS");

            // Caso 1: el semestre del alumno es PAR y s es IMPAR
            for (int i = 0; i < listaAzules.size(); i++) {
                Reticula r = listaM.get(listaAzules.get(i)[0]);
                try {
                    s = listaAzules.get(i)[1];
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);
                    // IMPRIME LOS VALORES
                    if (rd != null) {
                        System.out.println("-----------------------------------");
                        System.out.println("Analizando materia:");
                        System.out.println("  s = " + s);
                        System.out.println("  Disponible = " + rd.getDisponible());
                        System.out.println("  Color = " + rd.getColor());
                    }
                    if (rd != null) {
                        // Caso 1: el semestre del alumno es PAR y el semestre de la materia es IMPAR
                        if (semestreAlumno % 2 == 0 && s % 2 != 0) {
                            if (!rd.getDisponible() && !"blanco".equals(rd.getColor())) {
                                // rd.setDisponible(false);
                                //rd.setColor("nodisponible");
                                autorizarRequisitoMateria(rd);
                                System.out.println("Desactivado semestre " + s + " (Alumno par, materia impar)");
                            }
                        } // Caso 2: el semestre del alumno es IMPAR y el semestre de la materia es PAR
                        else if (semestreAlumno % 2 != 0 && s % 2 == 0) {
                            if (!rd.getDisponible() && !"blanco".equals(rd.getColor())) {
                                //rd.setDisponible(false);
                                // rd.setColor("nodisponible");
                                autorizarRequisitoMateria(rd);
                                System.out.println("Desactivado semestre " + s + " (Alumno impar, materia par)");
                            }
                        } // Caso 3 (opcional): si coincide par-par o impar-impar, se activa
                        else {
                            
                            System.out.println("else rd.getMateria()" + rd.getMateria());
                            
                                //VERIFICACION DE MATERIAS REQUISITADAS
                            //Busco la materia para ver si tiene un requisito. 
                            //No siempre debe tener un requisito.
                            RequisitosMateria rm = requisitosMateriaServicio.encontrarAntecedenteMateria(
                                    rd.getMateria());
                            //Si no tiene requisito puede tomarla
                            System.out.println("[" + rd.getMateria() + "]");

                            if (rd.getMateria().equals("1R6")) {
                                System.out.println("EXISTE MAS DE UN RM");
                                System.out.println("rm.getMateriaRelacion().getMateria:" + rm.getMateriaRelacion().getMateria());
                                System.out.println("estudiante.getNoDeControl():" + estudiante.getNoDeControl());

                            }

                            if (rm != null) {
                                
                                autorizo =autorizarRequisitoMateria(rd);//En cuanto encontrara uno tenía que regresar. 
                                List<HistoriaAlumno> ha = historiaAlumnoServicio.buscarPorEstudianteMateria(
                                        rm.getMateriaRelacion().getMateria(),
                                        estudiante.getNoDeControl());

                                System.out.println("Ha.size" + ha.size());

                                //Primero veo si no existe un historial de la materia de la materia del alumno que va a cursar quiere decir que nunca 
                                //la ha tomado, entonces al no tomarla quiere decir que el antecedente no existe y no puede tomar la siguiente
                                if (!ha.isEmpty()) {

                                    for (int j = 0; j < ha.size(); j++) {
                                        HistoriaAlumno hi = ha.get(j);
                                        //Verifica que la materia requisitada no tenga 0, si tiene 0 quiere decir que no la ha pasado y no puede tomar
                                        //la siguiente. 

                                        System.out.println("HiPeriodo" + hi.getPeriodo());
                                        System.out.println("HiMateria" + hi.getMateria().getMateria());
                                        System.out.println("HiCalificacion" + hi.getCalificacion());
                                        if (hi.getCalificacion() == 0 && autorizo!=true) {
                                           // System.out.println("Rd.getDisponible"+ rd.getDisponible());
                                           // System.out.println("Rd.getColor"+ rd.getColor());
                                            rd.setDisponible(false);
                                            rd.setColor("nodisponible");
                                        } else {
                                            rd.setDisponible(true);
                                            rd.setColor("disponible");

                                        }

                                    }

                                } else {
                                    //Si no hay historial obviamente no hay materia antecedente que no tomo, por lo general no podria tomar la siguiente
                                    //Aun si no curso la materia, verificamos que si esta autorizada si esta autorizada se activa inmediatamente, puede haber uno o mas.

                                }

                            } else {

                                rd.setDisponible(true);
                                rd.setColor("disponible");
                            }

                            System.out.println("Activado semestre " + s + " (coincide paridad)");
                        }

                        if (rd.getDisponible()) {
                            materiasDisponibles++;
                        }

                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }
            }

            mostrarPanel("MATERIAS DISPONIBLES[ MATERIAS" + materiasDisponibles + "]", "mensaje-info");

        }
    }

    public Boolean autorizarRequisitoMateria(ReticulaDatos rd) {
        //La posicoon 2 es para checar si hay al menos una materia requisitdada
        System.out.println("autorizacionesDisponibles[2]" + autorizacionesDisponibles[2]);
        if (autorizacionesDisponibles[2] != null) {
            System.out.println("autorizacionesDisponibles[2]" + autorizacionesDisponibles[2]);
            if (autorizacionesDisponibles[2].equals("RQ")) {
                System.out.println("listaAE.size():" + listaAE.size());
                //Pueden haber varias materias requisitadas, entonces recorro la lista de autorizacios para materias requisitadas
                for (int j = 0; j < listaAE.size(); j++) {
                    AutorizacionInscripcion au = listaAE.get(j);
                    if (au.getMateriaAfectada() != null) {//No todas las autorizaciones tienen materia para requisitar.
                        System.out.println("Au rd.getMateria():" + rd.getMateria());
                        System.out.println("au.getMateriaAfectada().getMateria():" + au.getMateriaAfectada().getMateria());
                        if (rd.getMateria().equals(au.getMateriaAfectada().getMateria())) {
                            rd.setDisponible(true);
                            rd.setColor("disponible");
                            return true;

                        } else {
                            rd.setDisponible(false);
                            rd.setColor("nodisponible");

                        }
                    }

                }

            }
        } else {

//Si no hay ninguna autorizada si la desactiva xd.
            rd.setDisponible(false);
            rd.setColor("nodisponible");

        }
        return false;
    }

    public void guardandoPosicionesDeCadaTipoDeColor() {//

        for (int j = 0; j < listaM.size(); j++) { // Recorro cada retícula
            Reticula r = listaM.get(j);
            System.out.println("===== Retícula #" + (j + 1) + " =====");

            for (int s = 1; s <= 10; s++) { // Recorro los 10 semestres
                try {
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);

                    if (rd != null) {
                        System.out.println("Semestre " + s + ": " + rd.getNombreMateria());
                        rd.setDisponible(false);

                        if ("especial".equals(rd.getColor())) {
                            Mrojas++;
                            listaRojas.add(new int[]{j, s});
                        }

                        if ("repeticion".equals(rd.getColor())) {
                            Mnaranjas++;
                            listaNaranjas.add(new int[]{j, s});
                        }

                        if ("ordinario".equals(rd.getColor())) {
                            Mamarillas++;
                            listaAmarillas.add(new int[]{j, s});
                        }

                        if ("disponible".equals(rd.getColor())) {
                            Mazules++;
                            listaAzules.add(new int[]{j, s});
                        }

                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }
            }
            System.out.println("-----------------------------------");
        }
    }

    public void verHorarios(String grupo) {
        this.grupo = grupo;
        listaHorarioAsignatura = gruposServicio.buscarGruposPorCampoGrupoSeleccionada(estudiante.getReticula().getReticula(),
                estudiante.getSemestre(),
                periodoActual.getPeriodo(),
                grupo);
    }

    public void onMateriaClick(ReticulaDatos materia) {
        // Aquí recuperas la materia seleccionada desde el component binding
        System.out.println("Materia Escogida->" + materia + "disponible?:->" + materia.getDisponible());
        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA", materia.getNombreMateria());

        materiaDeTabla = materia;
        materiaDeTabla.setDisponible(true);

        System.out.println("Nombre:" + materiaDeTabla.getNombreMateria()
                + "Id:" + materiaDeTabla.getMateria()
                + "Creditos" + materiaDeTabla.getCreditos()
                + "Color:" + materiaDeTabla.getColor()
                + "Clave:" + materiaDeTabla.getClave()
                + "Calificacion" + materiaDeTabla.getCalificacion());

        materiaSeleccionada = materiasCarrerasServicio.buscarMateriasCarreraPorMateria(materiaDeTabla.getMateria());
        listaPeriodoEscolar = periodoEscolarServicio.periodosEscolaresActivos();

        System.out.println("---------------DATOS---------------------");
        System.out.println("Encontre:" + materiaSeleccionada);
        System.out.println("Retícula: " + materiaSeleccionada.getReticula().getReticula());
        System.out.println("Semestre: " + materiaSeleccionada.getSemestreReticula());
        System.out.println("Periodo: " + listaPeriodoEscolar.get(0).getPeriodo());
        System.out.println("Materia: " + materiaSeleccionada.getMateria().getMateria());

        listaHorarioAsignatura = gruposServicio.buscarGruposPorCampoMateriaSeleccionada(materiaSeleccionada.getReticula().getReticula(),
                materiaSeleccionada.getSemestreReticula(),
                listaPeriodoEscolar.get(0).getPeriodo(),
                materiaSeleccionada.getMateria().getMateria());

    }

    public void confirmarSeleccionGrupo() {

        grupoBloqueado = true;//Para bloquear los grupos si ya he seleccionado

        listaHorarioASeleccionadas = listaHorarioAsignatura;//Para actualizar la tabla que estoy seleccionando 
        restaurarHorarioReticula();
        //cambiarEstadoMateriasSeleccionadasPorGrupo();
        addMessage(FacesMessage.SEVERITY_INFO, "GRUPO SELECCIONADO", "HAZ SELECCIONADO:" + grupo);
    }

    public void restaurarHorarioReticula() {

        for (int i = 0; i < listaHorarioASeleccionadas.size(); i++) {//Recorro para encontrar todas las materias 
            HorarioAsignatura ha = listaHorarioASeleccionadas.get(i);

            for (int j = 0; j < listaM.size(); j++) {//Recorro cada reticula de la tabla
                Reticula r = listaM.get(j);

                System.out.println("===== Retícula #" + (j + 1) + " =====");

                // Recorremos los 10 semestres dinámicamente
                for (int s = 1; s <= 10; s++) {//Recorro los semestres de cada reticula
                    try {
                        // Obtener el semestre actual usando reflexión
                        ReticulaDatos rd = (ReticulaDatos) r.getClass()
                                .getMethod("getSemestre" + s)
                                .invoke(r);

                        if (rd != null) {
                            // Aquí puedes guardar o procesar el objeto rd
                            System.out.println("Semestre " + s + ": " + rd.getNombreMateria());
                            System.out.println("HorarioAsignaturaGetMateria->" + ha.getMateria());
                            System.out.println("ReticulaDatosGetMateria->" + rd.getMateria());

                            if (ha.getMateria().equals(rd.getMateria())) {

                                if (grupoBloqueado) {
                                    rd.setDisponible(false);
                                    rd.setColor("cursando");
                                } else {
                                    rd.setDisponible(true);
                                    rd.setColor("disponible");
                                }

                            }
                        } else {
                            System.out.println("Semestre " + s + ": vacío");
                        }

                    } catch (Exception e) {
                        System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                    }
                }

                System.out.println("-----------------------------------");
            }

        }

    }

    public void guardarHorario() {

        for (int i = 0; i < listaHorarioASeleccionadas.size(); i++) {
            HorarioAsignatura hc = listaHorarioASeleccionadas.get(i);
            seleccionMateriasServicio.insertarNuevoSeleccionMaterias(
                    estudiante.getNoDeControl(),
                    hc);
        }

        addMessage(FacesMessage.SEVERITY_INFO, "HORARIO GUARDADO", "SE HA GUARDADO EL HORARIO");
        creditosSeleccionados = 0; //Para contabilizar de nuevo

        modoImprimir = true;
        modoInsertar = true;
        modoEliminar = true;
    }

    public void eliminarHorario(HorarioAsignatura item) {
        System.out.println("Item:" + item);
        listaHorarioASeleccionadas.remove(item);
        addMessage(FacesMessage.SEVERITY_WARN, "REMOVIENDO SELECCION",
                "La materia " + item.getAsignatura()
                + " se ha removido.");
        for (int j = 0; j < listaM.size(); j++) { // Recorro cada retícula
            Reticula r = listaM.get(j);
            System.out.println("===== Retícula #" + (j + 1) + " =====");

            for (int s = 1; s <= 10; s++) { // Recorro los 10 semestres
                try {
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);

                    if (rd != null) {
                        System.out.println("Semestre " + s + ": " + rd.getNombreMateria());
                        if (rd.getMateria().equals(item.getMateria())) {
                            rd.setDisponible(true);
                            rd.setColor("disponible");
                            creditosSeleccionados -= Integer.parseInt(rd.getCreditos());

                            if (rd.getColor().equals("repeticion")) {

                                Mnaranjas++;
                                System.out.println("🟠 Mnaranjas = " + Mnaranjas);
                                System.out.println("🟡 Mamarillas = " + Mamarillas);
                                System.out.println("Materia color = " + rd.getColor());

                                validaciones();

                            } else if (rd.getColor().equals("ordinario")) {

                                Mamarillas++;

                                System.out.println("🟠 Mnaranjas = " + Mnaranjas);
                                System.out.println("🟡 Mamarillas = " + Mamarillas);
                                System.out.println("Materia color = " + materiaDeTabla.getColor());

                                validaciones();

                            }
                        }
                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }
            }
            System.out.println("-----------------------------------");
        }
    }

    public void eliminarHorario() {//Mañana 06-11-2025
        addMessage(FacesMessage.SEVERITY_INFO, "HORARIO ELIMINADO", "SE HA ELIMINADO EL HORARIO");
        /*if (grupoBloqueado) {//para avisar que solo sea por grupo
            cambiarEstadoMateriasSeleccionadasPorGrupo();
        }*/
        grupoBloqueado = false;//Para bloquear los grupos si ya he seleccionado
        restaurarHorarioReticula();
        listaHorarioASeleccionadas = new ArrayList();
        modoImprimir = false;

        creditosSeleccionados = 0;//Para reinicializacion. 
        guardandoPosicionesDeCadaTipoDeColor();
        validaciones();

    }

    public void onTabChange() { //Solo se activa Sleccion con grupos cuando todas las materias a escoger estan en azul en el semestre del alumno.
        //System.out.println("estudiante.getCreditosAprobados()" + estudiante.getCreditosAprobados());
        //System.out.println("estudiante.getCreditosCursados()" + estudiante.getCreditosCursados());

        modoGrupo = !modoGrupo;
        if (modoGrupo) {//Para evitar que se ejecute todo sino que solamente cuando entre a modoGrupo.
            if (Objects.equals(estudiante.getCreditosAprobados(), estudiante.getCreditosCursados())) {
                grupoBloqueado = false;//Si ambos creditos aprobados o cursados son iguales si puedo seleccionar grupo. 
                //addMessage(FacesMessage.SEVERITY_INFO, "CREDITOS ALCANZADOS", "HAZ ALCANZADO LOS CREDITOS PUEDES SELECCIONAR GRUPO");
                int n = 0;
                int semestre = estudiante.getSemestre();
                for (Reticula r : listaM) {
                    try {
                        // Obtener dinámicamente el semestre usando reflexión
                        ReticulaDatos rd = (ReticulaDatos) r.getClass()
                                .getMethod("getSemestre" + semestre)
                                .invoke(r);

                        if (rd != null && rd.getDisponible()) {
                            n++;
                        }
                    } catch (Exception e) {
                        System.out.println("Error al obtener semestre " + semestre + ": " + e.getMessage());
                    }
                }

                // Determinar cuántas materias son necesarias para desbloquear el grupo
                int materiasRequeridas;
                switch (semestre) {
                    case 1:
                    case 2:
                    case 5:
                        materiasRequeridas = 6;
                        break;
                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                        materiasRequeridas = 7;
                        break;
                    case 9:
                        grupoBloqueado = true;
                        addMessage(FacesMessage.SEVERITY_INFO, "RESIDENCIAS", "SELECCIONA DESDE RETICULA");
                        return;
                    default:
                        grupoBloqueado = true;
                        addMessage(FacesMessage.SEVERITY_INFO, "GRUPOS NO DISPONIBLES", "NO PUEDES SELECCIONAR, AUTORIZA JEFE DE CARRERA");
                        return;
                }

                // Finalmente, asignar bloqueado o no
                grupoBloqueado = n != materiasRequeridas;
                if (!grupoBloqueado && modoGrupo) {
                    addMessage(FacesMessage.SEVERITY_INFO, "HORARIO POR GRUPO", "SELECCIONA UN GRUPO");
                } else {
                    addMessage(FacesMessage.SEVERITY_WARN, "HORARIO POR GRUPO", "NO PUEDES SELECCIONAR NINGUN GRUPO");

                }

                return;
            } else {
                grupoBloqueado = true;
                System.out.println("ENTROOOOOOOOOOOOOOOO ONTABACHANGE 2");
                if (modoGrupo) {
                    addMessage(FacesMessage.SEVERITY_WARN, "CREDITOS NO ALCANZADOS", "NO HAZ ALCANZADO LOS CREDITOS SUFICIENTE");
                }

                return;
            }

        }

        // grupoBloqueado = true;//Para bloquear los grupos si ya he seleccionado
    }

    public void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {//Para mostrar mensaje en ventana
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
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

    public void onSeleccionarGrupo() {
        Boolean mensajeAutorizado = null;

        System.out.println("===============SELECCIONADO==================");
        System.out.println("Seleccionado: " + grupoSeleccionado);
        System.out.println("Grupo: " + grupoSeleccionado.getGrupo());
        System.out.println("IdGrupo: " + grupoSeleccionado.getId());
        System.out.println("Materia: " + grupoSeleccionado.getMateria());
        System.out.println("Asignatura: " + grupoSeleccionado.getAsignatura());
        System.out.println("Docente: " + grupoSeleccionado.getDocente());
        System.out.println("Lunes: " + grupoSeleccionado.getLunes());
        System.out.println("Martes: " + grupoSeleccionado.getMartes());
        System.out.println("Miércoles: " + grupoSeleccionado.getMiercoles());
        System.out.println("Jueves: " + grupoSeleccionado.getJueves());
        System.out.println("Viernes: " + grupoSeleccionado.getViernes());
        System.out.println("Sábado: " + grupoSeleccionado.getSabado());
        System.out.println("Global: " + grupoSeleccionado.getGlobal());
        System.out.println("================SELECCIONADO=================");
        creditosSeleccionados += Integer.parseInt(materiaDeTabla.getCreditos());
        System.out.println("creditosSeleccionados->" + creditosSeleccionados + "creditosAutorizados->" + vr.getCreditosAutorizados());

        if (autorizacionesDisponibles[3] != null) {
            if (autorizacionesDisponibles[3].equals("SC")) {
                if (listaAE != null) {
                    for (int i = 0; i < listaAE.size(); i++) {
                        AutorizacionInscripcion x = listaAE.get(i);
                        if (x.getTipoAutorizacion().equals("SC") && x.getMateriaAfectada().getMateria().equals(grupoSeleccionado.getMateria())) {
                            MateriasCarreras mc = materiasCarrerasServicio.buscarMateriasCarreraPorMateria(grupoSeleccionado.getMateria());
                            vr.setCreditosAutorizados(vr.getCreditosAutorizados()
                                    + mc.getCreditosMateria()
                            );
                            addMessage(FacesMessage.SEVERITY_WARN, "SOBRE CARGA",
                                    "Créditos autorizados: " + vr.getCreditosAutorizados()+
                            " por "+x.getMateriaAfectada().getMateria());
                            break;
                        }
                    }
                }

            }
        }

        if (creditosSeleccionados <= vr.getCreditosAutorizados()) {
            boolean empalme = false;
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            limpiarEspaciosInicioHorarios(listaHorarioASeleccionadas);
            limpiarEspaciosInicioHorarios(Arrays.asList(grupoSeleccionado));
            for (HorarioAsignatura hc : listaHorarioASeleccionadas) {
                // Recorremos cada día
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
                            horariosNuevo = grupoSeleccionado.getLunes();
                            break;
                        case "martes":
                            horariosNuevo = grupoSeleccionado.getMartes();
                            break;
                        case "miercoles":
                            horariosNuevo = grupoSeleccionado.getMiercoles();
                            break;
                        case "jueves":
                            horariosNuevo = grupoSeleccionado.getJueves();
                            break;
                        case "viernes":
                            horariosNuevo = grupoSeleccionado.getViernes();
                            break;
                        case "sabado":
                            horariosNuevo = grupoSeleccionado.getSabado();
                            break;
                    }
                    System.out.println("horariosExistentes[" + horariosExistentes + "]");
                    System.out.println("horariosNuevo[" + horariosNuevo + "]");

                    if (horariosExistentes != null && horariosNuevo != null) {
                        // Separar rangos por el separador usado
                        String[] rangosExistentes = horariosExistentes.split("   ");
                        String[] rangosNuevo = horariosNuevo.split("   ");

                        for (String rangoE : rangosExistentes) {
                            for (String rangoN : rangosNuevo) {
                                // Parsear horas
                                String[] hE = rangoE.split(" - ");
                                String[] hN = rangoN.split(" - ");

                                try {
                                    Date inicioE = formatoHora.parse(hE[0].trim());
                                    Date finE = formatoHora.parse(hE[1].trim());
                                    Date inicioN = formatoHora.parse(hN[0].trim());
                                    Date finN = formatoHora.parse(hN[1].trim());

                                    // Verificar si se empalman
                                    System.out.println("inicioE = " + inicioE);
                                    System.out.println("finE = " + finE);
                                    System.out.println("inicioN = " + inicioN);
                                    System.out.println("finN = " + finN);
                                    if (inicioN.before(finE) && finN.after(inicioE)) {

                                        if (autorizacionesDisponibles[1] != null) {
                                            if (autorizacionesDisponibles[1].equals("CR")) {

                                                for (int i = 0; i < listaAE.size(); i++) {
                                                    AutorizacionInscripcion ai = listaAE.get(i);
                                                    if (ai.getTipoAutorizacion().equals("CR")) {
                                                        if (ai.getMateriaAfectada() != null) {
                                                            System.out.println("ai.getMateriaAfectada()" + ai.getMateriaAfectada().getMateria());
                                                            System.out.println("grupoSeleccionado.getMateria()" + grupoSeleccionado.getMateria());
                                                            if (ai.getMateriaAfectada().getMateria().equals(grupoSeleccionado.getMateria())) {
                                                                empalme = false;
                                                                mensajeAutorizado = true;
                                                                break;
                                                            } else {
                                                                empalme = true;
                                                                mensajeAutorizado = false;

                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        } else {
                                            empalme = true;
                                            creditosSeleccionados -= Integer.parseInt(materiaDeTabla.getCreditos());
                                            addMessage(FacesMessage.SEVERITY_WARN, "EMPALME DETECTADO",
                                                    "La materia " + grupoSeleccionado.getAsignatura()
                                                    + " tiene horario que se empalma");
                                            break;

                                        }

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

            if (!empalme) {

                materiaDeTabla.setColor("cursando");
                materiaDeTabla.setDisponible(false);
                addMessage(FacesMessage.SEVERITY_INFO, "HORARIO SELECCIONADO GRUPO " + grupoSeleccionado.getGrupo(),
                        grupoSeleccionado.getAsignatura());
                listaHorarioASeleccionadas.add(grupoSeleccionado);

                //Para validacion escoga por turnos
                if (materiaDeTabla.getColor().equals("repeticion")) {
                    if (Mnaranjas > 0) {
                        Mnaranjas--;
                        System.out.println("🟠 Mnaranjas = " + Mnaranjas);
                        System.out.println("🟡 Mamarillas = " + Mamarillas);
                        System.out.println("Materia color = " + materiaDeTabla.getColor());

                        validaciones();
                    }

                } else if (materiaDeTabla.getColor().equals("ordinario")) {
                    if (Mamarillas > 0) {
                        Mamarillas--;

                        System.out.println("🟠 Mnaranjas = " + Mnaranjas);
                        System.out.println("🟡 Mamarillas = " + Mamarillas);
                        System.out.println("Materia color = " + materiaDeTabla.getColor());

                        validaciones();
                    }

                }
            }

            if (mensajeAutorizado != null) {
                if (mensajeAutorizado) {
                    addMessage(FacesMessage.SEVERITY_INFO, "EMPALME AUTORIZADO",
                            "La materia " + grupoSeleccionado.getAsignatura()
                            + " tiene horario que se empalma");
                } else {
                    addMessage(FacesMessage.SEVERITY_WARN, "EMPALME DETECTADO",
                            "La materia " + grupoSeleccionado.getAsignatura()
                            + " tiene horario que se empalma");

                }
            }
            mensajeAutorizado = null;
        } else {
            creditosSeleccionados -= Integer.parseInt(materiaDeTabla.getCreditos());
            addMessage(FacesMessage.SEVERITY_WARN, "CREDITOS AUTORIZADOS ALCANZADOS",
                    "No puedes seleccionar mas materias");

        }

    }
}
