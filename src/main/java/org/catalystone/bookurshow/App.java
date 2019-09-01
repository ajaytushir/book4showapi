package org.catalystone.bookurshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Starting APP........." );
        SpringApplication.run(App.class, args);
    }
}
