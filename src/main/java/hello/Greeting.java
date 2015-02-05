package hello;

public class Greeting {

    private final long id;
    private final String content;
    private final String extraStuff;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
        extraStuff = "Hello from Jason!";
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    
    public String getExtraStuff() {
        return extraStuff;
    }
}