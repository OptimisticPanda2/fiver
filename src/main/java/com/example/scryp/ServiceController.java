package com.example.scryp;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class ServiceController {
    private UserService userService;
    public ServiceController(UserService userService)
    {
        this.userService = userService;
    }

    // request to find service by techStack
    @GetMapping("/services/tech/{techStack}")
    public List<ServiceEntity> searchByTechStack(@PathVariable String techStack)
    {
        return userService.searchByTechStack(techStack);
    }
    // request to add service on the profile of freelancer's account
    @PostMapping("/services")
    public String createService(@Valid @RequestBody ServiceDTO dto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userService.createService(dto, email);
    }
    // request to find service by category
    @GetMapping("/services/category/{category}")
    public List<ServiceEntity> searchByCategory(@PathVariable String category)
    {
        return userService.searchByCategory(category);
    }

    // request to show all services
    @GetMapping("services")
    public Page<ServiceResponseDTO> getAllServices( Pageable pageable)
    {
        return userService.getAllService(pageable);
    }
    // request to update service details
    @PatchMapping("/service/{id}")
    public String updateService(@PathVariable int id , @RequestBody ServiceDTO dto)
    {
        return userService.updateService(id,dto);
    }
   //request to add rating to the service provided
    @PostMapping("/services/{id}/rating")
    public String addRating(@PathVariable int id , @RequestParam int stars)
    {
        return userService.addRating(id,stars);
    }


}
