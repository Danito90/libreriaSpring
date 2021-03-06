package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egg.ejercicio01.libreria.entidades.Libro;
import egg.ejercicio01.libreria.entidades.Prestamo;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private PrestamoServicio prestamoServicio;

    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return libroRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Libro> findById(String id) {
        return libroRepositorio.findById(id);
    }

    @Transactional(readOnly = true)
    public Libro findById(Libro libro) {
        Optional<Libro> respuesta = libroRepositorio.findById(libro.getId());
        if (respuesta.isPresent()) {
            libro = respuesta.get();
        }
        return libro;
    }

    @Transactional
    public Libro save(Libro libro) throws ErrorServicio {
        validate2(libro);
        // libro.setEjemplaresPrestados(0);
        // libro.setEjemplaresRestantes(libro.getEjemplares());
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        return libroRepositorio.save(libro);
    }

    // @Transactional
    // public void newLibro(Long isbn, String titulo, Integer anio, Integer
    // ejemplares, Integer ejemplaresPrestados,
    // Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial)
    // throws ErrorServicio{
    // validateLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
    // ejemplaresRestantes, alta, autor, editorial);
    // Libro libro = new Libro();
    // libro.setIsbn(isbn);
    // libro.setTitulo(titulo);
    // libro.setAnio(anio);
    // libro.setEjemplares(ejemplares);
    // libro.setEjemplaresPrestados(ejemplaresPrestados);
    // libro.setEjemplaresRestantes(ejemplaresRestantes);
    // libro.setAlta(alta);
    // libro.setAutor(autor);
    // libro.setEditorial(editorial);
    // libroRepositorio.save(libro);
    // }

    // @Transactional
    // public void update(Libro libro) throws ErrorServicio{
    // validate2(libro);
    // libro.setEjemplaresRestantes(libro.getEjemplares()-libro.getEjemplaresPrestados());
    // libroRepositorio.save(libro);
    // }

    // @Transactional
    // public void updateLibro(String id, Long isbn, String titulo, Integer anio,
    // Integer ejemplares,
    // Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor
    // autor, Editorial editorial)
    // throws ErrorServicio {
    // validateLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
    // ejemplaresRestantes, alta, autor, editorial);
    // Optional<Libro> respuesta = libroRepositorio.findById(id);
    // if (respuesta.isPresent()) {
    // Libro libro = respuesta.get();
    // libro.setIsbn(isbn);
    // libro.setTitulo(titulo);
    // libro.setAnio(anio);
    // libro.setEjemplares(ejemplares);
    // libro.setIsbn(isbn);
    // libro.setTitulo(titulo);
    // libro.setAnio(anio);
    // libro.setEjemplares(ejemplares);
    // libro.setEjemplaresPrestados(ejemplaresPrestados);
    // libro.setEjemplaresRestantes(ejemplaresRestantes);
    // libro.setAlta(alta);
    // libro.setAutor(autor);
    // libro.setEditorial(editorial);
    // libroRepositorio.save(libro);
    // } else {
    // throw new ErrorServicio("No existe el libro con id: " + id);
    // }
    // }

    @Transactional
    public Libro disableEnable(String id) {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (respuesta.get().getAlta() == true) {
                respuesta.get().setAlta(false);
            } else {
                respuesta.get().setAlta(true);
            }
            libroRepositorio.save(respuesta.get());
        }
        return respuesta.get();
    }

    @Transactional
    public void prestar(Libro libro) throws ErrorServicio {
        if (libro.getEjemplaresRestantes() == 0) {
            throw new ErrorServicio("No hay ejemplares del libro disponibles");
        } else {
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
            libroRepositorio.save(libro);
        }
    }

    @Transactional
    public void devolver(Libro libro) throws ErrorServicio {
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        libroRepositorio.save(libro);
    }

    @Transactional
    public void deleteLibro(String id) throws ErrorServicio {
        String prestamosAsociados = "";
        int count = 0;
        for (Prestamo p : prestamoServicio.findAll()) {
            if (p.getLibro().getId().equals(id)) {
                count += 1;
                prestamosAsociados += p.getCliente().getNombre() + " " + p.getCliente().getApellido() + ", ";
            }
        }

        if (count > 0) {
            prestamosAsociados = prestamosAsociados.substring(0, prestamosAsociados.length() - 2) + ".";
            throw new ErrorServicio("No se puede eliminar el libro, porque posee prestamos a los siguientes clientes: "
                    + prestamosAsociados);
        } else {

            Optional<Libro> respuesta = libroRepositorio.findById(id);
            if (respuesta.isPresent()) {
                libroRepositorio.deleteById(id);
            } else {
                throw new ErrorServicio("No existe el libro con id: " + id);
            }
        }
    }

    // public void validateLibro(Long isbn, String titulo, Integer anio, Integer
    // ejemplares, Integer ejemplaresPrestados,
    // Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial)
    // throws ErrorServicio {
    // if (isbn == null) {
    // throw new ErrorServicio("El isbn no puede estar vacio");
    // }
    // if (titulo == null) {
    // throw new ErrorServicio("El titulo no puede estar vacio");
    // }
    // if (anio == null) {
    // throw new ErrorServicio("El a??o no puede estar vacio");
    // }
    // if (ejemplares == null) {
    // throw new ErrorServicio("El ejemplar no puede estar vacio");
    // }
    // if (ejemplaresPrestados == null) {
    // throw new ErrorServicio("El ejemplar prestado no puede estar vacio");
    // }
    // if (ejemplaresRestantes == null) {
    // throw new ErrorServicio("El ejemplar restante no puede estar vacio");
    // }
    // if (alta == null) {
    // throw new ErrorServicio("El alta no puede estar vacio");
    // }
    // if (autor == null) {
    // throw new ErrorServicio("El autor no puede estar vacio");
    // }
    // if (editorial == null) {
    // throw new ErrorServicio("La editorial no puede estar vacia");
    // }
    // }

    public void validate2(Libro libro) throws ErrorServicio {
        // if (libro.getIsbn() == null) {
        // throw new ErrorServicio("El isbn no puede estar vacio");
        // }

        // if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
        // throw new ErrorServicio("El titulo no puede estar vacio");
        // }
        // if (libro.getAnio() == null) {
        // throw new ErrorServicio("El a??o no puede estar vacio");
        // }
        // if (libro.getEjemplares() == null) {
        // throw new ErrorServicio("El ejemplar no puede estar vacio");
        // }
        // if (libro.getEjemplaresPrestados() == null) {
        // throw new ErrorServicio("El ejemplar prestado no puede estar vacio");
        // }
        // if (libro.getEjemplaresRestantes() == null) {
        // throw new ErrorServicio("El ejemplar restante no puede estar vacio");
        // }
        // if (libro.getAlta() == null) {
        // throw new ErrorServicio("El alta no puede estar vacio");
        // }
        if (libro.getEditorial().getId() == null || libro.getEditorial().getId().isEmpty()) {
            throw new ErrorServicio("La Editorial no puede estar vacia");
        } else {
            libro.setEditorial(editorialServicio.findById(libro.getEditorial()));
        }
        if (libro.getAutor().getId() == null || libro.getAutor().getId().isEmpty()) {
            throw new ErrorServicio("El Autor no puede estar vacio");
        } else {
            libro.setAutor(autorServicio.findById(libro.getAutor()));
        }

    }

}
