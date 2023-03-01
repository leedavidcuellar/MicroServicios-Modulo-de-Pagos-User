package com.Microservice.ModulosPagosUser;


import com.Microservice.ModulosPagosUser.entities.User;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
@DisplayName("Test User")
public class UserTest {

    @ParameterizedTest
    @DisplayName("data...")
    @CsvFileSource(resources = "/dataUserTest.csv")
    void testDataUserFullName(String name,String lastName,String dni, String mail,String password){
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setDni(dni);
        user.setMail(mail);
        user.setPassword(password);

        Assertions.assertAll(
                ()->{
                    Assertions.assertNotNull(user.getName(),()->"Name not be null");
                    Assertions.assertNotNull(user.getLastName(),()->"Lastname not be null");
                    Assertions.assertNotNull(user.getDni(),()->"dni not be null");
                    Assertions.assertNotNull(user.getMail(),()->"Mail not be null");
                    Assertions.assertNotNull(user.getPassword(),()->"Password not be null");
                },
                ()->{
                    String  wait = "Pablo";
                    String real = user.getName();
                    String  wait1 = "Sanchez";
                    String real2 = user.getLastName();

                    Assertions.assertEquals(wait,real,()->"Name must be same");
                    Assertions.assertEquals(wait1,real2, ()->"Lastname must be same");
                },
                ()->{
                    String real2 = user.getLastName();
                    Assertions.assertTrue(real2.equals("Sanchez"),()->"Lastname must be same");
                    MatcherAssert.assertThat(user.getMail(),containsString("@"));
                }
        );






    }

}
