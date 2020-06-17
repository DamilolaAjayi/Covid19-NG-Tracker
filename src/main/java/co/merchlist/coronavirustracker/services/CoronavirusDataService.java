package co.merchlist.coronavirustracker.services;

import co.merchlist.coronavirustracker.models.StateStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusDataService {

    private static final String VIRUS_DATA_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vSNE-GBC20004aZaVv2j3kmZwe_xDIHPp6aPTmizrK-KDJ7O1akUQzzid8T-m-7AcYtkmOZ8BAVXctH/pub?gid=502285668&single=true&output=csv";

    private List<StateStats> allStats = new ArrayList<>();

    public List<StateStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* 59 23 * * *")
    public void getCoronavirusData() throws IOException, InterruptedException {
        List<StateStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            StateStats stateStat = new StateStats();
            stateStat.setState(record.get("States Affected"));
            stateStat.setTotalNoOfCases(Integer.parseInt(record.get("Total No. of Cases")));
            stateStat.setTotalNoDischarged(Integer.parseInt(record.get("Total No. Discharged")));
            stateStat.setTotalNoOfCasesRT(Integer.parseInt(record.get("No. of Cases receiving treatment")));
            stateStat.setTotalNoOfDeaths(Integer.parseInt(record.get("No. of Deaths")));
            newStats.add(stateStat);
        }
        this.allStats = newStats;
    }
}
