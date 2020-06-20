package org.spring.explore.common.domain;


import lombok.Data;
import org.spring.explore.common.annotation.Super;

/**
 * 超级用户
 *
 * @author mason
 */
@Super
@Data
public class SuperUser extends User {

    private String address;
}
