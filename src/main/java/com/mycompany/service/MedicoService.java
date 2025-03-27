package com.mycompany.service;

@Service
public class MedicoService {
    
    private final RepositorioMedico repositorioMedico;

    @Autowired
    public MedicoService(RepositorioMedico repositorioMedico) {
        this.repositorioMedico = repositorioMedico;
    }

    public List<Medico> obtenerMedicos() {
        return repositorioMedico.findAll();
    }
