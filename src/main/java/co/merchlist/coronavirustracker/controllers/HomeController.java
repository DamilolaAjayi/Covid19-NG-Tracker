package co.merchlist.coronavirustracker.controllers;

import co.merchlist.coronavirustracker.models.StateStats;
import co.merchlist.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("/")
    public String home(Model model){
        List<StateStats> allStateStats = coronavirusDataService.getAllStats();
        int totalReportedCases = coronavirusDataService.getAllStats().stream().mapToInt(stat -> stat.getTotalNoOfCases()).sum();

        model.addAttribute("stateStats", allStateStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        return "home";
    }
}
