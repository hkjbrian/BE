package SJUCapstone.BE.diagnosis.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long symptomId; //증상 키

    @Schema(hidden = true)
    private Long userId;
    @Schema(hidden = true)
    private String userName;


//    @Schema(hidden = true)
//    @Type(JsonType.class)
//    @Column(name = "imagePaths", columnDefinition = "json")
//    private List<String> imagePaths; // S3 URL들을 저장하는 리스트

    private Long painLevel;

    @Lob
    @Type(JsonType.class)
    private List<String> symptomText;


    @Lob
    @Type(JsonType.class)
    private List<String> symptomArea;
}
