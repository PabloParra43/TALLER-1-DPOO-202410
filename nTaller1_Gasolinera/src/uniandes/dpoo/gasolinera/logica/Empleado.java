package uniandes.dpoo.gasolinera.logica;

/**
 * Esta clase representa a un empleado de la gasolinería que recauda el dinero producto de las ventas
 */
public class Empleado
{
    /**
     * El nombre del empleado
     */
    private String nombre;

    /**
     * La cantidad de dinero que ha recogido el empleado
     */
    private int cantidadDinero;

    /**
     * Construye un nuevo empleado con el nombre dado e inicializa la cantidad de dinero en 0
     * @param nombre El nombre del empleado. Debería ser único en la gasolinera
     */
    public Empleado( String nombre )
    {
        this.nombre = nombre;
        this.cantidadDinero = 0;
    }

    public String getNombre( )
    {
        return nombre;
    }

    public int getCantidadDinero( )
    {
        return cantidadDinero;
    }

    /**
     * Aumenta la cantidad de dinero que tiene el empleado
     * @param dinero La cantidad de dinero que el empleado recibió
     */
    public void agregarDinero( int dinero )
    {
        this.cantidadDinero += dinero;
    }
}
