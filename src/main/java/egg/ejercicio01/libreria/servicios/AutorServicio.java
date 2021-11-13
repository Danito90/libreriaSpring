package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Autor;
import egg.ejercicio01.libreria.entidades.Libro;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.AutorRepositorio;
import egg.ejercicio01.libreria.repositorios.LibroRepositorio;

@Service
public class AutorServicio {
    @Autowired // Spring inicializa automaticamente
    private AutorRepositorio autorRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    // // esta anotacion hace commit automatico si esta todo en orden, caso contrario
    // @Transactional // hace roollback
    // public void newAutor(String nombre, Boolean alta) throws ErrorServicio {
    //     // si no pasa las siguientes verificaciones no se sigue con el codigo
    //     validateAutor(nombre, alta);

    //     Autor autor = new Autor();
    //     autor.setNombre(nombre);
    //     autor.setAlta(alta);
    //     autorRepositorio.save(autor); // este save crea un nuevo id
    // }

    @Transactional
    public Autor save(Autor autor) throws ErrorServicio {
        validate2(autor);
        return autorRepositorio.save(autor);
    }

    // @Transactional
    // public void updateAutor(String id, String nombre, Boolean alta) throws ErrorServicio {
    //     // si no pasa las siguientes verificaciones no se sigue con el codigo
    //     validateAutor(nombre, alta);

    //     Optional<Autor> respuesta = autorRepositorio.findById(id);
    //     if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
    //         Autor autor = respuesta.get();
    //         autor.setNombre(nombre);
    //         autor.setAlta(alta);
    //         autorRepositorio.save(autor); // este save no crea id, actualiza
    //     } else {
    //         throw new ErrorServicio("No existe el autor con id: " + id);
    //     }
    // }

    @Transactional
    public void deleteAutor(String id) throws ErrorServicio {
        String librosAsociados = "";
        int count = 0;
        for (Libro l : libroRepositorio.findAll()) {
            if (l.getAutor().getId().equals(id)) {
                count += 1;
                librosAsociados += l.getTitulo() + ", ";
            }
        }
        
        if (count > 0) {
            librosAsociados = librosAsociados.substring(0, librosAsociados.length() - 2) + ".";
            throw new ErrorServicio("No se puede eliminar el autor porque tiene libros asociados: " + librosAsociados);
        } else {

            Optional<Autor> respuesta = autorRepositorio.findById(id);
            if (respuesta.isPresent()) {
                autorRepositorio.deleteById(id);
            } else {
                throw new ErrorServicio("No se se encontro un autor con el id: " + id);
            }
        }

    }

    @Transactional
    public Autor disableEnable(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (respuesta.get().getAlta() == true) {
                respuesta.get().setAlta(false);
            } else {
                respuesta.get().setAlta(true);
            }
            autorRepositorio.save(respuesta.get());
        }
        return respuesta.get();
    }

    // private void validateAutor(String nombre, Boolean alta) throws ErrorServicio {
    //     if (nombre == null || nombre.isEmpty()) {
    //         throw new ErrorServicio("El nombre no puede estar vacio");
    //     }
    //     if (alta == null) {
    //         throw new ErrorServicio("El alta no puede estar vacio");
    //     }
    // }

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
