package be.salushealthcare.salus.team;

import be.salushealthcare.salus.person.staff.Staff;
import be.salushealthcare.salus.person.staff.StaffRepository;
import be.salushealthcare.salus.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository repository;
    private final StaffRepository staffRepository;

    public List<Team> getAll(int page, int size, TeamSort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort == null ? Sort.unsorted() : sort.getSort());
        return repository.findAll(pageRequest).getContent();
    }

    public long countAll() {
        return repository.count();
    }

    public Team getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Transactional
    public Team create(String name, Long staffId) {
        Staff leader = staffRepository.getOne(staffId);
        if (!repository.existsByName(name)) {
            Team team = repository.saveAndFlush(Team
                .builder()
                .name(name)
                .leader(leader)
                .members(new ArrayList<>())
                .build());
            team.getMembers().add(leader);
            return team;
        } else {
            throw new TeamAlreadyExistsException(name);
        }
    }

    @Transactional
    public Team replaceTeamLeader(Long teamId, Long staffId) {
        Team team = getById(teamId);
        team.assignLeader(staffRepository.getOne(staffId));
        return team;
    }

    @Transactional
    public Team addToTeam(Long teamId, List<Long> staffIds) {
        Team team = getById(teamId);
        List<Staff> staffList = staffIds.stream()
                .map(staffRepository::getOne)
                .collect(Collectors.toList());
        team.getMembers().addAll(staffList);
        return team;
    }

    @Transactional
    public Team removeFromTeam(Long teamId, List<Long> staffIds) {
        Team team = getById(teamId);
        team.getMembers().removeIf(member -> staffIds.contains(member.getId()));
        return team;
    }

    public boolean isMember(Team team, User user) {
        return user != null && team.getMembers().contains(staffRepository.getOne(user.getPersonId()));
    }

    @Transactional
    public void leave(Long teamId, User user) {
        repository.getOne(teamId).getMembers().remove(staffRepository.getOne(user.getPersonId()));
    }

    @Transactional
    public boolean deleteTeam(Long teamId) {
        if (repository.existsById(teamId)) {
            repository.deleteById(teamId);
            return true;
        } else {
            return false;
        }
    }
}
