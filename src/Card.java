public class Card {

    private String name;
    Group group;
    private int point;

    public Card(String name, Group group, int point){
        this.name = name;
        this.group = group;
        this.point = point;
    }

    public String getName(){
        return name;
    }

    public Group getGroup(){
        return group;
    }

    public int getPoint(){
        return point;
    }
}
