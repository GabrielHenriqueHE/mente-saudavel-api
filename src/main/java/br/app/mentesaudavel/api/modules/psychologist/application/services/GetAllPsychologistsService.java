package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.psychologist.application.data.response.GetPsychologistProfileResponseDTO;
import br.app.mentesaudavel.api.modules.psychologist.mappers.PsychologistMapper;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.shared.data.response.SliceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllPsychologistsService {

    private final PsychologistRepository psychologistRepository;

    public SliceResponseDTO<GetPsychologistProfileResponseDTO> execute(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);

        Slice<GetPsychologistProfileResponseDTO> profiles = this.psychologistRepository
                .findAll(page)
                .map(PsychologistMapper::mapToDTO);

        return SliceResponseDTO
                .<GetPsychologistProfileResponseDTO>builder()
                .content(profiles.getContent())
                .hasNext(profiles.hasNext())
                .hasPrevious(profiles.hasPrevious())
                .pageNumber(profiles.getPageable().getPageNumber())
                .pageSize(profiles.getPageable().getPageSize())
                .numberOfElements(profiles.getNumberOfElements())
                .isFirst(profiles.isFirst())
                .isLast(profiles.isLast())
                .build();
    }
}
