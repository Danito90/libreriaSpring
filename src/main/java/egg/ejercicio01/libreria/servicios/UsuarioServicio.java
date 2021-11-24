package egg.ejercicio01.libreria.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import egg.ejercicio01.libreria.entidades.Imagen;
import egg.ejercicio01.libreria.entidades.Usuario;
import egg.ejercicio01.libreria.enums.Rol;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public Usuario save(Usuario usuario, MultipartFile archivo) throws ErrorServicio {
        validate2(usuario);
        String encriptada = new BCryptPasswordEncoder().encode(usuario.getPassword());
        usuario.setPassword(encriptada);

        if (usuario.getId()==null || usuario.getId().isEmpty()) {
            usuario.setRol(Rol.USUARIO);
        }else{
            usuario.setRol(usuario.getRol());
        }

        Imagen foto = imagenServicio.save(archivo);
        usuario.setImagenPerfil(foto);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void delete(String id) throws ErrorServicio {
        usuarioRepositorio.deleteById(id);
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

    public void validate2(Usuario usuario) throws ErrorServicio {
        if (usuarioRepositorio.findByUsuario(usuario.getUsuario()) != null && !usuarioRepositorio.findByUsuario(usuario.getUsuario()).getId().equals(usuario.getId()) ) {
            throw new ErrorServicio("Ya existe el usuario " + usuario.getUsuario());
        }
        if (usuarioRepositorio.findByMail(usuario.getMail()) != null && !usuarioRepositorio.findByMail(usuario.getMail()).getId().equals(usuario.getId())) {
            throw new ErrorServicio("El mail " + usuario.getMail() + " ya está en uso");
        }
    
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findById(String id) {
        return usuarioRepositorio.findById(id);
    }

    @Transactional(readOnly = true)
    public Usuario findById(Usuario usuario) {
        Optional<Usuario> optional = usuarioRepositorio.findById(usuario.getId());
        if (optional.isPresent()) {
            usuario = optional.get();
        }
        return usuario;
    }

    @Transactional
    public Usuario cambiarRol(String id) throws ErrorServicio{
    
    	Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
    	Usuario usuario=null;
    	if(respuesta.isPresent()) {
    		
    		usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.USUARIO)) {
    			
    		usuario.setRol(Rol.ADMIN);
    		
    		}else if(usuario.getRol().equals(Rol.ADMIN)) {
    			usuario.setRol(Rol.USUARIO);
    		}
    	}
        return usuario;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario user = usuarioRepositorio.findByUser(usuario);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>(); // crea la lista de permisos vacia

            // Creo una lista de permisos!
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + user.getRol()); // agrego permisos
            permisos.add(p1);

            // Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", user); // llave + valor

            User uuser = new User(user.getUsuario(), user.getPassword(), permisos);
            return uuser;

        } else {
            return null;
        }
    }

}
