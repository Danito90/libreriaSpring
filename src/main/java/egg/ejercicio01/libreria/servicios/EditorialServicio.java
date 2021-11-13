package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Editorial;
import egg.ejercicio01.libreria.entidades.Libro;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.EditorialRepositorio;
import egg.ejercicio01.libreria.repositorios.LibroRepositorio;

@Service
public class EditorialServicio {
    @Autowired // Spring inicializa automaticamente
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    // // esta anotacion hace commit automatico si esta todo en orden, caso contrario
    // @Transactional // hace roollback
    // public void newEditorial(String nombre, Boolean alta) throws ErrorServicio {
    //     // si no pasa las siguientes verificaciones no se sigue con el codigo
    //     validateEditorial(nombre, alta);

    //     Editorial editorial = new Editorial();
    //     editorial.setNombre(nombre);
    //     editorial.setAlta(alta);
    //     editorialRepositorio.save(editorial); // este save crea un nuevo id
    // }

    @Transactional
    public Editorial save(Editorial editorial) throws ErrorServicio {
        validate2(editorial);
        return editorialRepositorio.save(editorial);
    }

    // @Transactional
    // public void updateEditorial(String id, String nombre, Boolean alta) throws ErrorServicio {
    //     // si no pasa las siguientes verificaciones no se sigue con el codigo
    //     validateEditorial(nombre, alta);

    //     Optional<Editorial> respuesta = editorialRepositorio.findById(id);
    //     if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
    //         Editorial editorial = respuesta.get();
    //         editorial.setNombre(nombre);
    //         editorial.setAlta(alta);
    //         editorialRepositorio.save(editorial); // este save no crea id, actualiza
    //     } else {
    //         throw new ErrorServicio("No existe el editorial con id: " + id);
    //     }
    // }

    @Transactional
    public void deleteEditorial(String id) throws ErrorServicio {
        String librosAsociados = "";
        int count = 0;
        for (Libro l : libroRepositorio.findAll()) {
            if (l.getEditorial().getId().equals(id)) {
                count += 1;
                librosAsociados += l.getTitulo() + ", ";
            }
        }

        if (count > 0) {
            librosAsociados = librosAsociados.substring(0, librosAsociados.length() - 2) + ".";
            throw new ErrorServicio(
                    "No se puede eliminar la editorial porque tiene libros asociados: " + librosAsociados);
        } else {

            Optional<Editorial> respuesta = editorialRepositorio.findById(id);
            if (respuesta.isPresent()) { // si encuentra un usuario con ese id entonces modifica su info
                editorialRepositorio.deleteById(id);
            } else {
                throw new ErrorServicio("No existe el editorial con id: " + id);
            }
        }
    }

    @Transactional
    public void disableEnable(String id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (respuesta.get().getAlta() == true) {
                respuesta.get().setAlta(false);
            } else {
                respuesta.get().setAlta(true);
            }
            editorialRepositorio.save(respuesta.get());
        }

    }

    // private void validateEditorial(String nombre, Boolean alta) throws ErrorServicio {
    //     if (nombre == null || nombre.isEmpty()) {
    //         throw new ErrorServicio("El nombre no puede estar vacio");
    //     }
    //     if (alta == null) {
    //         throw new ErrorServicio("El alta no puede estar vacio");
    //     }
    // }

    private void validate2(Editorial editorial) throws ErrorServicio {
        if (editorial.getNombre() == null || editorial.getNombre().isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (editorial.getAlta() == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
    }

    @Transactional
    public List<Editorial> findAll() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public Optional<Editorial> findById(String id) {
        return editorialRepositorio.findById(id);
    }

    @Transactional
    public Editorial findById(Editorial editorial) {
        Optional<Editorial> optional = editorialRepositorio.findById(editorial.getId());
        if (optional.isPresent()) {
            editorial = optional.get();
        }
        return editorial;
    }
}
