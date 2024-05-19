package com.greckapps.cardfront.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public int AddUser(User user)
    {
        try{
            if(!DoesUserExist(user.getUser_id())){
                    userRepository.save(user);
                    return 0; 
            }
            else
                return -1;
        }
        catch (Exception e) {
            System.err.println(e);
            return -5;
        }
    }

    public User GetByUserID(String ID)
    {
        return userRepository.findByUsername(ID);
    }

    public boolean DoesUserExist(String ID)
    {
        return userRepository.existsByUserId(ID);
    }

    public int ValidateUser(User user)
    {
        try{
            if(DoesUserExist(user.getUser_id()))
            {
                if(user.getPass_enc() == GetByUserID(user.getUser_id()).getPass_enc())
                {
                    return 0;
                }
            }
            else
            {
                return -1;
            }
        }
        catch (Exception e) {
            System.err.println(e);
            return -5;
        }
        return 0;
    }

    public int UpdatePass(String userID, String newPass)
    {
        //incase DB calls cause errors to throw
        try {
            //grab user from database if not found return error
            User foundUser = GetByUserID(userID);
            if(foundUser != null)
            {
                //change password and save to db
                foundUser.setPass_enc(newPass);
                userRepository.save(foundUser);
                return 0;
            }
            else
                return  -1;
        } catch (Exception e) {
            System.err.println(e);
            return -5;
        }
    }
}
