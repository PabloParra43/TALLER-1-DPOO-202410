package uniandes.dpoo.gasolinera.consola;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import uniandes.dpoo.gasolinera.logica.Gasolinera;

/**
 * En esta clase se encuentra el método main de la aplicación.
 */
public class ConsolaPrincipal extends ConsolaBasica
{
    /**
     * Opciones que se mostrarán en el menú principal
     */
    private final String[] opcionesMenuPrincipal = new String[]{ "Usar gasolinera actual", "Crear nueva gasolinera", "Cargar gasolinera de un archivo", "Guardar gasolinera a un archivo", "Salir" };

    /**
     * El objeto Gasolinera que se utilizará durante la ejecución de la aplicación
     */
    private Gasolinera laGasolinera;

    /**
     * Muestra el menú principal de la aplicación y ejecuta la opción que seleccione el usuario.
     * 
     * El menú vuelve a mostrarse hasta que el usuario seleccione la opción para abandonar la aplicación.
     */
    private void mostrarMenuPrincipal( )
    {
        int opcionSeleccionada = mostrarMenu( "Menú principal", opcionesMenuPrincipal );
        if( opcionSeleccionada == 1 )
        {
            usarGasolinera( );
        }
        else if( opcionSeleccionada == 2 )
        {
            ConsolaCrearGasolinera consolaCreacion = new ConsolaCrearGasolinera( );
            laGasolinera = consolaCreacion.mostrarOpciones( );
        }
        else if( opcionSeleccionada == 3 )
        {
            cargarGasolinera( );
        }
        else if( opcionSeleccionada == 4 )
        {
            guardarGasolinera( );
        }
        else if( opcionSeleccionada == 5 )
        {
            System.out.println( "Saliendo ..." );
            System.exit( 0 );
        }
        mostrarMenuPrincipal( );
    }

    /**
     * Muestra las opciones para interactuar con una gasolinera, utilizando una instancia de la clase ConsolaUsarGasolinera
     */
    private void usarGasolinera( )
    {
        if( laGasolinera != null )
        {
            ConsolaUsarGasolinera consolaUso = new ConsolaUsarGasolinera( laGasolinera );
            consolaUso.mostrarMenu( );
        }
        else
        {
            System.out.println( "No hay en este momento una gasolinera que pueda usarse" );
        }
    }

    /**
     * Le pide al usuario el nombre de un archivo y luego crea una nueva Gasolinera utilizando la información contenida en el archivo.
     */
    private void cargarGasolinera( )
    {
        String nombreArchivo = pedirCadenaAlUsuario( "Indique el archivo con la información de la gasolinera. El archivo debe estar dentro de la carpeta 'datos'" );
        if( !nombreArchivo.trim( ).equals( "" ) )
        {
            File archivo = new File( "./datos/" + nombreArchivo );

            if( !archivo.exists( ) )
            {
                System.out.println( "El archivo indicado no existe" );
            }
            else
            {
                try
                {
                    laGasolinera = Gasolinera.cargarEstado( archivo );
                    System.out.println( "Se cargó la gasolinera a partir del archivo " + archivo.getAbsolutePath( ) );
                }
                catch( NumberFormatException e )
                {
                    System.out.println( "Hubo un error leyendo el archivo: hay números con un formato incorrecto" );
                    System.out.println( e.getMessage( ) );
                    e.printStackTrace( );
                }
                catch( FileNotFoundException e )
                {
                    System.out.println( "No se encontró el archivo indicado" );
                    System.out.println( e.getMessage( ) );
                    e.printStackTrace( );
                }
                catch( IOException e )
                {
                    System.out.println( "No se pudo leer el archivo indicado" );
                    System.out.println( e.getMessage( ) );
                    e.printStackTrace( );
                }
            }
        }

    }

    /**
     * Le pide al usuario el nombre de un archivo y luego guarda en el archivo el estado de la gasolinera sobre la que se está trabajando.
     */
    private void guardarGasolinera( )
    {
        if( laGasolinera == null )
        {
            System.out.println( "No hay ninguna gasolinera para guardar" );
        }
        else
        {
            String nombreArchivo = pedirCadenaAlUsuario( "Indique el nombre del archivo donde guardará la gasolinera en su estado actual. El archivo se guardará dentro de la carpeta 'datos'" );
            if( !nombreArchivo.trim( ).equals( "" ) )
            {
                File archivo = new File( "./datos/" + nombreArchivo );

                boolean confirmar = true;
                if( archivo.exists( ) )
                {
                    confirmar = pedirConfirmacionAlUsuario( "El archivo " + nombreArchivo + " ya existe. ¿Está seguro de que quiere reemplazarlo?" );
                }
                if( confirmar )
                {
                    try
                    {
                        laGasolinera.guardarEstado( archivo );
                        System.out.println( "El estado actual de la gasolinera fue salvado en el archivo " + archivo.getAbsolutePath( ) );
                    }
                    catch( IOException e )
                    {
                        System.out.println( "Hubo problemas guardando la información en el archivo" );
                        System.out.println( e.getMessage( ) );
                        e.printStackTrace( );
                    }
                }
            }
        }
    }

    /**
     * Este es el método que se utiliza para iniciar la aplicación
     * @param args
     */
    public static void main( String[] args )
    {
        ConsolaPrincipal c = new ConsolaPrincipal( );
        c.mostrarMenuPrincipal( );
    }

}
