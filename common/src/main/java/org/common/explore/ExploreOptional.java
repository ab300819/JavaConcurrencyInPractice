package org.common.explore;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>探索 Optional</p>
 *
 * @author mengshen
 * @date 2021/12/10 9:46
 */
public class ExploreOptional {

    private final static Logger log = LoggerFactory.getLogger(ExploreOptional.class);

    public static void main(String[] args) {
        User user = new User();
        user.setAddress(new Address());
        log.info("star orElse");
        Address address1 = Optional.of(user).map(t -> t.getAddress()).orElse(createAddress());
        log.info("star orElseGet");
        Address address2 = Optional.of(user).map(t -> t.getAddress()).orElseGet(()->createAddress());
    }


    public static class User {

        private String name;

        private Integer age;

        private Address address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    public static class Address {

        private String country;

        private String province;

        private String city;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    public static Address createAddress() {
        log.info("create address");

        return new Address();
    }

}
