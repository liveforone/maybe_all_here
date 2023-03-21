package maybe_all_here.mileageservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.mileageservice.repository.MileageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageService {

    private final MileageRepository mileageRepository;
}
