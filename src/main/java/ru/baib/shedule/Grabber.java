package ru.baib.shedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.baib.model.Post;
import ru.baib.parse.Parse;
import ru.baib.parse.SqlRuParse;
import ru.baib.store.PsqlStore;
import ru.baib.store.Store;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {

    private final Properties cfg = new Properties();

    public Store store() {
        return new PsqlStore(cfg);
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public void setCfg() throws IOException {
        try (InputStream in = Grabber.class.getClassLoader().getResourceAsStream("grabber.properties")) {
            cfg.load(in);
        }
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("store", store);
        dataMap.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(dataMap)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            List<Post> allParsedPosts = new ArrayList<>();
            for (String link: parse.getLinks()) {
                allParsedPosts.addAll(parse.list(link));
            }
            List<String> allLinks = new ArrayList<>();
            List<Post> allReadyInStore = store.getAll();
            for (Post linkInStore: allReadyInStore) {
                allLinks.add(linkInStore.getLink());
            }
            for (Post post: allParsedPosts) {
                if (!allLinks.contains(post.getLink())) {
                    store.save(post);
                    System.out.println("Saved in store");
                } else {
                    System.out.println("Allready in store. No need to save");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Grabber grabber = new Grabber();
        grabber.setCfg();
        Scheduler scheduler = grabber.scheduler();
        Store store = grabber.store();
        grabber.init(new SqlRuParse(), store, scheduler);
    }
}
