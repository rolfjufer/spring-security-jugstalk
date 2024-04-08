package ch.letsboot.jugstalk.jugstalk.repository;

import ch.letsboot.jugstalk.jugstalk.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}