package com.greckapps.cardfront.user.error;


public class UserError extends RuntimeException{
    protected String id;

    public UserError(String message)
    {
        super(message);
    }

    public static class FoundUser extends UserError{

        public FoundUser(String message)
        {
            super(message);
            id = "UFE";
        }
    }

    public static class NoUser extends UserError{
        public NoUser(String message)
        {
            super(message);
            id = "UNFE";
        }
    }

    public static class InvalidCode extends UserError{
        public InvalidCode(String message)
        {
            super(message);
            id = "IC";
        }
    }

    public String getId() {
        return id;
    }
}
