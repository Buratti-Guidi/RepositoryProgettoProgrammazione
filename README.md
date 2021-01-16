# RepositoryProgettoProgrammazione
Christopher Buratti & Luca Guidi

## Panoramica
Il progetto tratta l'implementazione di un servizio meteo in grado di fornire dati e statistiche riguardanti le temperature delle città circostanti una capitale (scelta dall'utente) all'interno di un "box" rettangolare delle dimensioni desiderate.   
In particolare, attraverso il framework SpringBoot, abbiamo realizzato un Restful Web Service in grado di interfacciarsi con la porta `localhost:8080` tramite la quale è possibile gestire le richieste effettuate dall'utente e restituire una risposta. Le richieste e le risposte vengono effettuate interamente in formato JSON.
Per acquisire i dati riguardanti le temperature delle città abbiamo utilizzato le API di `OpenWeatherMap`, le quali consentono di ottenere in tempo reale le informazioni metereologiche di una singola città o di un box di città a seconda della chiamata effettuata.

## Utilizzo
Il programma necessita di una prima inizializzazione, che richiede il nome di una capitale e la grandezza in km di un "box" rettangolare (attraverso POST /capital/{nomeCapitale}). (Vogliamo scrivere del file già esistente?). Una volta inizializzato il programma permette di utilizzare diverse

> Vai al paragrafo: <a href="#chiamate"> chiamateAPI </a>


Tipo | Rotta | Descrizione
---- | ----- | -----------
POST | <a href="#postCap"> /capital/{nomeCapitale} </a> | Inizializza il dataset e restituisce le informazioni metereologiche in tempo reale
GET  | <a href="#getData"> /data </a> | Restituisce tutti i valori contenuti nel dataset
POST | <a href="#postData"> /data </a> | Restituisce i valori del dataset che sono compresi tra le date specificate
POST | <a href="#postStats"> /stats </a> | Restituisce i valori delle statistiche specificate, nel numero di giorni indicato
POST | <a href="#postFilters"> /filters </a> | Restituisce solamente i valori delle statistiche di quelle città che rispettano le condizioni dei filtri
GET  | /save | Salva l'intero dataset su un file JSON
GET  | /metadata | Restituisce il tipo e il nome per esteso dei metadati

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
NotIncludedEqual | Non compreso (e uguale) tra | `"stat":{"NotIncluded":[1.1, 2.9]}`
Not | Diverso | `"stat":{"Not":4.7}`

### Filtri per nome delle città
Nome filtro | Descrizione | Esempio
----------- | ----------- | -------
In | Se trova una corrispondenza con i nomi specificati | `"Name":{"In":["Paris","Cergy"]}`
Nin | Se non viene trovata una corrispondenza con i nomi specificati | `"Name":{"Nin":["Paris","Cergy","Evry"]}`

### Operatori logici da applicare sui filtri
Nome operatore | Descrizione | Esempio
-------------- | ----------- | -------
And | Ritorna le città che soddisfano tutte le condizioni | `"and":{"avg":{"notIncluded":[10,15.9]},"var":{"lessEqual":2},"tempMax":{"greaterEqual":9}}`
Or | Ritorna le città che soddisfano almeno una condizione | `"or":{"avg":{"Included":[7.1,8.7]},"std":{"greater":0.5}}`

### Stat
Stat | Descrizione
---- | -----------
Avg  | Temperatura media
TempMin | Temperatura minima
TempMax | Temperatura massima
Var  | Varianza
Std  | Deviazione standard

# CHIAMATE <a name="chiamate"></a>
Il controller inoltra tutte le richieste che gli vengono fatte al WeatherService, che si occuperà di elaborarle

* ### **POST /capital/{nome capitale}** <a name="postCap"></a>
L' applicazione necessita di una inizializzazione, che consiste nella scelta del nome della capitale e di un box rettangolare, i cui lati sono espressi in km.
La città inserita viene controllata da `CityInfo`, che verifica da un file JSON interno se sia una effettiva capitale.
Successivamente, a partire dalle coordinate della capitale (ottenute attraverso una chiamata API) e dalla grandezza in km del box, vengono calcolate le coordinate in gradi decimali in cui sono presenti i 4 vertici del box rettangolare, attraverso `Box Calculator`.
Se esiste già un file JSON contenente delle "vecchie" chiamate effettuate dall'utente in merito ad una capitale e ad un box, viene letto e viene conseguentemente aggiornato il dataset, altrimenti ne viene creato uno nuovo.
Infine viene fatta la chiamata all' API che ci permette di avere le informazioni climatiche delle città contenute nel box di coordinate nell' ora corrente.
L' oggetto JSON ottenuto viene parsato dal `JSONWeatherParser`, i dati ottenuti vengono salvati nel dataset.
Prima di ritornare le informazioni all'utente, viene attivata una scheduled task che fa la chiamata API ogni ora.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/InizializationSeq.png?raw=true">

* ### **GET /data** <a name="getData"></a>
Attraverso il metodo `getData` di WeatherService viene creato un JSONArray con tutte le informazioni contenute nel dataset.
Il JSONArray viene poi ritornato al controller che lo ritorna al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/GetDataSeq.png?raw=true">

* ### **POST /stats** <a name="postStats"></a>
Attraverso il metodo `getStats` di WeatherService viene prima effettuato il parsing della richiesta, che contiene quali statistiche calcolare.
La classe `StatsService` si occuperà di calcolare le statistiche richieste, attraverso `Calculate`, sui dati contenuti nel database.
Il WeatherService creerà un JSONArray con le statistiche che viene poi ritornato al controller, il quale lo ritornerà al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/PostStatsSeq.png?raw=true">

* ### **POST /filters** <a name="postFilters"></a>
Attraverso il metodo `getFilteredStats` di WeatherService viene chiamato `FilterService`, il quale effettua il parsing della richiesta e interpreta i valori come statistiche o operatori. Vengono poi calcolate le statistiche attraverso `StatsService` e filtrate per mezzo dei filtri specificati. FilterCalc si occupera poi di creare un JSONArray contenente le città che rispettano i filtri e lo ritornerà al WeatherService, il quale lo ritornerà al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/FiltersSeq.png?raw=true">

## Software utilizzati
* #### [IDE Eclipse](https://www.eclipse.org/) - per la scrittura e lo sviluppo dell'intero programma in Java
* #### [Framework Spring](https://spring.io/projects/spring-framework) - per lo sviluppo di applicazioni enterprise
* #### [Postman](https://www.postman.com/) - per interfacciarsi in modo chiaro e veloce con la porta "localhost:8080" e verificare il corretto funzionamento dell'intero progetto
* #### [Visual Paradigm](https://www.visual-paradigm.com/) - per la creazione e modellazione dei diagrammi UML
* #### [JUnit5](https://junit.org/junit5/) - per lo svolgimento degli Unit Test

## Autori & contributo
[Christopher Buratti](https://github.com/christopherburatti) - 50%                                                             
[Luca Guidi](https://github.com/LucaGuidi5) - 50%
