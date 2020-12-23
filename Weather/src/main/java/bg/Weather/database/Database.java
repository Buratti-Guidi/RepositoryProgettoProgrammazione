package bg.Weather.database;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

import bg.Weather.model.HourCities;

public class Database {

	final int daysMax = 30; //Numero di giorni massimo per cui vogliamo salvare tutte le informazioni riguardanti le città appartenenti al box (scelto inizialmente all'avvio del programma)
	
	private LinkedList<HashSet<HourCities>> dataset = new LinkedList<HashSet<HourCities>>(); //Dataset contenente i valori delle città appartenenti al box negli ultimi "daysMax" giorni
	
	//Metodo che inserisce nel dataset i valori delle città appartenenti al box ordinati secondo il giorno in cui abbiamo ottenuto i dati
	public void aggiornaDatabase(HourCities cities) {
		
		//Se il dataset è vuoto...
		if(dataset.size() == 0) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp.add(cities);
			dataset.push(temp); //Aggiungo un HashSet contenente solo i valori della prima ora in assoluto a partire dall'avvio del programma
			return;
		}
		
		//Se il dataset non è vuoto ma non è neanche pieno...
		if(dataset.size() < daysMax) {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
			for(HourCities c : dataset.getFirst()) {
				cal = c.getCalendar(); //Ottengo i dati riguardanti il giorno che è presente in cima alla lista (oggi o ieri)
				break;
			}
			//Verifico se il giorno più recente presente è uguale a quello dell'HashSet che voglio inserire (è variata quindi solo l'ora)
			if(cal.get(Calendar.DAY_OF_MONTH) == cities.getCalendar().get(Calendar.DAY_OF_MONTH) && cal.get(Calendar.MONTH) == cities.getCalendar().get(Calendar.MONTH)) {
				
				if(cal.get(Calendar.HOUR_OF_DAY) == cities.getCalendar().get(Calendar.HOUR_OF_DAY)) //Se non è variata l'ora dell'ultima chiamata, non aggiorno il dataset
					return;
				
				HashSet<HourCities> day = new HashSet<HourCities>(); //HashSet temporaneo per aggiornare il giorno più recente (oggi)
				day = dataset.getFirst(); //Assegno ad h il valore di oggi così da non perdere i dati già salvati in esso
				
				day.add(cities); //Aggiorno l'HashSet con l'ultimo HourCities ottenuto in un'ora differente dello stesso giorno (dall'ultima API call)
				
				dataset.pollFirst(); //Rimuovo il giorno non aggiornato
				dataset.push(day); //Assegno ad "oggi" il nuovo Hashset con i valori aggiornati aggiungendo così anche quelli dell'ultima ora
				return;
			}
			else {
				HashSet<HourCities> day = new HashSet<HourCities>();
				day.add(cities);
				dataset.push(day);
				return;
			}
		}
		
		//Se il dataset è pieno...
		if(dataset.size() == daysMax) {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
			for(HourCities c : dataset.getFirst()) {
				cal = c.getCalendar(); //Ottengo i dati riguardanti il giorno che è presente in cima alla lista (oggi o ieri)
				break;
			}
			
			if(cal.get(Calendar.DAY_OF_MONTH) == cities.getCalendar().get(Calendar.DAY_OF_MONTH) && cal.get(Calendar.MONTH) == cities.getCalendar().get(Calendar.MONTH)) {
				
				if(cal.get(Calendar.HOUR_OF_DAY) == cities.getCalendar().get(Calendar.HOUR_OF_DAY)) //Se non è variata l'ora dell'ultima chiamata, non aggiorno il dataset
					return;
																			//						^
				HashSet<HourCities> h = new HashSet<HourCities>();			//					   /|\
				h = dataset.getFirst();                                     //					  / | \
				                                                            //					 /	|  \
				h.add(cities);                                              //	COME PRIMA			|
																			//						|
				dataset.pollFirst();										//						|
				dataset.push(h);											//						|
				return;
			}
			else {
				dataset.pollLast();
				
				HashSet<HourCities> temp = new HashSet<HourCities>();
				temp.add(cities);
				dataset.push(temp); //Aggiungo un HashSet contenente solo i valori della prima ora del "trentunesimo" giorno
				return;
			}
		}
	}
	
	public LinkedList<HashSet<HourCities>> getDataset() {
		return this.dataset;
	}
	
}
