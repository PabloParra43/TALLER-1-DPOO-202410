package uniandes.dpoo.gasolinera.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.gasolinera.exceptions.GasolinaInsuficienteException;
import uniandes.dpoo.gasolinera.utils.Sorteo;

/**
 * Esta clase tiene la información de una Gasolinera incluyendo los tipos de gasolina, los empleados y los surtidores
 */
public class Gasolinera
{
    // ************************************************************************
    // Atributos
    // ************************************************************************

    /**
     * Un arreglo con los surtidores que hay en la gasolinera
     */
    private Surtidor[] surtidores;

    /**
     * Un mapa de los tipos de gasolina en venta: las llaves son el nombre del tipo de gasolina
     */
    private Map<String, TipoGasolina> tiposGasolina;

    /**
     * Un mapa con los empleados de la gasolinea: las llaves son el nombre del empleado
     */
    private Map<String, Empleado> empleados;

    // ************************************************************************
    // Constructores
    // ************************************************************************

    /**
     * Construye una nueva gasolinera con la información entregada.
     * 
     * Inicialmente no se va a haber vendido ningún galón de ningún tipo de gasolina y los empleados no habrán recaudado dinero.
     * 
     * @param cantidadSurtidores La cantidad de surtidores en la gasolinera
     * @param listaTiposGasolina Una lista de TipoGasolina con la información de cada tipo en venta en la gasolinería
     * @param nombresEmpleados Un arreglo con los nombres de los empleados que trabajan en la gasolinera
     */
    public Gasolinera( int cantidadSurtidores, List<TipoGasolina> listaTiposGasolina, String[] nombresEmpleados )
    {
        // Guardar los tipos de gasolina en un mapa organizado con el nombre
        this.tiposGasolina = new HashMap<String, TipoGasolina>( );
        for( TipoGasolina tipo : listaTiposGasolina )
        {
            this.tiposGasolina.put( tipo.getNombre( ), tipo );
        }

        // Construir los empleados de la gasolinera partiendo con los nombres
        this.empleados = new HashMap<String, Empleado>( );
        for( String nombre : nombresEmpleados )
        {
            this.empleados.put( nombre, new Empleado( nombre ) );
        }

        // Construir los surtidores de la gasolinera usando los tipos de gasolina y asignarle empleados aleatoriamente
        this.surtidores = new Surtidor[cantidadSurtidores];
        for( int i = 0; i < cantidadSurtidores; i++ )
        {
            Empleado[] dummy = new Empleado[]{}; // Este arreglo se usa únicamente para indicarle el tipo genérico al método toArray de Collection
            Empleado seleccionado = Sorteo.seleccionarAlAzar( empleados.values( ).toArray( dummy ) );
            this.surtidores[ i ] = new Surtidor( tiposGasolina, seleccionado );
        }

    }

    /**
     * Construye una nueva gasolinera con la información entregada.
     * 
     * Este constructor asume que los objetos están correctamente enlazados: los tipos de gasolina en los surtidores son los mismos que estarán en la gasolinera, y los
     * empleados asignados a los surtidores son los mismos que estarán en la gasolinera.
     * 
     * @param surtidores Los surtidores de la gasolinera
     * @param tiposGasolina Una coleción de TipoGasolina con la información de cada tipo en venta en la gasolinería
     * @param empleados Una colección con los empleados que trabajan en la gasolinera
     */
    private Gasolinera( List<Surtidor> surtidores, Collection<TipoGasolina> tiposGasolina, Collection<Empleado> empleados )
    {
        // Guardar los tipos de gasolina en un mapa organizado con el nombre
        this.tiposGasolina = new HashMap<String, TipoGasolina>( );
        for( TipoGasolina tipo : tiposGasolina )
        {
            this.tiposGasolina.put( tipo.getNombre( ), tipo );
        }

        // Construir el mapa de los empleados de la gasolinera
        this.empleados = new HashMap<String, Empleado>( );
        for( Empleado empleado : empleados )
        {
            this.empleados.put( empleado.getNombre( ), empleado );
        }

        // Organizar los surtidores de la gasolinera en el arreglo de surtidores
        this.surtidores = new Surtidor[surtidores.size( )];
        for( int i = 0; i < surtidores.size( ); i++ )
        {
            this.surtidores[ i ] = surtidores.get( i );
        }
    }

    // ************************************************************************
    // Métodos
    // ************************************************************************

    /**
     * Retorna la cantidad de surtidores que hay en la gasolinera
     * @return Cantidad de surtidores
     */
    public int getCantidadSurtidores( )
    {
        return surtidores.length;
    }

    public Surtidor getSurtidor( int numSurtidor )
    {
        return surtidores[ numSurtidor ];
    }

    public TipoGasolina getTipoGasolina( String nombreTipoGasolina )
    {
        return tiposGasolina.get( nombreTipoGasolina );
    }

    public Collection<TipoGasolina> getTiposGasolina( )
    {
        return tiposGasolina.values( );
    }

    public Empleado getEmpleado( String nombreEmpleado )
    {
        return empleados.get( nombreEmpleado );
    }

    public Collection<Empleado> getEmpleados( )
    {
        return empleados.values( );
    }

    /**
     * Le vende a un cliente una cierta cantidad de gasolina, dada la cantidad de gasolina que quiere el cliente.
     * 
     * Si en la gasolinera no hay la cantidad suficiente del tipo de gasolina, le vende todo lo que haya de esa gasolina.
     * 
     * Después de realizada la transacción, debe haber quedado actualizado el inventario de gasolina (en el TipoGasolina), la cantidad entregada en el surtidor y la cantidad
     * de dinero que tiene el empleado que atiende en el surtidor.
     * @param nombreTipoGasolina El tipo de gasolina que quiere el cliente
     * @param cantidadSolicitada La cantidad en galones que quiere el cliente
     * @param numeroSurtidor El número del surtidor donde están atendiendo al cliente. Los surtidores están numerados desde 0.
     * @return El precio de la gasolina que se le vendió al cliente (no necesariamente es la cantidad solicitada)
     */
    public int venderGasolinaPorCantidad( String nombreTipoGasolina, double cantidadSolicitada, int numeroSurtidor )
    {
        Surtidor elSurtidor = surtidores[ numeroSurtidor ];
        TipoGasolina tipo = tiposGasolina.get( nombreTipoGasolina );

        double cantidadEntregada = 0;
        try
        {
            // Actualizar la cantidad de gasolina disponible de ese tipo
            tipo.despacharGasolina( cantidadSolicitada );
            cantidadEntregada = cantidadSolicitada;
        }
        catch( GasolinaInsuficienteException e )
        {
            System.out.println( e.getMessage( ) );
            cantidadEntregada = e.getCantidadDisponible( );
            try
            {
                tipo.despacharGasolina( cantidadEntregada );
            }
            catch( GasolinaInsuficienteException e1 )
            {
                // Nunca debería entrar acá
                e1.printStackTrace( );
            }
        }

        int precio = elSurtidor.venderGasolina( nombreTipoGasolina, cantidadEntregada );
        return precio;
    }

    /**
     * Le vende a un cliente una cierta cantidad de gasolina, dado el precio que quiere pagar el cliente.
     * 
     * Si en la gasolinera no hay la cantidad suficiente del tipo de gasolina, le vende todo lo que haya de esa gasolina.
     * 
     * Después de realizada la transacción, debe haber quedado actualizado el inventario de gasolina (en el TipoGasolina), la cantidad entregada en el surtidor y la cantidad
     * de dinero que tiene el empleado que atiende en el surtidor.
     * @param nombreTipoGasolina El tipo de gasolina que quiere el cliente
     * @param valorSolicitado El precio que quiere pagar el cliente por la gasolina que se le entregue
     * @param numeroSurtidor El número del surtidor donde están atendiendo al cliente. Los surtidores están numerados desde 0.
     * @return El precio de la gasolina que se le vendió al cliente (no necesariamente es el valor solicitado)
     */
    public int venderGasolinaPorPrecio( String nombreTipoGasolina, int valorSolicitado, int numeroSurtidor )
    {
        TipoGasolina tipo = tiposGasolina.get( nombreTipoGasolina );
        double cantidadSolicitada = valorSolicitado / ( double )tipo.getPrecioPorGalon( );

        return venderGasolinaPorCantidad( nombreTipoGasolina, cantidadSolicitada, numeroSurtidor );
    }

    /**
     * Guarga la información actual de la gasolinera en un archivo.
     * 
     * Si el archivo ya existe, se sobreescribe.
     * @param archivo El archivo donde se guardará la información
     * @throws IOException Se lanza esta excepción si hay problemas escribiendo en el archivo
     */
    public void guardarEstado( File archivo ) throws IOException
    {
        PrintWriter writer = new PrintWriter( archivo );

        // Guardar la información de los tipos de gasolina
        for( TipoGasolina tipo : tiposGasolina.values( ) )
        {
            writer.println( "tipo:" + tipo.getNombre( ) + ":" + tipo.getPrecioPorGalon( ) + ":" + tipo.getCantidadDisponible( ) );
        }

        // Guardar la información de los surtidores
        for( int i = 0; i < surtidores.length; i++ )
        {
            Surtidor surtidor = surtidores[ i ];
            writer.print( "surtidor:" + surtidor.getEmpleadoAsignado( ).getNombre( ) );
            for( TipoGasolina tipo : tiposGasolina.values( ) )
            {
                writer.print( ":" + tipo.getNombre( ) + ":" + surtidor.getGalonesVendidos( tipo.getNombre( ) ) );
            }
            writer.println( );
        }

        // Guardar la información de los empleados
        for( Empleado emp : empleados.values( ) )
        {
            writer.println( "empleado:" + emp.getNombre( ) + ":" + emp.getCantidadDinero( ) );
        }

        writer.close( );
    }

    /**
     * Carga toda la información de una gasolinera a partir de un archivo y retorna una nueva Gasolinera inicializada con esa información
     * @param archivo El archivo que contiene la información que se va a cargar
     * @return Una nueva gasolinera con su estado inicializado con la información del archivo
     * @throws FileNotFoundException Se lanza esta excepción si el archivo no se encuentra
     * @throws IOException Se lanza esta excepción si el archivo no se puede leer
     * @throws NumberFormatException Se lanza esta excepción si alguno de los números dentro del archivo tiene el formato equivocado
     */
    public static Gasolinera cargarEstado( File archivo ) throws FileNotFoundException, IOException, NumberFormatException
    {
        Map<String, TipoGasolina> tipos = new HashMap<String, TipoGasolina>( );
        Map<String, Empleado> empleados = new HashMap<String, Empleado>( );
        List<Surtidor> surtidores = new LinkedList<Surtidor>( );

        BufferedReader br = new BufferedReader( new FileReader( archivo ) );
        String line = br.readLine( );
        while( line != null )
        {
            String[] partes = line.split( ":" );
            if( partes[ 0 ].equals( "tipo" ) )
            {
                String nombre = partes[ 1 ];
                int precio = Integer.parseInt( partes[ 2 ] );
                double cantidad = Double.parseDouble( partes[ 3 ] );
                tipos.put( nombre, new TipoGasolina( nombre, precio, cantidad ) );
            }
            else if( partes[ 0 ].equals( "surtidor" ) )
            {
                String nombreEmpleado = partes[ 1 ];
                if( !empleados.containsKey( nombreEmpleado ) )
                {
                    empleados.put( nombreEmpleado, new Empleado( nombreEmpleado ) );
                }
                Empleado empleadoAsignado = empleados.get( nombreEmpleado );
                Surtidor nuevoSurtidor = new Surtidor( tipos, empleadoAsignado );
                for( int pos = 2; pos < partes.length; pos += 2 )
                {
                    String tipo = partes[ pos ];
                    double cantidad = Double.parseDouble( partes[ pos + 1 ] );
                    nuevoSurtidor.cambiarGalonesVendidos( tipo, cantidad );
                }
                surtidores.add( nuevoSurtidor );
            }
            else if( partes[ 0 ].equals( "empleado" ) )
            {
                String nombreEmpleado = partes[ 1 ];
                int dinero = Integer.parseInt( partes[ 2 ] );
                if( !empleados.containsKey( nombreEmpleado ) )
                {
                    empleados.put( nombreEmpleado, new Empleado( nombreEmpleado ) );
                }
                Empleado nuevoEmpleado = empleados.get( nombreEmpleado );
                nuevoEmpleado.agregarDinero( dinero );
            }

            line = br.readLine( );
        }
        br.close( );

        Gasolinera nuevaGasolinera = new Gasolinera( surtidores, tipos.values( ), empleados.values( ) );
        return nuevaGasolinera;
    }

}
