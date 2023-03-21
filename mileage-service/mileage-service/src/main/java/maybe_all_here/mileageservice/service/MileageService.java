package maybe_all_here.mileageservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.mileageservice.domain.Mileage;
import maybe_all_here.mileageservice.dto.mileage.MileageResponse;
import maybe_all_here.mileageservice.repository.MileageRepository;
import maybe_all_here.mileageservice.service.util.MileageMapper;
import maybe_all_here.mileageservice.utility.CommonUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageService {

    private final MileageRepository mileageRepository;

    public MileageResponse getMileageByEmail(String email) {
        Mileage mileage = mileageRepository.getMileageByEmail(email);

        if (CommonUtils.isNull(mileage)) {
            return new MileageResponse();
        }

        return MileageMapper.entityToDto(mileage);
    }
}
