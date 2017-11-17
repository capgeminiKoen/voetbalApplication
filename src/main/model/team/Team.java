package main.model.team;

public abstract class Team {

    private int proficiencyLevel;
    protected String name;
    private float baseMorale = 8;
    private float morale = baseMorale;

    public Team(int proficiencyLevel, String name){
        this.proficiencyLevel = proficiencyLevel;
        this.name = name;
    }

    public int getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(int proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    // Give extra skillpoints based on opponent's level
    public void addSkillPoints(int levelOfOpponent){
        if(levelOfOpponent > this.proficiencyLevel){
            this.proficiencyLevel += 2;
        }
        else {
            this.proficiencyLevel++;
        }
    }
    public void addSkillPoints(Team otherTeam){
        addSkillPoints(otherTeam.getProficiencyLevel());
    }

    // Return name
    public String getName() {
        return name;
    }

    public void resetMorale(){
        morale = baseMorale;
    }

    public float getMorale() {
        return morale;
    }

    // Upgrade morale
    public void raiseMorale(){
        morale++;
    }

    public void lowerMorale(int amount){
        morale -= amount;
        if(morale < 1)
            morale = 1;
    }

    // celebration stub
    public abstract void celebrate();

    @Override
    public String toString() {
        return name;
    }
}
