package ru.baib;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit2 {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try (InputStream in = AlertRabbit2.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            prop.load(in);
            Class.forName(prop.getProperty("driver-class-name"));
        }  catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try (Connection connection = DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("username"),
                prop.getProperty("password")
        )) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection rabbitConnection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            String add = "insert into rabbit (created_date) values (?)";
            try (PreparedStatement ps = rabbitConnection.prepareStatement(add, Statement.RETURN_GENERATED_KEYS)) {
                ps.setObject(1, LocalDateTime.now());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
