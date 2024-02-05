package uniandes.dpoo.gasolinera.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.gasolinera.utils.Sorteo;

class SorteoTest
{

    private static final String A = "A";
    private static final String B = "B";

    @Test
    public void testUnaOpcion( )
    {
        String[] opciones = new String[]{ A };

        String seleccionada = null;

        for( int i = 0; i < 1000; i++ )
        {
            seleccionada = Sorteo.seleccionarAlAzar( opciones );
            assertEquals( A, seleccionada, "Siempre debería queda seleccionada la misma opción" );
        }
    }

    @Test
    public void testDosOpciones( )
    {
        String[] opciones = new String[]{ A, B };

        String seleccionada = null;

        for( int i = 0; i < 1000; i++ )
        {
            seleccionada = Sorteo.seleccionarAlAzar( opciones );
            assertTrue( A.equals( seleccionada ) || B.equals( seleccionada ), "Siempre debería queda seleccionada una de las dos opciones" );
        }
    }

    @Test
    public void testVariasOpciones( )
    {
        String[] opciones = new String[100];
        for( int i = 0; i < opciones.length; i++ )
        {
            opciones[ i ] = "opción " + i;
        }

        String seleccionada = null;

        for( int i = 0; i < 1000; i++ )
        {
            seleccionada = Sorteo.seleccionarAlAzar( opciones );
            assertNotNull( seleccionada, "Siempre debería queda seleccionada alguna de las opciones" );
        }
    }

    @RepeatedTest(15)
    public void testBalanceado( )
    {
        int numOpciones = 10;
        int numIntentos = 10000;

        Integer[] opciones = new Integer[numOpciones];
        int[] histograma = new int[opciones.length];
        for( int i = 0; i < opciones.length; i++ )
        {
            opciones[ i ] = i;
        }

        int seleccionado = 0;

        for( int i = 0; i < numIntentos; i++ )
        {
            seleccionado = Sorteo.seleccionarAlAzar( opciones );
            histograma[ seleccionado ] += 1;
        }

        int maximo = 0;
        int minimo = numIntentos;
        int total = 0;

        for( int i = 0; i < histograma.length; i++ )
        {
            int valor = histograma[ i ];
            total += valor;
            if( valor > maximo )
                maximo = valor;
            if( valor < minimo )
                minimo = valor;
        }
        assertNotEquals( 0, minimo, "Todos los valores deberían haber aparecido al menos una vez" );

        double promedio = total / opciones.length;
        double promedioEsperado = numIntentos / numOpciones;

        assertEquals( promedioEsperado, promedio, promedioEsperado * 0.15, "No debería haber más de un 15% de diferencia en la cantidad de veces que apareció cada valor" );
    }
}
