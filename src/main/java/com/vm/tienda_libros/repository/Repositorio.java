package com.vm.tienda_libros.repository;

import com.vm.tienda_libros.Modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Repositorio extends JpaRepository<Libro, Integer> {
}
