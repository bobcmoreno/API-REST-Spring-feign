package desafio.zup.api.rest.funcions;

import java.util.Calendar;

public class RodizioHoje {

	public Calendar dataHoje = Calendar.getInstance();	
	
	public static boolean verificarodizio( int diaSemana)
		{
		return diaSemana == Calendar.DAY_OF_WEEK;
		}	
}
