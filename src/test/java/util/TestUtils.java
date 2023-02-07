package util;

import com.example.transportapi.entity.Address;
import com.example.transportapi.entity.User;
import com.example.transportapi.entity.enums.City;

public class TestUtils {

    public static User getUser(){
        User user1 = new User();
        Address address = new Address();
        address.setCity(String.valueOf(City.MUMBAI));
        address.setHouseAddress("Test address 123");
        address.setState("TEST_STATE");
        address.setPincode("366603");
        user1.setRoles("ROLE_USER");
        user1.setPassword("pass");
        user1.setEmail("test@email.com");
        user1.setAddress(address);
        user1.setMobileNumber("94948485");
        user1.setUsername("user1");
        return user1;
    }

    public static User getAdmin(){
        User admin = new User();
        Address address1 = new Address();
        address1.setCity(String.valueOf(City.MUMBAI));
        address1.setHouseAddress("Test address 123");
        address1.setState("TEST_STATE");
        address1.setPincode("363603");
        admin.setRoles("ROLE_ADMIN");
        admin.setPassword("pass");
        admin.setEmail("test@email.com");
        admin.setAddress(address1);
        admin.setMobileNumber("9438485");
        admin.setUsername("admin");
        return admin;
    }
}
