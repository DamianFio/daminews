package com.example.noticias.daminews.Servicios;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.noticias.daminews.entidades.Imagen;
import com.example.noticias.daminews.repository.ImagenRepositorio;

@Service
public class ImagenServicio {
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    // Este metodo solo se enecarga de guardar un archivo que no existe.
    // Como pueden ver no tiene mucha ciencia, es simplemente igualar lo que viene
    // de la imagen del html.
    public Imagen guardar(MultipartFile archivo) throws IOException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    // Este metodo por otro lado, requiere la existencia de un QUERY en la base de
    // datos para encontrar dicho objeto
    // Por este mismo motivo tiene un metodo de busqueda interno.
    public Imagen actualizar(MultipartFile archivo, Long id) {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }
        return null;
    }
}
