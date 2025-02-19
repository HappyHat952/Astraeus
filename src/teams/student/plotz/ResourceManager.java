package teams.student.plotz;

import objects.entity.node.Node;
import objects.entity.unit.Unit;
import objects.resource.Resource;

import java.util.ArrayList;

public class ResourceManager {
    public static ArrayList<Resource> takenResources = new ArrayList<Resource>();
    public static ArrayList<Resource> availableResources;
    public static ArrayList<Resource> allResources;
    public static ArrayList<Resource> thrownResources = new ArrayList<>();

    final static private int SAFE_DISTANCE = 3000;

    public ResourceManager()
    {
        allResources = objects.resource.ResourceManager.getResources();
        takenResources = new ArrayList<Resource>();
        availableResources = getSafeResources();
    }

    public static ArrayList<Resource> getSafeResources() {
        ArrayList<Resource> allResources = objects.resource.ResourceManager.getResources();
        ArrayList<Resource> safeResources = new ArrayList<Resource>();
        if (EnemyAnalysis.getFighters().isEmpty() || allResources.isEmpty()) {
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
        if (EnemyAnalysis.getFighters().isEmpty() || nodes.isEmpty()) {
            return null;
        }
        for(Node n: nodes) {
            if (ResourceManager.isSafe(n)) {
                safeNodes.add(n);
            }
        }
        return safeNodes;
    }


    public static boolean isSafe(Resource r) {
        ArrayList<Unit> enemies = EnemyAnalysis.getFighters();
        for(Unit u: enemies) {
            if (u.getDistance(r) < SAFE_DISTANCE) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSafe(Node n) {
        ArrayList<Unit> enemies = EnemyAnalysis.getFighters();

        for(Unit u: enemies) {
            if (u.getDistance(n) < SAFE_DISTANCE) {
                return false;
            }
        }
        return true;
    }




}