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

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/DamilolaAjayi/Covid19-NG-Tracker/master/src/main/resources/COVID19%20Data.csv";

    private List<StateStats> allStats = new ArrayList<>();

    public List<StateStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * * * * *")
    public void getCoronavirusData() throws IOException, InterruptedException {
        List<StateStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            StringReader csvBodyReader = new StringReader(httpResponse.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .parse(csvBodyReader);
       try{
            for (CSVRecord record : records) {
                StateStats stateStat = new StateStats();
                System.out.println(record.get("Total No. of Cases"));
                stateStat.setState(record.get("States Affected"));
                stateStat.setTotalNoOfCases(Integer.parseInt(record.get("Total No. of Cases")));
                stateStat.setTotalNoDischarged(Integer.parseInt(record.get("Total No. Discharged")));
                stateStat.setTotalNoOfCasesRT(Integer.parseInt(record.get("No. of Cases receiving treatment")));
                stateStat.setTotalNoOfDeaths(Integer.parseInt(record.get("No. of Deaths")));
                newStats.add(stateStat);
            }

        }catch(Exception e) {
            for (CSVRecord record : records) {
                StateStats stateStat = new StateStats();
                stateStat.setState("States Affected");
                stateStat.setTotalNoOfCases(Integer.parseInt("1"));
                stateStat.setTotalNoDischarged(Integer.parseInt("1"));
                stateStat.setTotalNoOfCasesRT(Integer.parseInt("1"));
                stateStat.setTotalNoOfDeaths(Integer.parseInt("1"));
                newStats.add(stateStat);
            }

            e.printStackTrace();
        }

        this.allStats = newStats;
    }
}
