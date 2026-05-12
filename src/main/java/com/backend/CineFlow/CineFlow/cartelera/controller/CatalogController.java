package com.backend.CineFlow.CineFlow.cartelera.controller;

import com.backend.CineFlow.CineFlow.cartelera.dto.BillboardMovieResponse;
import com.backend.CineFlow.CineFlow.cartelera.dto.CreateFunctionRequest;
import com.backend.CineFlow.CineFlow.cartelera.dto.FunctionResponse;
import com.backend.CineFlow.CineFlow.cartelera.dto.FunctionSeatsResponse;
import com.backend.CineFlow.CineFlow.cartelera.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cartelera")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/peliculas/cartelera")
    public List<BillboardMovieResponse> getBillboard() {
        return catalogService.obtenerCartelera();
    }

    @GetMapping("/funciones/{id}/butacas")
    public FunctionSeatsResponse getFunctionSeats(@PathVariable Long id) {
        return catalogService.obtenerButacasFuncion(id);
    }

    @PostMapping("/funciones")
    public ResponseEntity<FunctionResponse> createFunction(@RequestBody CreateFunctionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.crearFuncion(request));
    }
}