package com.DataMapping.Mapping.repositories;

import com.DataMapping.Mapping.entities.DepartmentEntity;
import com.DataMapping.Mapping.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByManager(EmployeeEntity employeeEntity);
}
