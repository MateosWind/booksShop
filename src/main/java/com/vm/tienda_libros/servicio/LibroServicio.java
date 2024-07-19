package com.vm.tienda_libros.servicio;

import com.vm.tienda_libros.Modelo.Libro;
import com.vm.tienda_libros.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServicio implements  ILibroService{

    @Autowired
    private Repositorio repositorio;

    @Override
    public List<Libro> listarLibros() {
        return repositorio.findAll();
    }

    @Override
    public Libro buscarLibroPorId(Integer idLibro) {
        Libro libro = repositorio.findById(idLibro).orElse(null);
        return libro;
    }

    @Override
    public void guardarLibro(Libro libro) {
        repositorio.save(libro);

    }

    @Override
    public void eliminarLibro(Libro libro) {
        repositorio.delete(libro);
    }

}
