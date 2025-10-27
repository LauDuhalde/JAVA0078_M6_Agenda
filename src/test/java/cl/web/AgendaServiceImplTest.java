package cl.web;

import cl.web.modelo.Evento;
import cl.web.service.AgendaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// reinicia el contexto de Spring despues de cada test porque AgendaServiceImpl
// es un Singleton que mantiene una lista en memoria compartida entre tests
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    void testNoAgregarEventoConDescripcionVacia() {
        Evento evento = new Evento("Evento", LocalDate.of(2025, 12, 10), "", "Carlos");

        boolean resultado = agendaService.agregarEvento(evento);

        assertFalse(resultado);
        assertEquals(0, agendaService.contarEventos());
    }

    @Test
    void testNoAgregarEventoConResponsableVacio() {
        Evento evento = new Evento("Cumpleaños", LocalDate.of(2025, 12, 25), "Celebración", "");

        boolean resultado = agendaService.agregarEvento(evento);

        assertFalse(resultado);
        assertEquals(0, agendaService.contarEventos());
    }

    @Test
    void testBuscarPorFechaNula() {
        List<Evento> resultados = agendaService.buscarPorFecha(null);

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }
    @Test
    void testNoAgregarEventoCompletoVacio() {
        Evento evento = new Evento("", null, "", "");

        boolean resultado = agendaService.agregarEvento(evento);

        assertFalse(resultado);
        assertEquals(0, agendaService.contarEventos());
    }

    @Test
    void testNoAgregarEventoTodosCamposNulos() {
        Evento evento = new Evento(null, null, null, null);

        boolean resultado = agendaService.agregarEvento(evento);

        assertFalse(resultado);
        assertEquals(0, agendaService.contarEventos());
    }

    @Test
    void testNoAgregarEventoSoloConEspacios() {
        Evento evento = new Evento("   ", null, "   ", "   ");

        boolean resultado = agendaService.agregarEvento(evento);

        assertFalse(resultado);
        assertEquals(0, agendaService.contarEventos());
    }
}
