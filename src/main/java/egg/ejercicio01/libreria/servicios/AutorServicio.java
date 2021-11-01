package egg.ejercicio01.libreria.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Autor;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
    @Autowired // Spring inicializa automaticamente
    private AutorRepositorio autorRepositorio;

    public void newAutor(String nombre, Boolean alta) throws ErrorServicio {
        // si no pasa las siguientes verificaciones no se sigue con el codigo
        validateAutor(nombre, alta);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(alta);
        autorRepositorio.save(autor); // este save crea un nuevo id
    }

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

    public void deleteAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            autorRepositorio.deleteById(id);
        }
    }

    public void disableAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor); // este save no crea id, actualiza
        }
    }

    public void enableAutor(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor); // este save no crea id, actualiza
        }
    }

    public void validateAutor(String nombre, Boolean alta) throws ErrorServicio {
        if (nombre == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (alta == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
    }
// 
}
