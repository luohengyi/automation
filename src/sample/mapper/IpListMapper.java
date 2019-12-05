package sample.mapper;

import org.apache.ibatis.annotations.Param;
import sample.bean.IpPerson;

import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/25
 * Time: 9:15 上午
 */
public interface IpListMapper {

    List<IpPerson> getIpsByAddrId(int addrId);

    boolean insertIp(IpPerson ipPerson);

    boolean deleteByAddrId(String addrId);

    IpPerson getIpByNameAndAddrId(@Param("name") String name, @Param("addrId") int addrId);

}
