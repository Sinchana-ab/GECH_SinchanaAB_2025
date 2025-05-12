package com.construction.companyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.construction.companyManagement.model.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
