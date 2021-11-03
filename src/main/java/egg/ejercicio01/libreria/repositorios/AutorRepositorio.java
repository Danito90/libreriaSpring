package egg.ejercicio01.libreria.repositorios;

import egg.ejercicio01.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("select a from Autor a")
    public List<Autor> findAll();

    
}
