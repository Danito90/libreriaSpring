package egg.ejercicio01.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egg.ejercicio01.libreria.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroController {
    @Autowired
    LibroServicio libroServicio;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("libros", libroServicio.findAll());
        return "libro";
    }

    @PostMapping("/form")
    public String nuevo(Model model) {

        return "libro-form";
    }

    @GetMapping("/editar")
    public String editar() {
        return "libro/editar";
    }

    @GetMapping("/eliminar")
    public String eliminar() {
        return "libro/eliminar";
    }

}
