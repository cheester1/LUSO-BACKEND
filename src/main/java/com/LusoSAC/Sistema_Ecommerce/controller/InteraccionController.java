package com.LusoSAC.Sistema_Ecommerce.controller;

import com.LusoSAC.Sistema_Ecommerce.model.Interaccion;
import com.LusoSAC.Sistema_Ecommerce.repository.InteraccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interacciones")
@CrossOrigin
public class InteraccionController {

    @Autowired
    private InteraccionRepository repo;

    @PostMapping
    public Interaccion guardar(@RequestBody Interaccion interaccion) {
        return repo.save(interaccion);
    }
}