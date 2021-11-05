package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Editorial;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public List<Editorial> findAll() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void newEditorial(String nombre, Boolean alta) throws ErrorServicio {
        // si no pasa las siguientes verificaciones no se sigue con el codigo
        validateEditorial(nombre, alta);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(alta);
        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void updateEditorial(String id, String nombre, Boolean alta) throws ErrorServicio {
        validateEditorial(nombre, alta);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorial.setAlta(alta);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No existe el autor con id: " + id);
        }
    }

    @Transactional
    public void disableEditorial(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No existe el autor con id: " + id);
        }
    }

    @Transactional
    public void enableEditorial(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No existe el autor con id: " + id);
        }
    }

    @Transactional
    public void deleteEditorial(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorialRepositorio.delete(editorial);
        } else {
            throw new ErrorServicio("No existe la editorial con id: " + id);
        }
    }

    

    private void validateEditorial(String nombre, Boolean alta) throws ErrorServicio {
        if (nombre == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (alta == null) {
            throw new ErrorServicio("El alta no puede estar vacio");
        }
    }

    

}
