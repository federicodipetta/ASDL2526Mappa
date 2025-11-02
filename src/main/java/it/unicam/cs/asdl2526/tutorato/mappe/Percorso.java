package it.unicam.cs.asdl2526.tutorato.mappe;

/**
 * Classe di tipo Percorso che rappresenta un percorso tra fermate utilizzando mezzi.
 * @author Federico Di Petta
 */
public class Percorso
{
    private final Fermata[] fermate;
    private final Mezzo[] mezzi;
    private int durata;

    /**
     * Costruisce un percorso dato un array di fermate e un array di mezzi.
     * @param fermate array di fermate
     * @param mezzi array di mezzi
     * @throws NullPointerException Se uno dei due array è null
     * @throws IllegalArgumentException Se la lunghezza degli array non è compatibile
     *                               (numero mezzi = numero fermate - 1)
     *                             oppure se non è possibile effettuare il tragitto
     */
    public Percorso(Fermata[] fermate, Mezzo[] mezzi)
    {
        if(fermate == null || mezzi == null)
            throw new NullPointerException("Uno dei due array è null");
        else if(mezzi.length != fermate.length - 1)
        {
            throw new IllegalArgumentException("La lunghezza degli array non è compatibile");
        }
        for(int i = 0; i < fermate.length - 1; i++)
        {
            if(!(mezzi[i].possibile(fermate[i], fermate[i + 1])))
            {
                throw new IllegalArgumentException("Non è possibile effettuare il tragitto");
            }
            else
            {
                this.durata += mezzi[i].tempoImpiegato(fermate[i], fermate[i+1]);
            }
        }
        this.fermate = fermate;
        this.mezzi = mezzi;
    }

    public Fermata[] getFermate() {
        return fermate;
    }

    public Mezzo[] getMezzi() {
        return mezzi;
    }

    /**
     * Restituisce la durata totale del percorso in minuti.
     * L'operazione deve essere eseguita in O(1).
     * @return durata totale in minuti
     */
    public int getDurataTotale()
    {
        return durata;
    }

    /**
     * Due percorsi sono uguali se hanno le stesse fermate e gli stessi mezzi nello stesso ordine.
     * @param other l'altro percorso
     */
    @Override
    public boolean equals(Object other)
    {
       if(other instanceof Percorso)
       {
           for(int i = 0; i < fermate.length - 1; i++)
           {
               if(!(this.fermate[i].equals(((Percorso) other).fermate[i]) && this.mezzi[i].equals(((Percorso) other).mezzi[i])))
               {
                   return false;
               }
           }
           return true;
       }
       else
       {
           return false;
       }
    }

    @Override
    public int hashCode()
    {
        int valoreHash = 0;
        for (int i = 0; i < fermate.length - 1; i++)
        {
            valoreHash = fermate[i].hashCode() + mezzi[i].hashCode();
        }
        return valoreHash;
    }
    
}
