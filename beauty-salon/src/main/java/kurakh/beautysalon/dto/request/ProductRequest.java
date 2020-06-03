package kurakh.beautysalon.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductRequest {

    @NotBlank
    @Size(min = 2)
    private String name;

    @NotNull
    @Positive
    private Integer timeMinutes;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime startTime
////            = null
//           ;

    @NotNull
    @Positive
    private Integer price;

    @NotNull
    private Long categoryId;

    private FileRequest fileRequest;
}
