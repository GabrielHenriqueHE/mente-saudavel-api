package br.app.mentesaudavel.api.shared.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SliceResponseDTO<T> {

    private List<T> content;
    private boolean hasNext;
    private boolean hasPrevious;
    private int pageNumber;
    private int pageSize;
    private int numberOfElements;
    private boolean isFirst;
    private boolean isLast;
}
