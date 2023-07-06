package com.example.noticias.daminews.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.noticias.daminews.Servicios.NoticiaServicio;
import com.example.noticias.daminews.entidades.Noticia;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    NoticiaServicio noticiaServicio;

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> imagenNoticia(@PathVariable Long id) {
        Noticia noticia = noticiaServicio.encontrarPorId(id);

        byte[] imagen = noticia.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

}
