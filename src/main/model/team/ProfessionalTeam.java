package main.model.team;

public class ProfessionalTeam extends Team {

    public ProfessionalTeam() {
        super(8, "ProfessionalTeam");
    }

    public ProfessionalTeam(int level){
        super(level, "ProfessionalTeam");
    }

    public ProfessionalTeam(int level, String name){
        super(level, name);
    }

    @Override
    public void celebrate() {
        System.out.println("The " + name + " are partying with their ridiculous budgets and sponsors.." +
                "\nThey wonder whether the amateur teams are having more fun atm.");
    }
}
