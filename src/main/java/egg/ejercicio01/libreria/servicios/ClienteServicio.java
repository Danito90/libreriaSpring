package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egg.ejercicio01.libreria.entidades.Cliente;
import egg.ejercicio01.libreria.entidades.Prestamo;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.ClienteRepositorio;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PrestamoServicio prestamoServicio;

    @Transactional
    public Cliente save(Cliente cliente) throws ErrorServicio {
        validate(cliente);
        return clienteRepositorio.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente findById(Cliente cliente) {
        Optional<Cliente> respuesta = clienteRepositorio.findById(cliente.getId());
        if (respuesta.isPresent()) {
            cliente = respuesta.get();
        }
        return cliente;
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> findById(String id) throws ErrorServicio {
        return clienteRepositorio.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepositorio.findAll();
    }

    @Transactional
    public void delete(Cliente cliente) throws ErrorServicio {

        String prestamosAsociados = "";
        int count = 0;
        for (Prestamo p : prestamoServicio.findAll()) {
            if (p.getCliente().getId().equals(cliente.getId())) {
                count += 1;
                prestamosAsociados += p.getLibro().getTitulo() + ", ";
            }
        }

        if (count > 0) {
            prestamosAsociados = prestamosAsociados.substring(0, prestamosAsociados.length() - 2) + ".";
            throw new ErrorServicio(
                    "No se puede eliminar el cliente, porque posee prestamos de los siguientes libros: " + prestamosAsociados);
        } else {
            Optional<Cliente> respuesta = clienteRepositorio.findById(cliente.getId());
            if (respuesta.isPresent()) {
                cliente = respuesta.get();
                clienteRepositorio.delete(cliente);
            } else {
                throw new ErrorServicio("No existe el cliente con id= " + cliente.getId());
            }
        }

    }

    public void validate(Cliente cliente) throws ErrorServicio {
        // if(cliente.getNombre().isEmpty() || cliente.getNombre()==null){
        // throw new ErrorServicio("El nombre no puede estar vacio");
        // }
        // if(cliente.getApellido().isEmpty()|| cliente.getApellido()==null){
        // throw new ErrorServicio("El apellido no puede estar vacio");
        // }
        // if(cliente.getDocumento().isEmpty()|| cliente.getDocumento()==null){
        // throw new ErrorServicio("El documento no puede estar vacio");
        // }
        // if(cliente.getAlta()==null){
        // throw new ErrorServicio("El alta no puede estar vacio");
        // }
        // if(cliente.getTelefono().isEmpty()|| cliente.getNombre()==null){
        // throw new ErrorServicio("El telefono no puede estar vacio");
        // }
    }

    @Transactional
    public Cliente disableEnable(String id) {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (respuesta.get().getAlta() == true) {
                respuesta.get().setAlta(false);
            } else {
                respuesta.get().setAlta(true);
            }
            clienteRepositorio.save(respuesta.get());
        }
        return respuesta.get();
    }

}
