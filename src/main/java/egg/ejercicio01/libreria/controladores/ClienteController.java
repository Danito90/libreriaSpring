package egg.ejercicio01.libreria.controladores;

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
    public String listar(Model model) {
        model.addAttribute("cliente", clienteServicio.findAll());
        return "cliente/cliente";
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
            redirectAttributes.addFlashAttribute("exito", "El cliente ''" + cliente.getNombre() + " "
                    + cliente.getApellido() + "'' se ha guardado con éxito");
            return "redirect:/cliente/lista";
        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al guardar el cliente  ''" + cliente.getNombre() + " " + cliente.getApellido() + "''");
            return "cliente/cliente-form";
        }

    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id, RedirectAttributes redirectAttributes) throws ErrorServicio {
        Cliente cliente = clienteServicio.disableEnable(id);
        if (cliente.getAlta()) {
            redirectAttributes.addFlashAttribute("exito",
                    "Se dio de alta al cliente ''" + cliente.getNombre() + cliente.getApellido() + "''");
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Se dio de baja al cliente ''" + cliente.getNombre() + cliente.getApellido() + "''");
        }
        return "redirect:/cliente/lista";
    }

    @GetMapping("/delete")
    public String delete(Model model, RedirectAttributes redirectAttributes,
            @RequestParam(required = false) String id) {

        try {
            Optional<Cliente> respuesta = clienteServicio.findById(id);
            if (respuesta.isPresent()) {
                clienteServicio.delete(respuesta.get());
                redirectAttributes.addFlashAttribute("exito", "El cliente ''" + respuesta.get().getNombre() + " "
                        + respuesta.get().getApellido() + "''  fue eliminado con exito");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cliente/lista";
    }
}
