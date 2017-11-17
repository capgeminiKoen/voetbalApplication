package main.model.team;

public class AmateurTeam extends Team{

    public AmateurTeam() {
        super(4, "AmateurTeam");
    }

    public AmateurTeam(int level) {
        super(level, "AmateurTeam");
    }

    public AmateurTeam(int level, String name){
        super(level, name);
    }

    @Override
    public void celebrate() {
        System.out.println("The " + name + " are celebrating in their regular pub, because this day will never be forgotten!" +
                "\nThey crash a party with their mascotte like no-one ever did before.");

    }
}
