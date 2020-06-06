package org.spring.explore.dependency.injection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.explore.common.domain.User;

/**
 * @author mason
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHolder {

    private User user;

}
