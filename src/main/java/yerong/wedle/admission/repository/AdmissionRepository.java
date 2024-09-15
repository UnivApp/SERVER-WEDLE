package yerong.wedle.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.admission.domain.Admission;
import yerong.wedle.admission.domain.AdmissionType;
import yerong.wedle.admission.dto.AdmissionResponse;

import java.util.Arrays;
import java.util.List;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    List<Admission> findByAdmissionTypeOrderByRankAsc(AdmissionType admissionType);

    List<Admission> findTop4ByAdmissionTypeOrderByRankAsc(AdmissionType admissionType);
}
