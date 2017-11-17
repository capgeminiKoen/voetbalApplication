package test;

import main.controller.TournamentController;
import main.model.team.AmateurTeam;
import main.model.team.ProfessionalTeam;
import main.model.tournament.Tournament;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Koen Griffioen
 * WARNING: Not done yet. Main focus was on the application ;-)
 */
public class Testing {
    @Test
    public void testTournamentController(){
        Tournament tournament = new Tournament();
        TournamentController tournamentController = new TournamentController();

        // Check whether we cannot play the game
        assertTrue(tournamentController.playTournament() == null);

        // Add tournament
        tournamentController.setTournament(tournament);

        // Add team
        tournament.addTeam(new AmateurTeam());

        // Check whether we cannot play the game
        assertTrue(tournamentController.playTournament() == null);

        // Add another team
        tournament.addTeam(new ProfessionalTeam());

        // Check whether we cannot play the game
        assertTrue(tournamentController.playTournament() != null);


    }
}
