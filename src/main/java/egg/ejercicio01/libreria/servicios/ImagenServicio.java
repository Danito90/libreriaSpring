package egg.ejercicio01.libreria.servicios;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egg.ejercicio01.libreria.entidades.Imagen;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen save(MultipartFile archivo,String usuario) throws ErrorServicio {
        if (archivo != null) {
            try {
                Imagen foto = new Imagen();
                foto.setMime(archivo.getContentType());
                foto.setNombre(usuario);
                foto.setContenido(archivo.getBytes()); // la lectura del archivo puede lanzar la excepcion

                return imagenRepositorio.save(foto);
            } catch (Exception e) {
                throw new ErrorServicio("Error al leer el archivo");
            }
        }
        return null;
    }

    @Transactional
    public Imagen update(String id, MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Imagen foto = new Imagen();

                if (id != null) {
                    Optional<Imagen> fotoActual = imagenRepositorio.findById(id);
                    if (fotoActual.isPresent()) {
                        foto = fotoActual.get();
                    }
                }

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes()); // la lectura del archivo puede lanzar la excepcion

                return imagenRepositorio.save(foto);
            } catch (Exception e) {
                throw new ErrorServicio("Error al leer el archivo");
            }
        }
        return null;
    }

    @Transactional
    public void delete(String id) throws ErrorServicio {
        Optional<Imagen> respuesta = imagenRepositorio.findById(id);
        if (respuesta.isPresent()) {
            imagenRepositorio.deleteById(id);
        } else {
            throw new ErrorServicio("No existe el la imagen");
        }
    }

    @Transactional(readOnly = true)
    public Imagen findById(Imagen imagen) {
        Optional<Imagen> optional = imagenRepositorio.findById(imagen.getId());
        if (optional.isPresent()) {
            imagen = optional.get();
        }
        return imagen;
    }
}
