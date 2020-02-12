package sample.service;

import org.apache.ibatis.annotations.Param;
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
public class IpService extends Service {

    private IpListMapper ipListMapper = SqlSessionFactoryUtil.getMapper(IpListMapper.class);

    public List<IpPerson> getIpListByAddrId(int addrId) {
        return ipListMapper.getIpsByAddrId(addrId);
    }

    public boolean isEnmty(IpPerson ipPerson) {
        IpPerson ipByNameAndAddrId = ipListMapper.getIpByNameAndAddrId(ipPerson.getIp(), ipPerson.getAddrId());
        if (null != ipByNameAndAddrId) {
            return false;
        }
        return true;
    }

    public boolean addIp(IpPerson ipPerson) {
        return ipListMapper.insertIp(ipPerson);
    }

    public boolean deleteByAddrId(String ip) {
        return ipListMapper.deleteByAddrId(ip);
    }

    public List<IpPerson> search(){
        return ipListMapper.search();
    }

}
