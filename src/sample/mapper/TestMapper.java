package sample.mapper;

import javafx.collections.ObservableList;
import org.apache.ibatis.annotations.Param;
import sample.bean.Firewall;
import sample.bean.FirewallPerson;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/7
 * Time: 5:28 下午
 */

public interface TestMapper {

    List<FirewallPerson> getDataList();

    boolean insert(Firewall firewall);

    boolean delete(int id);

    Firewall getFirewallById(int id);

    boolean edit(Firewall firewall);

    boolean creatTable(@Param("sqlString") String sqlString);
}
