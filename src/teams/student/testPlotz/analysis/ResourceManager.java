package teams.student.testPlotz.analysis;

import objects.entity.node.Node;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


import java.util.ArrayList;
import java.util.Objects;

public class ResourceManager {

    public static ArrayList<Resource> takenResources = new ArrayList<>();
    public static ArrayList<Resource> availableResources;
    public static ArrayList<Resource> allResources;

    public ResourceManager()
    {
        allResources = objects.resource.ResourceManager.getResources();
        takenResources = new ArrayList<>();
        availableResources = getSafeResources();
    }

    public static ArrayList<Resource> getSafeResources() {
        ArrayList<Resource> allResources = objects.resource.ResourceManager.getResources();
        ArrayList<Resource> safeResources = new ArrayList<Resource>();
        if (OverallAnalysis.getEnemy().getFighters().isEmpty() || allResources.isEmpty()) {
            return null;
        }
        if (takenResources.isEmpty()) {
            for (Resource r : allResources) {
                if (isSafe(r)) {
                    safeResources.add(r);
                }
            }
        } else {
            for (Resource r : allResources) {
                if (isSafe(r) && !takenResources.contains(r)) {
                    safeResources.add(r);
                }
            }
        }

        return safeResources;
    }

    public static ArrayList<Node> getSafeNodes() {
        ArrayList<Node> nodes = objects.entity.node.NodeManager.getNodes();
        ArrayList<Node> safeNodes = new ArrayList<Node>();
        if (OverallAnalysis.getEnemy().getFighters().isEmpty() || nodes.isEmpty()) {
            return null;
        }
        for(Node n: nodes) {
            if (isSafe(n)) {
                safeNodes.add(n);
            }
        }
        return safeNodes;
    }


    public static boolean isSafe(Resource r) {
        ArrayList<Unit> enemies = OverallAnalysis.getEnemy().getFighters();
        for(Unit u: enemies) {
            if (u.getDistance(r) < 2500) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSafe(Node n) {
        ArrayList<Unit> enemies = OverallAnalysis.getEnemy().getFighters();

        for(Unit u: enemies) {
            if (u.getDistance(n) < 2500) {
                return false;
            }
        }
        return true;
    }

    public static void draw(Graphics g) {
        if (getSafeResources() != null) {

            for (Resource r : getSafeResources()) {
                if (!r.isPickedUp()) {
                    g.setColor(Color.green);
                    g.fillOval(r.getX(), r.getY(), 20, 20);
                }
            }
        }
        for (Resource r: Objects.requireNonNull(takenResources)) {
            if (r != null && !r.isPickedUp()) {
                g.setColor(Color.yellow);
                g.fillOval(r.getX(), r.getY(), 20, 20);
            }
        }
        for (Node n: Objects.requireNonNull(getSafeNodes())) {
            g.setColor(Color.green);
            g.drawOval(n.getX(), n.getY(), 20, 20);
        }

    }

}
