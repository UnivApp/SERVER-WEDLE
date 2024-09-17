package yerong.wedle.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.department.domain.Department;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.university.domain.University;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByUniversity(University university);

    List<Department> findByUniversityAndDepartmentType(University university, DepartmentType departmentType);
}
