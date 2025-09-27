package model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;


    //using regular expression for email varification:-
    //using patter and matcher:-
    private static final String emailRegex="^(.+)@(.+)\\.(com|org|net|co)$";
    private static final Pattern patternMatcher=Pattern.compile(emailRegex);


    public static boolean isValidEmail(String email){
        Matcher matcher=patternMatcher.matcher(email);
        return matcher.matches();
    }

    public Customer(String firstName, String lastName, String email){ //constructor
        if(!isValidEmail(email)){
            throw new IllegalArgumentException("Error: Invalid email:-");
        }
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }
//Overriding java object classes
    @Override
    public boolean equals(Object op){
        if(this==op)
            return true;
        if(op==null || getClass()!=op.getClass())
            return false;
        Customer customer=(Customer) op;
        return Objects.equals(email, customer.email);
    }

    @Override
    public String toString(){
        return "first-Name:- " + firstName + "\tlast-Name:- " + lastName +"\temail:- " + email;
    }

    @Override
    public int hashCode(){
        return Objects.hash(email);
    }
}
