package net.botwithus.api.game.world;

import com.google.common.flogger.FluentLogger;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.Distance;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.WalkAction;
import net.botwithus.rs3.game.movement.Movement;
import net.botwithus.rs3.game.movement.NavPath;
import net.botwithus.rs3.game.movement.TraverseEvent;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.util.RandomGenerator;
import net.botwithus.rs3.game.Area;
import net.botwithus.rs3.game.Coordinate;

public class Traverse {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    public static boolean to(Coordinate coordinate) {
        return to(coordinate, RandomGenerator.nextInt(12, 20));
    }

    public static boolean to(Coordinate coordinate, int stepSize) {
        var player = Client.getLocalPlayer();
        if (player == null) {
            ScriptConsole.println("[Traverse#to]: Player is null");
            return false;
        }

        var pCoord = player.getCoordinate();
        if (pCoord != null && (pCoord.getX() > 6400 || pCoord.getY() > 12800)) {
            ScriptConsole.println("[Traverse#to]: Player is in an instance, attempting to walk to %s, %s", coordinate.getX(), coordinate.getY());
            return Traverse.bresenhamWalkTo(coordinate, true, stepSize);
        }
        TraverseEvent.State webMovement;
        ScriptConsole.println("[Traverse#to]: Player is not in an instance, attempting to walk to %s, %s | %s", coordinate.getX(), coordinate.getY(), webMovement = Movement.traverse(NavPath.resolve(coordinate)));

        if (webMovement == null || webMovement == TraverseEvent.State.FAILED || webMovement == TraverseEvent.State.NO_PATH) {
            ScriptConsole.println("[Traverse#to]: WebMovement failed, attempting to walk to via bresenham %s, %s", coordinate.getX(), coordinate.getY());
            return Traverse.bresenhamWalkTo(coordinate, true, stepSize);
        }

        ScriptConsole.println("[Traverse#to]: Traversal failed.");
        return false;
    }

    public static boolean to(Area area) {
        var coordinate = area.getRandomWalkableCoordinate();
        if (coordinate == null || (coordinate.getX() == 0 && coordinate.getY() == 0)) {
            ScriptConsole.println("[Traverse#to]: Area#getRandomWalkableCoordinate returned null or (0, 0), picking a random coordinate");
            coordinate = area.getRandomCoordinate();
        }
        return to(coordinate);
    }

    public static boolean bresenhamWalkTo(Coordinate coordinate, boolean minimap, int stepSize) {
        var player = Client.getLocalPlayer();
        if (player == null) {
            ScriptConsole.println("[Traverse#bresenhamWalkTo]: Player is null");
            return false;
        } else {
            Coordinate currentCoordinate = player.getCoordinate();
            int dx = coordinate.getX() - currentCoordinate.getX();
            int dy = coordinate.getY() - currentCoordinate.getY();
            int distance = (int)Math.hypot(dx, dy);
            if (distance > stepSize) {
                ScriptConsole.println("[Traverse#bresenhamWalkTo]: Attempting to walk to %s, %s, using Bresenham's line algorithm. Distance of %s exceeded stepSize of %s", coordinate.getX(), coordinate.getY(), distance, stepSize);
                int stepX = currentCoordinate.getX() + dx * stepSize / distance;
                int stepY = currentCoordinate.getY() + dy * stepSize / distance;
                Traverse.walkTo(new Coordinate(stepX, stepY, currentCoordinate.getZ()), minimap);
                return true;
            } else {
                ScriptConsole.println("[Traverse#bresenhamWalkTo]: Attempting to walk to %s, %s, using Bresenham's line algorithm. Distance of %s is within stepSize of %s", coordinate.getX(), coordinate.getY(), distance, stepSize);
                Traverse.walkTo(coordinate, minimap);
                return true;
            }
        }
    }

    public static boolean walkTo(Coordinate coordinate, boolean minimap) {
        if (coordinate == null) {
            ScriptConsole.println("[Traverse#walkTo]: Coordinate is null");
            return false;
        }
        var result = MiniMenu.interact(WalkAction.WALK.getType(), minimap ? 1 : 0, coordinate.getX(), coordinate.getY());
        ScriptConsole.println("[Traverse#walkTo]: Attempting to walk to %d, %d, %s", coordinate.getX(), coordinate.getY(), result);
        return result && Execution.delayUntil(RandomGenerator.nextInt(8000, 10000), () -> Distance.to(coordinate) < 5);
    }
}
