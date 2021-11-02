# Bots and Bidder

Um das Interface richtig testen zu können, habe ich ein kleine Framework Klasse *Auction* darum gebastelt. 
So etwas in der Art müsstet ihr ja schon haben um die Bidder gegeneinander antreten zu lassen. 

Ich habe mir noch überlegt so eine Art Bot-Arena zu implementieren, wo dann eine Menge von Bots übergeben werden
kann, die dann mit ausgewürfelten Startbedingungen (also Geld und Mengeneinheiten) gegeneinander antreten.
Es macht glaube ich einen großen Unterschied, ob man 1000 Runden spielt oder nur 10. 
Dann könnte man mit einer Prozentzahl angeben wie oft ein Bot A gegen ein Bot B gewonnen hat.
Bei statischen Bots dürfte das dann schon 100% bzw. 0 %. Sobald man dynamische Bots implementiert
die zumindest teilweise zufällig bieten, wird es aber schon interessant wie oft einer gewinnt.

## Statisch und Dynamisch Bots

Als statische Bots würde ich solche bezeichnen, die unabhängig vom Bieter-Verhalten des anderes Bots bieten.
Darunter fallen AveragerBidder, FixedBidder, RandomBidder. Die anderen Bots reagieren quasi auf das letzte Gebot des anderen Bots. 
Ich denke der beste Bot den man implementieren kann, errät die Strategie des anderen und bietet dann +1.
Prinzipiell muss ein guter Bot wenn er gewinnen möchte, beim Gewinnen der Menge möglichst +1 mehr bieten und beim
Verlieren möglichst 0. Das ist logisch :-)


Ich hätte jetzt noch ein paar Ideen für andere Bots die gut sein könnten (HackyBot, CleverRandomBot) und vielleicht
einer der ansatzweise die Strategie des Gegners erraten kann. Das übersteigt aber den Zeitrahmen. Besonders den HackyBidder hätte ich noch gerne implementiert.VIelleicht kann man in der Zukunft die Auction-Klasse gleich mitliefern,
dann muss die ein Bewerber nicht selber schreiben :-)

## Junit Tests

Die Art wie dich die Bots testen würden, habe ich mal nur so skizziert, weil das bekanntermaßen viel Zeit braucht.
Ich halt sehr viel von Tests, aber nicht unbedingt für Code der nur zum Spaß geschrieben wird.


