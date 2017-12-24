package com.coon.coon_auto_builder.service.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.coon.coon_auto_builder.tool.CmdHelper.runCmd;

@Component
@Slf4j
public class Kerl extends Tool {
    private final String kerlExecutable;
    private Map<String, String> erlInstallations = new ConcurrentHashMap<>();

    public Kerl(String kerlExecutable) {
        this.kerlExecutable = kerlExecutable;
    }

    @Override
    public boolean check() {
        try {
            version = runCmd(kerlExecutable + " version").trim();
            gatherKerlInstallations();
            ready = true;
            return true;
        } catch (IOException | InterruptedException e) {
            log.warn("Calling kerl error {}", e.getMessage());
            message = e.getMessage();
            return false;
        }
    }

    @Override
    public boolean install() {
        //TODO
        return false;
    }

    public Map<String, String> getErlInstallations() {
        return erlInstallations;
    }

    @Override
    public String toString() {
        StringBuilder installations = new StringBuilder();
        for (Map.Entry<String, String> entry : erlInstallations.entrySet())
            installations.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        return "Kerl version='" + version + "'" +
                ", erlInstallations:\n" + installations.toString();
    }

    @Override
    public Health health() {
        if (ready)
            return Health.up().withDetail("Version", version)
                    .withDetail("ErlVersions", erlInstallations.keySet()).build();
        return Health.down().withDetail("Error", message).build();
    }

    private void gatherKerlInstallations() throws IOException, InterruptedException {
        String installations = runCmd(kerlExecutable + " list installations");
        if (installations.isEmpty())
            return;
        String[] lines = installations.split("\n");
        for (String line : lines) {
            String[] installation = line.split(" ");
            erlInstallations.put(trimKey(installation[0]), installation[1]);
        }
    }

    private String trimKey(String installation) {
        if (installation.contains(".")) {
            String[] splitted = installation.split("\\.");
            return splitted[0];
        } else {
            return installation;
        }
    }
}
