package yerong.wedle.tuitionfee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.department.domain.DepartmentType;
import yerong.wedle.tuitionfee.domain.TuitionFee;
import yerong.wedle.university.domain.University;

import java.util.List;
import java.util.Optional;

public interface TuitionFeeRepository extends JpaRepository<TuitionFee, Long> {

    List<TuitionFee> findByUniversity(University university);
}
