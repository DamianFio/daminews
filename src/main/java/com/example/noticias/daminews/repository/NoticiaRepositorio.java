package com.example.noticias.daminews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.noticias.daminews.entidades.Noticia;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, Long> {

}
