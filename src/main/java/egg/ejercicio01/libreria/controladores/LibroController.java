package egg.ejercicio01.libreria.controladores;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egg.ejercicio01.libreria.entidades.Autor;
import egg.ejercicio01.libreria.entidades.Editorial;
import egg.ejercicio01.libreria.entidades.Libro;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.servicios.AutorServicio;
import egg.ejercicio01.libreria.servicios.EditorialServicio;
import egg.ejercicio01.libreria.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    AutorServicio autorServicio;

    @Autowired
    EditorialServicio editorialServicio;

    // Post solicita creacion o modificacio nde un nuevo registro, toca la base de
    // datos. Trae valores de un formulario y los pasa a un objeto Libro.
    @GetMapping("/lista") // get se utiliza solo para consultar informacion o mostrar, no envia datos, al
                          // server. Renderiza vistas
    public String listar(Model model) {
        model.addAttribute("libros", libroServicio.findAll());
        return "libro"; // Siempre string para devolver la url de la vista
    }

    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) { // RequestParam oblicatorio,
                                                                                  // required= false opcional
        if (id != null) {
            Optional<Libro> optional = libroServicio.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("libro", optional.get());
            } else {
                return "redirect:/libro/lista";
            }
        } else {
            model.addAttribute("libro", new Libro());
        }
        model.addAttribute("autores", autorServicio.findAll());
        model.addAttribute("editoriales", editorialServicio.findAll());
        return "libro-form";
    }

    @PostMapping("/save") // valida cada item
    public String guardar(Model model, RedirectAttributes redirectAttributes, @ModelAttribute @Valid Libro libro,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "libro-form";
            }
            libroServicio.save(libro);
            redirectAttributes.addFlashAttribute("exito", "Libro guardado con exito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el libro: " + e.getMessage());
        }
        return "redirect:/libro/lista";
    }

    // @PostMapping("/save") //solucionar no valida (copiado de tinder mascotas)
    // public String guardar(ModelMap modelo,@RequestParam Long isbn,@RequestParam
    // String titulo,@RequestParam Integer anio,@RequestParam Integer
    // ejemplares,@RequestParam Integer ejemplaresPrestados,
    // @RequestParam Integer ejemplaresRestantes,@RequestParam Boolean
    // alta,@RequestParam Autor autor,@RequestParam Editorial editorial) {
    // try {
    // libroServicio.newLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
    // ejemplaresRestantes, alta, autor, editorial);

    // } catch (ErrorServicio e) {
    // modelo.put("errorPuntual", e.getMessage());
    // return "libro-form.html";
    // }
    // return "libro-form.html";
    // }

    @GetMapping("/editar")
    public String editar() {
        return "libro/editar";
    }

    @GetMapping("/eliminar")
    public String eliminar() {
        return "libro/eliminar";
    }

}
