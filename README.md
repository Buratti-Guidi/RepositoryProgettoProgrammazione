# RepositoryProgettoProgrammazione
Christopher Buratti & Luca Guidi

## Descrizione generale
Il progetto tratta l'implementazione di un servizio meteo in grado di fornire dati e statistiche riguardanti le temperature delle città circostanti una capitale (scelta dall'utente) all'interno di un "box" rettangolare delle dimensioni desiderate.   
In particolare, attraverso il framework SpringBoot, abbiamo realizzato un Restful Web Service in grado di interfacciarsi con la porta `localhost:8080` tramite la quale è possibile gestire le richieste effettuate dall'utente e restituirne una risposta. Le richieste e le risposte vengono effettuate interamente in formato JSON.
Per l'ottenimento dei dati riguardanti le temperature delle città si è utilizzata l'API `OpenWeather`, la quale consente di ottenere in tempo reale le informazioni metereologiche di una singola città o di un box di città a seconda della chiamata effettuata.

## CHIAMATE
Il controller inoltra tutte le richieste che gli vengono fatte al WeatherService, che si occuperà di elaborarle

* ### **POST /capital/{nome capitale}**
L' applicazione necessita di una inizializzazione, che consiste nel nome della capitale e di un rettangolo di coordinate in km.
La città inserita viene controllata da `CityInfo`, che verifica da un file JSON interno se sia una effettiva capitale.
Viene successivamente calcolato il box di coordinate a partire dalle coordinate della capitale (ottenute attraverso una chiamata API) e dalle grandezze in km, attraverso `Box Calculator`.
Se esiste un file JSON che contiene delle "vecchie" chiamate, viene letto e viene conseguentemente aggiornato il database.
Infine viene fatta la chiamata all' API che ci permette di avere le informazioni climatiche delle città contenute nel box di coordinate nell' ora corrente.
L' oggetto JSON ottenuto viene parsato dal `JSONWeatherParser`, i dati ottenuti vengono salvati nel database.
Prima di ritornare le informazioni all'utente, viene attivata una scheduled task che fa la chiamata API ogni ora.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/InizializationSeq.png?raw=true">

* ### **GET /data**
Attraverso il metodo `getData` di WeatherService viene creato un JSONArray con tutte le informazioni contenute nel database.
Il JSONArray viene poi ritornato al controller che lo ritorna al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/GetDataSeq.png?raw=true">

* ### **POST /stats**
Attraverso il metodo `getStats` di WeatherService viene prima effettuato il parsing della richiesta, che contiene quali statistiche calcolare.
La classe `StatsService` si occuperà di calcolare le statistiche richieste, attraverso `Calculate`, sui dati contenuti nel database.
Il WeatherService creerà un JSONArray con le statistiche che viene poi ritornato al controller, il quale lo ritornerà al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/PostStatsSeq.png?raw=true">
