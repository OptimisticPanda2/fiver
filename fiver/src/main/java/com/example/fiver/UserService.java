package com.example.fiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceEntityRepository serviceRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    // create User Function
    public String register(CreateUserDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        User savedUser = userRepository.save(user);
        // check Role

        return "User Created Successfully";

    }

    public Page<UserResponseDTO> getAllUser(Pageable pageable) {
        Page<User> pageData = userRepository.findAll(pageable);

        List<UserResponseDTO> dtoList = pageData.getContent()
                .stream()
                .map(user -> convertToServiceDTO(user))
                .toList();

        return new PageImpl<UserResponseDTO>(dtoList, pageable, pageData.getTotalElements());
    }

    // find by service method
    public List<UserResponseDTO> findUserByTitle(String title) {
        List<ServiceEntity> service = serviceRepository.findByTitle(title);
        List<UserResponseDTO> response = new ArrayList<>();
        if (service != null) {
            for (ServiceEntity s : service) {
                UserResponseDTO dto = new UserResponseDTO();
                dto.setName(s.getUser().getName());
                dto.setEmail(s.getUser().getEmail());
                dto.setTitle(s.getTitle());
                dto.setDescription(s.getDescription());
                dto.setPrice(s.getPrice());
                response.add(dto);

            }
            return (response);
        } else {
            throw new RuntimeException(title + " Does Not Exist");
        }
    }

    // find by Id function
    public User findUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    // update user function
    public User updateUser(int id, User newData) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(newData.getName());
            existingUser.setEmail(newData.getEmail());
            existingUser.setPassword(newData.getPassword());
            existingUser.setRole(newData.getRole());
            userRepository.save(existingUser);
            return existingUser;
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    // delete user function
    public String deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }



    // method to show only required data not sensitive data
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setServices(user.getServices());
        return dto;
    }


    /* public Page<ServiceEntity> getAllServices(Pageable pageable) {
         return serviceRepository.findAll(pageable);
     }*/
    public UserResponseDTO convertToServiceDTO(User us) {

        List<User> user = userRepository.findAll();
        UserResponseDTO dto = new UserResponseDTO();
        if (user != null) {
            List<ServiceEntity> services = us.getServices();
            for (ServiceEntity s : services) {
                dto.setDescription(s.getDescription());
                dto.setTitle(s.getTitle());
                dto.setPrice(s.getPrice());
            }
        }
        dto.setId(us.getId());
        dto.setName(us.getName());
        dto.setEmail(us.getEmail());
        dto.setRole(us.getRole());
        return dto;
    }

    // method to get users with pagination
    public PaginatedResponse<UserResponseDTO> getService(Pageable pageable) {

        Page<ServiceEntity> pageData = serviceRepository.findAll(pageable);

        List<UserResponseDTO> dtoList = new ArrayList<>();

        for (ServiceEntity s : pageData.getContent()) {
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

    // method for login request
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        boolean match = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
        if (!match) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
// method to add service after successfully login
   public String createService(ServiceDTO dto,String email) {

       User user = userRepository.findByEmail(email);
       ServiceEntity service = new ServiceEntity();

       if (user == null)
       {
           throw new RuntimeException("User not found");
       }
       if (user.getRole().equalsIgnoreCase("FREELANCER")) {
           service.setTitle(dto.getTitle());
           service.setDescription(dto.getDescription());
           service.setPrice(dto.getPrice());
           serviceRepository.save(service);
       }
       else{
           throw new RuntimeException("Only freelancer can create service");
       }

       service.setUser(user);

       serviceRepository.save(service);
       return "Service Added Successfully";
   }
    // method to update partial information of user
                public User updatePartialUser(int id, User newData) {
                    User existingUser = userRepository.findById(id).orElse(null);
                    if (existingUser != null) {
                        if (newData.getName() != null) {
                            existingUser.setName(newData.getName());
                        }
                        if (newData.getEmail() != null) {
                            existingUser.setEmail(newData.getEmail());
                        }
                        if (newData.getPassword() != null) {
                            existingUser.setPassword(newData.getPassword());
                        }
                        if (newData.getRole() != null) {
                            existingUser.setRole(newData.getRole());
                        }
                        userRepository.save(existingUser);
                        return existingUser;
                    } else {
                        throw new UserNotFoundException("User not found with id: " + id);
                    }
                }


/*public String updateService(int id, ServiceDTO dto) {

    ServiceEntity service = serviceRepository.findById(id);

    if (dto.getTitle() != null) {
        service.setTitle(dto.getTitle());
    }
    if (dto.getDescription() != null) {
        service.setDescription(dto.getDescription());
    }
    if (dto.getPrice() != null) {
        service.setPrice(dto.getPrice());
    }

    serviceRepository.save(service);

    return "Service updated";
}*/
}
