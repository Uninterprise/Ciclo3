package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class ControladorProducto {

    private final RepositorioProducto repositorioProducto;

    @GetMapping("/productos") //path del controlador
    public String getTodosLosProductos(Model model) {
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos", productos);
        return "vistaProducto";
    }

    @GetMapping("/productos/{codigoProducto}") //path del controlador
    public String getProductoById(@PathVariable String codigoProducto, Model model) {
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(codigoProducto);
        Iterable<Producto> productos = repositorioProducto.findAllById(listaProducto);
        model.addAttribute("productos", productos);
        return "vistaProducto";
    }

    @GetMapping("/crear/producto") //path del controlador
    public String crearProducto(Model model) {

        model.addAttribute("producto", new Producto());
        return "vistaCrearProducto";
    }

    @PostMapping("/crear/producto")
    public RedirectView procesarProducto(@ModelAttribute Producto producto) {
        try {
            Producto productoGuardado = repositorioProducto.save(producto);

            if (productoGuardado == null) {
                return new RedirectView("/crear/producto#Error");

            }
            return new RedirectView("/productos#miModal", true);

        } catch (Exception e) {
            return new RedirectView("/crear/producto#Error");

        }

    }

    /*@GetMapping("/editar/{id}")
    public String editar(@PathVariable int id,Model model){
        Optional<Persona> persona=service.listarId(id);
        model.addAttribute("persona", persona);
        return "form";
    }
     */
    @GetMapping("/editar/{codProducto}")
    public String editarProducto(@PathVariable String codProducto, Model model) {
        Producto productoSeleccionado = repositorioProducto.findByCodProducto(codProducto);
        model.addAttribute("producto", productoSeleccionado);
//       model.addAttribute("producto", new Producto());
        return "vistaCrearProducto";
    }

    @GetMapping("/eliminarProd/{codProducto}")
    public String eliminarProducto(@PathVariable String codProducto, Model model) {

        try {
            repositorioProducto.deleteById(codProducto);
            return "redirect:/productos#miModal";
        } catch (Exception e) {
            return "redirect:/productos#Error";
        }
    }

}
