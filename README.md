# Blocksy - Il Bridge per i tuoi Voti su Minecraft

![Blocksy Logo](https://www.blocksy.it/logo.png)

**Blocksy** è il plugin ufficiale per integrare il tuo server Minecraft con la lista [blocksy.it](https://www.blocksy.it). Questo plugin permette di ricevere i voti effettuati sul sito direttamente nel tuo server, agendo come bridge verso Votifier.

## 🚀 Caratteristiche

- **Leggero**: Progettato per non impattare sulle performance del server.
- **Sicuro**: Comunicazione tramite HTTPS e API Key univoca.
- **Compatibile**: Supporta Spigot, Paper e derivati (1.8 - 1.21+).
- **Codice Disponibile**: Trasparenza totale su come vengono gestiti i dati dei tuoi voti.

## 🛠️ Installazione

1.  Scarica l'ultimo `.jar` dalla sezione [Releases](https://github.com/Blocksy-IT/Blocksy/releases) (oppure compilalo tu stesso).
2.  Inserisci il plugin nella cartella `plugins/` del tuo server.
3.  Assicurati di avere **Votifier** o **NuVotifier** installato e configurato.
4.  Riavvia il server o carica il plugin.
5.  Vai nel pannello di gestione del tuo server su [blocksy.it](https://www.blocksy.it/profile) e richiedi una **API Key**.
6.  Apri `plugins/Blocksy/config.yml` e inserisci la tua chiave:

```yaml
# API Key del tuo server
api-key: "IL-TUO-TOKEN-QUI"

# Intervallo di controllo voti in secondi
check-interval: 5
```

7.  Esegui `/blocksy reload` o riavvia il server.

## 💻 Sviluppo

Il plugin è basato su Maven. Per compilarlo:

```bash
mvn clean package
```

Il file compilato si troverà nella cartella `target/`.

## 🔒 Sicurezza e Privacy

Siamo consapevoli che la fiducia è fondamentale. Blocksy:

- **Non** raccoglie informazioni private sui giocatori (IP, password, ecc.).
- Invia solo richieste **GET** verso il nostro server per verificare la presenza di nuovi voti pendenti.
- I voti vengono processati localmente tramite l'evento standard di Votifier, permettendoti di usare i tuoi listener di premi preferiti.

## 📄 Licenza

Questo progetto è distribuito sotto una licenza **Proprietaria Source-Available**.

- **Permesso**: Visualizzazione del codice e compilazione per uso personale.
- **Divieto**: Modifica, redistribuzione o vendita del codice o del plugin compilato.

Consulta il file [LICENSE](LICENSE) per il testo completo della licenza.

---

Creato con ❤️ per la community italiana di Minecraft da [blocksy.it](https://www.blocksy.it).
