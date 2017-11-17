package main.controller;

import main.model.team.Team;
import main.model.tournament.GameEvent;
import main.model.tournament.Tournament;
import main.model.tournament.game.Game;

import java.util.ArrayList;
import java.util.Random;

public class TournamentController {

    private Tournament tournament;
    private Random random;

    public TournamentController(){
        random = new Random();
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    // Plays a tournament and returns the team that won
    public Team playTournament(){

        // Check whether we have a tournament!
        if(tournament == null){
            System.out.println("Please attach a tournament!!");
            return null;
        }

        // First, print teams
        printTeams();

        // Make sure there is a power of 2 of teams
        int numberOfTeams = tournament.getNumberOfTeams();
        if((numberOfTeams & (numberOfTeams - 1)) != 0 || numberOfTeams < 2) {
            System.out.println("Sorry, but we need n^2 teams (where n >= 1) to continue.");
            return null;
        }

        // calculate number of rounds
        int numberOfRounds = calculateNumberOfRounds(numberOfTeams);
        System.out.println("This tournament will consist of " + numberOfRounds + " rounds!");

        for (int i = 0; i < numberOfRounds; i++) {
            // Play a single, complete round
            playRound(i+1);
        }

        // Check if we indeed have only one team left.
        if(tournament.getNumberOfTeams() != 1){
            System.out.println("Somehow, we ended up messing something up, since we do not have a winner, but multiple.");
            System.out.println("We have " + tournament.getNumberOfTeams() + " teams to be exact. :(");
            return null;
        }
        else{
            return tournament.getTeamByIndex(0);
        }
    }

    // Play a round
    private void playRound(int roundNumber){

        // output something nice
        System.out.println();
        System.out.println("ROUND " + roundNumber + " IS STARTING!!");
        System.out.println("--------------------------------------");

        // get current tournament size
        int numberOfTeams = tournament.getTeams().size();

        // Shuffle teams first
        tournament.shuffleTeams();

        // This rounds winning teams will be saved temporarily
        ArrayList<Team> winningTeams = new ArrayList<>();

        // Clear the winning teams array
        winningTeams.clear();

        // Let all teams play against their selected opponent.
        // 1 plays against 2, 3 against 4, etc.
        for (int j = 0; j < numberOfTeams / 2; j++) {
            // Select two teams at a time
            Team team1 = tournament.getTeamByIndex(j * 2);
            Team team2 = tournament.getTeamByIndex(j * 2 + 1);

            // Make a game with these two teams
            Game game = new Game(team1, team2, random);

            // Let's Roll!!
            Team winningTeam = game.playGame(team1, team2);

            // Add winning team to next round ;)
            winningTeams.add(winningTeam);
        }
        // Update the teamlist with the winningteams alone
        tournament.copyWinningTeams(winningTeams);
    }


    private int calculateNumberOfRounds(int numberOfTeams){
        int rounds = 0;
        // Keep on dividing the teams by 2 to know the number of rounds.
        while(numberOfTeams != 1){
            numberOfTeams /= 2;
            rounds++;
        }
        return rounds;
    }

    public void printTeams(){
        System.out.println("Good day all. We are going to play this tournament with the following teams:");
        for( Team team : tournament.getTeams()){
            System.out.println(team.getName() + " - SL " + team.getProficiencyLevel());
        }
    }

}
