package v1.components.instagram;

import java.util.List;

public class InstagramAccount {

    private String name;
    private List<String> postDescriptions;
    private List<String> postComments;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPostDescriptions() {
        return postDescriptions;
    }

    public void setPostDescriptions(List<String> postDescriptions) {
        this.postDescriptions = postDescriptions;
    }

    public List<String> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<String> postComments) {
        this.postComments = postComments;
    }
}
