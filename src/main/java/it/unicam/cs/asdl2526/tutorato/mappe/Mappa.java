package it.unicam.cs.asdl2526.tutorato.mappe;

/**
 * Classe di tipo Mappa che rappresenta la mappa di LineaCity.
 * @author Federico Di Petta
 */
public class Mappa {


    private final Fermata[] fermate;
    private final Mezzo[] mezzi;

    public Mappa(Fermata[] fermate, Mezzo[] mezzi) {
        this.fermate = fermate;
        this.mezzi = mezzi;
    }

    /**
     * Verifica se esiste un tragitto possibile tra due fermate.
     * @param partenza fermata di partenza
     * @param arrivo fermata di arrivo
     * @return Ritorna true se esiste un tragitto possibile, false altrimenti.
     * @throws NullPointerException Se una delle due fermate è null.
     * @throws IllegalArgumentException Se le due fermate sono uguali oppure non fanno parte della mappa.
     */
    public boolean tragittoPossibile(Fermata partenza, Fermata arrivo) {
        if (partenza == null || arrivo == null)
            throw new NullPointerException("Nessuna delle due fermate può essere null");
        if(partenza.equals(arrivo))
            throw new IllegalArgumentException("Le due fermate non possono essere uguali");
        boolean partenzaIsPresent = false;
        boolean arrivoIsPresent = false;
        for (int i = 0; i < fermate.length && (!partenzaIsPresent || !arrivoIsPresent); i++) {
            if (!partenzaIsPresent && fermate[i].equals(partenza))
                partenzaIsPresent = true;
            if (!arrivoIsPresent && fermate[i].equals(arrivo))
                arrivoIsPresent = true;
        }
        if(!partenzaIsPresent || !arrivoIsPresent)
            throw new IllegalArgumentException("Non sono entrambe presenti nulla mappa");
        for (Mezzo mezzo : mezzi) {
            if (mezzo.possibile(partenza, arrivo))
                return true;
        }
        return false;
    }

    /**
     * Calcola il mezzo più veloce per effettuare il tragitto tra due fermate.
     * @param partenza fermata di partenza
     * @param arrivo fermata di arrivo
     * @return Ritorna il mezzo più veloce per effettuare il tragitto.
     * @throws NullPointerException Se una delle due fermate è null.
     * @throws IllegalArgumentException Se le due fermate sono uguali, non fanno parte della mappa oppure non esiste un tragitto possibile tra le due fermate.
     */
    public Mezzo mezzoPiuVeloce(Fermata partenza, Fermata arrivo) {
        if(!tragittoPossibile(partenza, arrivo))
            throw new IllegalArgumentException("Non esiste un tragitto possibile");
        Mezzo fastestMezzo = null;
        int fastestTempo = Integer.MAX_VALUE;
        for (Mezzo mezzo : mezzi) {
            if(mezzo.possibile(partenza, arrivo)){
                int tempoImpiegato = mezzo.tempoImpiegato(partenza, arrivo);
                if(tempoImpiegato < fastestTempo){
                    fastestMezzo = mezzo;
                    fastestTempo = tempoImpiegato;
                }
            }
        }
        return fastestMezzo;
    }

    /**
     * Verifica se esiste un percorso possibile tra due fermate.
     * Un percorso è composto da al massimo due mezzi.
     * @param partenza fermata di partenza
     * @param arrivo fermata di arrivo
     * @return true se esiste un percorso possibile, false altrimenti
     */
    public boolean percorsoPossibile(Fermata partenza, Fermata arrivo)
    {
        if (partenza == null || arrivo == null)
            throw new NullPointerException("Nessuna delle due fermate può essere null");
        if(partenza.equals(arrivo))
            throw new IllegalArgumentException("Le due fermate non possono essere uguali");
        //Controllo se esiste un tragitto diretto
        if (tragittoPossibile(partenza, arrivo))
            return true;

        //Controllo se esiste un percorso con due mezzi
        for (Mezzo m1 : mezzi) {
            for (Mezzo m2 : mezzi) {
                if (m1 == m2) continue; // evita di usare lo stesso mezzo due volte

                // cerco una fermata intermedia comune
                for (Fermata intermedia : fermate) {
                    if (m1.possibile(partenza, intermedia) && m2.possibile(intermedia, arrivo))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Calcola il percorso più veloce tra due fermate.
     * Il percorso è al massimo costruito su due mezzi per semplicità.
     * @param partenza partenza
     * @param arrivo arrivo
     * @return Ritorna il percorso più veloce tra le due fermate.
     * @throws NullPointerException Se una delle due fermate è null.
     * @throws IllegalArgumentException Se le due fermate sono uguali, non fanno parte della mappa oppure non esiste un tragitto possibile tra le due fermate.
     */
    public Percorso percorsoPiuVeloce(Fermata partenza, Fermata arrivo)
    {
        if (partenza == null || arrivo == null)
            throw new NullPointerException("Nessuna delle due fermate può essere null");
        if (partenza.equals(arrivo))
            throw new IllegalArgumentException("Le due fermate non possono essere uguali");
        if (!percorsoPossibile(partenza, arrivo))
            throw new IllegalArgumentException("Non esiste un percorso possibile tra le due fermate");

        Percorso migliore = null;
        int tempoMinimo = Integer.MAX_VALUE;

        //Percorsi con due mezzi
        for (Mezzo m1 : mezzi) {
            for (Mezzo m2 : mezzi) {
                for (Fermata intermedia : fermate) {
                    if (m1.possibile(partenza, intermedia) && m2.possibile(intermedia, arrivo)) {
                        int tempo = m1.tempoImpiegato(partenza, intermedia) + m2.tempoImpiegato(intermedia, arrivo);
                        if (tempo < tempoMinimo) {
                            tempoMinimo = tempo;
                            migliore = new Percorso(new Fermata[]{partenza, intermedia, arrivo},
                                    new Mezzo[]{m1, m2});
                        }
                    }
                }
            }
        }

        if (migliore == null)
            throw new IllegalArgumentException("Non esiste alcun percorso possibile");

        return migliore;
    }


}
