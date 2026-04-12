package com.example.fiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceEntityRepository serviceRepository;
    public ApiResponse addUser(CreateUserDTO cuDTO)
    {
            User user = new User();
            user.setName(cuDTO.getName());
            user.setEmail(cuDTO.getEmail());
            user.setPassword(cuDTO.getPassword());
            user.setRole(cuDTO.getRole());
            userRepository.save(user);
            UserDTO userDTO = convertToDTO(user);
            return  new ApiResponse("Data Saved Successfully",userDTO);
    }
    public Page<UserResponseDTO> getAllUser(Pageable pageable) {
        Page<User> pageData = userRepository.findAll(pageable);

        List<UserResponseDTO> dtoList = pageData.getContent()
                .stream()
                .map(user -> convertToServiceDTO(user))
                .toList();

        return new PageImpl<UserResponseDTO>(dtoList, pageable, pageData.getTotalElements());
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
        dto.setServices(user.getServices());
        return dto;
    }
    public ServiceEntity createService(int id , ServiceEntity service)
    {
        User findUser = userRepository.findById(id).get();
        if(findUser !=null) {
            ServiceEntity nservice = new ServiceEntity();
            nservice.setUser(findUser);
            nservice.setTitle(service.getTitle());
            nservice.setDescription(service.getDescription());
            nservice.setPrice(service.getPrice());
            ServiceEntity saved = serviceRepository.save(nservice);
            System.out.println("SERVICE METHOD HIT");
            return nservice;

        }
        else {
            throw new UserNotFoundException("User Not Found ! ");
        }
    }
    public UserResponseDTO getUserWithService(int id)
    {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            UserResponseDTO dto = new UserResponseDTO();
            List<ServiceEntity> services = user.getServices();
            for (ServiceEntity s : services) {
                dto.setDescription(s.getDescription());
                dto.setTitle(s.getTitle());
                dto.setPrice(s.getPrice());
            }
            dto.setId(id);
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            return dto;
        }
        else{ throw new UserNotFoundException("User not found with id: " + id);}
    }
   /* public Page<ServiceEntity> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }*/
    public UserResponseDTO convertToServiceDTO(User us)
    {

        List<User> user = userRepository.findAll();
        UserResponseDTO dto = new UserResponseDTO();
        if(user != null) {
            List<ServiceEntity> services = us.getServices();
            for (ServiceEntity s : services) {
                dto.setDescription(s.getDescription());
                dto.setTitle(s.getTitle());
                dto.setPrice(s.getPrice());
            }}
            dto.setId(us.getId());
            dto.setName(us.getName());
            dto.setEmail(us.getEmail());
            dto.setRole(us.getRole());
            return dto;
    }
    public PaginatedResponse<ServiceDTO> getServices(Pageable pageable) {

        Page<ServiceEntity> pageData = serviceRepository.findAll(pageable);

        List<ServiceDTO> dtoList = new ArrayList<>();

        for(ServiceEntity s : pageData.getContent()) {
            ServiceDTO dto = new ServiceDTO();
            dto.setTitle(s.getTitle());
            dto.setDescription(s.getDescription());
            dto.setPrice(s.getPrice());
            dtoList.add(dto);
        }

        PaginatedResponse<ServiceDTO> response = new PaginatedResponse<>();
        response.setData(dtoList);
        response.setPage(pageData.getNumber());
        response.setSize(pageData.getSize());
        response.setTotalElements(pageData.getTotalElements());
        response.setTotalPages(pageData.getTotalPages());

        return response;
    }
    public PaginatedResponse<UserResponseDTO> getService(Pageable pageable) {

        Page<ServiceEntity> pageData = serviceRepository.findAll(pageable);

        List<UserResponseDTO> dtoList = new ArrayList<>();

        for(ServiceEntity s : pageData.getContent()) {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setTitle(s.getTitle());
            dto.setDescription(s.getDescription());
            dto.setPrice(s.getPrice());
            dtoList.add(dto);
        }

        PaginatedResponse<UserResponseDTO> response = new PaginatedResponse<>();
        response.setData(dtoList);
        response.setPage(pageData.getNumber());
        response.setSize(pageData.getSize());
        response.setTotalElements(pageData.getTotalElements());
        response.setTotalPages(pageData.getTotalPages());

        return response;
    }
}
