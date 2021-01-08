# RepositoryProgettoProgrammazione
Christopher Buratti & Luca Guidi

## Descrizione generale
Il progetto tratta l'implementazione di un servizio meteo in grado di fornire dati e statistiche riguardanti le temperature delle città circostanti una capitale (scelta dall'utente) all'interno di un "box" rettangolare delle dimensioni desiderate.   
In particolare, attraverso il framework SpringBoot, abbiamo realizzato un Restful Web Service in grado di interfacciarsi con la porta `localhost:8080` tramite la quale è possibile gestire le richieste effettuate dall'utente e restituire una risposta. Le richieste e le risposte vengono effettuate interamente in formato JSON.
Per acquisire i dati riguardanti le temperature delle città abbiamo utilizzato le API di `OpenWeatherMap`, le quali consentono di ottenere in tempo reale le informazioni metereologiche di una singola città o di un box di città a seconda della chiamata effettuata.

Tipo | Rotta | Descrizione
---- | ----- | -----------
POST | /capital/{nomeCapitale} | Inizializza il dataset e restituisce la chiamata in tempo reale
GET  | /data | Restituisce tutti i valori contenuti nel dataset
POST | /data | Restituisce i valori del dataset che sono compresi tra le date specificate
POST | /stats | Restituisce i valori delle statistiche specificate, nel numero di giorni indicato
POST | /filters | Restituisce solamente i valori delle statistiche di quelle città che rispettano le condizioni dei filtri
GET  | /save | Salva l'intero dataset su un file JSON

### Filtri per campi numerici
Nome filtro | Descrizione | Esempio
----------- | ----------- | -------
Greater | Maggiore | `"stat":{"Greater":3.5}`
GreaterEqual | Maggiore e uguale | `"stat":{"GreaterEqual":3}`
Less | Minore | `"stat":{"Less":5}`
LessEqual | Minore | `"stat":{"LessEqual":5.0}`
Included | Compreso tra | `"stat":{"Included":[2, 5]}`
IncludedEqual | Compreso (e uguale) tra | `"stat":{"IncludedEqual":[1.5, 7]}`
NotIncluded | Non compreso tra | `"stat":{"NotIncluded":[1.5, 7]}`
Not | ? | ...

### Filtri per nome delle città
Nome filtro | Descrizione | Esempio
----------- | ----------- | -------
In | Se trova una corrispondenza con i nomi specificati | `"Name":{"In":["Paris","Cergy"]}`
Nin | Se non viene trovata una corrispondenza con i nomi specificati | `"Name":{"Nin":["Paris","Cergy","Evry"]}`

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
