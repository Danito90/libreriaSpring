package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Autor;
import egg.ejercicio01.libreria.entidades.Editorial;
import egg.ejercicio01.libreria.entidades.Libro;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public List<Libro> findAll(){
        return libroRepositorio.findAll();
    }

    public Optional<Libro> findById(String id){
        return libroRepositorio.findById(id);
    }

    @Transactional
    public Libro save(Libro libro){
        return libroRepositorio.save(libro);
    }

    @Transactional
    public void newLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) throws ErrorServicio{
        validateLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, alta, autor, editorial);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(alta);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }

    @Transactional
    public void updateLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares,
            Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial)
            throws ErrorServicio {
        validateLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, alta, autor, editorial);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setAlta(alta);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No existe el libro con id: " + id);
        }
    }

    @Transactional
    public void disableLibro(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No existe el libro con id: " + id);
        }
    }

    @Transactional
    public void enableLibro(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No existe el libro con id: " + id);
        }
    }

    @Transactional
    public void deleteLibro(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            libroRepositorio.deleteById(id);
        } else {
            throw new ErrorServicio("No existe el libro con id: " + id);
        }
    }

    public void validateLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("El isbn no puede ser nulo");
        }
        if (titulo == null) {
            throw new ErrorServicio("El titulo no puede ser nulo");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("El ejemplar no puede ser nulo");
        }
        if (ejemplaresPrestados == null) {
            throw new ErrorServicio("El ejemplar prestado no puede ser nulo");
        }
        if (ejemplaresRestantes == null) {
            throw new ErrorServicio("El ejemplar restante no puede ser nulo");
        }
        if (alta == null) {
            throw new ErrorServicio("El alta no puede ser nulo");
        }
        if (autor == null) {
            throw new ErrorServicio("El autor no puede ser nulo");
        }
        if (editorial == null) {
            throw new ErrorServicio("La editorial no puede ser nulo");
        }
    }

}
