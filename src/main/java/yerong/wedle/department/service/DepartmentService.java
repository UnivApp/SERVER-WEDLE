package yerong.wedle.department.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.department.domain.Department;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.department.repository.DepartmentRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UniversityRepository universityRepository;

    public List<DepartmentResponse> getDepartmentNamesByType(Long universityId, DepartmentType departmentType) {

        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);

        List<Department> departments = departmentRepository.findByUniversityAndDepartmentType(university, departmentType);
        return departments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<DepartmentResponse> getAllDepartmentNames(Long universityId) {

        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);

        List<Department> departments = departmentRepository.findByUniversity(university);
        return departments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DepartmentResponse convertToDto(Department department) {
        return new DepartmentResponse(
                department.getName(),
                department.getDepartmentType().getDisplayName()
        );
    }
}
