package org.netty.rpc.interfaces;

import java.util.List;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/25 01:49
 */
public interface DataService {

    String sendData(String body);

    List<String> getList();

}
