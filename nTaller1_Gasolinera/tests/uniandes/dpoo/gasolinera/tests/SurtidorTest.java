package uniandes.dpoo.gasolinera.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.gasolinera.logica.Empleado;
import uniandes.dpoo.gasolinera.logica.Surtidor;
import uniandes.dpoo.gasolinera.logica.TipoGasolina;

class SurtidorTest
{
    private static final double CANTIDAD_CORRIENTE = 1000;
    private static final int VALOR_CORRIENTE = 14500;
    private static final String CORRIENTE = "corriente";

    private static final int CANTIDAD_PLUS = 800;
    private static final int VALOR_PLUS = 16251;
    private static final String PLUS = "plus";

    private static final int CANTIDAD_EXTRA = 500;
    private static final int VALOR_EXTRA = 21090;
    private static final String EXTRA = "extra";

    private Surtidor elSurtidor;

    @BeforeEach
    void setUp( ) throws Exception
    {
        Map<String, TipoGasolina> tipos = new HashMap<String, TipoGasolina>( );
        tipos.put( CORRIENTE, new TipoGasolina( CORRIENTE, VALOR_CORRIENTE, CANTIDAD_CORRIENTE ) );
        tipos.put( PLUS, new TipoGasolina( PLUS, VALOR_PLUS, CANTIDAD_PLUS ) );
        tipos.put( EXTRA, new TipoGasolina( EXTRA, VALOR_EXTRA, CANTIDAD_EXTRA ) );

        Empleado empleado = new Empleado( "BOB" );
        elSurtidor = new Surtidor( tipos, empleado );
    }

    @Test
    public void testGetTiposGasolina( )
    {
        String[] tipos = elSurtidor.getTiposGasolina( );
        assertEquals( 3, tipos.length, "La cantidad de tipos de gasolina no es la correcta" );

        List<String> listaTipos = Arrays.asList( tipos );
        assertTrue( listaTipos.contains( CORRIENTE ), "El nombre del tipo de gasolina CORRIENTE no está en los tipos retornados" );
        assertTrue( listaTipos.contains( PLUS ), "El nombre del tipo de gasolina PLUS no está en los tipos retornados" );
        assertTrue( listaTipos.contains( EXTRA ), "El nombre del tipo de gasolina EXTRA no está en los tipos retornados" );
    }

    @Test
    public void testGetTipoGasolina( )
    {
        assertNull( elSurtidor.getTipoGasolina( "Diesel" ), "Debería retornar null si el tipo de gasolina no existe" );

        assertEquals( CORRIENTE, elSurtidor.getTipoGasolina( CORRIENTE ).getNombre( ), "No retornó el tipo correcto" );
        assertEquals( PLUS, elSurtidor.getTipoGasolina( PLUS ).getNombre( ), "No retornó el tipo correcto" );
        assertEquals( EXTRA, elSurtidor.getTipoGasolina( EXTRA ).getNombre( ), "No retornó el tipo correcto" );
    }

    @Test
    void testVenderGasolinaSimple( )
    {
        double cantidad = 50;
        int precio = elSurtidor.venderGasolina( CORRIENTE, cantidad );
        int precioEsperado = ( int ) ( cantidad * VALOR_CORRIENTE );
        assertEquals( precioEsperado, precio, "El precio calculado no es el esperado" );

        assertEquals( cantidad, elSurtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos en el registro del surtidor no es correcta" );

        Empleado e = elSurtidor.getEmpleadoAsignado( );
        assertEquals( precioEsperado, e.getCantidadDinero( ), "La cantidad de dinero que tiene el empleado no es correcta" );
    }

    @Test
    void testVenderGasolinaVariasVentas( )
    {
        double cantidad = 7;
        double galonesTotales = 0;

        int ventaTotal = 0;

        for( int i = 0; i < 10; i++ )
        {
            int precio = elSurtidor.venderGasolina( CORRIENTE, cantidad );

            int precioEsperado = ( int ) ( cantidad * VALOR_CORRIENTE );
            assertEquals( precioEsperado, precio, "El precio calculado no es el esperado" );

            galonesTotales += cantidad;
            assertEquals( galonesTotales, elSurtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos en el registro del surtidor no es correcta" );

            ventaTotal += precioEsperado;
            Empleado e = elSurtidor.getEmpleadoAsignado( );
            assertEquals( ventaTotal, e.getCantidadDinero( ), "La cantidad de dinero que tiene el empleado no es correcta" );
        }
    }

    @Test
    void testVenderGasolinaVariosTipos( )
    {
        double cantidadCorriente = 5;
        double cantidadExtra = 9;
        double cantidadPlus = 8.5;

        double galonesCorriente = 0;
        double galonesExtra = 0;
        double galonesPlus = 0;

        int ventaTotal = 0;

        for( int i = 0; i < 10; i++ )
        {
            int precioCorriente = elSurtidor.venderGasolina( CORRIENTE, cantidadCorriente );
            int precioExtra = elSurtidor.venderGasolina( EXTRA, cantidadExtra );
            int precioPlus = elSurtidor.venderGasolina( PLUS, cantidadPlus );

            galonesCorriente += cantidadCorriente;
            galonesExtra += cantidadExtra;
            galonesPlus += cantidadPlus;

            assertEquals( galonesCorriente, elSurtidor.getGalonesVendidos( CORRIENTE ), "La cantidad de galones vendidos en el registro del surtidor no es correcta" );
            assertEquals( galonesExtra, elSurtidor.getGalonesVendidos( EXTRA ), "La cantidad de galones vendidos en el registro del surtidor no es correcta" );
            assertEquals( galonesPlus, elSurtidor.getGalonesVendidos( PLUS ), "La cantidad de galones vendidos en el registro del surtidor no es correcta" );

            ventaTotal += precioCorriente + precioExtra + precioPlus;
            Empleado e = elSurtidor.getEmpleadoAsignado( );
            assertEquals( ventaTotal, e.getCantidadDinero( ), "La cantidad de dinero que tiene el empleado no es correcta" );
        }
    }
}
