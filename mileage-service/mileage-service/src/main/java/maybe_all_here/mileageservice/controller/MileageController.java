package maybe_all_here.mileageservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.mileageservice.controller.constant.ParamConstant;
import maybe_all_here.mileageservice.dto.mileage.MileageResponse;
import maybe_all_here.mileageservice.service.MileageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MileageController {

    private final MileageService mileageService;

    @GetMapping(MileageUrl.MY_MILEAGE)
    public ResponseEntity<?> getMyMileageForFeign(
            @PathVariable(ParamConstant.EMAIL) String email
    ) {
        MileageResponse mileage = mileageService.getMileageByEmail(email);

        return ResponseEntity.ok(mileage);
    }

    @GetMapping(MileageUrl.MILEAGE_INFO)
    public ResponseEntity<?> getMileageInfoForGetMethod(
            @PathVariable(ParamConstant.EMAIL) String email
    ) {
        MileageResponse mileage = mileageService.getMileageByEmail(email);

        return ResponseEntity.ok(mileage);
    }

    @PostMapping(MileageUrl.MILEAGE_INFO)
    public ResponseEntity<?> getMileageInfoForPostMethod(
            @PathVariable(ParamConstant.EMAIL) String email
    ) {
        MileageResponse mileage = mileageService.getMileageByEmail(email);

        return ResponseEntity.ok(mileage);
    }
}
