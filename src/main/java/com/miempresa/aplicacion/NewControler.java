/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miempresa.aplicacion;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yonnier Uni
 */
@RestController
public class NewControler {

    @RequestMapping("/bienvenido")
    public String NewControler() {
        
        return "redirect:/bienvenido";
    }

}
