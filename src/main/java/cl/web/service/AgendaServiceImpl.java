package cl.web.service;

import cl.web.modelo.Evento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service // Marca esta clase como servicio de Spring (capa de lógica de negocio)
public class AgendaServiceImpl implements AgendaService {

    private static final Logger logger = LoggerFactory.getLogger(AgendaServiceImpl.class);

    private static final List<Evento> eventos = new ArrayList<>();

    @Override
    public boolean agregarEvento(Evento evento) {
        if (evento == null) {
            logger.warn("Intento de agregar evento nulo");
            return false;
        }

        eventos.add(evento);
        logger.info("Evento agregado: {}", evento.getTitulo());
        return true;
    }

    @Override
    public List<Evento> listarEventos() {
        logger.debug("Listando {} eventos", eventos.size());
        return new ArrayList<>(eventos);
    }

    @Override
    public List<Evento> buscarPorFecha(LocalDate fecha) {
        if (fecha == null) {
            logger.warn("Búsqueda con fecha nula");
            return new ArrayList<>();
        }

        logger.debug("Buscando eventos en fecha: {}", fecha);

        return eventos.stream()
                .filter(e -> e.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    @Override
    public Map<LocalDate, List<Evento>> agruparPorFecha() {
        logger.debug("Agrupando eventos por fecha");

        return eventos.stream()
                .sorted((e1, e2) -> e1.getFecha().compareTo(e2.getFecha()))
                .collect(Collectors.groupingBy(Evento::getFecha));
    }

    @Override
    public int contarEventos() {
        return eventos.size();
    }
}