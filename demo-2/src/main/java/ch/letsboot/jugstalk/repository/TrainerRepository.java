package ch.letsboot.jugstalk.repository;

import ch.letsboot.jugstalk.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}