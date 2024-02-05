package uniandes.dpoo.gasolinera.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.gasolinera.exceptions.GasolinaInsuficienteException;
import uniandes.dpoo.gasolinera.logica.TipoGasolina;

class TipoGasolinaTest
{
    private static final int CANTIDAD_CORRIENTE = 50;
    private static final int VALOR_CORRIENTE = 10000;
    private static final String CORRIENTE = "corriente";
    private double CANTIDAD_VENTA = 15;

    private TipoGasolina tipoCorriente;

    @BeforeEach
    void setUp( ) throws Exception
    {
        tipoCorriente = new TipoGasolina( CORRIENTE, VALOR_CORRIENTE, CANTIDAD_CORRIENTE );
    }

    @Test
    void testDespacharGasolinaOk( )
    {
        assertEquals( CANTIDAD_CORRIENTE, tipoCorriente.getCantidadDisponible( ), "La cantidad inicial no es correcta" );
        try
        {
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
        }
        catch( GasolinaInsuficienteException e )
        {
            fail( "No debería estar acá porque hay suficiente gasolina: " + e.getMessage( ) );
        }

        assertEquals( CANTIDAD_CORRIENTE - CANTIDAD_VENTA, tipoCorriente.getCantidadDisponible( ), "La cantidad actualizada no es correcta" );
    }

    @Test
    void testDespacharGasolinaOkVarios( )
    {
        assertEquals( CANTIDAD_CORRIENTE, tipoCorriente.getCantidadDisponible( ), "La cantidad inicial no es correcta" );
        try
        {
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
        }
        catch( GasolinaInsuficienteException e )
        {
            fail( "No debería estar acá porque hay suficiente gasolina: " + e.getMessage( ) );
        }

        assertEquals( CANTIDAD_CORRIENTE - ( 3 * CANTIDAD_VENTA ), tipoCorriente.getCantidadDisponible( ), "La cantidad actualizada no es correcta" );
    }

    @Test
    void testDespacharGasolinaInsuficiente( )
    {
        assertEquals( CANTIDAD_CORRIENTE, tipoCorriente.getCantidadDisponible( ), "La cantidad inicial no es correcta" );
        try
        {
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
        }
        catch( GasolinaInsuficienteException e )
        {
            fail( "No debería estar acá porque hay suficiente gasolina: " + e.getMessage( ) );
        }

        assertEquals( CANTIDAD_CORRIENTE - ( 3 * CANTIDAD_VENTA ), tipoCorriente.getCantidadDisponible( ), "La cantidad actualizada no es correcta" );

        try
        {
            tipoCorriente.despacharGasolina( CANTIDAD_VENTA );
            fail( "No debería estar acá porque no había suficiente gasolina" );
        }
        catch( GasolinaInsuficienteException e )
        {
        }

        assertEquals( CANTIDAD_CORRIENTE - ( 3 * CANTIDAD_VENTA ), tipoCorriente.getCantidadDisponible( ), "La venta fallida no debió actualizar el inventario de gasolina" );
    }

}
