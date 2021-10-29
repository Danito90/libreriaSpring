
package egg.ejercicio01.libreria.repositorios;

import egg.ejercicio01.libreria.entidades.Editorial;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{ //String es el tipo de de id
    
    @Query("select e from Editorial e")
    public List<Editorial> findAll();
}
