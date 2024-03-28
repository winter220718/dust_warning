package jsj.finedustalarm.Controller;

import jakarta.servlet.annotation.WebServlet;
import jsj.finedustalarm.Service.DustWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;

@Controller
@WebServlet(urlPatterns = "/*")
public class DustWarningController {

    private final DustWarningService dustWarningService;

    @Autowired
    public DustWarningController(DustWarningService dustWarningService) {
        this.dustWarningService = dustWarningService;
    }




}
