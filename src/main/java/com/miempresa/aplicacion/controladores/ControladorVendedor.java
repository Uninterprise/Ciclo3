package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class ControladorVendedor {

    private final RepositorioVendedor repositorioVendedor;

    @GetMapping("/vendedores") //path del controlador
    public String getTodosLosVendedores(Model model) {
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        model.addAttribute("vendedores", vendedores);
        return "vistaVendedor";
    }

    @GetMapping("/vendedores/{codigoVendedor}") //path del controlador
    public String getVendedorById(@PathVariable String codigoVendedor, Model model) {
        Vendedor vendedores = repositorioVendedor.findByCodVendedor(codigoVendedor);
        model.addAttribute("vendedores", vendedores);
        return "vistaVendedor";
    }

    @GetMapping("/crear/vendedor") //path del controlador
    public String crearVendedor(Model model) {

        model.addAttribute("vendedor", new Vendedor());
        return "vistaCrearVendedor";
    }

    @PostMapping("/crear/vendedor")
    public RedirectView procesarVendedor(@ModelAttribute Vendedor vendedor) {
        try {

            Vendedor vendedorGuardado = repositorioVendedor.save(vendedor);
            if (vendedorGuardado == null) {
                return new RedirectView("/crear/vendedor#Error", true);
            }
            return new RedirectView("/vendedores#miModal", true);
        } catch (Exception e) {
            return new RedirectView("/crear/vendedor#Error");
        }
    }

    @GetMapping("/editarVend/{codVendedor}")
    public String editarVendedor(Model model, @PathVariable String codVendedor) {
        try {
            Vendedor vendedores = repositorioVendedor.findByCodVendedor(codVendedor);
            repositorioVendedor.save(vendedores);
            model.addAttribute("vendedor", vendedores);
            return "vistaCrearVendedor";
        } catch (Exception e) {
            return "redirect:/vendedores#Error";
        }
    }

    @GetMapping("/eliminar/{codVendedor}")
    public String eliminarVendedor(Model model, @PathVariable String codVendedor) {
        try {
            repositorioVendedor.deleteById(codVendedor);
            return "redirect:/vendedores#miModal";
        } catch (Exception e) {
            return "redirect:/vendedores#Error";
        }
    }

}
