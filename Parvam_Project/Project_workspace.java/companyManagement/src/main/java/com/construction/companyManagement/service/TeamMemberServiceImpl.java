package com.construction.companyManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.construction.companyManagement.model.TeamMember;
import com.construction.companyManagement.repository.TeamMemberRepository;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Override
    public List<TeamMember> getAllTeamMembers() {
        return teamMemberRepo.findAll();
    }

    @Override
    public void addTeamMember(TeamMember teamMember) {
        teamMemberRepo.save(teamMember);
    }
}
