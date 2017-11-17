package main.model.tournament.game;

import main.model.team.Team;
import main.model.tournament.GameEvent;

import java.util.Random;

public class Game {
    private Team team1, team2;
    private int team1_score = 0, team2_score = 0;
    private Random random;

    public Game(Team team1, Team team2, Random random){
        this.team1 = team1;
        this.team2 = team2;
        this.random = random;
    }

    // Play a single game. Winner is returned and gets another skillpoint!!
    public Team playGame(Team team1, Team team2){

        // Output
        System.out.println("Team " + team1.getName() + " is going to play against " + team2.getName() + " tonight!! Who knows what this'll bring..");
        if(team1.getProficiencyLevel() > team2.getProficiencyLevel()){
            System.out.println("Team " + team1.getName() + " is the preferred team");
        }
        else if(team1.getProficiencyLevel() < team2.getProficiencyLevel()){
            System.out.println("Team " + team2.getName() + " is the preferred team");
        }
        else{
            System.out.println("Both teams seem to have an equal chance at winning!");
        }

        return simulateGame(team1, team2);

    }

    // Simulate a game
    private Team simulateGame(Team team1, Team team2){
        // Reset team's morales
        team1.resetMorale();
        team2.resetMorale();

        // Divide the game into 17 * 5 minutes, starting from 5'.
        for (int i = 5; i <= 90; i += 5) {
            // Get random gameEvent
            GameEvent gameEvent  = getRandomGameEvent();

            // Handle game event
            handleGameEvent(i, gameEvent);
        }

        // Get the winning team
        Team winningTeam;
        if(team1_score > team2_score){
            winningTeam = team1;
            winningTeam.addSkillPoints(team2);
        }
        else if(team2_score > team1_score) {
            winningTeam = team2;
            winningTeam.addSkillPoints(team1);
        }
        else{
            // We are playing an extension since we still don't have a winner
            playExtension();
            if(team1_score > team2_score) {
                winningTeam = team1;
                winningTeam.addSkillPoints(team2);
            }
            else if(team2_score > team1_score) {
                winningTeam = team2;
                winningTeam.addSkillPoints(team1);
            }
            else{
                // Into the penalty round
                penalties();
                if(team1_score > team2_score) {
                    winningTeam = team1;
                    winningTeam.addSkillPoints(team2);
                }
                else {
                    winningTeam = team2;
                    winningTeam.addSkillPoints(team1);
                }
            }
        }

        System.out.println("Team " + winningTeam + " has won the game with an amazing score of " + team1_score + "-" + team2_score);
        System.out.println("Thanks for watching, and a good evening.");
        System.out.println();
        return winningTeam;
    }

    private void playExtension(){
        System.out.println("No winner yet! We are going into the extension!!");
        // Divide the game into 6 * 5 minutes, starting from 90'.
        for (int i = 95; i <= 120; i += 5) {
            // Get random gameEvent
            GameEvent gameEvent  = getRandomGameEvent();

            // Handle game event
            handleGameEvent(i, gameEvent);
        }
    }

    // Handle a gameEvent
    private void handleGameEvent(int time, GameEvent gameEvent){
        boolean team_1_won = determineWinningTeam(gameEvent.positive);
        switch (gameEvent){
            case Nothing:
                break;
            case Goal:
                // Raise morale at winningTeam
                if(team_1_won){
                    team1_score++;
                    team1.raiseMorale();
                    team2.lowerMorale(1);
                }
                else{
                    team2_score++;
                    team2.raiseMorale();
                    team1.lowerMorale(1);
                }
            case FoulMedium:
                if(team_1_won) team1.lowerMorale(1);
                else team2.lowerMorale(1);
            case FoulSevere:
                if(team_1_won) team1.lowerMorale(5);
                else team2.lowerMorale(5);
        }

        // Print game event per time
        printGameEvent(time, gameEvent, team_1_won);
    }

    private void penalties(){
        System.out.println("No winner still.. Going into PENALTY ROUND!!");
        // Take five penalties
        for (int i = 0; i < 5; i++) {
            takePenaltyForBothTeams(121 + i);
        }

        if(team1_score != team2_score) return;

        // If we STILL do not have a winner, repeat takePenaltyForBothTeams until we do.
        int time = 126;
        while (team1_score != team2_score){
            takePenaltyForBothTeams(time);
            time++;
        }
        // Now, we finally have a winning team..
    }

    private void takePenaltyForBothTeams(int time){
        // Start with 120

        // The gameEvent is Goal.
        GameEvent gameEvent = GameEvent.Goal;
        boolean team1_favored;

        // Penalty for team 1
        // If the favor is for team 1
        team1_favored = determineWinningTeam(true);
        if(team1_favored) {
            team1_score++;
            team1.raiseMorale();
            printGameEvent(time, gameEvent, true);
        }

        // Penalty for team 2
        team1_favored = determineWinningTeam(true);
        if(!team1_favored) {
            team2_score++;
            team2.raiseMorale();
            printGameEvent(time, gameEvent, false);
        }
    }

    // Prints a game event including the score.
    private void printGameEvent(int time, GameEvent gameEvent, boolean team_1_won){
        System.out.print(time + "' " + team1_score + "-" + team2_score);
        if(gameEvent != GameEvent.Nothing){
            System.out.print(" " + gameEvent.name + " for " + (team_1_won ? team1 : team2));
        }
        System.out.println();
    }

    // Determines the winning team based on skillLevel and morale. These two change over time.
    // Returns a boolean which is true if the first team has won.
    private boolean determineWinningTeam(boolean positive){
        // First, calculate total of both skill levels and morale
        float totalSkillLevel = team1.getProficiencyLevel() + team2.getProficiencyLevel() + team1.getMorale() + team2.getMorale();
        // Divide team 1's skill level by total to get something in between [0-1].
        float team1Odds = (team1.getProficiencyLevel() + team1.getMorale()) / totalSkillLevel;
        // Toss the coin!!
        float odds = random.nextFloat();

        if(odds < team1Odds){
            // Team 1 has won. Give the positive case to team1.
            return positive;
        }
        else{
            return !positive;
        }
    }

    // Randomize a gameEvent.
    private GameEvent getRandomGameEvent(){
        float totalOdds = 0;
        float odds = random.nextFloat() * 100;
        for(GameEvent gameEvent : GameEvent.values()){
            totalOdds += gameEvent.baseOdds;
            if(odds < totalOdds){
                return gameEvent;
            }
        }
        return GameEvent.Nothing;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}
