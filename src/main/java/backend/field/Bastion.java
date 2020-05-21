package backend.field;
import backend.Carac;
import backend.Personnage;


public class Bastion extends Field{


        public Bastion(){
            super("bastion");
        }

        public void affect(Personnage personnage){
            Carac caracPerso = personnage.getCaracteristique();
            int heal = (int) (caracPerso.getMaxHp() * 0.80);
            personnage.healed(caracPerso.getMaxHp() - heal);
        }

        public void disaffect(Personnage personnage){

        }
    }

