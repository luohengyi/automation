package sample.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/25
 * Time: 9:17 上午
 */
public class IpPerson {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty ip = new SimpleStringProperty();
    private SimpleIntegerProperty addrId = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public int getAddrId() {
        return addrId.get();
    }

    public SimpleIntegerProperty addrIdProperty() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId.set(addrId);
    }

    @Override
    public String toString() {
        return "Ip{" +
                "id=" + id +
                ", ip=" + ip +
                ", addrId=" + addrId +
                '}';
    }
}
