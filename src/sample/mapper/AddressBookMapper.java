package sample.mapper;

import sample.bean.AddressBook;
import sample.bean.AddressBookPerson;

import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/21
 * Time: 11:59 上午
 */
public interface AddressBookMapper {

    boolean insertAddressBook(AddressBook addressBook);
    List<AddressBookPerson> getAllAddressBook(int firewallId);
    boolean delete(int id);
    AddressBook getAddressBookByid(int id);

    boolean update(AddressBook addressBook);
}
