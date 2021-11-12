package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Prestamo;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.PrestamoRepositorio;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Transactional
    public Prestamo save(Prestamo prestamo) throws ErrorServicio {
        validate(prestamo);
        return prestamoRepositorio.save(prestamo);
    }

    @Transactional
    public void delete(Prestamo prestamo) throws ErrorServicio {
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(prestamo.getId());
        if (respuesta.isPresent()) {
            prestamoRepositorio.delete(respuesta.get());
        } else {
            throw new ErrorServicio("No existe el cliente");
        }
    }


    public void validate(Prestamo prestamo) throws ErrorServicio {

        // if(prestamo.getFechaPrestamo()==null){
        // throw new ErrorServicio("La fecha de prestamo no puede ser nula");
        // }
        // if(prestamo.getFechaDevolucion()==null){
        // throw new ErrorServicio("La fecha de devolucion no puede ser nula");
        // }
        // if(prestamo.getAlta()==null){
        // throw new ErrorServicio("La fecha de alta no puede ser nula");
        // }

        if (prestamo.getLibro().getId() == null || prestamo.getLibro().getId().isEmpty()) {
            throw new ErrorServicio("El Libro no puede estar vacio");
        } else {
            prestamo.setLibro(libroServicio.findById(prestamo.getLibro()));
        }

        if (prestamo.getCliente().getId() == null || prestamo.getCliente().getId().isEmpty()) {
            throw new ErrorServicio("El Cliente no puede estar vacio");
        } else {
            prestamo.setCliente(clienteServicio.findById(prestamo.getCliente()));
        }
    }

    @Transactional
    public List<Prestamo> findALL(String id) {
        return prestamoRepositorio.findAll();
    }

    @Transactional
    public Prestamo findById(Prestamo prestamo) throws ErrorServicio {
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(prestamo.getId());
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("No existe el prestamo");
        }
    }

    @Transactional
    public void enable(Prestamo prestamo){
        Optional<Prestamo> respuesta= prestamoRepositorio.findById(prestamo.getId());
        if(respuesta.isPresent()){
            prestamo=respuesta.get();
            prestamo.setAlta(true);
            prestamoRepositorio.save(prestamo);
        }
    }

    @Transactional
    public void disable(Prestamo prestamo){
        Optional<Prestamo> respuesta= prestamoRepositorio.findById(prestamo.getId());
        if(respuesta.isPresent()){
            prestamo=respuesta.get();
            prestamo.setAlta(false);
            prestamoRepositorio.save(prestamo);
        }
    }
}