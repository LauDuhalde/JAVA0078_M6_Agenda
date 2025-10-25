package cl.web;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cl.web.modelo.Evento;
import cl.web.service.AgendaServiceImpl;

class AgendaServiceTest {

	   @Autowired
	    private AgendaServiceImpl agendaService;

	    @Test
	    void testAgregarEvento() {
	        Evento e = new Evento("Reunión", LocalDate.now(), "Plan anual", "Andrea");
	        agendaService.agregarEvento(e);
	        List<Evento> eventos = agendaService.listarEventos();
	        assertFalse(eventos.isEmpty());
	    }

	    private void assertFalse(boolean empty) {
			// TODO Auto-generated method stub
			
		}

		@Test
	    void testRecuperarEventosPorFecha() {
	        LocalDate fecha = LocalDate.of(2025, 10, 25);
	        agendaService.agregarEvento(new Evento("Evento 1", fecha, "Desc1", "Juan"));
	        agendaService.agregarEvento(new Evento("Evento 2", fecha, "Desc2", "María"));

	        List<Evento> eventosPorFecha = agendaService.buscarPorFecha(fecha);
	        assertEquals(2, eventosPorFecha.size());
	    }

		private void assertEquals(int i, int size) {
			// TODO Auto-generated method stub
			
		}
	}

