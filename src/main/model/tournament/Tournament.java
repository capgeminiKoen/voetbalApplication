package main.model.tournament;

import main.model.team.Team;

import java.util.ArrayList;
import java.util.Collections;

public class Tournament {
    private ArrayList<Team> teams;

    // Create an arrayList obj
    public Tournament(){
        teams = new ArrayList<>();
    }

    // Add a team
    public void addTeam(Team team){
        // Prevent duplicates
        if(!teams.contains(team)){
            teams.add(team);
        }
    }

    // Randomly shuffle teams for random match each time
    public void shuffleTeams(){
        Collections.shuffle(teams);
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public Team getTeamByIndex(int id){
        return teams.get(id);
    }

    public void copyWinningTeams(ArrayList<Team> winningTeams){
        teams.clear();
        for (Team winningTeam : winningTeams){
            teams.add(winningTeam);
        }
    }

    public int getNumberOfTeams(){
        return teams.size();
    }
}
