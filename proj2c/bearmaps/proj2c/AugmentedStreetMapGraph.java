package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private Map<Point, Long> PointToID = new HashMap<>();
    private Map<String, List<Node>> cleanNameToNodes = new HashMap<>();
    private final KDTree kdTree;
    private final MyTrieSet trieSet = new MyTrieSet();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();

        for (Node node : nodes) {
            if (node.name() != null) {
                String cleanName = cleanString(node.name());
                trieSet.add(cleanName);

                // multiple nodes share the same name
                cleanNameToNodes.putIfAbsent(cleanName, new LinkedList<>());
                cleanNameToNodes.get(cleanName).add(node);
            }

            long id = node.id();
            // only consider the node that has neighbors and turn these nodes to Points to
            // serve the KDTree
            if (!this.neighbors(id).isEmpty()) {
                PointToID.put(new Point(node.lon(), node.lat()), node.id());
            }
        }
        kdTree = new KDTree(new ArrayList<>(PointToID.keySet()));
    }

    /**
     * For Project Part II Returns the vertex closest to the given longitude and
     * latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nearest = kdTree.nearest(lon, lat);
        return PointToID.get(nearest);
    }

    /**
     * For Project Part III (gold points) In linear time, collect all the names of
     * OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our
     *               without punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name
     *         matches the cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> cleanNames = trieSet.keysWithPrefix(cleanString(prefix));
        HashSet<String> locationSet = new HashSet<>();
        for (String name : cleanNames) {
            List<Node> sameNameNodes = cleanNameToNodes.get(name);
            for (Node node : sameNameNodes) {
                locationSet.add(node.name());
            }
        }
        return new LinkedList<>(locationSet);
    }

    /**
     * For Project Part III (gold points) Collect all locations that match a cleaned
     * <code>locationName</code>, and return information about each node that
     * matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the cleaned
     *         <code>locationName</code>, and each location is a map of parameters
     *         for the Json response as specified: <br>
     *         "lat" -> Number, The latitude of the node. <br>
     *         "lon" -> Number, The longitude of the node. <br>
     *         "name" -> String, The actual name of the node. <br>
     *         "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> locations = new LinkedList<>();
        List<Node> sameNameNodes = cleanNameToNodes.get(cleanString(locationName));

        if (sameNameNodes != null) {
            for (Node node : sameNameNodes) {
                Map<String, Object> locationInfo = new HashMap<>();
                locationInfo.put("lat", node.lat());
                locationInfo.put("lon", node.lon());
                locationInfo.put("name", node.name());
                locationInfo.put("id", node.id());
                locations.add(locationInfo);
            }
        }
        return locations;
    }

    /**
     * Useful for Part III. Do not modify. Helper to process strings into their
     * "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
