package tt.hashtranslator.model;

import java.util.List;

public record ApplicationRequest(
        List<String> hashes
) {
}
