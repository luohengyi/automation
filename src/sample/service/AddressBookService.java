package sample.service;

import sample.bean.AddressBook;
import sample.bean.AddressBookPerson;
import sample.bean.Firewall;
import sample.mapper.AddressBookMapper;
import sample.util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 1:49 下午
 */
public class AddressBookService extends Service {

    private AddressBookMapper addressBookMapper = SqlSessionFactoryUtil.getMapper(AddressBookMapper.class);
    private FirewallService firewallService = FirewallService.build();

    /**
     * 添加地址薄
     *
     * @param addressBook 地址薄
     * @return boolean
     */
    public boolean addAddressBook(AddressBook addressBook) {
        if (addressBook.getName().isEmpty()) {
            error = "地址薄名称不能为空！";
            return false;
        }
        Firewall firewall = firewallService.getFirewall(addressBook.getFirewallid());
        if (null == firewall) {
            error = "非法数据！";
            return false;
        }

        return addressBookMapper.insertAddressBook(addressBook);
    }

    /**
     * 获取防火墙地址薄
     *
     * @param id 防火墙id
     * @return List<AddressBook>
     */
    public List<AddressBookPerson> getFirewallAddressBook(int id) {
        return addressBookMapper.getAllAddressBook(id);
    }

    public boolean remove(int id) {
        return addressBookMapper.delete(id);
    }

    public AddressBook getAddressBookbyId(int id) {
        return addressBookMapper.getAddressBookByid(id);
    }


    public boolean edit(AddressBook addressBook) {
        if (addressBook.getName().trim().equals("")) {
            error="地址薄名称不能为空";
            return false;
        }
        return addressBookMapper.update(addressBook);
    }
}
