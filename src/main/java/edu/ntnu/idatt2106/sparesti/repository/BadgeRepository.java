package edu.ntnu.idatt2106.sparesti.repository;


import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findById(Long bagdeId);

    List<Badge> findAllByUser_Username(String email, Pageable pageable);


}
