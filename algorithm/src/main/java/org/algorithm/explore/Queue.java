package org.algorithm.explore;

/**
 * <p>队列接口 FIFO</p>
 *
 * @author mason
 * @date 2022/8/22 22:52
 */
public interface Queue {

    boolean push(String data);

    String take();

}
