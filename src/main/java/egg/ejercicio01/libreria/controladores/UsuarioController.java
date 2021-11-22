package egg.ejercicio01.libreria.controladores;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egg.ejercicio01.libreria.entidades.Usuario;
import egg.ejercicio01.libreria.errores.ErrorServicio;
import egg.ejercicio01.libreria.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/lista")
    public String lista(Model model) {
        model.addAttribute("usuario", usuarioServicio.findAll());
        return "usuario/usuario";
    }

    @GetMapping("/form")
    public String nuevo(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Usuario> resultado = usuarioServicio.findById(id);
            if (resultado.isPresent()) {
                model.addAttribute("usuario", resultado.get());
            } else {
                return "redirect:/usuario/lista";
            }
        } else {
            model.addAttribute("usuario", new Usuario());
        }
        return "usuario/usuario-form";
    }

    @PostMapping("/save")
    public String guardar(Model model, @ModelAttribute @Valid Usuario usuario, BindingResult bindingResult,@RequestParam(required= false) MultipartFile imagen, ModelMap modelo,
            RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                return "usuario/usuario-form";
            }
            usuarioServicio.save(usuario,imagen);
            redirectAttributes.addFlashAttribute("exito",
                    "El usuario ''" + usuario.getUsuario() + "'' se ha guardado con exito");
            return "redirect:/usuario/lista";
        } catch (ErrorServicio e) {
            modelo.addAttribute("errorServicio", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar el usuario ''" + usuario.getUsuario() + "''");
            return "usuario/usuario-form";
        }
    }

    @GetMapping("/enable")
    public String activar(@RequestParam String id, RedirectAttributes redirectAttributes) throws ErrorServicio {
        Usuario usuario = usuarioServicio.disableEnable(id);
        if (usuario.getAlta()) {
            redirectAttributes.addFlashAttribute("exito", "Se dio de alta al usuario ''" + usuario.getUsuario() + "''");
        } else {
            redirectAttributes.addFlashAttribute("error", "Se dio de baja al usuario ''" + usuario.getUsuario() + "''");
        }
        return "redirect:/usuario/lista";
    }

    @GetMapping("/delete")
    public String eliminar(RedirectAttributes redirectAttributes, @RequestParam(required = true) String id) {

        try {
            Optional<Usuario> resultado = usuarioServicio.findById(id);
            if (resultado.isPresent()) {
                usuarioServicio.delete(id);
                redirectAttributes.addFlashAttribute("exito",
                        "El usuario ''" + resultado.get().getUsuario() + "'' se ha eliminado con exito");
            }

        } catch (ErrorServicio e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuario/lista";

    }
}


