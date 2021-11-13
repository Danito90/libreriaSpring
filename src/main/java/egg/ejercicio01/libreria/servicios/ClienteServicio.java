package egg.ejercicio01.libreria.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.ejercicio01.libreria.entidades.Cliente;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.repositorios.ClienteRepositorio;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public Cliente save(Cliente cliente) throws ErrorServicio {
        validate(cliente);
        return clienteRepositorio.save(cliente);
    }

    @Transactional
    public Cliente findById(Cliente cliente) {
        Optional<Cliente> respuesta = clienteRepositorio.findById(cliente.getId());
        if (respuesta.isPresent()) {
            cliente = respuesta.get();
        }
        return cliente;
    }

    @Transactional
    public Optional<Cliente> findById(String id) throws ErrorServicio {
        return clienteRepositorio.findById(id);
    }

    @Transactional
    public List<Cliente> findAll() {
        return clienteRepositorio.findAll();
    }

    @Transactional
    public void delete(Cliente cliente) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(cliente.getId());
        if (respuesta.isPresent()) {
            cliente = respuesta.get();
            clienteRepositorio.delete(cliente);
        } else {
            throw new ErrorServicio("No existe el cliente con id= " + cliente.getId());
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
    public void disableEnable(String id) {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            if (respuesta.get().getAlta() == true) {
                respuesta.get().setAlta(false);
            } else {
                respuesta.get().setAlta(true);
            }
            clienteRepositorio.save(respuesta.get());
        }
    }

}
