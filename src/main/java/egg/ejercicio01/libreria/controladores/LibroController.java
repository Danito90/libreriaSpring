package egg.ejercicio01.libreria.controladores;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
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


    // @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //solo para este rol autorizado, podemos hacerlo desde security config
    @GetMapping("/lista") // get se utiliza solo para consultar informacion o mostrar, no envia datos, al
                          // server. Renderiza vistas
    public String listar(Model model) {
        model.addAttribute("libros", libroServicio.findAll());
        return "libro/libro"; // Siempre string para devolver la url de la vista
    }
    
    // @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) { // RequestParam obligatorio,
                                                                                  // required= false opcional
        if (id != null) {
            Optional<Libro> resultado = libroServicio.findById(id);
            if (resultado.isPresent()) {
                model.addAttribute("libro", resultado.get());
            } else {
                return "redirect:/libro/lista";
            }
        } else {
            model.addAttribute("libro", new Libro());
        }
        carga(model);
        return "libro/libro-form";
    }

    // Post solicita creacion o modificacio nde un nuevo registro, toca la base de
    // datos. Trae valores de un formulario y los pasa a un objeto Libro.
    // @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save") // valida cada item
    public String guardar(Model model, ModelMap modelo, RedirectAttributes redirectAttributes,
            @ModelAttribute @Valid Libro libro, BindingResult bindingResult) { // Es indispensable que BindingResult
                                                                               // bindingResult se pase
                                                                               // justo despues de la Entidad(clase) si
                                                                               // no no funciona
        try {
            if (bindingResult.hasErrors()) {
                carga(model);
                return "libro/libro-form";
            }

            if (libro.getEjemplares() < libro.getEjemplaresPrestados()) {
                bindingResult.rejectValue("ejemplares", "error.libro",
                        "El numero de ejemplares no puede ser menor al numero de ejemplares prestados");
                carga(model);
                return "libro/libro-form";
            }

            libroServicio.save(libro);
            redirectAttributes.addFlashAttribute("exito",
                    "El libro ''" + libro.getTitulo() + "'' se ha guardado con exito");
            return "redirect:/libro/lista";
        } catch (ErrorServicio e) {
            carga(model);
            if (e.getMessage().equals("La Editorial no puede estar vacia")) {
                modelo.put("editorialError", e.getMessage());
            } else {
                modelo.put("autorError", e.getMessage());
            }

            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "libro/libro-form";
        }

    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id, RedirectAttributes redirectAttributes) throws ErrorServicio {
        Libro libro = libroServicio.disableEnable(id);
        if (libro.getAlta()) {
            redirectAttributes.addFlashAttribute("exito", "Se dio de alta al libro ''" + libro.getTitulo() + "''");
        } else {
            redirectAttributes.addFlashAttribute("error", "Se dio de baja al libro ''" + libro.getTitulo() + "''");
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

    @GetMapping("/delete")
    public String eliminar(@RequestParam(required = true) String id, RedirectAttributes redirectAttributes) {

        try {
            Optional<Libro> resultado = libroServicio.findById(id);
            if (resultado.isPresent()) {
                libroServicio.deleteLibro(id);
                redirectAttributes.addFlashAttribute("exito",
                        "El Libro ''" + resultado.get().getTitulo() + "'' ha sido eliminado con exito");
            }

        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/libro/lista";
    }

    public void carga(Model model) {
        model.addAttribute("autores", autorServicio.findAll()); // vuelve a cargar select
        model.addAttribute("editoriales", editorialServicio.findAll());
    }

}
