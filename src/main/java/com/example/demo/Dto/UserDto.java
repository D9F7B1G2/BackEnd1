package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String genero;
    private String rh;
    private String celular;
    private String emailPersonal;
    private String fechaDeNacimiento;
    private String contactoEmergencia1;
    private String numeroEmergencia1;
    private String estadoCivil;
    private String tieneHijos;
    private String cantidadHijos;
    private String tipoFamilia;
    private String nivelEscolaridad;
    private String profesion;
    private String estudiosEnCurso;
    private String tipoDeEstudioEnCurso;
    private String nombreEstudioEnCurso;
    private String tipoDeVivienda;
    private String ciudadMunicipioResidencia;
    private String localidadSoloParaBogota;
    private String barrioSoloParaBogota;
    private String direccionActual;
    private String tieneMascota;
    private String cantidadDeMascotas;
    private String numeroDocumento;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String password;
    private String passwordAnterior;  
    private String passwordConfirmacion;  
    private String departamento;
    private String tipoDocumento;
    private String estadoDeRegistro;
    private byte[] imgProfile;
    private String ubicacionLab;
    private byte[] cedulaPdf;
    private byte[] cv;
    private byte[] certifLaboral;
    private byte[] certifAcademica;
    private byte[] certifEps;
    private byte[] certifFondoPension;
    private byte[] certifArl;
    private byte[] certifBancario;
    private byte[] certifAntecedDisciplinario;
    private byte[] certifAntecedFiscales;
    private byte[] certifAntecedPenales;
    private byte[] libretaMilitar;
    private byte[] certifMedOcupacional;
    private byte[] referPersonales;

    private String idRol;
}
