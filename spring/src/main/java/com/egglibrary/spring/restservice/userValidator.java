
package com.egglibrary.spring.restservice;


public class userValidator {
    
    private final boolean validator;
    
    public userValidator(boolean v){
        this.validator=v;
    }

    public boolean isValidator() {
        return validator;
    }
    
    
    
}
