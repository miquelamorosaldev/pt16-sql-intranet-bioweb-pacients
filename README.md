# pt16-sql-intranet-bioweb-pacients

## Plantilla PT16. COMPLETA

L'objectiu és continuar el projecte de la PT15, amb connexió a una base de dades MySQL o MariaDb. 
Aprofitarem per afegir alguna funcionalitat més: més opcions de cerca i opcions de CRUD pels pacients. 

## REQUERIMENTS
* Desenvolupament multiplataforma, prioritzant l'ús de Linux Ubuntu 20.04
* IDE amb suport per Java → Apache Netbeans 12, mínim versió 12.0
* JDK → Open JDK 11 actualitzada. També pot ser necessari la JDK 8 actualitzada.
* Servidor web configurat amb Java → Apache Tomcat 9.0.20 o Glassfish 5.0.11
* Llibreria JavaEE Web 7 (Apache Netbeans la inclou)
* Llibreries JUnit 4 si volem fer testing avançat (Apache Netbeans la inclou)
* Llibraria generació fitxers JSON (SimpleJSON o GSON) per tractar estructures i facilitar creació Web Services.

## DESCRIPCIÓ DEL PROJECTE:

L’ICS ens ha demanat una tercera fase d’una aplicació web per a gestionar dades bàsiques de pacients de
la secció d’oncologia; que consisteix en guardar de forma bona part la intranet d’un portal web les
funcionalitats de l’anterior versió.

Per tant, apart que les funcionalitats concretes de la Pt14 i Pt15 funcionin ens interessa que la web ens dirigeixi a un formulari de login d’usuaris 
(amb usuari i contrasenya) que validi els usuaris a través d’un sistema segur (ja sigui a través de fitxers JSON o CSV o amb base de dades SQL).
Només en el cas que l’usuari i contrasenya tinguin un format valid i existeixin a la base de dades d’usuaris podrem realitzar les operacions que haviem creat abans (llista pacients, editar/crear nous pacients, filtrar pacients per criteris de cerca, etc...)

## FUNCIONALITATS REALITZAR
* Login d’usuaris, que es validin a través de base de dades (usuaris visitants, no registrats)
* Llistat d’usuaris de la Base de dades (només per a administradors)
* Logout (usuaris registrats)
* Filtrar la llista de pacients per RH, Grup Sanguini i altres ...
* Editar i eliminar pacients. (NOVA FUNCIONALITAT OBLIGATÒRIA PER LA PT16)
* Mostrar la informació dels pacients en un web service. (NOVA FUNCIONALITAT OBLIGATÒRIA PER LA PT16)

## FUNCIONALITATS JA FETES (NOMÉS DISPONIBLES PER USUARIS REGISTRATS).
* Tractament cadenes ADN
* Llistat de pacients
* Afegir pacient (només per a usuaris admin)
