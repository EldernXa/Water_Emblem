package IA;

import java.util.ArrayList;

public interface Heuristique  {
    int calculerHeuristique(Etat e);
    Etat meilleurEtat(ArrayList<Etat> list);
}
