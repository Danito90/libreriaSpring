package egg.ejercicio01.libreria.repositorios;

import org.springframework.stereotype.Repository;
import egg.ejercicio01.libreria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClienteRepositorio extends JpaRepository <Cliente, String> {

}
    

