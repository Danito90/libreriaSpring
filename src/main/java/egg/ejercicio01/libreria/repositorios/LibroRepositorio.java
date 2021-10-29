
package egg.ejercicio01.libreria.repositorios;

import egg.ejercicio01.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
}
