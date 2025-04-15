package com.mycompany.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioPaciente;

@Service
public class PacienteService {
    private final RepositorioPaciente repositorioPaciente;


   public PacienteService(RepositorioPaciente RepositorioPaciente) {
      this.repositorioPaciente = RepositorioPaciente;
   }

   public Optional<String> login(String dni, String password) {
      Optional<Paciente> pacienteOptional = this.repositorioPaciente.findByDni(dni);
      if (pacienteOptional.isPresent()) {
          Paciente paciente = pacienteOptional.get();
          System.out.println("Usuario encontrado");
          
          // Aquí es donde debes verificar la contraseña
          if (paciente.getContrasenia().equals(password)) {
              System.out.println("Login exitoso");
              return Optional.of(paciente.getDni()); // O devolver algún token/identificador
          } else {
              System.out.println("Contraseña inválida o no se pudo validar.");
              return Optional.empty();
          }
      } else {
          System.out.println("Usuario no encontrado.");
          return Optional.empty();
      }
  }


   public Paciente getPacienteByDNI(String dni) {
      return !this.repositorioPaciente.findByDni(dni).isPresent() ? null : (Paciente)this.repositorioPaciente.findByDni(dni).get();
   }

  
}
