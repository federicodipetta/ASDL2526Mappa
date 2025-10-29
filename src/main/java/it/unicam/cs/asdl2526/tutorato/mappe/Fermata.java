package it.unicam.cs.asdl2526.tutorato.mappe;

/**
 * Una classe di tipo fermata identifica la fermata fisica della città.
 * Essendo la LineaCity la città ci basta sapere la posizione senza coordinate.
 * @author Federico Di Petta
 */
public class Fermata implements ComparabileInDistanza<Fermata>, Comparable<Fermata> {

    public final int posizione;
    public final String nome;

    /**
     * Una Fermata è caratterizzata da una posizione e un nome.
     * @param posizione posizione
     * @param nome nome della fermata
     * @throws NullPointerException Se il nome è null
     */
    Fermata(int posizione, String nome) {
        if (nome == null) {
            throw new NullPointerException("Il nome non può essere null");
        }
        this.posizione = posizione;
        this.nome = nome;
    }

    @Override
    public int compareToInDistanza(Fermata other) {
        return Math.abs(this.posizione - other.posizione);
    }

    @Override
    public int compareTo(Fermata other)
    {
        if(this.posizione < other.posizione)
        {
            return -1;
        }
        else if (this.posizione > other.posizione)
        {
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public String getNome() {
        return nome;
    }

    public int getPosizione() {
        return posizione;
    }


    /**
     * Due fermate sono uguali se hanno la stessa posizione
     * @param other l'altra fermata
     * @return ritorna true se le fermate hanno la stessa posizione, false altrimenti.
     */
    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof Fermata))
            return false;
        Fermata o = (Fermata) other;
        return this.posizione == o.posizione;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + nome.hashCode();
        hash = hash * prime + Integer.hashCode(posizione);
        return hash;
    }
}
