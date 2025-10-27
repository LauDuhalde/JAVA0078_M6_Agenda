// AgendaServiceImpl.java - VERSIÓN MEJORADA
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

@Service
public class AgendaServiceImpl implements AgendaService {

    private static final Logger logger = LoggerFactory.getLogger(AgendaServiceImpl.class);

    private  final List<Evento> eventos = new ArrayList<>();

    @Override
    public boolean agregarEvento(Evento evento) {
        if (evento == null) {
            logger.warn("Intento de agregar evento nulo");
            return false;
        }

        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
            logger.warn("Intento de agregar evento con título vacío");
            return false;
        }

        if (evento.getFecha() == null) {
            logger.warn("Intento de agregar evento con fecha nula");
            return false;
        }

        if (evento.getDescripcion() == null || evento.getDescripcion().trim().isEmpty()) {
            logger.warn("Intento de agregar evento con descripción vacía");
            return false;
        }

        if (evento.getResponsable() == null || evento.getResponsable().trim().isEmpty()) {
            logger.warn("Intento de agregar evento con responsable vacío");
            return false;
        }

        try {
            eventos.add(evento);
            logger.info("Evento agregado exitosamente: {} - Fecha: {}",
                    evento.getTitulo(), evento.getFecha());
            return true;
        } catch (Exception e) {
            logger.error("Error al agregar evento: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Evento> listarEventos() {
        logger.debug("Listando {} eventos", eventos.size());
        try {
            return eventos.stream()
                    .filter(e -> e != null && e.getFecha() != null)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al listar eventos: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Evento> buscarPorFecha(LocalDate fecha) {
        if (fecha == null) {
            logger.warn("Búsqueda con fecha nula");
            return new ArrayList<>();
        }

        logger.debug("Buscando eventos en fecha: {}", fecha);

        try {
            return eventos.stream()
                    .filter(e -> e != null && e.getFecha() != null && e.getFecha().equals(fecha))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al buscar eventos por fecha: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public Map<LocalDate, List<Evento>> agruparPorFecha() {
        logger.debug("Agrupando eventos por fecha");

        try {
            return eventos.stream()
                    .filter(e -> e != null && e.getFecha() != null)
                    .sorted((e1, e2) -> e1.getFecha().compareTo(e2.getFecha()))
                    .collect(Collectors.groupingBy(Evento::getFecha));
        } catch (Exception e) {
            logger.error("Error al agrupar eventos por fecha: {}", e.getMessage(), e);
            return new java.util.TreeMap<>();
        }
    }

    @Override
    public int contarEventos() {
        try {
            return (int) eventos.stream()
                    .filter(e -> e != null && e.getFecha() != null)
                    .count();
        } catch (Exception e) {
            logger.error("Error al contar eventos: {}", e.getMessage(), e);
            return 0;
        }
    }
}