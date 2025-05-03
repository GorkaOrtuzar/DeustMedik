package com.mycompany.service;

import com.mycompany.modelo.Mensaje;
import com.mycompany.modelo.Paciente;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioMensaje;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.DTO.ConversacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MensajeService {

    @Autowired
    private RepositorioMensaje repositorioMensaje;

    @Autowired
    private RepositorioPaciente pacienteRepository;

    @Autowired
    private RepositorioMedico medicoRepository;

    public Mensaje guardarMensaje(Mensaje mensaje) {
        mensaje.setFecha(LocalDateTime.now());
        return repositorioMensaje.save(mensaje);
    }

    public List<Mensaje> obtenerConversacion(String remitenteDni, String destinatarioDni) {
        return repositorioMensaje
            .findByRemitenteDniAndDestinatarioDniOrRemitenteDniAndDestinatarioDniOrderByFecha(
                remitenteDni, destinatarioDni,
                destinatarioDni, remitenteDni
            );
    }

    public List<ConversacionDTO> obtenerConversaciones(String usuarioDni) {
        if (pacienteRepository.existsByDni(usuarioDni)) {
            return medicoRepository.findAll().stream()
                .map(m -> new ConversacionDTO(
                    m.getDni(),
                    m.getNombre() + " " + m.getApellido()
                ))
                .collect(Collectors.toList());
        }

        List<String> socios = repositorioMensaje.findConversacionPartners(usuarioDni);
        return socios.stream()
            .filter(Objects::nonNull)
            .map(dni -> 
                pacienteRepository.findByDni(dni)
                    .map(p -> new ConversacionDTO(
                        dni, 
                        p.getNombre() + " " + p.getApellido()
                    ))
                    .orElseThrow(() -> 
                        new IllegalArgumentException("Paciente no encontrado con DNI " + dni)
                    )
            )
            .collect(Collectors.toList());
    }
}