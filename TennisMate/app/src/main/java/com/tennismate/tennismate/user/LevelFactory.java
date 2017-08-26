package com.tennismate.tennismate.user;
import com.tennismate.tennismate.user.Level.*;

public class LevelFactory
{
    public Level createLevel(int level){

        Level l;
        switch (level){

            case 1:
                l =  new Level(1, "ITN-1", "This player has had intensive" +
                        " training for national tournament competition at the" +
                        " junior and senior levels and has extensive professional" +
                        " tournament experience. Currently holds or is capable of holding" +
                        " an ATP / WTA ranking and their major source of income is through" +
                        " tournament prize money.");
                break;

            case 2:
                l =  new Level(2, "ITN-2", "This player has power and / or consistency as" +
                        " a major weapon. Can vary strategies and styles of play in a competitive" +
                        " situation. The player is usually a nationally-ranked player.");
            break;

            case 3:
                l =  new Level(3, "ITN-3", "This player has good shot anticipation and frequently" +
                        " has an outstanding shot or attribute around which a game may be structured." +
                        " Can regularly hit winners and force errors off short balls. Can put away" +
                        " volleys and smashes and has a variety of serves to rely on.");
            break;

            case 4:
                l =  new Level(4, "ITN-4", "This player can use power and spins and has begun to" +
                        " handle pace. Has sound footwork, can control depth of shots, and can vary" +
                        " game plan according to opponents. Can hit first serves with power and can" +
                        " utilise spin on second serves.");
            break;

            case 5:
                l =  new Level(5, "ITN-5", "This player has dependable strokes, including directional" +
                        " control and depth on both groundstrokes and on moderate shots. The player has" +
                        " the ability to use lobs, overheads, approach shots and volleys with some" +
                        " success.");
            break;

            case 6:
                l =  new Level(6, "ITN-6", "This player exhibits more aggressive net play, has" +
                        " improved court coverage, improved shot control and is developing" +
                        " teamwork in doubles.");
            break;

            case 7:
                l =  new Level(7, "ITN-7", "This player is fairly consistent when hitting medium paced" +
                        " shots, but is not yet comfortable with all strokes. The player lacks" +
                        " control over depth, direction and power.");
            break;

            case 8:
                l =  new Level(8, "ITN-8", "This player is able to judge / control where the ball is" +
                        " going and can sustain a short rally.");
            break;

            case 9:
                l =  new Level(9, "ITN-9", "This player needs on court experience, while strokes" +
                        " can be completed with some success.");
            break;

            case 10:
                l =  new Level(10, "ITN-10", "This player is starting to play competitively");
            break;

            default:
                l =  null;
        }
        return l;
    }
}
