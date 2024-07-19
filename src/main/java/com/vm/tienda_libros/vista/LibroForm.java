package com.vm.tienda_libros.vista;

import com.vm.tienda_libros.Modelo.Libro;
import com.vm.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {

    private JLabel tiendaDeLibrosLabel;
    LibroServicio libroServicio;
    private JPanel panel;
    private JTextField idTexto;
    private JTable tablaLibros;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarLibroButton;
    private JButton modificarLibroButton;
    private JButton eliminarLibroButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio=libroServicio;
        iniciarForma();
        agregarLibroButton.addActionListener(e -> agregarLibro());


        modificarLibroButton.addActionListener(e -> modificarLibro());
        eliminarLibroButton.addActionListener(e -> eliminarLibro());
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                cargarLibroSeleccionado();
            }
        });
    }

    private void eliminarLibro(){
        var renglon = tablaLibros.getSelectedRow();
        if(renglon !=-1){
            String idLibro =
                    tablaLibros.getModel().getValueAt(renglon,0).toString();
            var libro = new Libro();
            libro.setIdLibro(Integer.parseInt(idLibro));
            libroServicio.eliminarLibro(libro);
            mostrarMensaje("Se ha eliminado el "+idLibro+ "Libro");
            limpiarFormulario();
            listarLibros();
        }
    }

    private void modificarLibro(){
        if(this.idTexto.getText().equals("")){
            mostrarMensaje("No se ha seleccionado ningun registro Valido");
        }else{
            if(libroTexto.getText().equals("")){
                mostrarMensaje("Proporcione el nombre del Libro...");
                libroTexto.requestFocusInWindow();
                return;
            }
            int idLibro = Integer.parseInt(idTexto.getText());
            var nombreLibro = libroTexto.getText();
            var autor = autorTexto.getText();
            var precio = Double.parseDouble(precioTexto.getText());
            var existencias = Integer.parseInt(existenciasTexto.getText());
            var libro = new Libro(idLibro,nombreLibro,autor,precio,existencias);
            libroServicio.guardarLibro(libro);
            mostrarMensaje("Se ha modificado el libro");
            limpiarFormulario();
            listarLibros();
        }
    }

    private void cargarLibroSeleccionado(){
        var renglon = tablaLibros.getSelectedRow();
        if(renglon !=-1){
            String idLibro =
                    tablaLibros.getModel().getValueAt(renglon,0).toString();
            idTexto.setText(idLibro);
            String nombreLibro=
                    tablaLibros.getModel().getValueAt(renglon,1).toString();
            libroTexto.setText(nombreLibro);
            String autor=
                    tablaLibros.getModel().getValueAt(renglon,2).toString();
            autorTexto.setText(autor);
            String precio=
                    tablaLibros.getModel().getValueAt(renglon,3).toString();
            precioTexto.setText(precio);
            String existencias=
                    tablaLibros.getModel().getValueAt(renglon,4).toString();
            existenciasTexto.setText(existencias);
        }
    }

    private void agregarLibro(){
        if(libroTexto.getText().equals("")){
            mostrarMensaje("Proporciona el nombre del libro");
            libroTexto.requestFocusInWindow();
            return ;
        }
        var nombreLibro= libroTexto.getText();
        var autor= autorTexto.getText();
        var precio= Double.parseDouble(precioTexto.getText());
        var existencias= Integer.parseInt(existenciasTexto.getText());
        //Crear el objeto de Libro
        var libro = new Libro(null,nombreLibro,autor,precio,existencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el libro!");
        limpiarFormulario();
        listarLibros();
    }
    private void limpiarFormulario(){
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this,mensaje);

    }

   private void iniciarForma(){
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x =(tamanioPantalla.width-getWidth()/2);
        int y=(tamanioPantalla.height-getHeight()/2);
        setLocation(x,y);
   }

    private void createUIComponents() {
        //Componente oculto de idTexto
        idTexto= new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloLibros = new DefaultTableModel(0,5);
        String[] cabeceros={"Id","Libro","Autor","Precio","Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabeceros);
        this.tablaLibros= new JTable(tablaModeloLibros);
        listarLibros();

    }

    private void listarLibros(){
        tablaModeloLibros.setRowCount(0);
        var libros = libroServicio.listarLibros();
        libros.forEach((libro) ->{

            Object[] renglonLibro = {
              libro.getIdLibro(),
              libro.getNombreLibro(),
              libro.getAutor(),
              libro.getPrecio(),
              libro.getExistencias()
            };
            this.tablaModeloLibros.addRow(renglonLibro);
                });
    }
}
