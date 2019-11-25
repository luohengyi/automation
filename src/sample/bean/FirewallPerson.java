package sample.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/8
 * Time: 10:05 上午
 */
public class FirewallPerson {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty ip = new SimpleStringProperty();
    private SimpleIntegerProperty port = new SimpleIntegerProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();

    public FirewallPerson() {
    }

    public FirewallPerson(SimpleIntegerProperty id, SimpleStringProperty name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIp() {
        return ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public int getPort() {
        return port.get();
    }

    public SimpleIntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
        return "FirewallPerson{" +
                "id=" + id +
                ", name=" + name +
                ", ip=" + ip +
                ", port=" + port +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}
