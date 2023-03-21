package maybe_all_here.mileageservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mileage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private long mileagePoint;

    @Builder
    public Mileage(Long id, String email, long mileagePoint) {
        this.id = id;
        this.email = email;
        this.mileagePoint = mileagePoint;
    }
}
