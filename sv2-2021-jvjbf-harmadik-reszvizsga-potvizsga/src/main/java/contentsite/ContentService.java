package contentsite;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ContentService {
    private Set<User> allUsers =new HashSet<>();
    private Set<Content> allContent =new HashSet<>();

    public Set<User> getAllUsers() {
        return allUsers;
    }

    public Set<Content> getAllContent() {
        return allContent;
    }

    public void registerUser(String name, String password){
        if (allUsers.stream().map(User::getUserName).noneMatch(value->value.equals(name))){
            allUsers.add(new User(name,password));
        }else {
            throw new IllegalArgumentException("Username is already taken: "+name);
        }
    }

    public void addContent(Content content){
        if (allContent.stream().noneMatch(content1 -> content1.getTitle().equals(content.getTitle()))){
            allContent.add(content);
        }else {
            throw new IllegalArgumentException("Content name is already taken: "+content.getTitle());
        }
    }

    public void logIn(String username,String password){
        if (!allUsers.contains(new User(username,password))){
            throw new IllegalArgumentException("Username is wrong!");
        }else if (allUsers.stream().filter(user -> user.getUserName().equals(username)).noneMatch(user -> user.getPassword()==(username+password).hashCode())){
            throw new IllegalArgumentException("Password is Invalid!");
        }
        allUsers.stream().filter(user -> user.getUserName().equals(username)).collect(Collectors.toList()).get(0).setLogIn(true);
    }

    public void clickOnContent(User user,Content content){
        if(allUsers.stream().filter(user1 -> user1.equals(user)).allMatch(user1 -> user1.isLogIn())){
            if (allContent.stream().filter(content1 -> content1.equals(content)).allMatch(content1 -> content1.isPremiumContent())){
                if (user.isPremiumMember()){
                    allContent.stream().filter(content1 -> content1.equals(content)).collect(Collectors.toList()).get(0).click(user);
                }else{
                    throw new IllegalStateException("Upgrade for Premium to watch this content!");
                }
            }else {
                allContent.stream().filter(content1 -> content1.equals(content)).collect(Collectors.toList()).get(0).click(user);
            }
        }else {
            throw new IllegalStateException("Log in to watch this content!");
        }
    }
}
