package com.example.fiver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity,Integer>
{
List<ServiceEntity> findByTitle(String title);
ServiceEntity findById(int id);
}