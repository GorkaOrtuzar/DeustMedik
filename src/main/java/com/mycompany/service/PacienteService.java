package com.mycompany.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;

@Service
public class PacienteService {
    private final RepositorioPaciente repositorioPaciente;
    private final RepositorioCita repositorioCita;


   public PacienteService(RepositorioPaciente RepositorioPaciente, RepositorioCita repositorioCita) {
      this.repositorioPaciente = RepositorioPaciente;
      this.repositorioCita = repositorioCita;
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


   /**
 * Obtiene el historial de citas para un paciente.
 */
public List<Cita> obtenerHistorialCitas(String dni) {
    // Buscar al paciente por DNI
    Optional<Paciente> pacienteOptional = repositorioPaciente.findByDni(dni);
    
    if (pacienteOptional.isEmpty()) {
        // Paciente no encontrado
        System.out.println("Paciente no encontrado para obtener historial de citas.");
        return Collections.emptyList();
    }
    
    Paciente paciente = pacienteOptional.get();
    
    // Obtener todas las citas del paciente ordenadas por fecha (más reciente primero)
    List<Cita> citas = repositorioCita.findByPacienteDNIOrderByFechaHoraDesc(paciente.getDni());
    System.out.println("Se encontraron " + citas.size() + " citas para el paciente con DNI: " + dni);
    
    return citas;
}


  
}
