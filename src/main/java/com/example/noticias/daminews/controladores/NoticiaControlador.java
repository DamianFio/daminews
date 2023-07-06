package com.example.noticias.daminews.controladores;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.noticias.daminews.Servicios.NoticiaServicio;
import com.example.noticias.daminews.entidades.Noticia;
import com.example.noticias.daminews.exepciones.MiException;

@Controller
@RequestMapping("/")
public class NoticiaControlador {

    @Autowired
    NoticiaServicio noticiaServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String cuerpo, MultipartFile archivo)
            throws MiException, IOException {
        try {
            noticiaServicio.registar(archivo, nombre, cuerpo);
            return "registrar";
        } catch (MiException e) {
            e.printStackTrace();
            return "registrar";
        }
    }

    @GetMapping("/editar")
    public String editar(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "editar.html";
    }

    // Aca empieza el mapping para editar el contenido persistente, notese que ambos
    // estan mapeados al mismo lugar.
    @GetMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable Long id, ModelMap modelo, MultipartFile archivo) {
        modelo.put("noticia", noticiaServicio.encontrarPorId(id));
        return "noticias_editar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable Long id, @RequestParam String nombre, @RequestParam String cuerpo,
            MultipartFile archivo,
            ModelMap modelo) throws MiException {

        noticiaServicio.actualizar(archivo, id, nombre, cuerpo);

        return "redirect:../noticias";
    }

    // Este metodo se encarga de eliminar de la persistencia.
    @GetMapping("eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        noticiaServicio.eliminar(id);
        return "redirect:../noticias";
    }

    // Este metodo es solo para mostrar.
    @GetMapping("/noticias")
    public String listar(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "noticias_list.html";
    }

}
