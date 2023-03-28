package maybe_all_here.itemservice.controller;

import lombok.RequiredArgsConstructor;
import maybe_all_here.itemservice.controller.constant.ParamConstant;
import maybe_all_here.itemservice.controller.constant.UploadFileUrl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class UploadFileController {

    @GetMapping(UploadFileUrl.SHOW_IMAGE)
    @ResponseBody
    public Resource showImage(
            @PathVariable(ParamConstant.SAVE_FILE_NAME) String saveFileName
    ) throws MalformedURLException {
        return new UrlResource(ParamConstant.FILE_RESOURCE_PATH + saveFileName);
    }
}
