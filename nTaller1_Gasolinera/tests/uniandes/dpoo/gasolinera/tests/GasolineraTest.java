package uniandes.dpoo.gasolinera.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.gasolinera.logica.Empleado;
import uniandes.dpoo.gasolinera.logica.Gasolinera;
import uniandes.dpoo.gasolinera.logica.Surtidor;
import uniandes.dpoo.gasolinera.logica.TipoGasolina;

public class GasolineraTest
{
    private static final double CANTIDAD_CORRIENTE = 100;
    private static final int VALOR_CORRIENTE = 14500;
    private static final String CORRIENTE = "corriente";

    private static final double CANTIDAD_PLUS = 80;
    private static final int VALOR_PLUS = 16251;
    private static final String PLUS = "plus";

    private static final double CANTIDAD_EXTRA = 50;
    private static final int VALOR_EXTRA = 21090;
    private static final String EXTRA = "extra";

    private static final String ALICE = "Alice";
    private static final String BOB = "Bob";
    private static final String CHARLY = "Charly";

    private Gasolinera g1;
    private Gasolinera g2;

    @BeforeEach
    void setUp( ) throws Exception
    {
        crearGasolinera1( );
        crearGasolinera2( );
    }

    private void crearGasolinera1( )
    {
        int cantidadSurtidores = 1;
        List<TipoGasolina> tipos = new LinkedList<TipoGasolina>( );
        tipos.add( new TipoGasolina( CORRIENTE, VALOR_CORRIENTE, CANTIDAD_CORRIENTE ) );
        String[] empleados = new String[]{ ALICE };

        g1 = new Gasolinera( cantidadSurtidores, tipos, empleados );
    }

    private void crearGasolinera2( )
    {
        int cantidadSurtidores = 4;
        List<TipoGasolina> tipos = new LinkedList<TipoGasolina>( );
        tipos.add( new TipoGasolina( CORRIENTE, VALOR_CORRIENTE, CANTIDAD_CORRIENTE ) );
        tipos.add( new TipoGasolina( PLUS, VALOR_PLUS, CANTIDAD_PLUS ) );
        tipos.add( new TipoGasolina( EXTRA, VALOR_EXTRA, CANTIDAD_EXTRA ) );

        String[] empleados = new String[]{ ALICE, BOB, CHARLY };

        g2 = new Gasolinera( cantidadSurtidores, tipos, empleados );
    }

    @Test
    void testConstructor1( )
    {
        // Verificar que haya la cantidad correcta de surtidores
        assertEquals( 1, g1.getCantidadSurtidores( ), "La cantidad de surtidores es incorrecta" );

        // Verificar que los tipos de gasolina en los surtidores sean correctos
        Surtidor s0 = g1.getSurtidor( 0 );
        String[] nombresTipos = s0.getTiposGasolina( );
        assertEquals( 1, nombresTipos.length, "La cantidad de tipos de gasolina no es correcta" );
        assertEquals( CORRIENTE, nombresTipos[ 0 ], "El nombre del tipo de gasolina no es correcto" );

        // Verificar que las cantidades de gasolina disponibles inicialmente sean correctas
        TipoGasolina gasolinaCorriente = g1.getTipoGasolina( CORRIENTE );
        assertEquals( CANTIDAD_CORRIENTE, gasolinaCorriente.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

        // Verificar que el tipo de gasolina en la gasolinera y en el surtidor sean el mismo objeto y no copias
        TipoGasolina tipoEnSurtidor = s0.getTipoGasolina( CORRIENTE );
        TipoGasolina tipoEnGasolinera = g1.getTipoGasolina( CORRIENTE );

        assertSame( tipoEnSurtidor, tipoEnGasolinera, "Los tipos de gasolina en la Gasolinera y en el Surtidor debería ser exactamente el mismo objeto" );

        // Verificar que haya objetos inicializados correctamente para los empleados
        Empleado e = g1.getEmpleado( ALICE );
        assertNotNull( e, "El empleado debería existir" );
        assertEquals( 0, e.getCantidadDinero( ), "El empleado no debería tener dinero inicialmente" );
    }

    @Test
    void testConstructor2( )
    {
        Set<String> nombresEsperados = new HashSet<String>( );
        nombresEsperados.add( CORRIENTE );
        nombresEsperados.add( PLUS );
        nombresEsperados.add( EXTRA );

        // Verificar que haya la cantidad correcta de surtidores
        assertEquals( 4, g2.getCantidadSurtidores( ), "La cantidad de surtidores es incorrecta" );

        // Verificar que los tipos de gasolina en cada surtidor sean correctos

        for( int i = 0; i < 4; i++ )
        {
            Surtidor surtidor = g2.getSurtidor( i );
            String[] nombresTipos = surtidor.getTiposGasolina( );
            assertEquals( 3, nombresTipos.length, "La cantidad de tipos de gasolina no es correcta" );

            Set<String> nombresEncontrados = new HashSet<String>( );

            for( String nombre : nombresTipos )
            {
                assertTrue( nombresEsperados.contains( nombre ), "El tipo de gasolina en el surtidor era uno de los esperados" );
                assertFalse( nombresEncontrados.contains( nombre ), "El tipo de gasolina está repetido" );
                nombresEncontrados.add( nombre );
            }

            // Verificar que el tipo de gasolina en la gasolinera y en el surtidor sean el mismo objeto y no copias
            for( String nombreTipo : nombresEsperados )
            {
                TipoGasolina tipoEnSurtidor = surtidor.getTipoGasolina( nombreTipo );
                TipoGasolina tipoEnGasolinera = g2.getTipoGasolina( nombreTipo );

                assertSame( tipoEnSurtidor, tipoEnGasolinera, "Los tipos de gasolina en la Gasolinera y en el Surtidor debería ser exactamente el mismo objeto" );
            }
        }

        // Verificar que las cantidades de gasolina disponibles inicialmente sean correctas
        TipoGasolina gasolinaCorriente = g2.getTipoGasolina( CORRIENTE );
        assertEquals( CANTIDAD_CORRIENTE, gasolinaCorriente.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

        TipoGasolina gasolinaPlus = g2.getTipoGasolina( PLUS );
        assertEquals( CANTIDAD_PLUS, gasolinaPlus.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

        TipoGasolina gasolinaExtra = g2.getTipoGasolina( EXTRA );
        assertEquals( CANTIDAD_EXTRA, gasolinaExtra.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

    }

    @Test
    public void testCargarEstado( )
    {
        // Cargar la gasolinera desde un archivo
        try
        {
            g2 = Gasolinera.cargarEstado( new File( "./datostest/prueba.gas" ) );
        }
        catch( Exception e )
        {
            fail( "No debería haber entrado acá: " + e.getMessage( ) );
        }

        Set<String> nombresEsperados = new HashSet<String>( );
        nombresEsperados.add( CORRIENTE );
        nombresEsperados.add( PLUS );
        nombresEsperados.add( EXTRA );

        // Verificar que haya la cantidad correcta de surtidores
        assertEquals( 4, g2.getCantidadSurtidores( ), "La cantidad de surtidores es incorrecta" );

        // Verificar que los tipos de gasolina en cada surtidor sean correctos
        for( int i = 0; i < 4; i++ )
        {
            Surtidor surtidor = g2.getSurtidor( i );
            String[] nombresTipos = surtidor.getTiposGasolina( );
            assertEquals( 3, nombresTipos.length, "La cantidad de tipos de gasolina no es correcta" );

            Set<String> nombresEncontrados = new HashSet<String>( );

            for( String nombre : nombresTipos )
            {
                assertTrue( nombresEsperados.contains( nombre ), "El tipo de gasolina en el surtidor era uno de los esperados" );
                assertFalse( nombresEncontrados.contains( nombre ), "El tipo de gasolina está repetido" );
                nombresEncontrados.add( nombre );
            }

            // Verificar que el tipo de gasolina en la gasolinera y en el surtidor sean el mismo objeto y no copias
            for( String nombreTipo : nombresEsperados )
            {
                TipoGasolina tipoEnSurtidor = surtidor.getTipoGasolina( nombreTipo );
                TipoGasolina tipoEnGasolinera = g2.getTipoGasolina( nombreTipo );

                assertSame( tipoEnSurtidor, tipoEnGasolinera, "Los tipos de gasolina en la Gasolinera y en el Surtidor debería ser exactamente el mismo objeto" );
            }

            // Verificar que la cantidad de gasolina vendida en el surtidor sea la correcta
            if( i == 0 )
            {
                assertEquals( 1, surtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 1, surtidor.getGalonesVendidos( PLUS ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 1, surtidor.getGalonesVendidos( EXTRA ), "La cantidad de galones vendidos es incorrecta" );
            }
            else if( i == 1 )
            {
                assertEquals( 2.2, surtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 2.2, surtidor.getGalonesVendidos( PLUS ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 2.2, surtidor.getGalonesVendidos( EXTRA ), "La cantidad de galones vendidos es incorrecta" );
            }
            else if( i == 2 )
            {
                assertEquals( 3, surtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 0, surtidor.getGalonesVendidos( PLUS ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 0, surtidor.getGalonesVendidos( EXTRA ), "La cantidad de galones vendidos es incorrecta" );
            }
            else if( i == 3 )
            {
                assertEquals( 4, surtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 0, surtidor.getGalonesVendidos( PLUS ), "La cantidad de galones vendidos es incorrecta" );
                assertEquals( 4, surtidor.getGalonesVendidos( EXTRA ), "La cantidad de galones vendidos es incorrecta" );
            }

            // Verificar que los empleados asignados sean los correctos
            if( i == 0 )
            {
                assertSame( g2.getEmpleado( ALICE ), surtidor.getEmpleadoAsignado( ), "El empleado asignado al surtidor no es el correcto" );
            }
            else if( i == 1 )
            {
                assertSame( g2.getEmpleado( BOB ), surtidor.getEmpleadoAsignado( ), "El empleado asignado al surtidor no es el correcto" );
            }
            else if( i == 2 )
            {
                assertSame( g2.getEmpleado( CHARLY ), surtidor.getEmpleadoAsignado( ), "El empleado asignado al surtidor no es el correcto" );
            }
            else if( i == 3 )
            {
                assertSame( g2.getEmpleado( BOB ), surtidor.getEmpleadoAsignado( ), "El empleado asignado al surtidor no es el correcto" );
            }
        }

        // Verificar que las cantidades de gasolina disponibles inicialmente sean correctas
        TipoGasolina gasolinaCorriente = g2.getTipoGasolina( CORRIENTE );
        assertEquals( CANTIDAD_CORRIENTE, gasolinaCorriente.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

        TipoGasolina gasolinaPlus = g2.getTipoGasolina( PLUS );
        assertEquals( CANTIDAD_PLUS, gasolinaPlus.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

        TipoGasolina gasolinaExtra = g2.getTipoGasolina( EXTRA );
        assertEquals( CANTIDAD_EXTRA, gasolinaExtra.getCantidadDisponible( ), "La cantidad disponible no es correcta" );

    }

    @Test
    public void testGetEmpleado( )
    {
        Empleado e1 = g2.getEmpleado( ALICE );
        assertNotNull( e1, "El empleado debería existir" );
        assertEquals( ALICE, e1.getNombre( ), "El empleado tiene el nombre incorrecto" );

        Empleado e2 = g2.getEmpleado( BOB );
        assertNotNull( e2, "El empleado debería existir" );
        assertEquals( BOB, e2.getNombre( ), "El empleado tiene el nombre incorrecto" );

        Empleado e3 = g2.getEmpleado( CHARLY );
        assertNotNull( e3, "El empleado debería existir" );
        assertEquals( CHARLY, e3.getNombre( ), "El empleado tiene el nombre incorrecto" );

        assertNull( g2.getEmpleado( "INEXISTENTE" ), "Si se busca un empleado inexistente debe retornar null" );
    }

    @Test
    public void testVenderGasolinaPorPrecioSencillo( )
    {
        int precioDeseado = 30000;
        int precioPagado = g2.venderGasolinaPorPrecio( CORRIENTE, precioDeseado, 1 );

        assertEquals( precioDeseado, precioPagado, "El precio pagado no es correcto" );

        Empleado empleadoSurtidor = g2.getSurtidor( 1 ).getEmpleadoAsignado( );
        assertEquals( precioPagado, empleadoSurtidor.getCantidadDinero( ), "La cantidad de dinero que tiene el empleado asignado al surtidor no es correcta" );

        double galonesVendidos = g2.getSurtidor( 1 ).getGalonesVendidos( CORRIENTE );
        double galonesEsperados = precioPagado / ( double )VALOR_CORRIENTE;
        assertEquals( galonesEsperados, galonesVendidos, 0.01, "La cantidad de galones vendidos en el surtidor no es correcta" );

        double galonesDisponibles = g2.getTipoGasolina( CORRIENTE ).getCantidadDisponible( );
        assertEquals( CANTIDAD_CORRIENTE - galonesDisponibles, galonesVendidos, 0.01, "El inventario del tipo de gasolina no se actualizó correctamente" );
    }

    @Test
    public void testVenderGasolinaPorCantidadSencillo( )
    {
        double cantidadDeseada = 10;
        int precioPagado = g2.venderGasolinaPorCantidad( CORRIENTE, cantidadDeseada, 1 );
        int precioEsperado = ( int ) ( VALOR_CORRIENTE * cantidadDeseada );

        assertEquals( precioEsperado, precioPagado, "El precio pagado no es correcto" );

        Empleado empleadoSurtidor = g2.getSurtidor( 1 ).getEmpleadoAsignado( );
        assertEquals( precioPagado, empleadoSurtidor.getCantidadDinero( ), "La cantidad de dinero que tiene el empleado asignado al surtidor no es correcta" );

        double galonesVendidos = g2.getSurtidor( 1 ).getGalonesVendidos( CORRIENTE );
        double galonesEsperados = precioPagado / ( double )VALOR_CORRIENTE;
        assertEquals( galonesEsperados, galonesVendidos, 0.01, "La cantidad de galones vendidos en el surtidor no es correcta" );

        double galonesDisponibles = g2.getTipoGasolina( CORRIENTE ).getCantidadDisponible( );
        assertEquals( CANTIDAD_CORRIENTE - galonesDisponibles, galonesVendidos, 0.01, "El inventario del tipo de gasolina no se actualizó correctamente" );
    }

    @Test
    public void testVenderGasolinaPorCantidadInsuficiente( )
    {
        double cantidadDeseada = 110;
        double cantidadRealEsperada = 100;
        int precioPagado = g2.venderGasolinaPorCantidad( CORRIENTE, cantidadDeseada, 1 );
        int precioEsperado = ( int ) ( VALOR_CORRIENTE * cantidadRealEsperada );

        assertEquals( precioEsperado, precioPagado, "El precio pagado no es correcto" );

        Empleado empleadoSurtidor = g2.getSurtidor( 1 ).getEmpleadoAsignado( );
        assertEquals( precioPagado, empleadoSurtidor.getCantidadDinero( ), "La cantidad de dinero que tiene el empleado asignado al surtidor no es correcta" );

        double galonesVendidos = g2.getSurtidor( 1 ).getGalonesVendidos( CORRIENTE );
        double galonesEsperados = precioPagado / ( double )VALOR_CORRIENTE;
        assertEquals( galonesEsperados, galonesVendidos, 0.01, "La cantidad de galones vendidos en el surtidor no es correcta" );

        double galonesDisponibles = g2.getTipoGasolina( CORRIENTE ).getCantidadDisponible( );
        assertEquals( 0, galonesDisponibles, 0.01, "El inventario del tipo de gasolina no se actualizó correctamente" );
    }

}
