/**
 * Card classes
 *
 * @author Alan Zhang
 */
public class Card {
    // card name
    private String name;
    // card group
    Group group;
    // card points
    private int point;

    // constructor
    public Card(String name, Group group, int point) {
        this.name = name;
        this.group = group;
        this.point = point;
    }

    /**
     * name getter
     * 
     * @return - name
     */
    public String getName() {
        return name;
    }

    /**
     * group getter
     * 
     * @return - group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * point getter
     * 
     * @return point
     */
    public int getPoint() {
        return point;
    }
}
