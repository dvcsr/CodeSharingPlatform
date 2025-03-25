package platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.DTO.ClientRequestDTO;
import platform.DTO.CodeResponseDTO;
import platform.model.HiddenCode;

import java.util.List;
import java.util.UUID;

@Repository
public interface HiddenCodeRepository extends JpaRepository<HiddenCode, UUID> {
}
