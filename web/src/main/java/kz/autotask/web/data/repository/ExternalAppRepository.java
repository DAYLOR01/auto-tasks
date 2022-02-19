package kz.autotask.web.data.repository;

import kz.autotask.web.data.entity.ExternalApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalAppRepository extends JpaRepository<ExternalApp, Long> {
}
