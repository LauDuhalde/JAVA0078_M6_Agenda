package cl.web.service;

import cl.web.modelo.Contacto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AgendaService {
    boolean registrar(Contacto contacto);

    List<Contacto> listar();

    Optional<Contacto> buscarPorNombre(String nombre);

    int contarContactos();
}
