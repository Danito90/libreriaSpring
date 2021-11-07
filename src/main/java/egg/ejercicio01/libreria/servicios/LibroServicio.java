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
    public Libro save(Libro libro) throws ErrorServicio{
        validate2(libro);
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
            throw new ErrorServicio("El isbn no puede estar vacio");
        }
        if (titulo == null) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede estar vacio");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("El ejemplar no puede estar vacio");
        }
        if (ejemplaresPrestados == null) {
            throw new ErrorServicio("El ejemplar prestado no puede estar vacio");
        }
        if (ejemplaresRestantes == null) {
            throw new ErrorServicio("El ejemplar restante no puede estar vacio");
        }
        if (alta == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
        if (autor == null) {
            throw new ErrorServicio("El autor no puede estar vacio");
        }
        if (editorial == null) {
            throw new ErrorServicio("La editorial no puede estar vacia");
        }
    }

    public void validate2(Libro libro) throws ErrorServicio {
        if (libro.getIsbn() == null) {
            throw new ErrorServicio("El isbn no puede estar vacio");
        }
        if (libro.getTitulo() == null) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }
        if (libro.getAnio() == null) {
            throw new ErrorServicio("El año no puede estar vacio");
        }
        if (libro.getEjemplares() == null) {
            throw new ErrorServicio("El ejemplar no puede estar vacio");
        }
        if (libro.getEjemplaresPrestados() == null) {
            throw new ErrorServicio("El ejemplar prestado no puede estar vacio");
        }
        if (libro.getEjemplaresRestantes() == null) {
            throw new ErrorServicio("El ejemplar restante no puede estar vacio");
        }
        if (libro.getAlta() == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
        if (libro.getAutor() == null) {
            throw new ErrorServicio("El autor no puede estar vacio");
        }
        if (libro.getEditorial() == null) {
            throw new ErrorServicio("La editorial no puede estar vacia");
        }
    }

}
