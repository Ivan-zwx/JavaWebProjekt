package hr.algebra.javawebprojekt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestHistory {

    private Integer idRequestHistory;
    private String username;
    private String vrijemeRequesta;
    private String request;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHistory that = (RequestHistory) o;
        return idRequestHistory.equals(that.idRequestHistory) &&
                Objects.equals(username, that.username) &&
                Objects.equals(vrijemeRequesta, that.vrijemeRequesta) &&
                Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRequestHistory, username, vrijemeRequesta, request);
    }
}
