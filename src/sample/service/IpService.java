package sample.service;

import sample.bean.IpPerson;
import sample.mapper.IpListMapper;
import sample.mapper.TestMapper;
import sample.util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/25
 * Time: 9:32 上午
 */
public class IpService {

    private IpListMapper ipListMapper = SqlSessionFactoryUtil.getMapper(IpListMapper.class);

    public List<IpPerson> getIpListByAddrId(int addrId) {
        return ipListMapper.getIpsByAddrId(addrId);
    }

    public boolean addIp(IpPerson ipPerson) {
        return ipListMapper.insertIp(ipPerson);
    }
}
