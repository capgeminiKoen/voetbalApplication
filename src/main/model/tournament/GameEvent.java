package main.model.tournament;

public enum GameEvent {

    Nothing(50, "", true),
    Goal(25, "GOAAAL", true),
    FoulMedium(10, "Medium foul", false),
    FoulSevere(5, "Severe foul", false);

    public float baseOdds;
    public String name;
    public boolean positive;
    GameEvent(float odds, String eventName, boolean positive){
        baseOdds = odds;
        this.name = eventName;
        this.positive = positive;
    };


}
