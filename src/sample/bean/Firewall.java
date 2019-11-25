package sample.bean;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/8
 * Time: 9:08 上午
 */
public class Firewall {
    private int id;
    private String name;
    private String ip;
    private int port;
    private String username;
    private String password;

    public Firewall() {
    }

    /**
     *
     * @param id id
     * @param name name
     * @param ip ip
     * @param port  port
     * @param username username
     * @param password password
     */
    public Firewall(int id, String name, String ip, int port, String username, String password) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    @Override
    public String toString() {
        return "Firewall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
