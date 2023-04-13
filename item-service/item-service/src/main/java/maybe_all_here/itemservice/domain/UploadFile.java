package maybe_all_here.itemservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String saveFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;

    @Builder
    public UploadFile(Long id, String saveFileName, Item item) {
        this.id = id;
        this.saveFileName = saveFileName;
        this.item = item;
    }

    public void create(String saveFileName, Item item) {
        this.saveFileName = saveFileName;
        this.item = item;
    }
}
