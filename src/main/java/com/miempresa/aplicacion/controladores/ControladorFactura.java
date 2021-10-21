package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.FacturaDto;
import com.miempresa.aplicacion.modelos.RepositorioFactura;
import com.miempresa.aplicacion.modelos.Factura;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import java.sql.Date;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
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
public class ControladorFactura {

    private final RepositorioFactura repositorioFactura;
    private final RepositorioProducto repositorioProducto;
    private final RepositorioVendedor repositorioVendedor;

    @GetMapping("/facturas") //path del controlador
    public String getTodasLasFacturas(Model model) {
        Iterable<Factura> facturas = repositorioFactura.findAll();
        model.addAttribute("facturas", facturas);
        return "vistaFactura";
    }

    @GetMapping("/facturas/{numeroFactura}") //path del controlador
    public String getFacturaByNumero(@PathVariable String numeroFactura, Model model) {
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(numeroFactura);
        Factura factura = repositorioFactura.findByNumeroFactura(numeroFactura);
        model.addAttribute("facturas", factura);
        return "vistaFactura";
    }

    @GetMapping("/crear/factura") //path del controlador
    public String crearFactura(Model model) {
        try {
            Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
            model.addAttribute("vendedores", vendedores);

            Iterable<Producto> productos = repositorioProducto.findAll();
            model.addAttribute("productos", productos);

            Factura fact = new Factura();
            model.addAttribute("factura", fact);

            return "vistaCrearFactura";

        } catch (Exception e) {
            return "redirect:/facturas#Error";

        }

    }

    @PostMapping("/crear/factura")
    public RedirectView procesarFatura(@ModelAttribute Factura factura) {

        try {
            Factura facturaGuardada = repositorioFactura.save(factura);
            if (facturaGuardada == null) {
                return new RedirectView("/crear/factura#Error", true);
            }
            return new RedirectView("/facturas#miModal", true);

        } catch (Exception e) {
            return new RedirectView("/crear/factura#Error");

        }
    }

    @GetMapping("/editarFact/{numeroFactura}")
    public String editarFactura(@PathVariable String numeroFactura, Model model) {
        try {

            Factura facturaSeleccionado = repositorioFactura.findByNumeroFactura(numeroFactura);
            System.out.println(" " + numeroFactura);

            model.addAttribute("factura", facturaSeleccionado);

            System.out.println("fa " + facturaSeleccionado.getIdVenta() + " " + facturaSeleccionado.getNumeroFactura());
            Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
            model.addAttribute("vendedores", vendedores);

            Iterable<Producto> productos = repositorioProducto.findAll();
            model.addAttribute("productos", productos);
            //System.out.println("id "+facturaSeleccionado.getIdVenta());
            return "vistaCrearFactura";
        } catch (Exception e) {
            return "redirect:/facturas#Error";

        }
    }

    @GetMapping("/eliminarFact/{idVenta}")
    public String eliminarFactura(Model model, @PathVariable Long idVenta) {
        repositorioFactura.deleteById(idVenta);
        return "redirect:/facturas";
    }
}
