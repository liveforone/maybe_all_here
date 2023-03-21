package maybe_all_here.mileageservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maybe_all_here.mileageservice.service.MileageService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MileageController {

//    "/my-mileage/{email}"

    private final MileageService mileageService;
}
