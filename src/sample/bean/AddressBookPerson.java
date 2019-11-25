package sample.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 2:27 下午
 */
public class AddressBookPerson {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty firewallId = new SimpleIntegerProperty();

    public AddressBookPerson() {
    }

    public AddressBookPerson(SimpleIntegerProperty id, SimpleStringProperty name, SimpleIntegerProperty firewallId) {
        this.id = id;
        this.name = name;
        this.firewallId = firewallId;
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

    public int getFirewallId() {
        return firewallId.get();
    }

    public SimpleIntegerProperty firewallIdProperty() {
        return firewallId;
    }

    public void setFirewallId(int firewallId) {
        this.firewallId.set(firewallId);
    }
}
