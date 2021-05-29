package desafio.zup.api.rest.funcions;

import java.util.Calendar;

public class RodizioHoje {

	public Calendar dataHoje = Calendar.getInstance();	
	
	public static boolean verificarodizio( int diaSemana)
		{
		
			if (diaSemana == Calendar.DAY_OF_WEEK)
				{
					return true;
				}
			else
				{
					return false;
				}
		}	
}
