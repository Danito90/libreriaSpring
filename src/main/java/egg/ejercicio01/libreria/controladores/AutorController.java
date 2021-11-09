package egg.ejercicio01.libreria.controladores;

import java.util.List;
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
        return "autor";
    }


    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Autor> autor = autorServicio.findById(id);
            if (autor.isPresent()) {
                model.addAttribute("autor", autor.get());
            }else{
                return "redirect:/autor/lista";
            }
        } else {
            model.addAttribute("autor", new Autor());
        }
        return "autor-form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute @Valid Autor autor, ModelMap modelo, RedirectAttributes redirectAttributes,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "autor-form";
            }
            autorServicio.save(autor);
            redirectAttributes.addFlashAttribute("exitoAutor", "Autor guardado con exito");
            return "redirect:/autor/lista";
        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("errorAutor", "Error al guardar el autor");
            return "redirect:/autor/lista";
        }
    }

}
