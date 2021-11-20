package egg.ejercicio01.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import egg.ejercicio01.libreria.entidades.Usuario;

@Controller
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

}
    

