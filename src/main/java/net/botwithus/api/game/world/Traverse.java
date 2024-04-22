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
        var player = Client.getLocalPlayer();
        if (player == null) {
            ScriptConsole.println("[Traverse#to]: Player is null");
            return false;
        }

        var pCoord = player.getCoordinate();
        if (pCoord != null && (pCoord.getX() > 6400 || pCoord.getY() > 12800)) {
            ScriptConsole.println("[Traverse#to]: Player is in an instance, attempting to walk to %d, %d", coordinate.getX(), coordinate.getY());
            return Traverse.bresenhamWalkTo(coordinate, true, RandomGenerator.nextInt(8, 14));
        }
        var webMovement =  Movement.traverse(NavPath.resolve(coordinate));
        ScriptConsole.println("[Traverse#to]: Player is not in an instance, attempting to walk to %d, %d | %s", coordinate.getX(), coordinate.getY(), webMovement);
        if (webMovement == null || webMovement == TraverseEvent.State.FAILED || webMovement == TraverseEvent.State.NO_PATH) {
            ScriptConsole.println("[Traverse#to]: WebMovement failed, attempting to walk to via bresenham %d, %d", coordinate.getX(), coordinate.getY());
            return Traverse.bresenhamWalkTo(coordinate, true, RandomGenerator.nextInt(8, 14));
        }

        ScriptConsole.println("[Traverse#to]: Traversal failed.");
        return false;
    }

    public static boolean to(Area area) {
        return to(area.getRandomWalkableCoordinate());
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
                ScriptConsole.println("[Traverse#bresenhamWalkTo]: Attempting to walk to %d, %d, using Bresenham's line algorithm. Distance of %s exceeded stepSize of %s", coordinate.getX(), coordinate.getY(), distance, stepSize);
                int stepX = currentCoordinate.getX() + dx * stepSize / distance;
                int stepY = currentCoordinate.getY() + dy * stepSize / distance;
                Traverse.walkTo(new Coordinate(stepX, stepY, currentCoordinate.getZ()), minimap);
                return true;
            } else {
                ScriptConsole.println("[Traverse#bresenhamWalkTo]: Attempting to walk to %d, %d, using Bresenham's line algorithm. Distance of %s is within stepSize of %s", coordinate.getX(), coordinate.getY(), distance, stepSize);
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
