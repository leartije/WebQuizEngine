package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMPLETED_QUIZZES_LOG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedQuizzesLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long logId;
    @Column(name = "user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userName;
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

}
