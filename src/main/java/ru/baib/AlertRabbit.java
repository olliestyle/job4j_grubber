package ru.baib;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            prop.load(in);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            /* 1. Конфигурирование
            Начало работы происходит с создания класса управляющего всеми работами.
            В объект Scheduler мы будем добавлять задачи, которые хотим выполнять периодически.*/
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            /* 2. Создание задачи
            quartz каждый раз создает объект с типом org.quartz.Job. Вам нужно создать класс реализующий этот интерфейс.
            Внутри этого класса нужно описать требуемые действия. В нашем случае - это вывод на консоль текста.*/
            JobDetail job = newJob(Rabbit.class).build();
            // 3. Создание расписания
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(prop.getProperty("rabbit.interval")))
                    .repeatForever();
            /* 4. Задача выполняется через триггер
            Здесь можно указать, когда начинать запуск. Мы хотим сделать это сразу.*/
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            // 5. Загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}
