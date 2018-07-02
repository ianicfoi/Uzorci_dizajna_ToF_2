# Uzorci_dizajna_ToF_2
Internet of Things aplikacija s komandnom linijom koja koristi različite uzorke dizajna

<br />

Zadaća se nastavlja na opis 1. zadaće uz određene promjene i nove elemente. ToF sastoji se od uređaja (senzora, aktuatora i sl) koji primaju kontrolne signale (poruke), obavljaju mjerenja i druge aktivnosti (pomicanje mehanizma, npr. otvaranje ili zatvaranje, paljenje ili gašenje i sl), vraćaju informaciju o mjerenim elementima i statusu. U FOI postoji više prostorija, dvorana, područja i sl. (sve njih zovemo mjesta) na kojima se mogu postaviti uređaji. Podaci o mjestima, senzorima i aktuatorima nalaze se u priloženim datotekama. 

<br />

U sustavu postoji samo jedan generator slučajnog broja koja se inicijalizira zadanim sjemenom prilikom pokretanja programa. Generator ima metode public int dajSlucajniBroj(int odBroja, int doBroja) i public float dajSlucajniBroj(float odBroja, float doBroja). 

<br />

Konfiguracija: Na početku se učitavaju mjesta te je potrebno osigurati da tijekom izvršavanja programa postoji samo jedna instanca pojedinog mjesta na bazi jednoznačnosti naziva mjesta. Ujedno se svakom mjestu pridružuje njegov jednoznačni broj unutar mjesta u intervalu 1 - 1000 pomoću generatora slučajnog broja, zatim se mjesto pohranjuje u niz po modelu FIFO unutar određene klase koja sadrži sva mjesta. Zatim se za mjesto postave potrebni uređaji. Svako mjesto ima zadani broj senzora i aktuatora. Temeljem tipa mjesta potrebno je pridružiti potreban broj senzora i aktuatora koji svojim tipom odgovaraju tipu mjesta. Generatorom slučajnog broja odabire se pojedini senzor ili aktuator između onih koji odgovaraju tipu. Svakom pridruženom senzoru i aktuatoru pridružuje se njegov jednoznačni broj unutar senzora ili aktuatora u intervalu 1 - 1000 pomoću generatora slučajnog broja.  

<br />

Slijedi faza inicijalizacije sustava koja prolazi po mjestima na bazi rastuće vrijednosti broja mjesta uz korištenje vlastitog iteratora. Tu se svakom uređaju pošalje inicijalizacijska poruka. Uređaj vraća svoj status (1 - u redu, 0 - pogreška), temeljem generatora slučajnog broja uz prosječnu 90% ispravnost. Ako je pogreška uređaj se ne koristi u nastavku. Nakon faze inicijalizacije pojedinog mjesta slijedi faza opremanja mjesta tijekom koje se pojedinom ispravnom aktuatoru pridružuje određeni broj ispravnih senzora koji postoje unutar tog mjesta. Broj se dobije generatorom slučajnog broja u intervalu od 1 do broj ispravnih senzora tog mjesta. Senzori se odabiru slučajnim odabirom. Jedan senzor može biti pridružen većem broju aktuatora. Za kraju faze opremanja za svaki aktuator prikazuju se njemu pridruženi senzori, te se za svaki senzor prikazuje kojim aktuatorima je pridružen.

<br />

U nastavku temeljem odabranog algoritma provjere (koji se dinamički učitava, a potrebno je implementirati min 3 različita algoritma koji ne koriste dodatne strukture podataka za podatke o mjestima a baziraju se na korištenju iteratora) dretva u zadanim ciklusima provjerava po jedno mjesto s time da jedno mjesto ne može biti dva puta provjereno dok nisu sva ostala mjesta provjerena. Provjera mjesta polazi od utvrđivanja statusa njegovih uređaja. Uređaj koji 3 puta u nizu (3 slijedna ciklusa dretve za isto mjesto) vrati pogrešku, označava se kao neispravan te ga je potrebno odmah zamijeniti novim uređajem istog modela i inicijalizirati. Potrebno je ispisati informacije u obavljanim poslovima.

<br />

Nakon provjere (utvrđivanje stanja) od senzora se traži očitanje vrijednosti i ispiše se informacija o tome. Kod aktuatora se, samo u slučaju kada je bila promjena vrijednosti kod barem jednog senzora koji je pridružen tom aktuatoru, prvo traži očitanje vrijednosti a zatim izvršavanje radnje. U jednom ciklusu provjere mjesta aktuator može samo jednom obaviti svoju radnju. Kod onih koji ima binarnu vrstu obavlja se suprotna radnja trenutnom stanju. Kod ostalih se generira slučajni broj u intervalu pojedinog aktuatora.Taj broj se dodaje u smjeru prethodne operacije. Prvo se počinje s povećavanjem vrijednosti. Kada se dođe do maksimalne vrijednosti aktuatora tada se mijenja smjer i počinje se sa smanjivanjem vrijednosti. I tako se provodi šetnja od jedne (min) do druge strane vrijednosti (max) i obratno. Ispisuju se informacije o tome. Nakon što prođe zadano vrijeme izvršavanja programa, više se ne izvršava dretva. Slijedi prikaz statističkih podataka i ostalih informacija o svakom mjestu i njegovim uređajima tijekom rada.
