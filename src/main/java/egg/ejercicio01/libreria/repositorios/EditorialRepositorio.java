
package egg.ejercicio01.libreria.repositorios;

import egg.ejercicio01.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{ //String es el tipo de de id
    
}
