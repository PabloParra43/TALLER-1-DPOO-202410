package uniandes.dpoo.gasolinera.logica;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Esta clase representa a un surtidor de gasolina en la gasolinera.
 * 
 * Todos los surtidores entregan gasolina de los mismos tanques, pero cada surtidor lleva la cuenta de cuánto ha entregado, de cada tipo de gasolina.
 *
 */
public class Surtidor
{

    /**
     * Un mapa donde se lleva la cuenta de los galones de gasolina entregados por tipo de gasolina.
     * 
     * La llave del mapa es el nombre del tipo de gasolina, y el valor es la cantidad de galones.
     */
    private Map<String, Double> galonesVendidos;

    /**
     * Un mapa de los tipos de gasolina en venta: las llaves son el nombre del tipo de gasolina
     */
    private Map<String, TipoGasolina> tiposGasolina;

    /**
     * El empleado que está asignado para atender el surtidor
     */
    private Empleado empleadoAsignado;

    public Surtidor( Map<String, TipoGasolina> tiposGasolina, Empleado empleado )
    {
        this.empleadoAsignado = empleado;
        this.tiposGasolina = tiposGasolina;

        galonesVendidos = new HashMap<String, Double>( );
        for( String nombreTipo : tiposGasolina.keySet( ) )
        {
            galonesVendidos.put( nombreTipo, new Double( 0 ) );
        }
    }

    public Empleado getEmpleadoAsignado( )
    {
        return empleadoAsignado;
    }

    /**
     * Retorna la cantidad de galones de gasolina vendidos en el surtidor según el tipo de gasolina.
     * @param nombreTipoGasolina El nombre del tipo de gasolina
     * @return La cantidad de galones, o null si el tipo de gasolina no se ofrece en el surtidor
     */
    public Double getGalonesVendidos( String nombreTipoGasolina )
    {
        return galonesVendidos.get( nombreTipoGasolina );
    }

    /**
     * Cambia la cantidad de galones vendidos de un cierto tipo de gasolina
     * @param nombreTipoGasolina El nombre del tipo de gasolina
     * @param cantidad La nueva cantidad que estará registrada en el surtidor
     */
    public void cambiarGalonesVendidos( String nombreTipoGasolina, double cantidad )
    {
        galonesVendidos.put( nombreTipoGasolina, cantidad );
    }

    /**
     * Retorna un arreglo con los nombres de los tipos de gasolina disponibles en el surtidor
     * @return Un arreglo con un elemento por cada tipo de gasolina
     */
    public String[] getTiposGasolina( )
    {
        Set<String> conjuntoLlaves = tiposGasolina.keySet( );
        String[] dummy = new String[]{}; // Este arreglo vacío sirve solo para indicarle el tipo al método toArray de Collection
        return conjuntoLlaves.toArray( dummy );
    }

    /**
     * Retorna el objeto con la descripción completa del tipo de gasolina seleccionado
     * @param nombreTipoGasolina El nombre del tipo de gasolina
     * @return Un objeto de tipo TipoGasolina o null si el nombre no existe
     */
    public TipoGasolina getTipoGasolina( String nombreTipoGasolina )
    {
        return tiposGasolina.get( nombreTipoGasolina );
    }

    /**
     * Este método registra una venta de gasolina en el surtidor. El método debe hacer dos acciones:
     * 
     * 1. Calcular el precio de la gasolina vendida y registrar que el empleado tiene esa cantidad de dinero
     * 
     * 2. Actualizar la cantidad de gasolina vendida en el surtidor
     * 
     * @param nombreTipoGasolina El tipo de gasolina que se vendió
     * @param cantidadEntregada La cantidad de galones de gasolina que se le entregaron al cliente
     * @return El precio de la compra, redondeado al entero más cercano
     */
    public int venderGasolina( String nombreTipoGasolina, double cantidadEntregada )
    {
        // Calcular el precio de la gasolina vendida y registrar que el empleado tiene esa cantidad de dinero
        TipoGasolina tipo = tiposGasolina.get( nombreTipoGasolina );
        int precio = ( int )Math.round( tipo.getPrecioPorGalon( ) * cantidadEntregada );
        empleadoAsignado.agregarDinero( precio );

        // Actualizar la cantidad de gasolina vendida en el surtidor
        double cantidadAnterior = galonesVendidos.get( nombreTipoGasolina );
        galonesVendidos.put( nombreTipoGasolina, cantidadAnterior + cantidadEntregada );

        return precio;
    }

}
