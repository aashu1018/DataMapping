package com.DataMapping.Mapping.services;

import com.DataMapping.Mapping.entities.DepartmentEntity;
import com.DataMapping.Mapping.entities.EmployeeEntity;
import com.DataMapping.Mapping.repositories.DepartmentRepository;
import com.DataMapping.Mapping.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentEntity createNewDepartment(DepartmentEntity departmentEntity){
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity getDepartmentById(Long id){
        return departmentRepository.findById(id).orElse(null);
    }

    public DepartmentEntity assignManagerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                            department.setManager(employee);
                            return departmentRepository.save(department);
                        }
                )
        ).orElse(null);
    }

    public DepartmentEntity getAssignedDepartmentOfManager(Long employeeId) {
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
//        return employeeEntity.map( employee -> {
//            return employee.getManagerDepartment();
//        }).orElse(null);

        EmployeeEntity employee = EmployeeEntity.builder().id(employeeId).build();
        return departmentRepository.findByManager(employee);
    }

    public DepartmentEntity assignWorkerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                    employee.setWorkerDepartment(department);
                    employeeRepository.save(employee);

                            department.getWorkers().add(employee);
                            return department;
                        }
                )
        ).orElse(null);
    }

    public DepartmentEntity assignFreelancerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                    employee.getFreelanceDepartments().add(department);
                    employeeRepository.save(employee);

                            department.getFreelancers().add(employee);

                            return department;
                        }
                )
        ).orElse(null);
    }
}
