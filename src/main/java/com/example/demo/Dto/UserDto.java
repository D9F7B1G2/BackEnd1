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
    private String passwordAnterior;  // Nuevo campo
    private String passwordConfirmacion;  // Nuevo campo
    private String departamento;
    private String tipoDocumento;
    private String estadoDeRegistro;
    private byte[] cvProfile;
    private String imgProfile;

}
