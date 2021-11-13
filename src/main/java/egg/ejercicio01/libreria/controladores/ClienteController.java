package egg.ejercicio01.libreria.controladores;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egg.ejercicio01.libreria.entidades.Cliente;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.servicios.ClienteServicio;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/lista")
    public List<Cliente> listar(Model model) {
        return clienteServicio.findAll();
    }

    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) throws ErrorServicio {
        if (id != null) {
            Optional<Cliente> respuesta = clienteServicio.findById(id);
            if (respuesta.isPresent()) {
                model.addAttribute("cliente", respuesta.get());
            } else {
                return "redirect:/cliente/lista";
            }
        } else {
            model.addAttribute("cliente", new Cliente());
        }
        return "cliente/cliente-form";
    }

    @PostMapping("/save")
    public String guardar(Model model, RedirectAttributes redirectAttributes, @ModelAttribute @Valid Cliente cliente,
            BindingResult bindingResult) throws ErrorServicio {

        try {
            if (bindingResult.hasErrors()) {
                return "cliente/cliente-form";
            }
            clienteServicio.save(cliente);
            redirectAttributes.addFlashAttribute("exitoCliente",
                    "El cliente ''" + cliente.getNombre() + " " + cliente.getApellido() + "''  guardado con éxito");
            return "redirect:/cliente/lista";
        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("errorCliente",
                    "Error al guardar el cliente  ''" + cliente.getNombre() + " " + cliente.getApellido());
            return "cliente/cliente-form";
        }

    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id, Model model) throws ErrorServicio {
        clienteServicio.disableEnable(id);
        return "redirect:/cliente/lista";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam(required = false) String id) {

        try {
            Optional<Cliente> respuesta = clienteServicio.findById(id);
            if (respuesta.isPresent()) {
                clienteServicio.delete(respuesta.get());
                model.addAttribute("exitoCliente", "El cliente ''" + respuesta.get().getNombre() + " "
                        + respuesta.get().getApellido() + "''  fue eliminado con exito");
            }
        } catch (Exception e) {
            model.addAttribute("errorCliente", e.getMessage());
        }
        return "redirect:/cliente/lista";
    }
}
