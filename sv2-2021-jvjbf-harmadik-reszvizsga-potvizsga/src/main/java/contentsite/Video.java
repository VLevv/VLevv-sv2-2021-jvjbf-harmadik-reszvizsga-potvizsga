package contentsite;

import java.util.ArrayList;
import java.util.List;

public class Video implements Content{
    private String title;
    private int length;
    private List<User> clicked=new ArrayList<>();

    public Video(String title, int length) {
        this.title = title;
        this.length = length;
    }

    @Override
    public boolean isPremiumContent() {
        if(length>15){
            return true;
        }
        else return false;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<User> clickedBy() {
        return new ArrayList<>(clicked);
    }

    @Override
    public void click(User user) {
        clicked.add(user);
    }
}
