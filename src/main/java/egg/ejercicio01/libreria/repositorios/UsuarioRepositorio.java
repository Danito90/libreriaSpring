package egg.ejercicio01.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import egg.ejercicio01.libreria.entidades.Usuario;

@Controller
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    // Usuario findByUsuario(String usuario); si solo buscara el usuario..
    // la clausula findBy funciona como un where

    @Query("SELECT a from Usuario a WHERE a.usuario LIKE :usuario AND a.alta = true")
    public Usuario findByUser(@Param("usuario") String usuario);

    Usuario findByMail(String mail);
    Usuario findByUsuario(String usuario);
}
