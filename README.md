# RepositoryProgettoProgrammazione
Christopher Buratti & Luca Guidi

## Panoramica
Il progetto tratta l'implementazione di un servizio meteo in grado di fornire dati e statistiche riguardanti le temperature delle città circostanti una capitale (scelta dall'utente) all'interno di un "box" rettangolare delle dimensioni desiderate.   
In particolare, attraverso il framework SpringBoot, abbiamo realizzato un Restful Web Service in grado di interfacciarsi con la porta `localhost:8080` tramite la quale è possibile gestire le richieste effettuate dall'utente e restituire una risposta. Le richieste e le risposte vengono effettuate interamente in formato JSON.
Per acquisire i dati riguardanti le temperature delle città abbiamo utilizzato le API di `OpenWeatherMap`, le quali consentono di ottenere in tempo reale le informazioni metereologiche di una singola città o di un box di città a seconda della chiamata effettuata.

## Utilizzo <a name="utilizzo"></a>
Avviando il programma esso sarà in "ascolto" alla porta locale `localhost:8080`.
Il programma necessita di una prima inizializzazione, che richiede il nome di una capitale e la grandezza in km di un "box" rettangolare. Una volta inizializzato, il programma 
mette a disposizione diverse rotte per la consultazione dei dati e delle statistiche.

### Rotte
Tipo | Rotta | Descrizione
---- | ----- | -----------
POST | <a href="#rottaCap"> /capital/{nomeCapitale} </a> | Inizializza il dataset e restituisce le informazioni metereologiche in tempo reale
GET  | <a href="#rottaData"> /data </a> | Restituisce tutti i valori contenuti nel dataset
POST | /data | Restituisce i valori del dataset che sono compresi tra le date specificate
POST | <a href="#rottaStats"> /stats </a> | Restituisce i valori delle statistiche specificate, nel numero di giorni indicato
POST | <a href="#rottaFilters"> /filters </a> | Restituisce solamente i valori delle statistiche di quelle città che rispettano le condizioni dei filtri
GET  | /save | Salva l'intero dataset su un file JSON
GET  | /metadata | Restituisce il tipo e il nome per esteso dei metadati

### Esempi Rotte di tipo POST

* #### /capital/Paris <a name="rottaCap"></a>
```
{
    "length" : 100,
    "width": 150
}
```
Permette di inizializzare il programma con il nome di una capitale, in questo caso "Paris", e con la grandezza del box.
In questo esempio la lunghezza (length) viene impostata a 100 km, mentre la larghezza (width) a 150 km.
> <a href="#postCap"> Informazioni aggiuntive </a>

* #### /data <a name="rottaData"></a>
```
{
    "from" : "10/01/2021", 
    "to" : "16/01/2021"
}
```
Permette di ottenere i dati contenuti nel dataset tra le date specificate.
> <a href="#getData"> Informazioni aggiuntive </a>

* #### /stats <a name="rottaStats"></a>
```
{
    "stat1" : "avg",
    "stat2" : "tempMax",
    "days" : 22
}
```
Permette di ottenere le <a href="#Stat"> statistiche</a>, che devono essere specificate in ordine progressivo, nel numero di giorni specificato su "days".
Inoltre se si vogliono visualizzare i dati di una statistica ordinati in modo decrescente è necessario richiedere una statistica sola. Infatti se si richiedono più statistiche insieme, la visualizzazione delle città non rispetterà nessun ordine particolare.
> <a href="#postStats"> Informazioni aggiuntive </a>

* #### /filters <a name="rottaFilters"></a>
```
{
   "days" : 18,
   "and" : {
       "tempmin":{
           "greater":-1
       },
       "name" : {
           "nin" : "Paris"
       },
       "or" : {
           "var" : {
               "lessEqual" : 0.014
           },
           "avg" :{
               "included" : [
                   2.2,
                   3
               ]
           }
       }
   }
}
```
Permette di ottenere tutte le statistiche delle città che rispettano i filtri.
* Semplici regole della sintassi:
1. E' necessario specificare il numero di giorni a partire dal giorno corrente ("days"), su cui si vogliono calcolare le statistiche.
2. Quando si inserisce una <a href="#Stat">statistica</a> bisogna specificare un filtro da applicare.
3. Se si vogliono filtrare le statistiche in base ad una statistica, è necessario utilizzare il nome della statistica e si potranno utilizzare i <a href="#filterNumber">filtri di tipo numerico </a>
4. Se si vogliono filtrare le statistiche in base al nome delle città, è necessario utilizzare il parametro *"Name"* al posto del nome della statistica e si potranno allora utilizzare i <a href="#filterName">filtri dedicati ai nomi </a>
5. Se si inserisce un operator, esso si aspetta un numero indefinito di parametri di tipo <a href="#Operator">operatore </a> o <a href="#Stat">statistiche </a>. E' perciò possibile annidare più operatori.

> <a href="#postFilters"> Informazioni aggiuntive </a>

## INFO su Stats e Filtri

### Stats <a name="Stat"></a>
Stat | Descrizione
---- | -----------
Avg  | Temperatura media
TempMin | Temperatura minima
TempMax | Temperatura massima
Var  | Varianza
Std  | Deviazione standard

### Filtri per campi numerici <a name="filterNumber"></a>
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

### Filtri per nome delle città <a name="filterName"></a>
Nome filtro | Descrizione | Esempio
----------- | ----------- | -------
In | Se trova una corrispondenza con i nomi specificati | `"Name":{"In":["Paris","Cergy"]}`
Nin | Se non viene trovata una corrispondenza con i nomi specificati | `"Name":{"Nin":["Paris","Cergy","Evry"]}`

### Operatori logici da applicare sui filtri <a name="Operator"></a>
Nome operatore | Descrizione | Esempio
-------------- | ----------- | -------
And | Ritorna le città che soddisfano tutte le condizioni | `"and":{"avg":{"notIncluded":[10,15.9]},"var":{"lessEqual":2},"tempMax":{"greaterEqual":9}}`
Or | Ritorna le città che soddisfano almeno una condizione | `"or":{"avg":{"Included":[7.1,8.7]},"std":{"greater":0.5}}`

# UML
## Use Case Diagram
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/UseCase.png?raw=true">

## Class Diagram
* ### All Packages
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/allClassesDiagramWAssociation.png?raw=true">

* ### Package bg.Weather.controller
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/controller.png?raw=true">

* ### Package bg.Weather.service
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/service.png?raw=true">

* ### Package bg.Weather.model
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/model.png?raw=true">

* ### Package bg.Weather.dataset
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/dataset.png?raw=true">

* ### Package bg.Weather.util.stats
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/statsClass.png?raw=true">

* ### Package bg.Weather.util.filter
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/filter.png?raw=true">

* ### Package bg.Weather.util.operator
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/operatorClass.png?raw=true">

* ### Package bg.Weather.exception
<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/exceptionClass.png?raw=true">

## Chiamate <a name="chiamate"></a>
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
> <a href="#rottaCap"> Come si usa </a>

* ### **GET /data** <a name="getData"></a>
Attraverso il metodo `getData` di WeatherService viene creato un JSONArray con tutte le informazioni contenute nel dataset.
Il JSONArray viene poi ritornato al controller che lo ritorna al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/GetDataSeq.png?raw=true">
> <a href="#rottaData"> Come si usa </a>

* ### **POST /stats** <a name="postStats"></a>
Attraverso il metodo `getStats` di WeatherService viene prima effettuato il parsing della richiesta, che contiene quali statistiche calcolare.
La classe `StatsService` si occuperà di calcolare le statistiche richieste, attraverso `Calculate`, sui dati contenuti nel database.
Il WeatherService creerà un JSONArray con le statistiche che viene poi ritornato al controller, il quale lo ritornerà al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/PostStatsSeq.png?raw=true">
> <a href="#rottaStats"> Come si usa </a>

* ### **POST /filters** <a name="postFilters"></a>
Attraverso il metodo `getFilteredStats` di WeatherService viene chiamato `FilterService`, il quale effettua il parsing della richiesta e interpreta i valori come statistiche o operatori. Vengono poi calcolate le statistiche attraverso `StatsService` e filtrate per mezzo dei filtri specificati. FilterCalc si occupera poi di creare un JSONArray contenente le città che rispettano i filtri e lo ritornerà al WeatherService, il quale lo ritornerà al client.

<img src="https://github.com/Buratti-Guidi/RepositoryProgettoProgrammazione/blob/main/FiltersSeq.png?raw=true">
> <a href="#rottaFilters"> Come si usa </a>

## Software utilizzati
* #### [IDE Eclipse](https://www.eclipse.org/) - per la scrittura e lo sviluppo dell'intero programma in Java
* #### [Framework Spring](https://spring.io/projects/spring-framework) - per lo sviluppo di applicazioni enterprise
* #### [Postman](https://www.postman.com/) - per interfacciarsi in modo chiaro e veloce con la porta "localhost:8080" e verificare il corretto funzionamento dell'intero progetto
* #### [Visual Paradigm](https://www.visual-paradigm.com/) - per la creazione e modellazione dei diagrammi UML
* #### [JUnit5](https://junit.org/junit5/) - per lo svolgimento degli Unit Test

## Autori & contributo
[Christopher Buratti](https://github.com/christopherburatti) - 50%                                                             
[Luca Guidi](https://github.com/LucaGuidi5) - 50%
