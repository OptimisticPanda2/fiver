package com.example.scryp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity,Integer>
{
List<ServiceEntity> findByTitle(String title);
ServiceEntity findById(int id);
List<ServiceEntity> findByCategoryContainingIgnoreCase(String category);
List<ServiceEntity> findByTechStackContainingIgnoreCase(String techStack);
List<ServiceEntity> findAll();
}