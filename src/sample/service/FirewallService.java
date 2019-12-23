package sample.service;

import sample.bean.AddressBookPerson;
import sample.bean.Firewall;
import sample.bean.FirewallPerson;
import sample.mapper.AddressBookMapper;
import sample.mapper.TestMapper;
import sample.util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/20
 * Time: 3:55 下午
 */
public class FirewallService extends Service {

    private FirewallService() {

    }

    private static FirewallService firewallService = new FirewallService();

    //通过静态工厂获取 FirewallServer
    public static FirewallService build() {
        return firewallService;
    }

    private TestMapper testMapper = SqlSessionFactoryUtil.getMapper(TestMapper.class);


    /**
     * 添加防火墙
     *
     * @param firewall firewall
     * @return boolean
     */
    public boolean add(Firewall firewall) {

        if (firewall.getName().isEmpty()) {
            error = "防火墙名称不能为空";
            return false;
        }

        if (firewall.getIp().isEmpty()) {
            error = "防火墙ip不能为空";
            return false;
        }

        if (firewall.getPort() == 0) {
            error = "防火墙端口不能为空";
            return false;
        }

        if (firewall.getPassword().isEmpty()) {
            error = "防火墙密码不能为空";
            return false;
        }

        if (firewall.getUsername().isEmpty()) {
            error = "防火墙用户名不能为空";
            return false;
        }

        return testMapper.insert(firewall);
    }

    /**
     * 获取是有的防火墙
     *
     * @return List<FirewallPerson>
     */
    public List<FirewallPerson> getDataList() {
        List<FirewallPerson> dataList = null;
        try {
            dataList = testMapper.getDataList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 删除防火墙
     *
     * @param id id
     * @return boolean
     */
    public boolean delete(int id) {
        AddressBookMapper addressBookMapper = SqlSessionFactoryUtil.getMapper(AddressBookMapper.class);
        List<AddressBookPerson> allAddressBook = addressBookMapper.getAllAddressBook(id);
        if (allAddressBook.size() > 0) {
            error = "数据被占用！";
            return false;
        }
        return testMapper.delete(id);
    }

    public Firewall getFirewall(int id) {
        return testMapper.getFirewallById(id);
    }


    public boolean edit(Firewall firewall) {
        return testMapper.edit(firewall);
    }
}
