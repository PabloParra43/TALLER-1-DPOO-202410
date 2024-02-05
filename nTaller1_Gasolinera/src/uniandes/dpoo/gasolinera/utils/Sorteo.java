package uniandes.dpoo.gasolinera.utils;

public class Sorteo<E>
{
    public static <E> E seleccionarAlAzar( E[] opciones )
    {
        E seleccionado = null;
        double cantidad = opciones.length;
        int posicion = ( int ) ( Math.random( ) * cantidad );
        seleccionado = opciones[ posicion ];
        return seleccionado;
    }
}
