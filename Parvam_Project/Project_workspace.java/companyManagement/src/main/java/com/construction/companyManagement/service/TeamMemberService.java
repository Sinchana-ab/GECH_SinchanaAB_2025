package com.construction.companyManagement.service;

import java.util.List;
import com.construction.companyManagement.model.TeamMember;

public interface TeamMemberService {
    List<TeamMember> getAllTeamMembers();
    void addTeamMember(TeamMember teamMember);
}
