package yerong.wedle.tuitionfee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yerong.wedle.department.domain.Department;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.department.dto.DepartmentResponse;
import yerong.wedle.tuitionfee.domain.TuitionFee;
import yerong.wedle.tuitionfee.dto.TuitionFeeResponse;
import yerong.wedle.tuitionfee.exception.TuitionFeeNotFoundException;
import yerong.wedle.tuitionfee.repository.TuitionFeeRepository;
import yerong.wedle.university.domain.University;
import yerong.wedle.university.exception.UniversityNotFoundException;
import yerong.wedle.university.repository.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TuitionFeeService {

    private final TuitionFeeRepository tuitionFeeRepository;
    private final UniversityRepository universityRepository;
    public List<TuitionFeeResponse> getTuitionFeesByType(Long universityId) {

        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);

        List<TuitionFee> tuitionFees = tuitionFeeRepository.findByUniversity(university);
        if (tuitionFees.isEmpty()) {
            throw new TuitionFeeNotFoundException();
        }

        return tuitionFees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TuitionFeeResponse getAverageTuitionFee(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(UniversityNotFoundException::new);

        List<TuitionFee> tuitionFees = tuitionFeeRepository.findByUniversity(university);
        if (tuitionFees.isEmpty()) {
            throw new TuitionFeeNotFoundException();
        }

        double averageFee = tuitionFees.stream()
                .mapToDouble(TuitionFee::getFeeAmount)
                .average()
                .orElseThrow(TuitionFeeNotFoundException::new);

        return new TuitionFeeResponse("전체 평균 등록금", averageFee);
    }

    private TuitionFeeResponse convertToDto(TuitionFee tuitionFee) {
        return new TuitionFeeResponse(
                tuitionFee.getDepartmentType().getDisplayName(),
                tuitionFee.getFeeAmount()
        );
    }
}
