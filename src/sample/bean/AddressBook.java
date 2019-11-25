package sample.bean;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 12:00 下午
 */
public class AddressBook {
    private int id;
    private String name;
    private int firewallid;

    public AddressBook() {
    }

    public AddressBook(int id, String name, int firewallid) {
        this.id = id;
        this.name = name;
        this.firewallid = firewallid;
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

    public int getFirewallid() {
        return firewallid;
    }

    public void setFirewallid(int firewallid) {
        this.firewallid = firewallid;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firewallid=" + firewallid +
                '}';
    }
}
