package uniandes.dpoo.gasolinera.exceptions;

@SuppressWarnings("serial")
public class GasolinaInsuficienteException extends Exception
{

    private String tipoGasolina;
    private double cantidadDisponible;
    private double cantidadSolicitada;

    public GasolinaInsuficienteException( String tipoGasolina, double cantidadDisponible, double cantidadSolicitada )
    {
        super( );
        this.tipoGasolina = tipoGasolina;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    @Override
    public String getMessage( )
    {
        String m = "No hay suficiente gasolina del tipo " + tipoGasolina + ".\n";
        m += "Se solicitaron " + cantidadSolicitada + " galones, pero sólo hay disponibles " + cantidadDisponible + " galones";
        return m;
    }

    public double getCantidadDisponible( )
    {
        return cantidadDisponible;
    }
}
