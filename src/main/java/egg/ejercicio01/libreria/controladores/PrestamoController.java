package egg.ejercicio01.libreria.controladores;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egg.ejercicio01.libreria.entidades.Prestamo;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.servicios.ClienteServicio;
import egg.ejercicio01.libreria.servicios.LibroServicio;
import egg.ejercicio01.libreria.servicios.PrestamoServicio;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoServicio prestamoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("prestamo", prestamoServicio.findAll());
        return "prestamo/prestamo";
    }

    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) throws ErrorServicio {
        if (id != null) {
            Optional<Prestamo> respuesta = prestamoServicio.findById(id);
            if (respuesta.isPresent()) {
                model.addAttribute("prestamo", respuesta.get());
            } else {
                return "redirect:/prestamo/lista";
            }
        } else {
            model.addAttribute("prestamo", new Prestamo());
        }
        model.addAttribute("libros", libroServicio.findAll());
        model.addAttribute("clientes", clienteServicio.findAll());
        return "prestamo/prestamo-form";
    }

    @GetMapping("/save")
    public String guardar(ModelMap modelo, RedirectAttributes redirectAttributes, Model model,
            @ModelAttribute @Valid Prestamo prestamo, BindingResult bindingResult) throws ErrorServicio {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("libros", libroServicio.findAll());
                model.addAttribute("clientes", clienteServicio.findAll());
                return "prestamo/prestamo-form";
            }
            prestamoServicio.save(prestamo);
            redirectAttributes.addFlashAttribute("exito", "Prestamo guardado con éxito");
            return "redirect:/prestamo/lista";
        } catch (ErrorServicio e) {
            model.addAttribute("libros", libroServicio.findAll());
            model.addAttribute("clientes", clienteServicio.findAll());
            if (e.getMessage().equals("El Libro no puede estar vacio")) {
                modelo.put("libroError", e.getMessage()); // viene de la validacion
            } else {
                modelo.put("clienteError", e.getMessage()); // viene de la validacion
            }
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // viene de la validacion
            return "prestamo/prestamo-form";
        }
    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id, RedirectAttributes redirectAttributes) throws ErrorServicio {
        Prestamo prestamo = prestamoServicio.disableEnable(id);
        if (prestamo.getAlta()) {
            redirectAttributes.addFlashAttribute("exito", "Se dio de alta el prestamo");
        } else {
            redirectAttributes.addFlashAttribute("error", "Se dio de baja al prestamo");
        }
        return "redirect:/prestamo/lista";
    }

    @GetMapping("/delete")
    public String delete(Model model, RedirectAttributes redirectAttributes,
            @RequestParam(required = false) String id) {

        try {
            Optional<Prestamo> respuesta = prestamoServicio.findById(id);
            if (respuesta.isPresent()) {
                prestamoServicio.delete(respuesta.get());
                redirectAttributes.addFlashAttribute("exito", "El prestamo fue eliminado con exito");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/prestamo/lista";
    }
}
