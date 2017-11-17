package main;

import main.controller.TournamentController;
import main.model.team.AmateurTeam;
import main.model.team.ProfessionalTeam;
import main.model.team.Team;
import main.model.tournament.Tournament;

public class Application {

    public static void main(String[] args) {
        // Create tournament
        Tournament tournament = new Tournament();
        // Create new tournament controller and give it the tournament
        TournamentController tournamentController = new TournamentController();
        tournamentController.setTournament(tournament);

        // Added to show that we can scale this thing up!
        for (int i = 0; i < 8; i++) {
            // Make some teams
            tournament.addTeam(new AmateurTeam(4, "Red amateur devils"));
            tournament.addTeam(new AmateurTeam(4, "Blue amateur devils"));
            tournament.addTeam(new AmateurTeam(3, "Complete amateur noobies"));
            tournament.addTeam(new AmateurTeam(5, "Somewhat moderately good soccerplayers"));
            tournament.addTeam(new ProfessionalTeam(8, "Black professional brigade"));
            tournament.addTeam(new ProfessionalTeam(8, "Silver professional brigade"));
            tournament.addTeam(new ProfessionalTeam(10, "Arsenal"));
            tournament.addTeam(new ProfessionalTeam(14, "FC Barfelona"));
        }

        // Play that funky.. game.
        Team winningTeam = tournamentController.playTournament();
        if(winningTeam != null) {
            System.out.println("WE HAVE A WINNER!!");
            System.out.println(winningTeam + " will recieve endless fame for the game.");
            System.out.println(winningTeam + " has a total Skill level of " + winningTeam.getProficiencyLevel());
            // Print the personalized winning message
            winningTeam.celebrate();
        }
        else{
            String printString = "";
            printString += "It was a dreadful day.. No-one knew how to make a correct schedule," +
                    "\nso no winner could be chosen :'(.. " +
                    "\nThis day is a black day in the history of soccer..";
            System.out.println(printString);
        }
    }
}
