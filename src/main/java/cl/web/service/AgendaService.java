package cl.web.service;

import cl.web.modelo.Evento;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface AgendaService {
    boolean agregarEvento(Evento evento);

    List<Evento> listarEventos();

    List<Evento> buscarPorFecha(LocalDate fecha);

    Map<LocalDate, List<Evento>> agruparPorFecha();

    int contarEventos();
}
