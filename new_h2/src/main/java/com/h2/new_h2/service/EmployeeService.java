package com.h2.new_h2.service;

import com.h2.new_h2.exception.RecordNotFoundException;
import com.h2.new_h2.model.EmployeeEntity;
import com.h2.new_h2.model.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public List<EmployeeEntity> getAllEmployees()
    {
        System.out.println("getAllEmployees");
        List<EmployeeEntity> result = (List<EmployeeEntity>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<EmployeeEntity>();
        }
    }


    public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException
    {
        System.out.println("getEmployeeById");
        Optional<EmployeeEntity> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity)
    {
        System.out.println("createOrUpdateEmployee");
        // Create new entry
        if(entity.getId()  == null)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            // update existing entry
            Optional<EmployeeEntity> employee = repository.findById(entity.getId());

            if(employee.isPresent())
            {
                EmployeeEntity newEntity = employee.get();
                newEntity.setEmail(entity.getEmail());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());

                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException
    {
        System.out.println("deleteEmployeeById");

        Optional<EmployeeEntity> employee = repository.findById(id);

        if(employee.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}