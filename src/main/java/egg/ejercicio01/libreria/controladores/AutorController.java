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

import egg.ejercicio01.libreria.entidades.Autor;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.servicios.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    AutorServicio autorServicio;

    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("autor", autorServicio.findAll());
        return "autor/autor";
    }

    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Autor> resultado = autorServicio.findById(id);
            if (resultado.isPresent()) {
                model.addAttribute("autor", resultado.get());
            } else {
                return "redirect:/autor/lista";
            }
        } else {
            model.addAttribute("autor", new Autor());
        }
        return "autor/autor-form";
    }

    @PostMapping("/save")
    public String guardar(Model model, @ModelAttribute @Valid Autor autor, BindingResult bindingResult, ModelMap modelo,
            RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "autor/autor-form";
            }
            autorServicio.save(autor);
            redirectAttributes.addFlashAttribute("exitoAutor",
                    "El autor ''" + autor.getNombre() + "'' se ha guardado con exito");
            return "redirect:/autor/lista";
        } catch (ErrorServicio e) {
            modelo.addAttribute("errorServicio", e.getMessage());
            redirectAttributes.addFlashAttribute("errorAutor", "Error al guardar el autor ''" + autor.getNombre());
            return "autor/autor-form";
        }
    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id) throws ErrorServicio {
        autorServicio.disableEnable(id);
        return "redirect:/autor/lista";
    }

    @GetMapping("/delete")
    public String eliminar(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {

        try {
            Optional<Autor> resultado = autorServicio.findById(id);
            if (resultado.isPresent()) {
                autorServicio.deleteAutor(id);
                redirectAttributes.addFlashAttribute("exitoAutor",
                        "El autor ''" + resultado.get().getNombre() + "'' se ha eliminado con exito");
            }

        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("errorAutor", e.getMessage());
        }
        return "redirect:/autor/lista";

    }
}
