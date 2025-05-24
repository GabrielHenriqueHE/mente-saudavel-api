package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.psychologist.application.data.request.CreatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.enums.UserType;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.modules.user.repositories.UserRepository;
import br.app.mentesaudavel.api.shared.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreatePsychologistService {

    private final PsychologistRepository psychologistRepository;
    private final UserRepository userRepository;

    public void execute(CreatePsychologistRequestDTO data, User user) {
        Map<String, String[]> errors = new HashMap<>();

        boolean userHasProfile = this.psychologistRepository.existsByUser(user);

        if (userHasProfile) {
            errors.put("email", new String[] {"User already has a profile."});
        }

        LocalDate now = LocalDate.now();
        boolean psychologistHasAtLeastTwentyYearsOld = data
                .birthDate()
                .isBefore(now.minusYears(20L));

        if (!psychologistHasAtLeastTwentyYearsOld) {
            errors.put("birthDate", new String[] {
                    "Psychologist must be at least twenty years old."
            });
        }

        boolean activitiesStartedAfterPsychologistTwentyYears = data
                .activitiesStartDate().isAfter(data.birthDate().plusYears(18L));

        if (!activitiesStartedAfterPsychologistTwentyYears) {
            errors.put("activitiesStartDate", new String[] {
                    "Psychologist must be at least twenty years old"
            });
        }

        if (!errors.isEmpty()) {
            throw new UnprocessableEntityException(
                    "Error during psychologist profile creation.",
                    errors
            );
        }

        Psychologist psychologist = new Psychologist(
                data.name(),
                data.crp(),
                data.birthDate(),
                data.activitiesStartDate(),
                data.slug(),
                user
        );

        user.setType(UserType.PSYCHOLOGIST);

        this.userRepository.save(user);
        this.psychologistRepository.save(psychologist);
    }
}
