package maybe_all_here.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Recommend {

    RECOMMEND("true"),
    NOT_RECOMMEND("false");

    private String value;
}
