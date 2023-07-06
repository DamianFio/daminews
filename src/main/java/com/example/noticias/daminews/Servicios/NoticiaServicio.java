package com.example.noticias.daminews.Servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.noticias.daminews.entidades.Imagen;
import com.example.noticias.daminews.entidades.Noticia;
import com.example.noticias.daminews.exepciones.MiException;
import com.example.noticias.daminews.repository.NoticiaRepositorio;

@Service
public class NoticiaServicio {
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registar(MultipartFile archivo, String nombre, String cuerpo) throws IOException, MiException {
        validar(nombre, cuerpo);
        Noticia noticia = new Noticia();
        noticia.setNombre(nombre);
        noticia.setCuerpo(cuerpo);

        Imagen imagen = imagenServicio.guardar(archivo);
        noticia.setImagen(imagen);

        noticiaRepositorio.save(noticia);

    }

    public Noticia encontrarPorId(Long id) {
        return noticiaRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList<>();

        noticias = noticiaRepositorio.findAll();

        return noticias;
    }

    // Este metodo se encarga de actualizar el objeto completo, incluso su objeto
    // herenciado.
    // Asi que tiene que generar una doble busqueda, el objeto mas su herencia.
    @Transactional
    public void actualizar(MultipartFile archivo, Long id, String nombre, String cuerpo) throws MiException {
        validar(nombre, cuerpo);
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        if (respuesta != null) {
            Noticia noticia = respuesta.get();
            noticia.setNombre(nombre);
            noticia.setCuerpo(cuerpo);

            if (archivo.isEmpty()) {
                noticiaRepositorio.save(noticia);
            } else {
                Long idFoto = null;

                if (noticia.getImagen() != null) {
                    idFoto = noticia.getImagen().getId();
                }

                Imagen imagen = imagenServicio.actualizar(archivo, idFoto);

                noticia.setImagen(imagen);

                noticiaRepositorio.save(noticia);
            }

        }

    }

    // Este metodo elimina de la persistencia luego de buscar y encontrar el objeto.
    public void eliminar(Long id) {
        Noticia noticia = noticiaRepositorio.getOne(id);
        noticiaRepositorio.delete(noticia);

    }

    private void validar(String nombre, String cuerpo) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacio");
        }

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("El cuerpo no puede estar vacio");
        }
    }
}
