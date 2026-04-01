package com.example.fiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public String addUser(User user)
    {
        userRepository.save(user);
        return "User Added Successfully !";
    }
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }
    public User findUserById(int id )
    {
        return userRepository.findById(id).orElse(null);
    }
    public String updateUser(int id , User newData)
    {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null )
        {
            existingUser.setName(newData.getName());
            existingUser.setEmail(newData.getEmail());
            existingUser.setPassword(newData.getPassword());
            existingUser.setRole(newData.getRole());
            userRepository.save(existingUser);
            return "User Updated Successfully";
        }
        else{return "There no entry for this ID";}
    }
    public String deleteUser(int id )
    {
        if (userRepository.existsById(id))
        {
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        }
        else{return "User Not Found";}
    }
    public String updatePartialUser(User newData,int id )
    {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null )
        {
            if(newData.getName() != null)
            {existingUser.setName(newData.getName());}
            if(newData.getEmail()!=null)
            {existingUser.setName(newData.getName());}
            if(newData.getPassword()!=null)
            {existingUser.setPassword(newData.getPassword());}
            if(newData.getRole()!=null)
            {existingUser.setRole(newData.getName());}
            userRepository.save(existingUser);
            return "User's Detail Modified Successfully!";
        }
        else{return "User Not Found";}
    }
}
