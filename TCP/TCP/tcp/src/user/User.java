package user;

public class User {
    String username;
    String password;
    String directory;

    public User(String username, String password, String directory) {
        this.username = username;
        this.password = password;
        this.directory = directory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
