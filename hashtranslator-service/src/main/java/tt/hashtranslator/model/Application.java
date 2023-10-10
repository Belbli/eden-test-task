package tt.hashtranslator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("applications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Application {
    @Id
    private UUID id;
    private UUID userId;
    private List<HashResult> hashResults;
}
