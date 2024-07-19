package com.vm.tienda_libros.servicio;

import com.vm.tienda_libros.Modelo.Libro;

import java.util.List;

public interface ILibroService {
    public List<Libro> listarLibros();

    public Libro buscarLibroPorId(Integer idLibro);

    public void guardarLibro(Libro libro);

    public void eliminarLibro(Libro libro);
}
