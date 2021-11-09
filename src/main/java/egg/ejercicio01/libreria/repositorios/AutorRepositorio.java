package egg.ejercicio01.libreria.repositorios;

import egg.ejercicio01.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE %?1%")
    List<Autor> buscarPorNombre(String nombre);

    
}
