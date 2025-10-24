package cl.web.service;

import cl.web.modelo.Contacto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* Clase que implementa la interfaz AgendaService.
* la anotacion @Service indica que esta clase es un bean de spring.
*
* los Override son para sobreescribir los metodos de la interfaz.
 */
@Service
    public class AgendaServiceImpl implements AgendaService {

        private static final Logger logger = LoggerFactory.getLogger(AgendaServiceImpl.class);

        private static final List<Contacto> contactos = new ArrayList<>();

        @Override
        public boolean registrar(Contacto contacto) {
            if (contacto == null) {
                logger.warn("Intento de registrar un contacto nulo");
                return false;
            }

            boolean existeCorreo = contactos.stream()
                    .anyMatch(c -> c.getCorreo().equalsIgnoreCase(contacto.getCorreo()));

            if (existeCorreo) {
                logger.warn("Intento de registrar contacto con correo duplicado: {}", contacto.getCorreo());
                return false;
            }

            contactos.add(contacto);
            logger.info("Contacto registrado exitosamente: {}", contacto.getNombre());
            return true;
        }

        @Override
        public List<Contacto> listar() {
            logger.debug("Listando {} contactos", contactos.size());
            return new ArrayList<>(contactos);
        }

        @Override
        public Optional<Contacto> buscarPorNombre(String nombre) {
            if (nombre == null || nombre.trim().isEmpty()) {
                logger.warn("Búsqueda con nombre vacío o nulo");
                return Optional.empty();
            }

            logger.debug("Buscando contacto con nombre: {}", nombre);

            Optional<Contacto> resultado = contactos.stream()
                    .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .findFirst();

            if (resultado.isPresent()) {
                logger.info("Contacto encontrado: {}", resultado.get().getNombre());
            } else {
                logger.info("No se encontró contacto con nombre: {}", nombre);
            }

            return resultado;
        }

        @Override
        public int contarContactos() {
            return contactos.size();
        }
}
