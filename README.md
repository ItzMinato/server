Aplikácia umožňuje správu študijných skupín, úloh a zdrojov prostredníctvom rozdelenia na serverovú časť a JavaFX klienta.
Je určená pre študentov a učiteľov, ktorí potrebujú jednoduchý systém na:

vytváranie a správu študijných skupín
zdieľanie úloh v skupine
nahrávanie a prezeranie zdrojov (súbory alebo URL odkazy)
jednoduchú autentifikáciu používateľov

Cieľom aplikácie je poskytnúť praktický nástroj na organizáciu práce v menších tímových alebo školských projektoch a 
zároveň demonštrovať architektúru klient–server, prácu s databázou, REST API, autentifikáciou a JavaFX používateľským rozhraním

Pre spustenie projektu je potrebné:
Spustiť serverovú časť
prejsť na branch main
vo foldere je potrebné ApplicationStudyPlatformer spustiť Spring Boot aplikáciu (Run ApplicationStudyPlatformer)

Spustiť klientskú časť (JavaFX)
prejsť na branch client-ui
v module client-ui v Maven konfigurácii spustiť položku JavaFX Application



<img width="823" height="1166" alt="image" src="https://github.com/user-attachments/assets/d60b1669-3b33-4d9e-aeff-0ed7606f2594" />



1. Users API
POST /api/users/register

Popis: Registrácia nového používateľa.
Body JSON:
{
  "username": "test",
  "password": "1234",
  "role": "STUDENT"
}

Príklad odpovede:
{
  "id": 1,
  "username": "test",
  "role": "STUDENT"
}

POST /api/users/login

Popis: Prihlásenie používateľa.
Body JSON:
{
  "username": "test",
  "password": "1234"
}

Príklad odpovede:
{
  "id": 1,
  "username": "test",
  "role": "STUDENT"
}



2. Groups API
GET /api/groups

Popis: Získanie všetkých skupín.
Príklad odpovede:

  {
    "id": 1,
    "name": "Math group",
    "description": "Algebra lessons",
    "createdBy": 2
  }

  POST /api/groups

Popis: Vytvorenie novej skupiny.
Body JSON:
{
  "name": "Math group",
  "description": "Algebra lessons",
  "creatorId": 1
}

Príklad odpovede:
{
  "id": 1,
  "name": "Math group"
}


3. Tasks API
GET /api/groups/{groupId}/tasks

Popis: Zoznam úloh v skupine.

Príklad odpovede:
  {
    "id": 1,
    "title": "Homework 1",
    "description": "Solve equations",
    "status": "OPEN"
  }

  POST /api/groups/{groupId}/tasks

Popis: Vytvorenie novej úlohy v skupine.
Body JSON:
{
  "title": "Homework",
  "description": "Task description",
  "createdById": 1
}


Príklad odpovede:
{
  "id": 3,
  "title": "Homework"
}


PATCH /api/groups/{groupId}/tasks/{taskId}/status

Popis: Zmena stavu úlohy.
Body JSON:
{
  "status": "DONE"
}

Príklad odpovede:
{
  "id": 3,
  "status": "DONE"
}

4. Resources API
GET /api/groups/{groupId}/resources

Popis: Získanie všetkých zdrojov v skupine.
Príklad odpovede:
  {
    "id": 1,
    "fileName": "image.png",
    "filePath": "uploads/1/image.png",
    "uploadedBy": 1
  }

  POST /api/groups/{groupId}/resources — vkladanie URL zdroja

Body JSON:
{
  "title": "Java documentation",
  "url": "https://docs.oracle.com",
  "createdById": 1
}

Odpoveď:
{
  "id": 4,
  "title": "Java documentation"
}

POST /api/groups/{groupId}/resources/upload — upload súboru (multipart)

Body (multipart/form-data):

meta: JSON
{ "title": "Screenshot", "uploadedById": 1 }
file: binárny súbor

Príklad odpovede:
{
  "id": 10,
  "fileName": "image.png",
  "uploadedAt": "2025-12-09T22:01:44"
}



<img width="587" height="475" alt="image" src="https://github.com/user-attachments/assets/b03bf3ee-67ff-46e0-be8a-38f35a423ee2" />
<img width="889" height="633" alt="image" src="https://github.com/user-attachments/assets/f6008be8-d8c5-4804-b536-dea3fc6c0d89" />
<img width="891" height="629" alt="image" src="https://github.com/user-attachments/assets/57e3e465-0be7-4181-925c-66b52a9efee7" />
<img width="891" height="600" alt="image" src="https://github.com/user-attachments/assets/b0609015-e06a-49f4-9bbf-3e3dc42e2da2" />
<img width="888" height="624" alt="image" src="https://github.com/user-attachments/assets/b57b1a2c-0db4-4ce7-a4d1-e11cbc7d2c95" />
<img width="896" height="634" alt="image" src="https://github.com/user-attachments/assets/adeac334-23c3-426a-b07a-263f2192b307" />

1. Prihlásenie používateľa

Po spustení aplikácie sa zobrazí prihlasovacie okno
Používateľ zadá meno a heslo
Po úspešnom prihlásení sa načítajú dáta používateľa zo servera


2. Zobrazenie zoznamu skupín

Používateľ vidí všetky skupiny, v ktorých sa nachádza
Zo zoznamu si môže vybrať konkrétnu študijnú skupinu


3. Detail skupiny

Po otvorení skupiny sa zobrazia informácie:
názov skupiny
popis
meno tvorcu

Používateľ si môže vybrať ďalšiu akciu:
zobraziť úlohy (Tasks)
zobraziť zdroje (Resources)
alebo sa vrátiť späť


4. Práca s úlohami (Tasks)

V časti Tasks sa zobrazí zoznam všetkých úloh skupiny
Používateľ môže:

vytvoriť novú úlohu
otvoriť existujúcu úlohu
zmeniť stav úlohy (napr. na DONE)
Vytvorená úloha sa odošle na server cez REST API


5. Práca so zdrojmi (Resources)

Používateľ vidí zoznam všetkých zdrojov v skupine (súbory alebo URL odkazy)
Môže:

pridať nový zdroj:
buď URL odkaz
alebo nahrať súbor cez multipart upload

otvoriť zdroj:
URL sa otvorí v prehliadači
súbor sa stiahne z Base64 alebo diskovej cesty a otvorí v systéme


6. Návrat späť

Zo všetkých obrazoviek sa dá vrátiť späť na detail skupiny alebo na zoznam skupín
Aplikácia pracuje bez obnovovania alebo zatvárania – používateľ sa plynulo pohybuje medzi oknami




Počas vývoja aplikácie sme narazili na viacero technických problémov
ktoré bolo potrebné vyriešiť, aby klient–server komunikácia fungovala správne a stabilne.

Chybné JSON požiadavky → HTTP 400 / 500 chyby
Pri prvých pokusoch o odosielanie požiadaviek zo strany JavaFX klienta server vrátil chybu HTTP 500 – internal server error
Dôvodom bolo nesprávne vytvorenie JSON objektov (napr. chýbajúce polia ako createdById, zlé pomenovania alebo null hodnoty)

Riešenie:
zaviedli sme korektné DTO objekty aj na klientskej strane
upravili sme API požiadavky tak, aby presne zodpovedali tomu, čo očakáva server
doplnili sme validáciu ešte pred odoslaním na server (napr. kontrola prázdnych názvov, neplatných URL)




Pri vývoji projektu sme využili pomoc AI nástrojov (ChatGPT, Copilot), najmä pri generovaní štruktúry REST API 
návrhu JavaFX controllerov a pri tvorbe pomocných metód na komunikáciu so serverom. AI výrazne urýchlila proces písania kódu a poskytovala návrhy riešení 
ktoré by sme manuálne hľadali oveľa dlhšie.

Napriek tomu bolo potrebné veľa častí manuálne doladiť:

upravovanie generovaného kódu tak, aby zodpovedal reálnej architektúre projektu
riešenie chýb pri komunikácii medzi klientom a serverom (napr. problémy s JSON formátom, HTTP 400/500 chybami alebo nesprávnymi URL cestami)
debugovanie FXML súborov, ktoré často zlyhávali na malých detailoch (nesprávne ID komponentov, neexistujúce metódy v controlleri)
manuálne dopĺňanie logiky okolo multipartu pri nahrávaní súborov, kde AI návrhy nefungovali na prvý pokus
prepojenie jednotlivých častí UI (navigácia medzi oknami, preposielanie ID skupiny/užívateľa)

Práca s AI mi pomohla lepšie pochopiť:

ako funguje komunikácia klient–server cez HTTP
ako sa vytvárajú REST endpointy a ako správne formátovať požiadavky
tvorbu JavaFX aplikácie s viacero obrazovkami
ako hľadať a opravovať chyby v kóde













