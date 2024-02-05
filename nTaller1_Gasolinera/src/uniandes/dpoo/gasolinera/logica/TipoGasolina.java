package uniandes.dpoo.gasolinera.logica;

import uniandes.dpoo.gasolinera.exceptions.GasolinaInsuficienteException;

/**
 * Encapsula la información sobre un tipo de gasolina disponible en la gasolinera.
 * 
 * Esta clase será la encargada de saber cuánta gasolina de un determinado tipo hay aún disponible en la gasolinera
 */
public class TipoGasolina
{
    private String nombre;

    private int precioPorGalon;

    private double cantidadDisponible;

    public TipoGasolina( String nombre, int precioPorGalon, double cantidadDisponible )
    {
        this.nombre = nombre;
        this.precioPorGalon = precioPorGalon;
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getNombre( )
    {
        return nombre;
    }

    public int getPrecioPorGalon( )
    {
        return precioPorGalon;
    }

    public double getCantidadDisponible( )
    {
        return cantidadDisponible;
    }

    public void despacharGasolina( double cantidadSolicitada ) throws GasolinaInsuficienteException
    {
        // Verificar si hay suficiente gasolina y lanzar una excepción si no alcanza
        if( cantidadSolicitada > cantidadDisponible )
        {
            GasolinaInsuficienteException ex = new GasolinaInsuficienteException( nombre, cantidadDisponible, cantidadSolicitada );
            throw ex;
        }

        // Reducir la cantidad disponible
        cantidadDisponible -= cantidadSolicitada;
    }

    @Override
    public String toString( )
    {
        return this.nombre;
    }
}
