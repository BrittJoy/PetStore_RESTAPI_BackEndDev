package power.wash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import power.wash.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
