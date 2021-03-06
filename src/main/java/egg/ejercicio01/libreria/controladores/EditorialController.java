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

import egg.ejercicio01.libreria.entidades.Editorial;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("editorial", editorialServicio.findAll());
        return "editorial/editorial";
    }

    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Editorial> resultado = editorialServicio.findById(id);
            if (resultado.isPresent()) {
                model.addAttribute("editorial", resultado.get());
            } else {
                return "redirect:/editorial/lista";
            }
        } else {
            model.addAttribute("editorial", new Editorial());
        }
        return "editorial/editorial-form";
    }

    @PostMapping("/save")
    public String guardar(Model model, @ModelAttribute @Valid Editorial editorial, BindingResult bindingResult,
            ModelMap modelo, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "editorial/editorial-form";
            }
            editorialServicio.save(editorial);
            redirectAttributes.addFlashAttribute("exito",
                    "La editorial ''" + editorial.getNombre() + "'' se ha guardado con exito");
            return "redirect:/editorial/lista";
        } catch (ErrorServicio e) {
            modelo.addAttribute("errorServicio", e.getMessage());
            redirectAttributes.addFlashAttribute("error",
                    "Error al guardar el editorial ''" + editorial.getNombre() + "''");
            return "editorial/editorial-form";
        }
    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id, RedirectAttributes redirectAttributes) throws ErrorServicio {
        Editorial editorial = editorialServicio.disableEnable(id);
        if (editorial.getAlta()) {
            redirectAttributes.addFlashAttribute("exito", "Se dio de alta a la editorial ''" + editorial.getNombre() + "''");
        } else {
            redirectAttributes.addFlashAttribute("error", "Se dio de baja a la editorial ''" + editorial.getNombre() + "''");
        }
        return "redirect:/editorial/lista";
    }

    @GetMapping("/delete")
    public String eliminar(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {

        try {
            Optional<Editorial> resultado = editorialServicio.findById(id);
            if (resultado.isPresent()) {
                editorialServicio.deleteEditorial(id);
                redirectAttributes.addFlashAttribute("exito",
                        "La editorial ''" + resultado.get().getNombre() + "'' se ha eliminado con exito");
            }

        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/editorial/lista";

    }
}
