package homes.has.repository;

import homes.has.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

}
