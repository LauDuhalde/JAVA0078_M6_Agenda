package cl.web;

import cl.web.modelo.Evento;
import cl.web.service.AgendaServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class AgendaServiceImplTest {

	@Autowired
    private AgendaServiceImpl agendaService;

    @Test
    void testAgregarEvento() {
        Evento evento = new Evento("Reunión de equipo",LocalDate.of(2025, 10, 23),"Planificación","Pedro");

        boolean resultado = agendaService.agregarEvento(evento);

        assertTrue(resultado);
        assertEquals(1, agendaService.contarEventos());
        assertEquals("Reunión de equipo", agendaService.listarEventos().get(0).getTitulo());
    }

    @Test
    void testBuscarPorFecha() {
        LocalDate fecha = LocalDate.of(2025, 10, 20);

        Evento evento1 = new Evento("Reunión", fecha, "Planificación", "Loreto");
        Evento evento2 = new Evento("Capacitación", LocalDate.of(2025, 10, 24), "Java Avanzado", "Pedro");

        agendaService.agregarEvento(evento1);
        agendaService.agregarEvento(evento2);

        List<Evento> resultados = agendaService.buscarPorFecha(fecha);

        assertEquals(1, resultados.size());
        assertEquals("Reunión", resultados.get(0).getTitulo());
    }
}
