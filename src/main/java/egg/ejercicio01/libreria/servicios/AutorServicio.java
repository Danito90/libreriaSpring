package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Autor;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
    @Autowired // Spring inicializa automaticamente
    private AutorRepositorio autorRepositorio;

    // esta anotacion hace commit automatico si esta todo en orden, caso contrario
    @Transactional // hace roollback
    public void newAutor(String nombre, Boolean alta) throws ErrorServicio {
        // si no pasa las siguientes verificaciones no se sigue con el codigo
        validateAutor(nombre, alta);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(alta);
        autorRepositorio.save(autor); // este save crea un nuevo id
    }

    @Transactional
    public Autor save(Autor autor) throws ErrorServicio {
        validate2(autor);
        return autorRepositorio.save(autor);
    }

    @Transactional
    public void updateAutor(String id, String nombre, Boolean alta) throws ErrorServicio {
        // si no pasa las siguientes verificaciones no se sigue con el codigo
        validateAutor(nombre, alta);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autor.setAlta(alta);
            autorRepositorio.save(autor); // este save no crea id, actualiza
        } else {
            throw new ErrorServicio("No existe el autor con id: " + id);
        }
    }

    @Transactional
    public void deleteAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            autorRepositorio.deleteById(id);
        }
    }

    @Transactional
    public void disableAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor); // este save no crea id, actualiza
        }
    }

    @Transactional
    public void enableAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor); // este save no crea id, actualiza
        }
    }

    private void validateAutor(String nombre, Boolean alta) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (alta == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
    }

    private void validate2(Autor autor) throws ErrorServicio {
        if (autor.getNombre() == null || autor.getNombre().isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (autor.getAlta() == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
    }

    @Transactional
    public List<Autor> findAll() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public Optional<Autor> findById(String id) {
        return autorRepositorio.findById(id);
    }

    @Transactional
    public Autor findById(Autor autor) {
        Optional<Autor> optional = autorRepositorio.findById(autor.getId());
        if (optional.isPresent()) {
            autor = optional.get();
        }
        return autor;
    }
}
