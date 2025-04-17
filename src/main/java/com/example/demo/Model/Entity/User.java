package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "info_empleado") // Nombre de la tabla en la base de datos
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genero") // Nombre de la columna
    private String genero;

    @Column(name = "rh")
    private String rh;

    @Column(name = "celular")
    private String celular;

    @Column(name = "email_personal")
    private String emailPersonal;

    @Column(name = "fecha_de_nacimiento")
    private String fechaDeNacimiento;

    @Column(name = "contacto_emergencia_1")
    private String contactoEmergencia1;

    @Column(name = "numero_emergencia_1")
    private String numeroEmergencia1;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "tiene_hijos")
    private String tieneHijos;

    @Column(name = "cantidad_hijos")
    private String cantidadHijos;

    @Column(name = "tipo_familia")
    private String tipoFamilia;

    @Column(name = "nivel_escolaridad")
    private String nivelEscolaridad;

    @Column(name = "profesion")
    private String profesion;

    @Column(name = "estudios_en_curso")
    private String estudiosEnCurso;

    @Column(name = "tipo_de_estudio_en_curso")
    private String tipoDeEstudioEnCurso;

    @Column(name = "nombre_estudio_en_curso")
    private String nombreEstudioEnCurso;

    @Column(name = "tipo_de_vivienda")
    private String tipoDeVivienda;

    @Column(name = "ciudad_municipio_residencia")
    private String ciudadMunicipioResidencia;

    @Column(name = "localidad_solo_para_bogota")
    private String localidadSoloParaBogota;

    @Column(name = "barrio_solo_para_bogota")
    private String barrioSoloParaBogota;

    @Column(name = "direccion_actual")
    private String direccionActual;

    @Column(name = "tiene_mascota")
    private String tieneMascota;

    @Column(name = "cantidad_de_mascotas")
    private String cantidadDeMascotas;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "nombre1")
    private String nombre1;

    @Column(name = "nombre2")
    private String nombre2;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "password")
    private String password;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "estado_de_registro")
    private String estadoDeRegistro;

    @Column(name = "img_profile")
    private byte[] imgProfile;

    @Column(name = "ubicacion_lab")
    private String ubicacionLab;

    @Column(name = "cedula_pdf")
    private byte[] cedulaPdf;

    @Column(name = "cv_pdf")
    private byte[] cv;

    @Column(name = "certif_laboral_pdf")
    private byte[] certifLaboral;

    @Column(name = "certif_academica_pdf")
    private byte[] certifAcademica;
    
    @Column(name = "certif_eps_pdf")
    private byte[] certifEps;
    
    @Column(name = "certif_fondo_pension_pdf")
    private byte[] certifFondoPension;
    
    @Column(name = "certif_arl_pdf")
    private byte[] certifArl;

    @Column(name = "certif_bancario_pdf")
    private byte[] certifBancario;
    
    @Column(name = "certif_anteced_disciplinario_pdf")
    private byte[] certifAntecedDisciplinario;
    
    @Column(name = "certif_anteced_fiscales_pdf")
    private byte[] certifAntecedFiscales;
    
    @Column(name = "certif_anteced_penales_pdf")
    private byte[] certifAntecedPenales;
    
    @Column(name = "libreta_militar_pdf")
    private byte[] libretaMilitar;
    
    @Column(name = "certif_med_ocupacional_pdf")
    private byte[] certifMedOcupacional;
    
    @Column(name = "refer_personales_pdf")
    private byte[] referPersonales;

    @Column (name = "id_rol")
    private String idRol;

}