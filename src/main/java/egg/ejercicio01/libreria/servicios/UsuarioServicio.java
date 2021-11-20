package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.ejercicio01.libreria.entidades.Imagen;
import egg.ejercicio01.libreria.entidades.Usuario;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.UsuarioRepositorio;
import egg.ejercicio01.libreria.servicios.ImagenServicio;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public Usuario save(Usuario usuario,MultipartFile archivo) throws ErrorServicio {
        // validate2(usuario);
        Imagen imagen=imagenServicio.save(archivo);
        usuario.setImagenPerfil(imagen);
        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void delete(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            imagenServicio.delete(respuesta.get().getImagenPerfil().getId());
            usuarioRepositorio.deleteById(id);
        } else {
            throw new ErrorServicio("No existe el usuario ");
        }
    }

    @Transactional
    public Usuario disableEnable(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (respuesta.get().getAlta() == true) {
                respuesta.get().setAlta(false);
            } else {
                respuesta.get().setAlta(true);
            }
            usuarioRepositorio.save(respuesta.get());
        }
        return respuesta.get();
    }


    // private void validate2(Usuario usuario) throws ErrorServicio {
    //     if (usuario.getImagenPerfil().getId() == null || usuario.getImagenPerfil().getId().isEmpty()) {
    //         throw new ErrorServicio("La imagen debe ser cargada");
    //     } else {
    //         usuario.setImagenPerfil(imagenServicio.findById(usuario.getImagenPerfil()));
    //     }
    // }

    @Transactional
    public List<Usuario> findAll() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public Optional<Usuario> findById(String id) {
        return usuarioRepositorio.findById(id);
    }

    @Transactional
    public Usuario findById(Usuario usuario) {
        Optional<Usuario> optional = usuarioRepositorio.findById(usuario.getId());
        if (optional.isPresent()) {
            usuario = optional.get();
        }
        return usuario;
    }

}
