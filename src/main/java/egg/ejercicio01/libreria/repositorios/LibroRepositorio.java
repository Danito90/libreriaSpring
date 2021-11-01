
package egg.ejercicio01.libreria.repositorios;

import egg.ejercicio01.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("select l from Libro l")
    public List<Libro> findAll();

}
