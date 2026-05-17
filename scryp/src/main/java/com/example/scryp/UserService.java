package com.example.scryp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.ArrayList;

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
    @Autowired
    private ProjectRequestRepository projectRequestRepository;

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
public String createService(ServiceDTO dto, String email) {

    User user = userRepository.findByEmail(email);

    if (user == null) {
        throw new RuntimeException("User not found");
    }

    if (!user.getRole().equalsIgnoreCase("FREELANCER")) {
        throw new RuntimeException("Only freelancer can create service");
    }

    ServiceEntity service = new ServiceEntity();

    service.setTitle(dto.getTitle());
    service.setDescription(dto.getDescription());
    service.setPrice(dto.getPrice());

    service.setCategory(dto.getCategory());
    service.setDeliveryDays(dto.getDeliveryDays());
    service.setTechStack(dto.getTechStack());

    service.setGithubLink(dto.getGithubLink());
    service.setDemoVideoLink(dto.getDemoVideoLink());
    service.setPortfolioLink(dto.getPortfolioLink());

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

   //method for sending requests
    public String sendRequest(ProjectRequestDTO dto , String email)
    {
        User client = userRepository.findByEmail(email);
         ServiceEntity service = serviceRepository.findById(dto.getServiceId());

        if (service !=null)
        {
            User freelancer = service.getUser();
            ProjectRequest request = new ProjectRequest();
            request.setClient(client);
            request.setFreelancer(freelancer);
            request.setService(service);
            request.setDescription(dto.getDescription());
            request.setProjectTitle(dto.getProjectTitle());
            request.setBudget(dto.getBudget());
            request.setStatus("PENDING");
            projectRequestRepository.save(request);
            return "Request Sent Successfully";
        }
        else
        {
           return "Service Not Found";
        }
    }
    // method for updating the request status e.g Accepted/Rejected
    public String updateProjectRequestStatus(int id , String status)
    {
        ProjectRequest request = projectRequestRepository.findById(id);
        if(request == null)
        {
            throw new RuntimeException("Request Not Found");
        }
        request.setStatus(status);

        projectRequestRepository.save(request);
        return "Project Request Status Updated";
    }
    //method for client to check the status of the requests
    public List<ProjectRequest> getClientRequest(String email)
    {
        User client = userRepository.findByEmail(email);

        return projectRequestRepository.findByClient(client);
    }
    public List<ProjectRequest> getFreelancerRequest(String email)
    {
        User freelancer = userRepository.findByEmail(email);
        return projectRequestRepository.findByFreelancer(freelancer);
    }
    // method to find the service by TechStack
    public List<ServiceEntity> searchByTechStack(String techStack)
    {
        return serviceRepository
                .findByTechStackContainingIgnoreCase(techStack);
    }
    // method to find the service by category
    public List<ServiceEntity> searchByCategory(String category)
    {
        return serviceRepository.
                findByCategoryContainingIgnoreCase(category);
    }
    // method to show all services
    public Page<ServiceResponseDTO> getAllService(Pageable pageable)
    {
        Page<ServiceEntity> services = serviceRepository.findAll(pageable);

        List<ServiceResponseDTO> response = new ArrayList<>();

        for(ServiceEntity service : services)
        {
            ServiceResponseDTO dto = new ServiceResponseDTO();

            dto.setTitle(service.getTitle());
            dto.setDescription(service.getDescription());
            dto.setPrice(service.getPrice());

            dto.setCategory(service.getCategory());
            dto.setTechStack(service.getTechStack());

            dto.setGithubLink(service.getGithubLink());
            dto.setDemoVideoLink(service.getDemoVideoLink());
            dto.setPortfolioLink(service.getPortfolioLink());

            dto.setFreelancerName(service.getUser().getName());

            response.add(dto);
        }
        return new PageImpl<>(
                response,
                pageable,
                services.getTotalElements()
        );
    }
    // method to delete service
    public String deleteService(int id)
    {
        ServiceEntity service = serviceRepository.findById(id);
        if(service != null)
        {
            serviceRepository.deleteById(id);
            return "Service Deleted Successfully";
        }
        else
        {
        return  "Service Not Found";
        }
    }
    // method to update service details
    public String updateService(int id , ServiceDTO dto)
    {
        ServiceEntity service = serviceRepository.findById(id);
        if(service == null)
        { return "Service Not Found";}
        if(dto.getTitle()!=null)
        {
            service.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null)
        {
            service.setDescription(dto.getDescription());
        }
        if(dto.getPrice() != 0)
        {
            service.setPrice(dto.getPrice());
        }
        if(dto.getCategory() != null)
        {
            service.setCategory(dto.getCategory());
        }
        if(dto.getTechStack() != null)
        {
            service.setTechStack(dto.getTechStack());
        }
        if(dto.getGithubLink() != null)
        {
            service.setGithubLink(dto.getGithubLink());
        }
        if(dto.getDemoVideoLink() != null)
        {
            service.setDemoVideoLink(dto.getDemoVideoLink());
        }
        if(dto.getPortfolioLink() != null)
        {
            service.setPortfolioLink(dto.getPortfolioLink());
        }
        if(dto.getDeliveryDays() != 0)
        {
            service.setDeliveryDays(dto.getDeliveryDays());
        }
        serviceRepository.save(service);
        return "Service Updated Successfully";
    }
}
