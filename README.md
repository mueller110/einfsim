
------------------------------------------------------------------------------
TODO:
Initialisierungsphase
90%-Quantile
neues Erfinden (Tod der Notfälle?, Unterbrechung: High priority vor Low priority?)


Notaufnahme

In der Notaufnahme eines Krankenhauses erscheint durchschnittlich alle 40min ein Patient. 2 Ärzte versehen Dienst um die Erstbehandlungen durchzuführen. Etwa 20% der Patienten müssen möglichst rasch behandelt werden (= akute Notfälle), der Rest kann warten. Ersteren wird die höchste Priorität 3 zugeordnet (Behandlung durch einen Arzt so rasch als möglich), dann Reduktion der Priorität auf 2 (warten bis wieder ein Arzt verfügbar ist und nochmals Behandlung vor Verlassen der Notaufnahme). Jenen Patienten, die warten können wird Priorität 1 zugeordnet (werden behandelt, wenn sie an der Reihe sind, eher kürzer als akute Notfälle) und erhalten dann ebenfalls Priorität 2 (jedoch auch hier kürzere Behandlungszeit). Danach verlassen auch diese die Notaufnahme.

Zu simulieren ist eine längerer Zeitraum (z.B. 20 Tage), wobei die Notaufnahme 24 Stunden am Tag geöffnet ist. Es sollen ermittelt werden: jeweils maximale und mittlere Anzahl der wartenden akuten Notfälle und restlichen Patienten; die mittlere Wartezeit beider Patientenarten; den Anteil jener Patienten, die nicht warten müssen; Anteil der Patienten, welche maximal 5min warten müssen. 

Weiters von Interesse ist jene Zeit x, sodass 90% der Patienten maximal x Zeiteinheiten in der Notaufnahme verbringen (vom Eintreffen bis zum Verlassen nach Behandlungsende) - genannt 90%-Quantile.

Wie sehen die Ergebnisse aus, wenn zuerst eine 2 Tage lange Initialisierungsphase "vorgeschaltet" wird. D.h. es sind speziell die Ergebnisse nach 0, 2 und 20/22 Tagen von besonderem Interesse.

------------------------------------------------------------------------------

