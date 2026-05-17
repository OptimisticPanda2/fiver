package com.example.scryp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequest,Integer>
{
 ProjectRequest findById(int id);
 List<ProjectRequest> findByClient(User client);
 List<ProjectRequest> findByFreelancer(User freelancer);
}