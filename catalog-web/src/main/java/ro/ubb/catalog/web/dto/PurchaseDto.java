package ro.ubb.catalog.web.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PurchaseDto extends BaseDto {
    private Long clientID;
    private Long bookID;
    private String date;
}
