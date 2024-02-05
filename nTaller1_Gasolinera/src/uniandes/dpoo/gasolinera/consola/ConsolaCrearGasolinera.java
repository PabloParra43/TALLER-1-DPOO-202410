package uniandes.dpoo.gasolinera.consola;

import java.util.ArrayList;
import java.util.List;

import uniandes.dpoo.gasolinera.logica.Gasolinera;
import uniandes.dpoo.gasolinera.logica.TipoGasolina;

/**
 * Dentro de esta clase está la lógica necesaria para permitirle a los usuarios crear una nueva gasolinera.
 */
public class ConsolaCrearGasolinera extends ConsolaBasica
{
    /**
     * Opciones que se mostrarán en el menú para crear una gasolinera
     */
    private final String[] opcionesCrearGasolinera = new String[]{ "Nuevo tipo de gasolina", "Nuevo empleado", "Cambiar cantidad surtidores", "Crear gasolinera", "Regresar sin crear una gasolinera" };

    /**
     * Acá se almacenan temporalmente los tipos de gasolina mientras se está configurando una nueva gasolinera
     */
    private List<TipoGasolina> tiposGasolina = new ArrayList<TipoGasolina>( );

    /**
     * Acá se almacenan temporalmente los nombres de los empleados mientras se está configurando una nueva gasolinera
     */
    private List<String> nombresEmpleados = new ArrayList<String>( );

    /**
     * Esta es la cantidad de surtidores que deberá tener la nueva gasolinera cuando se construya
     */
    private int cantidadSurtidores = 1;

    /**
     * Muestra todas las opciones para configurar una nueva gasolinera.
     * @return Retorna una nueva gasolinera creada con la configuración dada, o retorna null si el usuario no quiso crear una nueva gasolinera
     */
    public Gasolinera mostrarOpciones( )
    {
        Gasolinera nuevaGasolinera = null;
        boolean regresar = false;

        while( nuevaGasolinera == null && !regresar )
        {
            mostrarEstadoActual( this.cantidadSurtidores, this.tiposGasolina, this.nombresEmpleados.toArray( new String[]{} ) );

            int opcionSeleccionada = mostrarMenu( "Menú de creación", opcionesCrearGasolinera );
            if( opcionSeleccionada == 1 )
            {
                agregarTipoGasolina( );
            }
            else if( opcionSeleccionada == 2 )
            {
                agregarNombreEmpleado( );
            }
            else if( opcionSeleccionada == 3 )
            {
                cambiarCantidadSurtidores( );
            }
            else if( opcionSeleccionada == 4 )
            {
                boolean todoOk = true;
                if( this.nombresEmpleados.size( ) == 0 )
                {
                    System.out.println( "No se puede crear una gasolinera sin empleados" );
                    todoOk = false;
                }
                if( this.tiposGasolina.size( ) == 0 )
                {
                    System.out.println( "No se puede crear una gasolinera sin tipos de gasolina" );
                    todoOk = false;
                }

                if( todoOk )
                    nuevaGasolinera = new Gasolinera( cantidadSurtidores, tiposGasolina, this.nombresEmpleados.toArray( new String[]{} ) );
            }
            else if( opcionSeleccionada == 5 )
            {
                regresar = true;
            }
        }

        return nuevaGasolinera;
    }

    /**
     * En este método se le preguntan al usuario los detalles de un nuevo tipo de gasolina, y se agrega el nuevo tipo a la lista de tipos temporales.
     * 
     * Este método también valida que la información sea correcta.
     */
    private void agregarTipoGasolina( )
    {
        String nombreTipo = pedirCadenaAlUsuario( "Digite el nombre del nuevo tipo de gasolina" );

        boolean nombreValido = true;
        for( TipoGasolina tipo : tiposGasolina )
        {
            if( tipo.getNombre( ).equals( nombreTipo ) )
                nombreValido = false;
        }

        if( !nombreValido )
        {
            System.out.println( "No puede haber dos tipos de gasolina con el mismo nombre" );
        }
        else
        {
            double cantidad = pedirNumeroAlUsuario( "Digite la cantidad de galones disponibles del nuevo tipo de gasolina" );
            int precio = pedirEnteroAlUsuario( "Digite el precio por galón del nuevo tipo de gasolina" );

            TipoGasolina nuevoTipoGasolina = new TipoGasolina( nombreTipo, precio, cantidad );
            tiposGasolina.add( nuevoTipoGasolina );
        }
    }

    /**
     * En este método se le pregunta al usuario el nombre de un nuevo empleado y se almacena en la lista de nombres temporales
     */
    private void agregarNombreEmpleado( )
    {
        String nuevoNombre = pedirCadenaAlUsuario( "Digite el nombre del nuevo empleado" );
        nombresEmpleados.add( nuevoNombre );
    }

    /**
     * En este método se le pregunta al usuario la cantidad de surtidores que habrá en la gasolinera y se revisa que el número sea válido
     */
    private void cambiarCantidadSurtidores( )
    {
        System.out.println( "Actualmente hay " + cantidadSurtidores + " surtidores en la gasolinera." );
        int cantidad = pedirEnteroAlUsuario( "¿Cuántos surtidores quiere que haya?" );
        if( cantidad < 1 )
        {
            System.out.println( "La cantidad de surtidores no puede ser menor a 1" );
        }
        else
        {
            cantidadSurtidores = cantidad;
        }
    }

}
