package typesafedevcontest.view;

import typesafedevcontest.database.Log;

import java.util.List;

public class LogsViewModel {

    private List<Log> logs;

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

}
