package com.coon.coon_auto_builder.service;

import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceHealth implements HealthIndicator {

    private HikariPool pool;

    @Autowired
    public DataSourceHealth(DataSource dataSource) {
        pool = (HikariPool) new DirectFieldAccessor(dataSource).getPropertyValue("pool");
    }


    @Override
    public Health health() {
        return Health.up()
                .withDetail("active", pool.getActiveConnections())
                .withDetail("idle", pool.getIdleConnections())
                .withDetail("total", pool.getTotalConnections())
                .withDetail("waiting", pool.getThreadsAwaitingConnection())
                .build();
    }
}
