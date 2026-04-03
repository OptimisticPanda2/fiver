package com.example.fiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public UserDTO addUser(CreateUserDTO cuDTO)
    {
            User user = new User();
            user.setName(cuDTO.getName());
            user.setEmail(cuDTO.getEmail());
            user.setPassword(cuDTO.getPassword());
            user.setRole(cuDTO.getRole());
            userRepository.save(user);
            UserDTO userDTO = convertToDTO(user);
        return  userDTO;
    }
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }
    public User findUserById(int id )
    {
        User user = userRepository.findById(id).orElse(null);
        if(user!=null)
        { return user;}
        else { throw new UserNotFoundException("User not found with id: " + id);}
    }
    public User updateUser(int id , User newData)
    {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null )
        {
            existingUser.setName(newData.getName());
            existingUser.setEmail(newData.getEmail());
            existingUser.setPassword(newData.getPassword());
            existingUser.setRole(newData.getRole());
            userRepository.save(existingUser);
            return existingUser;
        }
        else{ throw new UserNotFoundException("User not found with id: " + id);}
    }
    public String deleteUser(int id )
    {
        if (userRepository.existsById(id))
        {
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        }
        else{ throw new UserNotFoundException("User not found with id: " + id);}
    }
    public User updatePartialUser(int id ,User newData)
    {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null )
        {
            if(newData.getName() != null)
            {existingUser.setName(newData.getName());}
            if(newData.getEmail()!=null)
            {existingUser.setEmail(newData.getEmail());}
            if(newData.getPassword()!=null)
            {existingUser.setPassword(newData.getPassword());}
            if(newData.getRole()!=null)
            {existingUser.setRole(newData.getRole());}
            userRepository.save(existingUser);
            return existingUser;
        }
        else{ throw new UserNotFoundException("User not found with id: " + id);}
    }
    public UserDTO convertToDTO(User user)
    {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
