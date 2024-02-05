package uniandes.dpoo.gasolinera.consola;

import uniandes.dpoo.gasolinera.logica.Gasolinera;
import uniandes.dpoo.gasolinera.logica.TipoGasolina;

/**
 * Dentro de esta clase está la lógica necesaria para permitirle a los usuarios usar una gasolinera.
 */
public class ConsolaUsarGasolinera extends ConsolaBasica
{
    /**
     * Opciones que se mostrarán en el menú para usar una gasolinera
     */
    private final String[] opcionesUsarGasolinera = new String[]{ "Vender gasolina por volumen", "Vender gasolina por precio", "Regresar" };

    /**
     * La gasolinera sobre la que se realizarán las operaciones
     */
    private Gasolinera laGasolinera;

    /**
     * Crea una nueva ConsolaUsarGasolinera dándole una Gasolinera para que se realicen las operaciones
     * @param gasolinera
     */
    public ConsolaUsarGasolinera( Gasolinera gasolinera )
    {
        this.laGasolinera = gasolinera;
    }

    /**
     * Muestra el menú para usar la gasolinera y ejecuta la opción seleccionada por el usuario.
     * 
     * El menú vuelve a mostrarse hasta que el usuario selecciona la opción de regresar.
     */
    public void mostrarMenu( )
    {
        boolean regresar = false;

        while( !regresar )
        {
            mostrarEstadoActual( laGasolinera.getCantidadSurtidores( ), laGasolinera.getTiposGasolina( ), laGasolinera.getEmpleados( ) );

            int opcionSeleccionada = mostrarMenu( "Opciones de la Gasolinera", opcionesUsarGasolinera );
            if( opcionSeleccionada == 1 )
            {
                venderGasolinaPorVolumen( );
            }
            else if( opcionSeleccionada == 2 )
            {
                venderGasolinaPorPrecio( );
            }
            else if( opcionSeleccionada == 3 )
            {
                regresar = true;
            }
        }
    }

    /**
     * Dentro de este método se llevan a cabo todos los pasos para vender gasolina dada la cantidad que quiere pagar el comprador.
     * 
     * En este método se le pide al usuario la información que se requiere para la venta, y se valida que la información sea correcta.
     */
    private void venderGasolinaPorPrecio( )
    {
        int cantidadSurtidores = laGasolinera.getCantidadSurtidores( );

        int surtidor = pedirEnteroAlUsuario( "Indique el surtidor donde se va a realizar la venta. Debe ser un entero entre 0 y " + ( cantidadSurtidores - 1 ) );
        if( surtidor < 0 || surtidor >= cantidadSurtidores )
        {
            System.out.println( "El número del surtidor no es válido" );
        }
        else
        {
            String nombreTipoGasolina = pedirOpcionAlUsuario( laGasolinera.getTiposGasolina( ) );
            if( nombreTipoGasolina != null )
            {
                TipoGasolina tipo = laGasolinera.getTipoGasolina( nombreTipoGasolina );
                System.out.println( "El precio por galón para la gasolina " + tipo.getNombre( ) + " es " + tipo.getPrecioPorGalon( ) );

                int precio = pedirEnteroAlUsuario( "Indique cuánto quiere pagar por la gasolina. Debe ser un número entero positivo y mayor a 0" );
                if( precio <= 0 )
                {
                    System.out.println( "El número debe ser estrictamente positivo." );
                }
                else
                {
                    int precioReal = laGasolinera.venderGasolinaPorPrecio( nombreTipoGasolina, precio, surtidor );
                    if( precioReal == precio )
                    {
                        System.out.println( "Se realizó una venta por " + precio + " pesos" );
                    }
                    else
                    {
                        System.out.println( "No había suficiente inventario del tipo de gasolina indicado, así que la venta se hizo sólo por " + precioReal + " pesos" );
                    }
                }
            }
        }
    }

    /**
     * Dentro de este método se llevan a cabo todos los pasos para vender gasolina dada la cantidad en galones que quiere el comprador.
     * 
     * En este método se le pide al usuario la información que se requiere para la venta, y se valida que la información sea correcta.
     */
    private void venderGasolinaPorVolumen( )
    {
        int cantidadSurtidores = laGasolinera.getCantidadSurtidores( );

        int surtidor = pedirEnteroAlUsuario( "Indique el surtidor donde se va a realizar la venta. Debe ser un entero entre 0 y " + ( cantidadSurtidores - 1 ) );
        if( surtidor < 0 || surtidor >= cantidadSurtidores )
        {
            System.out.println( "El número del surtidor no es válido" );
        }
        else
        {
            String nombreTipoGasolina = pedirOpcionAlUsuario( laGasolinera.getTiposGasolina( ) );
            if( nombreTipoGasolina != null )
            {
                TipoGasolina tipo = laGasolinera.getTipoGasolina( nombreTipoGasolina );
                System.out.println( "El precio por galón para la gasolina " + tipo.getNombre( ) + " es " + tipo.getPrecioPorGalon( ) );

                double cantidadGasolina = pedirNumeroAlUsuario( "Indique cuántos galores quiere comprar. Debe ser un número positivo y mayor a 0" );
                if( cantidadGasolina <= 0 )
                {
                    System.out.println( "El número debe ser estrictamente positivo." );
                }
                else
                {
                    int precioVenta = laGasolinera.venderGasolinaPorCantidad( nombreTipoGasolina, cantidadGasolina, surtidor );
                    System.out.println( "Se realizó una venta por " + precioVenta + " pesos" );
                }
            }
        }

    }
}
