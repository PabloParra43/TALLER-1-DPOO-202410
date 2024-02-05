package uniandes.dpoo.gasolinera.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.gasolinera.logica.Empleado;

public class EmpleadoTest
{
    private Empleado e1;

    @BeforeEach
    void setUp( ) throws Exception
    {
        e1 = new Empleado( "e1" );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testEmpleado( )
    {
        assertEquals( "e1", e1.getNombre( ), "El nombre del empleado no es el correcto" );
        assertEquals( 0, e1.getCantidadDinero( ), "El nuevo empleado no debería tener dinero" );
    }

    @Test
    void testAgregarDinero( )
    {
        assertEquals( 0, e1.getCantidadDinero( ), "El nuevo empleado no debería tener dinero" );
        e1.agregarDinero( 100 );
        assertEquals( 100, e1.getCantidadDinero( ), "El nuevo empleado ya debería tener dinero" );
        e1.agregarDinero( 100 );
        assertEquals( 200, e1.getCantidadDinero( ), "El nuevo empleado debería tener más dinero ahora" );
    }

}
